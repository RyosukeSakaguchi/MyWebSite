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
import common.UtilLogic;
import dao.UserInfoDao;

/**
 * Servlet implementation class UserList
 */
@WebServlet("/UserList")
public class UserList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserList() {
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
		}else {
			int userNumberPerPage = 5;
			if(request.getParameter("pageNumber") != null) {
				List<UserBeans> userList = new ArrayList<UserBeans>();;
				int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
				String userIdList[] = request.getParameterValues("userIdList[]");
				for(int i = 0; i < userIdList.length; i++) {
					UserBeans userInfo = UserInfoDao.findAll(Integer.parseInt(userIdList[i]));
					userList.add(userInfo);
				}
				int totalPageNumber = UtilLogic.getTotalPageNumber(userList, userNumberPerPage);
				request.setAttribute("userNumberPerPage", userNumberPerPage);
				request.setAttribute("totalPageNumber", totalPageNumber);
				request.setAttribute("pageNumber", pageNumber);
				request.setAttribute("userList", userList);
			}else {
				// userテーブルにある全てのユーザーを取り出す
				List<UserBeans> userList = UserInfoDao.findAll();
				userList = UtilLogic.userListSort(userList);
				int totalPageNumber = UtilLogic.getTotalPageNumber(userList, userNumberPerPage);
				int pageNumber = 1;
				request.setAttribute("userNumberPerPage", userNumberPerPage);
				request.setAttribute("totalPageNumber", totalPageNumber);
				request.setAttribute("pageNumber", pageNumber);
				request.setAttribute("userList", userList);

		}
			// userList.jspへフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/userList.jsp");
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

		try {
			request.setCharacterEncoding("UTF-8");

			UserInfoDao userInfoDao = new UserInfoDao();

			List<UserBeans> userList = userInfoDao.searchUser(
				request.getParameter("login_id"),
				request.getParameter("name"),
				request.getParameter("position"),
				request.getParameter("birth_date_from"),
				request.getParameter("birth_date_to"),
				request.getParameter("workSituation")
			);

			int userNumberPerPage = 5;
			int totalPageNumber = UtilLogic.getTotalPageNumber(userList, userNumberPerPage);
			int pageNumber = 1;


			// リクエストパラメータを保存
			request.setAttribute("userList", userList);
			request.setAttribute("userNumberPerPage", userNumberPerPage);
			request.setAttribute("totalPageNumber", totalPageNumber);
			request.setAttribute("pageNumber", pageNumber);
			request.setAttribute("login_id", request.getParameter("login_id"));
			request.setAttribute("name", request.getParameter("name"));
			request.setAttribute("position", request.getParameter("position"));
			request.setAttribute("birth_date_from", request.getParameter("birth_date_from"));
			request.setAttribute("birth_date_to", request.getParameter("birth_date_to"));
			request.setAttribute("workSituation", request.getParameter("workSituation"));


			request.getRequestDispatcher("jsp/userList.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
