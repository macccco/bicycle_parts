package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import cnst.Constant;
import dto.BicyclePartsDto;

public class BicyclePartsDao {

	/**
	 * 
	 * @param userId   ユーザーID
	 * @param userPass パスワード
	 */
	public Connection getConnection() throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//-----------------
		// 接続
		//-----------------
		//DBにコネクションするために必要　"jdbc:postgresql://[場所(Domain)]:[ポート番号]/[DB名]"
		Connection conn = DriverManager.getConnection(Constant.JDBC_CONNECTION,
				Constant.JDBC_USER, // ログインロール
				Constant.JDBC_PASS); // パスワード
		//自動コミットオフ
		conn.setAutoCommit(false);

		return conn;
	}

	//コネクションのcloseメソッド
	public void close(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	//全件表示のSELECT文実行メソッド
	public List<BicyclePartsDto> selectAll(HttpServletRequest request) throws Exception {
		Connection conn = getConnection();
		//SELECT文をターゲットにする時に必要構文
		Statement stmt = conn.createStatement();

		List<BicyclePartsDto> list = new ArrayList<>();

		try {

			String sql = "SELECT *  FROM bicycle_parts ORDER BY parts_id";
			System.out.println(sql);
			//sql文をexcuteする
			ResultSet rs = stmt.executeQuery(sql);

			//listにaddしていく
			while (rs.next()) {
				String dateCreate = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(rs.getTimestamp("date_create"));
				Timestamp stamp = rs.getTimestamp("date_update");
				String dateUpdate = "";
				if (stamp != null) {
					dateUpdate = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(stamp);
				}

				list.add(new BicyclePartsDto(rs.getString("parts_id"),
						rs.getString("parts_name"),
						rs.getString("parts_maker"),
						rs.getString("category"),
						rs.getString("type"),
						rs.getString("price"),
						dateCreate,
						dateUpdate,
						rs.getString("pict")));
			}

			//手動コミット
			conn.commit();
			//JDBCのリソース解放
			rs.close();
			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			//SELECT文実行失敗でnullを返す
			return null;
		} finally {
			//コネクションをクローズする
			close(conn);
		}

		return list;

	}
	
	
	//検索時のSELECT文実行メソッド
	public List<BicyclePartsDto> search(HttpServletRequest request, List<String> columns, String ao, String likeSet)
			throws Exception {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		List<BicyclePartsDto> list = new ArrayList<>();
		try {

			String sql = "SELECT *  FROM bicycle_parts";

			int ret = 0;
			//リストcolumnsの要素に一つでも文字列が入っていたらWHERE句を入れる。
			for (String col : columns) {
				if (!col.equals("")) {
					ret = 1;
					break;
				}
			}
			if (ret == 1) {
				sql += " WHERE";
				ret = 0;
			}
			
			String[] cols = { "parts_id", "parts_name", "parts_maker", "category", "type", "price", "price", "date_create", "date_create", "date_update", "date_update"};
			for (int i = 0; i < cols.length; i++) {
				if (!columns.get(i).equals("")) {
					switch (i) {
					case 0:
						sql += " " + cols[i] + " = " + columns.get(i);
						break;
					case 1:
						if (likeSet.equals("あいまい")) {
							sql += " " + cols[i] + " ILIKE '%" + columns.get(i) + "%'";
						} else {
							sql += " " + cols[i] + " = '" + columns.get(i) + "'";
						}
						break;
					case 5:
						if(!columns.get(6).equals("")) {
							sql += " (";
						}
						sql += " " + cols[i] + " >= " + columns.get(i);
						break;
					case 6:
						if(!columns.get(5).equals("") && ao.equals("OR")) {
							sql = sql.replaceAll("OR$", "AND");
						}
						sql += " " + cols[i] + " <= " + columns.get(i);
						if(!columns.get(5).equals("")) {
							sql += " )";
						}
						break;
					case 7:
						if(!columns.get(8).equals("")) {
							sql += " (";
						}
						sql += " " + cols[i] + " >= '" + columns.get(i) + "'";
						break;
					case 8:
						if(!columns.get(7).equals("") && ao.equals("OR")) {
							sql = sql.replaceAll("OR$", "AND");
						}
						sql += " " + cols[i] + " <= '" + columns.get(i) + "'";
						if(!columns.get(7).equals("")) {
							sql += " )";
						}
						break;
					case 9:
						if(!columns.get(10).equals("")) {
							sql += " (";
						}
						sql += " " + cols[i] + " >= '" + columns.get(i) + "'";
						break;
					case 10:
						if(!columns.get(9).equals("") && ao.equals("OR")) {
							sql = sql.replaceAll("OR$", "AND");
						}
						sql += " " + cols[i] + " <= '" + columns.get(i) + "'";
						if(!columns.get(9).equals("")) {
							sql += " )";
						}
						break;
					default:
						sql += " " + cols[i] + " = '" + columns.get(i) + "'";
					}
					ret = 1;
				}
				//switch文でsql文を付け足した場合は、"AND"か"OR"を付け足す
				if (ret == 1) {
					sql += " " + ao;
				}
				ret = 0;
			}
			//末尾の" AND"か" OR"を削除
			if (sql.endsWith(" AND")) {
				sql = sql.substring(0, sql.length() - 4);
			} else if (sql.endsWith(" OR")) {
				sql = sql.substring(0, sql.length() - 3);
			}

			sql += " ORDER BY parts_id";
			System.out.println(sql);

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String dateCreate = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(rs.getTimestamp("date_create"));
				Timestamp stamp = rs.getTimestamp("date_update");
				String dateUpdate = "";
				if (stamp != null) {
					dateUpdate = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(stamp);
				}
				list.add(new BicyclePartsDto(rs.getString("parts_id"),
						rs.getString("parts_name"),
						rs.getString("parts_maker"),
						rs.getString("category"),
						rs.getString("type"),
						rs.getString("price"),
						dateCreate,
						dateUpdate,
						rs.getString("pict")));
			}

			//手動コミット
			conn.commit();
			//JDBCのリソース解放
			rs.close();
			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			//SELECT文実行失敗でnullを返す
			return null;
		} finally {
			//コネクションをクローズする
			close(conn);
		}

		return list;

	}
	
	
	//parts_idの最大値に1を足して取得するメソッド
	public String selectNextId(HttpServletRequest request) throws SQLException {
		Connection conn = getConnection();
		//SELECT文をターゲットにする時に必要構文
		Statement stmt = conn.createStatement();
		String nextId = "";

		try {
			String sql = "SELECT MAX(parts_id) + 1 AS nextId FROM bicycle_parts;";
			System.out.println(sql);
			//sql文をexcuteする
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				nextId = rs.getString("nextId");
			}
			//手動コミット
			conn.commit();
			//JDBCのリソース解放
			rs.close();
			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			//SELECT文実行失敗でnullを返す
			return null;
		} finally {
			//コネクションをクローズする
			close(conn);
		}

		return nextId;
	}
	
	
	//DELETE文実行メソッド
	public void delete(HttpServletRequest request, String partsId) throws SQLException {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		//DELETE文を用意
		String sql = "DELETE FROM bicycle_parts WHERE parts_id = " + partsId;
		System.out.println(sql);
		
		try {
			//SQL文を実行
			stmt.executeUpdate(sql);
			//手動コミット
			conn.commit();
			//JDBCのリソース解放
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			//DELETE文の実行に失敗した場合処理をロールバック
			conn.rollback();
		} finally {
			//コネクションをクローズする
			close(conn);
		}
	}
	
	
	//INSERT文実行メソッド
	public void insert(HttpServletRequest request, List<String> columns) throws SQLException {
		Connection conn = getConnection();
		//INSERT文を用意
		String sql = "INSERT INTO bicycle_parts VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		//ステートメントを取得する
		PreparedStatement pstmt = conn.prepareStatement(sql);
		//ステートメントのプレースホルダーに変数を格納する
		pstmt.setInt(1, Integer.parseInt(columns.get(0)));
		pstmt.setString(2, columns.get(1));
		pstmt.setString(3, columns.get(2));
		pstmt.setString(4, columns.get(3));
		pstmt.setString(5, columns.get(4));
		if (columns.get(5).equals("")) {
			pstmt.setObject(6, null);
		} else {
			pstmt.setInt(6, Integer.parseInt(columns.get(5)));
		}
		//INSERTした日付は現在時刻をTimestamp型で取得
		pstmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
		//UPDATEした日付はnullを代入
		pstmt.setTimestamp(8, null);
		pstmt.setString(9, columns.get(6));
		System.out.println(sql);
		
		try {
			//SQL文を実行
			pstmt.executeUpdate();
			//手動コミット
			conn.commit();
			//JDBCのリソース解放
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			//INSERT文の実行に失敗した場合処理をロールバック
			conn.rollback();
		} finally {
			//コネクションをクローズする
			close(conn);
		}
	}
	
	
	//UPDATE文実行メソッド
	public void update(HttpServletRequest request, String partsId, Map<String, String> map) throws SQLException {
		Connection conn = getConnection();
		//UPDATE文を用意
		String sql = "UPDATE bicycle_parts SET";
		//mapに格納されているキーとバリューを"[カラム名] = [値]"というSQL文にして足していく
		for (Entry<String, String> entry : map.entrySet()) {
			if(!entry.getValue().equals("")) {
				if (entry.getKey().equals("price")) {
					sql += " " + entry.getKey() + " = " + entry.getValue() + ",";
				} else {
					sql += " " + entry.getKey() + " = '" + entry.getValue() + "',";
				}
			} else {
				if (entry.getKey().equals("price")) {
					sql += " " + entry.getKey() + " = 0,";
				} else {
					sql += " " + entry.getKey() + " = '',";
				}
			}
		}
		sql += " date_update = '" + new Timestamp(System.currentTimeMillis()).toString() + "'";
		sql += " WHERE parts_id = " + partsId;
		System.out.println(sql);
		//ステートメントを取得する
		Statement stmt = conn.createStatement();
		
		try {
			//SQL文を実行
			stmt.executeUpdate(sql);
			//手動コミット
			conn.commit();
			//JDBCのリソース解放
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			//UPDATE文の実行に失敗した場合処理をロールバック
			conn.rollback();
		} finally {
			//コネクションをクローズする
			close(conn);
		}
	}

}
