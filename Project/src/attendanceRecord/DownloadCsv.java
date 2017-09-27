package attendanceRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.UserBeans;
import beans.WorkSituationBeans;
import common.CsvFileWrite;
import common.UtilLogic;
import dao.UserInfoDao;
import dao.WorkSituationDao;

/**
 * Servlet implementation class OutputCSV
 */
@WebServlet("/OutputCSV")
public class DownloadCsv extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DownloadCsv() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String yearAndMonth = request.getParameter("yearAndMonth");
		String dateString = request.getParameter("date");

		if (yearAndMonth != null) {

			if (yearAndMonth.replaceAll("-", "") == "") {

				request.setAttribute("salaryErrMsg", "入力に誤りがあります");

				// UserListへリダイレクト
				UserList userList = new UserList();
				userList.doGet(request, response);
				return;
			}

			int year = UtilLogic.yearAndMonthToYear(yearAndMonth);
			int month = UtilLogic.yearAndMonthToMonth(yearAndMonth);

			List<UserBeans> userList = new ArrayList<UserBeans>();
			userList = UserInfoDao.findAll();

			CsvFileWrite.getSalary(response, userList, year, month);

			// UserListへリダイレクト
			response.sendRedirect("UserList");
			return;
		} else if (dateString == null) {
			int id = Integer.parseInt(request.getParameter("id"));
			int year = Integer.parseInt(request.getParameter("year"));
			int month = Integer.parseInt(request.getParameter("month"));

			UserBeans userInfo = new UserBeans();
			userInfo = UserInfoDao.findAll(id);

			List<WorkSituationBeans> workSituationList = new ArrayList<WorkSituationBeans>();
			workSituationList = WorkSituationDao.findAll(userInfo.getLoginId(), year, month);
			request.setAttribute("workSituationList", workSituationList);

			String titalWorkTime = UtilLogic.totalWorkTime(workSituationList);
			String titalOvertime = UtilLogic.totalOvertime(workSituationList);

			CsvFileWrite.getMonthlyWorkSituation(response, workSituationList, userInfo.getName(), year, month, titalWorkTime,
					titalOvertime);

			// MonyhlyWorkCheckへリダイレクト
			response.sendRedirect("MonthlyWorkCheck?id=" + id + "&year=" + year + "&month=" + month);
			return;

		} else {
			int id = Integer.parseInt(request.getParameter("id"));
			int year = Integer.parseInt(request.getParameter("year"));
			int month = Integer.parseInt(request.getParameter("month"));
			int date = Integer.parseInt(dateString);

			UserBeans userInfo = new UserBeans();
			userInfo = UserInfoDao.findAll(id);

			List<WorkSituationBeans> workSituationList = new ArrayList<WorkSituationBeans>();
			workSituationList = WorkSituationDao.findAll(userInfo.getLoginId(), year, month, date);
			request.setAttribute("workSituationList", workSituationList);

			CsvFileWrite.getDailyWorkSituation(response, workSituationList, userInfo.getName(), year, month, date);

			// DailyWorkCheckへリダイレクト
			response.sendRedirect("DailyWorkCheck?id=" + id + "&year=" + year + "&month=" + month + "&date=" + date);
			return;
		}
	}

}
