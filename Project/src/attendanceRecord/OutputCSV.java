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
import common.CSVFileWrite;
import common.UtillLogic;
import dao.UserInfoDao;

/**
 * Servlet implementation class OutputCSV
 */
@WebServlet("/OutputCSV")
public class OutputCSV extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OutputCSV() {
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

		int year = UtillLogic.yearAndMonthToYear(yearAndMonth);
		int month = UtillLogic.yearAndMonthToMonth(yearAndMonth);


		List<UserBeans> userList = new ArrayList<UserBeans>();
		userList = UserInfoDao.findAll();

		CSVFileWrite.main(userList, year, month);

		// UserListへリダイレクト
		response.sendRedirect("UserList");
	}

}
