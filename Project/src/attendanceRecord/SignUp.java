package attendanceRecord;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UserBeans;
import common.UtillLogic;
import dao.UserInfoDao;

/**
 * Servlet implementation class SignUp
 */
@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignUp() {
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
			// LoginScreenへリダイレクト
			response.sendRedirect("LoginScreen");
		} else if(loginUser.getId() != 1){
			// LoginScreenへリダイレクト
			response.sendRedirect("WorkSituationRegistration");
		} else {
			// signUp.jspへフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/signUp.jsp");
			dispatcher.forward(request, response);
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
		String passwordConf = request.getParameter("passwordConf");
		String name = request.getParameter("name");
		String birthDate = request.getParameter("birthDate");
		String position = request.getParameter("position");

		// 現在時刻を読みやすい文字列形式でdateStrに代入
		Date date = new Date();
		SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = f1.format(date);

		// 暗号化されたパスワードとパスワード(確認)を生成
		String encPass = null;
		try {
			encPass = UtillLogic.encrpt(password);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		// 入力項目に未入力があるかないかで分岐
		if (!UserInfoDao.userCheck(loginId, password, passwordConf, name, birthDate)) {
			//リクエストパラメーターを保存
			request.setAttribute("errMsg", "入力された内容は正しくありません。");
			request.setAttribute("loginId", loginId);
			request.setAttribute("name", name);
			request.setAttribute("birthDate", birthDate);
			request.setAttribute("position", position);

			// signUp.jspへフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/signUp.jsp");
			dispatcher.forward(request, response);
		} else {
			//誕生日と現在の日付をlong型に変換
			SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd");
			Date birth = null;
			try {
				birth = f2.parse(birthDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			long dateTime = date.getTime();
			long birthTime = birth.getTime();

			//誕生日が現在の日付以前であるかで分岐
			if (dateTime - birthTime < 0) {
				// リクエストパラメーターを保存
				request.setAttribute("errMsg", "生年月日が正しくありません。");
				request.setAttribute("loginId", loginId);
				request.setAttribute("name", name);
				request.setAttribute("birthDate", birthDate);
				request.setAttribute("position", position);

				// signUp.jspへフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/signUp.jsp");
				dispatcher.forward(request, response);
			} else {
				// ユーザー情報をuserテーブルに保存
				UserInfoDao.userSet(loginId, encPass, name, birthDate, dateStr, position);

				// UserListへリダイレクト
				response.sendRedirect("UserList");
			}
		}
	}
}
