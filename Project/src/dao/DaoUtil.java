package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import beans.PositionBeans;
import beans.SearchConditionBeans;
import common.UtilLogic;

/**
 * + * Daoに関する共通クラス +
 */
public class DaoUtil {

	// SQLの記号
	/**  */
	public final String SQL_WHERE = "where";
	/** and */
	public final String SQL_AND = "and";
	/** or */
	public final String SQL_OR = "or";

	// 一致条件の一覧。
	/** 完全一致(key = value) */
	public final int WHERE_TYPE_EQUAL = 1;
	/** 部分一致(key like '% value %') */
	public final int WHERE_TYPE_LIKE_PARTIAL_MATCH = 2;
	/** 〜より大きい(key >= value) */
	public final int WHERE_TYPE_GENDER_OR_EQUAL = 3;
	/** 〜より小さい(key <= value) */
	public final int WHERE_TYPE_LESS_OR_EQUAL = 4;
	/** 不一致(key != value) */
	public final int WHERE_TYPE_NOT_EQUAL = 5;

	/**
	 * SearchConditionBeansのリストをもとにwhere条件を足したSQLを返す。
	 *
	 * @param sql
	 * @param conditions
	 * @return
	 */
	public String addWhereCondition(String sql, List<SearchConditionBeans> conditions) {
		StringBuilder resulet = new StringBuilder();
		resulet.append(sql);

		for (SearchConditionBeans condition : conditions) {

			// 値がnullまたは空文字の場合は以降の処理をスキップ
			if (UtilLogic.isEmpty(condition.getSearchValue())) {
				continue;
			}

			// 元のsqlにwhereが含まれていない場合は「where」それ以外は「and」から開始
			if (sql.indexOf(SQL_WHERE) == -1) {
				resulet.append(" " + SQL_WHERE + " ");
			} else {
				if (condition.getisAnd()) {
					resulet.append(" " + SQL_AND + " ");
				} else {
					resulet.append(" " + SQL_OR + " ");
				}

			}

			// 値が数値型かどうかをチェック(文字列の場合シングルクォーテーションで囲む)
			try {
				Integer.parseInt(condition.getSearchValue());
			} catch (NumberFormatException e) {
				// 部分一致検索の場合は%ごと囲むためシングルクォーテーションをつけない
				if (condition.getSearchCondition() != WHERE_TYPE_LIKE_PARTIAL_MATCH) {
					condition.setSearchValue("'" + condition.getSearchValue() + "'");
				}

			}

			// 検索条件ごとのSQLを作成
			switch (condition.getSearchCondition()) {
			case WHERE_TYPE_EQUAL:
				resulet.append(condition.getSearchKey() + " = " + condition.getSearchValue());
				break;
			case WHERE_TYPE_LIKE_PARTIAL_MATCH:
				resulet.append(condition.getSearchKey() + " like " + "'%" + condition.getSearchValue() + "%'");
				break;
			case WHERE_TYPE_GENDER_OR_EQUAL:
				resulet.append(condition.getSearchKey() + " >= " + condition.getSearchValue() + "");
				break;
			case WHERE_TYPE_LESS_OR_EQUAL:
				resulet.append(condition.getSearchKey() + " <= " + condition.getSearchValue() + "");
				break;
			case WHERE_TYPE_NOT_EQUAL:
				resulet.append(condition.getSearchKey() + " != " + condition.getSearchValue());
				break;
			}

			sql = resulet.toString();
		}

		return sql;
	}

	public static Time getTime(int id, String timeName) {
		Connection conn = null;
		Time time = new Time(Calendar.getInstance().getTimeInMillis());

		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			// SELECT文を準備
			String sql = "SELECT " + timeName + " FROM time_master where id='" + id + "'";
			// SELECTを実行し、結果表を取得
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				time = rs.getTime(timeName);
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
		return time;

	}

	public static List<PositionBeans> findAllPosition() {
		Connection conn = null;
		List<PositionBeans> positionList = new ArrayList<PositionBeans>();

		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			// SELECT文を準備
			String sql = "select * from position_master";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SELECTを実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();

			// 結果表に格納されたレコードの内容を
			// UserInfoインスタンスに設定し、Listインスタンスに追加
			while (rs.next()) {
				PositionBeans position = new PositionBeans();
				position.setId(rs.getInt("id"));
				position.setPosition(rs.getString("position"));
				positionList.add(position);
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
		return positionList;

	}

}
