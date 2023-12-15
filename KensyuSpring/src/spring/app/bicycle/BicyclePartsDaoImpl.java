package spring.app.bicycle;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import dao.BicyclePartsDao;
import dto.BicyclePartsDto;


@Repository
public class BicyclePartsDaoImpl implements BicyclePartsDao{
	
	//JdbcTemplateクラスをAutowiredでDI
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	//全件検索
	@Override
	public List<BicyclePartsDto> selectAll() throws Exception {
		String sql = "SELECT * FROM bicycle_parts ORDER BY parts_id";
		BicycleRowMapper rowMapper = new BicycleRowMapper();
		List<BicyclePartsDto> list = jdbcTemplate.query(sql, rowMapper);
			
		if(list == null || list.size() == 0) {
			return null;
		}
		return list;
	}
	
	
	
	//検索フォームで検索
	@Override
	public List<BicyclePartsDto> search(List<String> columns, String AorO, String like) throws Exception {
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
					if (like.equals("あいまい")) {
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
					if(!columns.get(5).equals("") && AorO.equals("OR")) {
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
					if(!columns.get(7).equals("") && AorO.equals("OR")) {
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
					if(!columns.get(9).equals("") && AorO.equals("OR")) {
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
				sql += " " + AorO;
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

		BicycleRowMapper rowMapper = new BicycleRowMapper();
		List<BicyclePartsDto> list = jdbcTemplate.query(sql, rowMapper);

		if(list == null || list.size() == 0) {
			return null;
		}
		return list;
	}
	
	
	//特定の１行だけ取得
	@Override
	public BicyclePartsDto selectDto(String partsId) {
		String sql = "SELECT * FROM bicycle_parts WHERE parts_id = ?";
		BicycleRowMapper rowMapper = new BicycleRowMapper();
		BicyclePartsDto dto = jdbcTemplate.queryForObject(sql, rowMapper, Integer.parseInt(partsId));
		return dto;
	}
	
	
	//parts_idの最大値プラス１を取得
	@Override
	public String selectNextId() throws Exception {
		String sql = "SELECT MAX(parts_id) + 1 AS nextId FROM bicycle_parts";
		String nextId = jdbcTemplate.queryForObject(sql, String.class);
		return nextId;
	}
	
	
	//登録
	@Override
	public int insert(List<String> columns) throws Exception {
		String sql = "INSERT INTO bicycle_parts VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		int ret = jdbcTemplate.update(
				sql,
				Integer.parseInt(columns.get(0)),
				columns.get(1),
				columns.get(2),
				columns.get(3),
				columns.get(4),
				columns.get(5).equals("") ? null : Integer.parseInt(columns.get(5)),
				new Timestamp(System.currentTimeMillis()),
				null,
				columns.get(6)
				);
		return ret;
	}
	
	
	//更新
	@Override
	public int update(String partsId, Map<String, String> map) throws Exception {
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
		sql += " WHERE parts_id = ?";
		int ret = jdbcTemplate.update(sql, Integer.parseInt(partsId));
		
		return ret;
	}
	
	
	//削除
	@Override
	public int delete(String partsId) throws Exception {
		String sql = "DELETE FROM bicycle_parts WHERE parts_id = ?";
		int ret = jdbcTemplate.update(sql, Integer.parseInt(partsId));
		
		return ret;
	}
	
	
}
