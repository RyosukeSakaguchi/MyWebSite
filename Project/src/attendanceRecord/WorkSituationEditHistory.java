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
import beans.WorkSituationEditBeans;
import dao.UserInfoDao;
import dao.WorkSituationEditDao;

/**
 * Servlet implementation class WorkSituationEditHistory
 */
@WebServlet("/WorkSituationEditHistory")
public class WorkSituationEditHistory extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WorkSituationEditHistory() {
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
			String disp = request.getParameter("disp");

			// 5件表示なのか20件表示なのかはresultで区別し、resultをリクエストパラメータに保存
			boolean result = true;
			if(disp == null) {
				result = false;
				request.setAttribute("dispMsg", "(最新5件)");
			}else {
				request.setAttribute("dispMsg", "(最新20件)");
			}
			request.setAttribute("result", result);

			// パラメータidに対応するUserBeans型のuserInfoインスタンスとユーザーの名前をリクエストスコープに保存
			UserBeans userInfo = new UserBeans();
			userInfo = UserInfoDao.findAll(id);
			request.setAttribute("userInfo", userInfo);
			request.setAttribute("name", userInfo.getName());


			// 勤務状況のリストを取得し、workSituationListインスタンスをリクエストスコープに保存
			String loginId = userInfo.getLoginId();
			List<WorkSituationEditBeans> workSituationEditList = new ArrayList<WorkSituationEditBeans>();
			workSituationEditList = WorkSituationEditDao.findAll(loginId);
			request.setAttribute("workSituationEditList", workSituationEditList);

			// workSituationEditHistory.jspへフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/workSituationEditHistory.jsp");
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
