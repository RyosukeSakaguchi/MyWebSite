package attendanceRecord;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UserBeans;

/**
 * Servlet implementation class UserDetail
 */
@WebServlet("/UserDetail")
public class UserDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserDetail() {
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

		    // リクエストスコープに保存するインスタンス(JavaBeans)の生成
			UserBeans userInfo = new UserBeans();

			// ユーザーを探してuserInfoに代入
			userInfo = dao.UserInfoDao.findAll(id);
			request.setAttribute("userInfo", userInfo);

			// userDetail.jspへフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/userDetail.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// doGetメソッドを実行
		doGet(request, response);
	}

}
