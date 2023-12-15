package spring.app.bicycle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowMapper;

import dto.BicyclePartsDto;

public class BicycleRowMapper implements RowMapper<BicyclePartsDto>{
	
	@Override
	public BicyclePartsDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		String dateCreate = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(rs.getTimestamp("date_create"));	//Timestamp型からString型へ変換
		Timestamp stamp = rs.getTimestamp("date_update");
		String dateUpdate = "";
		if (stamp != null) {
			dateUpdate = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(stamp);	//Timestamp型からString型へ変換
		}
		BicyclePartsDto dto = new BicyclePartsDto(
				rs.getString("parts_id"),
				rs.getString("parts_name"),
				rs.getString("parts_maker"),
				rs.getString("category"),
				rs.getString("type"),
				rs.getString("price"),
				dateCreate,
				dateUpdate,
				rs.getString("pict")
				);
		
		return dto;
	}
}
