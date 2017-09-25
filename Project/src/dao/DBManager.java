package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
	private static String url = "jdbc:mysql://localhost/attendanceRecord";
    private static String user = "root";
    private static String pass = "password";

    /**
     * DBへ接続するコネクションを返す
     */
    public static Connection getConnection() {
    		Connection con = null;
        try {
        		Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url,user,pass);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        return con;
    }


}
