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
 * Servlet implementation class RegistrationComplete
 */
@WebServlet("/RegistrationComplete")
public class RegistrationComplete extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationComplete() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// HttpSessionインスタンスの取得
				HttpSession session = request.getSession();

				// セッションにログイン情報があるかないかで分岐
				if ((UserBeans) session.getAttribute("loginUser") == null) {
					// LoginScreenへリダイレクト
					response.sendRedirect("LoginScreen");
				} else {
					String situation = request.getParameter("situation");
					if(situation.equals("start")) {
						request.setAttribute("scsMsg", "今日も一日頑張りましょー");
					}else {
						request.setAttribute("scsMsg", "今日も一日お疲れ様でした");
					}
					// registrationComplete.jspへフォワード
					RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/registrationComplete.jsp");
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
