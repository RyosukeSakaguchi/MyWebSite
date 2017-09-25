package attendanceRecord;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UserBeans;
import common.UtillLogic;

/**
 * Servlet implementation class LoginScreen
 */
@WebServlet("/LoginScreen")
public class LoginScreen extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginScreen() {
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
		UserBeans loginUser = (UserBeans) session.getAttribute("loginUser");

		// セッションにログイン情報があるかないかで分岐
		if (loginUser == null) {
			// セッションにログイン情報がないときはloginScreen.jspへフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/loginScreen.jsp");
			dispatcher.forward(request, response);
		}else if(loginUser.getId() == 1){
			// ログインユーザーが管理者のとき、UserListへリダイレクト
			response.sendRedirect("UserList");
		}else {
			// ログインユーザーが一般ユーザーのとき、workSituationRegistrationへリダイレクト
			response.sendRedirect("WorkSituationRegistration");
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// リクエストパラメータの文字コードを指定
		request.setCharacterEncoding("UTF-8");

		// リクエストパラメータの取得
		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");

		//暗号化されたパスワードを生成
		String encPass = null;
		try {
			encPass = UtillLogic.encrpt(password);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		//ログインができるかできないかを判断
		if (!dao.UserInfoDao.userCheck(loginId, encPass)) {
			request.setAttribute("errMsg", "ログインIDまたはパスワードが異なります。");
			request.setAttribute("loginId", loginId);
			// loginScreen.jspへフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/loginScreen.jsp");
			dispatcher.forward(request, response);
		} else {
			// セッションスコープに保存するインスタンス(JavaBeans)の生成
			UserBeans userInfo = new UserBeans();

			// ユーザーを探し出し、userInfoに代入
			userInfo = dao.UserInfoDao.findAll(loginId, encPass);

			// HttpSessionインスタンスの取得
			HttpSession session = request.getSession();

			// セッションスコープにインスタスを保存
			session.setAttribute("loginUser", userInfo);

			//管理者か一般ユーザーかで分岐
			if(userInfo.getId() == 1) {
				// UserListへリダイレクト
				response.sendRedirect("UserList");
			}else {
				// WorkSituationRegistrationへリダイレクト
				response.sendRedirect("WorkSituationRegistration");
			}
		}
	}
}
