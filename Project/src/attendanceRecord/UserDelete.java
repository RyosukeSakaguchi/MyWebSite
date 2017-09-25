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
import dao.UserInfoDao;
import dao.WorkSituationDao;
import dao.WorkSituationEditDao;

/**
 * Servlet implementation class UserDelete
 */
@WebServlet("/UserDelete")
public class UserDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserDelete() {
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
		} else if (request.getParameter("id") == null) {
			String delListId[] = request.getParameterValues("delListId[]");
			if (delListId == null) {
				// チェックをつけていないメッセージをリクエストスコープに保存
				request.setAttribute("noCheckMsg", "未選択です。");

				// UserListのdoGetメソッドを実行
				UserList userList = new UserList();
				userList.doGet(request, response);
				return;

			} else {

				UserBeans[] userList = new UserBeans[delListId.length];

				for (int i = 0; i < delListId.length; i++) {
					userList[i] = new UserBeans();
					userList[i] = UserInfoDao.findAll(Integer.parseInt(delListId[i]));
				}

				request.setAttribute("userList[]", userList);

				// userDelete.jspへフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/userDelete.jsp");
				dispatcher.forward(request, response);
				return;
			}
		} else {
			// リクエストパラメータの取得
			int id = Integer.parseInt(request.getParameter("id"));

			// リクエストスコープに保存するインスタンス(JavaBeans)の生成
			UserBeans userInfo = new UserBeans();

			// ユーザーを探してuserInfoに代入
			userInfo = UserInfoDao.findAll(id);
			request.setAttribute("userInfo", userInfo);

			// userDelete.jspへフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/userDelete.jsp");
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

		// リクエストスコープにパラメーターがあるかで分岐
		if (id == null) {
			String idList[] = request.getParameterValues("idList[]");
			for (int i = 0; i < idList.length; i++) {
				UserBeans userInfo = new UserBeans();
				userInfo = UserInfoDao.findAll(Integer.parseInt(idList[i]));
				WorkSituationDao.userSituDel(userInfo.getLoginId());
				WorkSituationEditDao.userSituEditDel(userInfo.getLoginId());
				UserInfoDao.userDel(idList[i]);
			}

			// ユーザー消去成功のメッセージをリクエストスコープに保存
			request.setAttribute("sucMsg", "ユーザー情報の削除に成功しました。");

			// UserListのdoGetメソッドを実行
			UserList userList = new UserList();
			userList.doGet(request, response);
			return;
		} else {
			UserBeans userInfo = new UserBeans();
			userInfo = UserInfoDao.findAll(Integer.parseInt(id));
			WorkSituationDao.userSituDel(userInfo.getLoginId());
			WorkSituationEditDao.userSituEditDel(userInfo.getLoginId());

			// ユーザーを消去するメソッドを実行
			UserInfoDao.userDel(id);

			// ユーザー消去成功のメッセージをリクエストスコープに保存
			request.setAttribute("sucMsg", "ユーザー情報の削除に成功しました。");

			// UserListのdoGetメソッドを実行
			UserList userList = new UserList();
			userList.doGet(request, response);
		}
	}

}
