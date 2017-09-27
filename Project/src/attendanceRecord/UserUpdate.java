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
import common.UtilLogic;
import dao.UserInfoDao;

/**
 * Servlet implementation class UserUpdate
 */
@WebServlet("/UserUpdate")
public class UserUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserUpdate() {
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
			userInfo = UserInfoDao.findAll(id);
			request.setAttribute("userInfo", userInfo);

			// userUpdate.jspへフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/userUpdate.jsp");
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
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String passwordConf = request.getParameter("passwordConf");
		String name = request.getParameter("name");
		String birthDate = request.getParameter("birthDate");
		String position = request.getParameter("position");


		// 現在時刻を読みやすい文字列形式でdateStrに代入
		Date date = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = f.format(date);

		// 暗号化されたパスワードとパスワード(確認)を生成
		String encPass = null;
		String encPassConf = null;
		try {
			encPass = UtilLogic.encrpt(password);
			encPassConf = UtilLogic.encrpt(passwordConf);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}


		// if:入力項目に未入力があるかないかで分岐
		if (name.length() == 0 || birthDate.length() == 0) {
			request.setAttribute("errMsg", "未入力の箇所があります。");
			doGet(request, response);
			return;
		}

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

		if(dateTime - birthTime < 0) {
			request.setAttribute("errMsg", "生年月日が正しくありません。");
			doGet(request, response);
			return;
		}


		// パスワードとパスワード(確認)が同じであるか、もし同じ時にパスワードは未入力であるかで分岐
		if (!encPass.equals(encPassConf)) {
			request.setAttribute("errMsg", "パスワードとパスワード(確認)が一致しません。");
			doGet(request, response);
			return;
		} else if (password.length() == 0) {
			// ユーザー情報を更新
			dao.UserInfoDao.userUpdate(id, name, position, birthDate, dateStr);
		} else {
			// ユーザー情報を更新
			dao.UserInfoDao.userUpdate(id, encPass, name, position, birthDate, dateStr);
		}

		// HttpSessionインスタンスの取得
		HttpSession session = request.getSession();

		UserBeans loginUser = (UserBeans) session.getAttribute("loginUser");

		if(loginUser.getId() == 1) {
			// UserListへリダイレクト
			response.sendRedirect("UserList");
		}else {
			// WorkSituationRegistrationへリダイレクト
			response.sendRedirect("WorkSituationRegistration");
		}

	}

}
