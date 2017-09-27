package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import beans.SearchConditionBeans;
import beans.UserBeans;
import common.UtilLogic;

public class UserInfoDao extends DaoUtil {

	/**
	 * ログインIDとパスワードを受け取り、一致するユーザーが存在するかどうかをチェックするメソッド
	 *
	 * @param loginId
	 *            ログインID
	 * @param encPass
	 *            暗号化されたパスワード
	 * @return 一致するユーザーがuserテーブルにいる場合はtrue、いない場合はfalseを返す
	 */
	public static boolean userCheck(String loginId, String encPass) {
		Connection conn = null;
		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			// SELECT文を準備
			String sql = "select * from user where login_id = ? and password = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, loginId);
			pStmt.setString(2, encPass);

			// SELECTを実行し、結果表（ResultSet）を取得
			ResultSet rs = pStmt.executeQuery();

			// 結果表に格納されたレコード数で繰り返し
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// データベース切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * 新規登録が可能かどうかを調べるメソッド
	 *
	 * @param loginId
	 *            ログインID
	 * @param encPass
	 *            暗号化されたパスワード
	 * @param encPassConf
	 *            暗号化されたパスワード(確認)
	 * @param name
	 *            ユーザー名
	 * @param birthDate
	 *            誕生日
	 * @return パスワード以外に未記入がない、かつ、パスワードとパスワード(確認)が一致している、かつ、ログインIDが被っていないときtrueを返す(それ以外はfalse)
	 */
	public static boolean userCheck(String loginId, String password, String passwordConf, String name,
			String birthDate) {
		Connection conn = null;
		if (loginId.length() != 0 && password.length() != 0 && name.length() != 0 && birthDate.length() != 0) {
			if (password.equals(passwordConf)) {
				try {
					// データベースへ接続
					conn = DBManager.getConnection();

					// SELECT文を準備
					String sql = "select * from user";
					PreparedStatement pStmt = conn.prepareStatement(sql);

					// SELECTを実行し、結果表を取得
					ResultSet rs = pStmt.executeQuery();

					// 結果表に格納されたレコード数で繰り返し
					while (rs.next()) {
						if (loginId.equals(rs.getString("login_id"))) {
							return false;
						}
					}
					return true;
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					// データベース切断
					if (conn != null) {
						try {
							conn.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}

				}
			}
		}

		return false;
	}

	/**
	 * userテーブルにある全てのユーザーを取り出すメソッド
	 *
	 * @return userテーブルにある全てのユーザーのリスト(userList)
	 */
	public static List<UserBeans> findAll() {
		Connection conn = null;
		List<UserBeans> userList = new ArrayList<UserBeans>();

		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			// SELECT文を準備
			String sql = "select * from user";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SELECTを実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();

			// 結果表に格納されたレコードの内容を
			// UserInfoインスタンスに設定し、Listインスタンスに追加
			while (rs.next()) {
				UserBeans userInfo = new UserBeans();
				userInfo.setId(rs.getInt("id"));
				userInfo.setLoginId(rs.getString("login_id"));
				userInfo.setName(rs.getString("name"));
				userInfo.setBirthDate(rs.getDate("birth_date"));
				userInfo.setPassword(rs.getString("password"));
				userInfo.setPosition(rs.getString("position"));
				userInfo.setCreateDate(rs.getTimestamp("create_date"));
				userInfo.setUpdateDate(rs.getTimestamp("update_date"));
				userList.add(userInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			// データベース切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return userList;
	}

	/**
	 * userテーブルにあるIDの値がidであるユーザーを取り出すメソッド
	 *
	 * @param id
	 *            ID
	 * @return IDの値がidであるユーザー(UserInfo型)
	 */
	public static UserBeans findAll(int id) {
		Connection conn = null;
		UserBeans userInfo = new UserBeans();

		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			// SELECT文を準備
			String sql = "select * from user where id = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, id);

			// SELECTを実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();

			// 結果表に格納されたレコード数で繰り返し、IDの値がidであるユーザーを探す
			while (rs.next()) {
				userInfo.setId(id);
				userInfo.setLoginId(rs.getString("login_id"));
				userInfo.setName(rs.getString("name"));
				userInfo.setBirthDate(rs.getDate("birth_date"));
				userInfo.setPosition(rs.getString("position"));
				userInfo.setPassword(rs.getString("password"));
				userInfo.setCreateDate(rs.getTimestamp("create_date"));
				userInfo.setUpdateDate(rs.getTimestamp("update_date"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			// データベース切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return userInfo;

	}

	/**
	 * ログインIDと暗号化されたパスワードを受け取り、一致するユーザーを取り出すメソッド
	 *
	 * @param loginId
	 *            ログインID
	 * @param encPass
	 *            暗号化されたパスワード
	 * @return ログインIDと暗号化されたパスワードがそれぞれ、loginIdとencPassであるユーザー(UserInfo型)
	 */
	public static UserBeans findAll(String loginId, String encPass) {
		Connection conn = null;
		UserBeans userInfo = new UserBeans();
		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			// SELECT文を準備
			String sql = "select * from user where login_id = ? and password = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, loginId);
			pStmt.setString(2, encPass);

			// SELECTを実行し、結果表（ResultSet）を取得
			ResultSet rs = pStmt.executeQuery();

			// 結果表に格納されたレコード数で繰り返し、ログインIDと暗号化されたパスワードがそれぞれ、loginIdとencPassであるユーザーを探す
			if (rs.next()) {
				userInfo.setId(rs.getInt("id"));
				userInfo.setLoginId(loginId);
				userInfo.setName(rs.getString("name"));
				userInfo.setBirthDate(rs.getDate("birth_date"));
				userInfo.setPassword(encPass);
				userInfo.setCreateDate(rs.getTimestamp("create_date"));
				userInfo.setUpdateDate(rs.getTimestamp("update_date"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			// データベース切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return userInfo;

	}

	/**
	 * IDとユーザー名と誕生日と更新日を受け取り、ユーザー情報を更新するメソッド
	 *
	 * @param id
	 *            ID
	 * @param name
	 *            ユーザー名
	 * @param birthDate
	 *            誕生日
	 * @param dateStr
	 *            更新日
	 */
	public static void userUpdate(String id, String name, String position, String birthDate, String dateStr) {
		Connection conn = null;

		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			// UPDATE文を準備
			String sql = "UPDATE user SET name ='" + name + "', position ='" + position + "', birth_date ='" + birthDate
					+ "', update_date ='" + dateStr + "' WHERE id = ? ";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, id);

			// UPDATEを実行
			pStmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// データベース切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * IDと暗号化されたパスワードとユーザー名と誕生日と更新日を受け取り、ユーザー情報を更新するメソッド
	 *
	 * @param id
	 *            ID
	 * @param encPass
	 *            暗号化されたパスワード
	 * @param name
	 *            ユーザー名
	 * @param birthDate
	 *            誕生日
	 * @param dateStr
	 *            更新日
	 */
	public static void userUpdate(String id, String encPass, String name, String position, String birthDate,
			String dateStr) {
		Connection conn = null;
		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			// UPDATE文を準備
			String sql = "UPDATE user SET name ='" + name + "', position ='" + position + "', birth_date ='" + birthDate
					+ "', update_date ='" + dateStr + "', password ='" + encPass + "' WHERE id = ? ";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, id);

			// UPDATEを実行
			pStmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// データベース切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * ログインIDと暗号化されたパスワードとユーザー名と誕生日と登録日を受け取り、ユーザー情報を作成するメソッド
	 *
	 * @param loginId
	 *            ログインID
	 * @param encPass
	 *            暗号化されたパスワード
	 * @param name
	 *            ユーザー名
	 * @param birthDate
	 *            誕生日
	 * @param dateStr
	 *            登録日
	 */
	public static void userSet(String loginId, String encPass, String name, String birthDate, String dateStr,
			String position) {
		Connection conn = null;
		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			// INSERT文を準備
			String sql = "INSERT INTO user (login_id, name, position, birth_date, password, create_date, update_date) VALUES ('"
					+ loginId + "' , '" + name + "' , '" + position + "' , '" + birthDate + "' , '" + encPass + "' , '"
					+ dateStr + "' , '" + dateStr + "'  )";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// INSERTを実行
			pStmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// データベース切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * IDを受け取り、一致するユーザーを消去するメソッド
	 *
	 * @param id
	 *            ID
	 */
	public static void userDel(String id) {
		Connection conn = null;
		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			// DELETE文を準備
			String sql = "DELETE FROM user WHERE id =?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, id);

			// DELETEを実行
			pStmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// データベース切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 全てのユーザーを消去するメソッド
	 */
	public static void allUserDel() {
		Connection conn = null;
		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			// DELETE文を準備
			String sql = "DELETE FROM user WHERE id NOT IN ('1')";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// DELETEを実行し、結果表（ResultSet）を取得
			pStmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// データベース切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * * ユーザを検索する(引数に渡された値が空でないもののみ検索する) * @param loginId * @param userName * @param
	 * position * @param birthdayFrom * @param birthdayTo * @return * @throws
	 * SQLException
	 */
	public List<UserBeans> searchUser(String loginId, String userName, String position, String birthdayFrom,
			String birthdayTo, String workSituation) throws SQLException {
		Connection con = DBManager.getConnection();

		String sql1 = "SELECT * FROM user";
		String sql2 = "SELECT login_id FROM work_situation";

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String createDate = sdf.format(date);

		// 各種検索要件を設定してSQLを生成
		List<SearchConditionBeans> conditions1 = new ArrayList<SearchConditionBeans>();
		conditions1.add(new SearchConditionBeans("login_id", loginId, WHERE_TYPE_EQUAL));
		conditions1.add(new SearchConditionBeans("name", userName, WHERE_TYPE_LIKE_PARTIAL_MATCH));
		conditions1.add(new SearchConditionBeans("position", position, WHERE_TYPE_EQUAL));
		conditions1.add(new SearchConditionBeans("birth_date", birthdayFrom, WHERE_TYPE_GENDER_OR_EQUAL));
		conditions1.add(new SearchConditionBeans("birth_date", birthdayTo, WHERE_TYPE_LESS_OR_EQUAL));
		sql1 = addWhereCondition(sql1, conditions1);

		List<SearchConditionBeans> conditions2 = new ArrayList<SearchConditionBeans>();
		conditions2.add(new SearchConditionBeans("create_date", createDate, WHERE_TYPE_EQUAL));
		conditions2.add(new SearchConditionBeans("length( work_situ )", "6", WHERE_TYPE_EQUAL));
		sql2 = addWhereCondition(sql2, conditions2);


		PreparedStatement st1 = con.prepareStatement(sql1);
		ResultSet rs1 = st1.executeQuery();
		PreparedStatement st2 = con.prepareStatement(sql2);
		ResultSet rs2 = st2.executeQuery();

		ArrayList<String> attendUserLoginIdList = new ArrayList<String>();

		while (rs2.next()) {
			String attendUserLoginId = rs2.getString("login_id");
			attendUserLoginIdList.add(attendUserLoginId);
		}

		List<UserBeans> userList = new ArrayList<UserBeans>();

		while (rs1.next()) {
			UserBeans user = new UserBeans();
			user.setId(rs1.getInt("id"));
			user.setLoginId(rs1.getString("login_id"));
			user.setName(rs1.getString("name"));
			user.setPosition(rs1.getString("position"));
			user.setBirthDate(rs1.getDate("birth_date"));
			user.setPassword(rs1.getString("password"));
			user.setCreateDate(rs1.getTimestamp("create_date"));
			user.setCreateDate(rs1.getTimestamp("update_date"));
			if(workSituation.equals("")) {
				userList.add(user);
			}else if(workSituation.equals("勤務中")){
				for(String auli : attendUserLoginIdList) {
					if(auli.equals(user.getLoginId())) {
						userList.add(user);
					}
				}
			}else if(workSituation.equals("帰宅")){
				boolean result = true;
				for(String auli : attendUserLoginIdList) {
					if(auli.equals(user.getLoginId())) {
						result = false;
					}
				}
				if(result) {
					userList.add(user);
				}
			}
		}

		userList = UtilLogic.userListSort(userList);

		return userList;

	}

}
