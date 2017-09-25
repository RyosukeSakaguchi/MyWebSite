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
import dao.UserInfoDao;
import dao.WorkSituationDao;

/**
 * Servlet implementation class DailyWorkCheck
 */
@WebServlet("/DailyWorkCheck")
public class DailyWorkCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DailyWorkCheck() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			int date = Integer.parseInt(request.getParameter("date"));

			UserBeans userInfo = new UserBeans();
			userInfo = UserInfoDao.findAll(id);
			request.setAttribute("userInfo", userInfo);
			request.setAttribute("name", userInfo.getName());

			String loginId = userInfo.getLoginId();

			List<WorkSituationBeans> workSituationList = new ArrayList<WorkSituationBeans>();
			workSituationList = WorkSituationDao.findAll(loginId, year, month, date);
			request.setAttribute("workSituationList", workSituationList);

			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("date", date);

			// monthlyWorkCheck.jspへフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/dailyWorkCheck.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
