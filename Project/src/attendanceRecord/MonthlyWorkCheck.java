package attendanceRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UserBeans;
import beans.WorkSituationBeans;
import common.UtilLogic;
import dao.UserInfoDao;
import dao.WorkSituationDao;

/**
 * Servlet implementation class MonthlyWorkCheck
 */
@WebServlet("/MonthlyWorkCheck")
public class MonthlyWorkCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MonthlyWorkCheck() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// HttpSessionインスタンスの取得
		HttpSession session = request.getSession();

		// セッションにログイン情報があるかないかで分岐
		if ((UserBeans) session.getAttribute("loginUser") == null) {
			// LoginScreenへリダイレクト
			response.sendRedirect("LoginScreen");
		} else {
			// リクエストパラメータの取得
			int id = Integer.parseInt(request.getParameter("id"));
			int year = Integer.parseInt(request.getParameter("year"));
			int month = Integer.parseInt(request.getParameter("month"));

			// パラメータidに対応するUserBeans型のuserInfoインスタンスとユーザーの名前をリクエストスコープに保存
			UserBeans userInfo = new UserBeans();
			userInfo = UserInfoDao.findAll(id);
			request.setAttribute("userInfo", userInfo);
			request.setAttribute("name", userInfo.getName());

			// 勤務状況のリストを取得し、workSituationListインスタンスをリクエストスコープに保存
			String loginId = userInfo.getLoginId();
			List<WorkSituationBeans> workSituationList = new ArrayList<WorkSituationBeans>();
			workSituationList = WorkSituationDao.findAll(loginId, year, month);
			request.setAttribute("workSituationList", workSituationList);

			// ユーザーの総勤務時間と総残業時間を取得し、リクエストスコープに保存
			String titalWorkTime = UtilLogic.totalWorkTime(workSituationList);
			String titalOvertime = UtilLogic.totalOvertime(workSituationList);
			request.setAttribute("titalWorkTime",  titalWorkTime);
			request.setAttribute("titalOvertime",  titalOvertime);


			//リクエストスコープにパラメータを保存
			request.setAttribute("year", year);
			request.setAttribute("month", month);

			// monthlyWorkCheck.jspへフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/monthlyWorkCheck.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
