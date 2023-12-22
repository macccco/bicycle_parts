package spring.app.bicycle;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import dao.LoginDao;
import dto.LoginDto;

@Repository
public class LoginDaoImpl implements LoginDao{
	
	//JdbcTemplateクラスをAutowiredでDI
	@Autowired
	private JdbcTemplate jdbcTemplate;

	//特定の１行だけ取得
	@Override
	public LoginDto loginSelect(String userName) {
		String sql = "SELECT * FROM m_user WHERE user_name = ?";
		LoginDto dto = null;
		try {
			dto = jdbcTemplate.queryForObject(
					sql,
					new RowMapper<LoginDto>() {
						public LoginDto mapRow(ResultSet rs, int rowNum) {
							LoginDto dto = null;
							try {
								dto = new LoginDto(
										rs.getString("user_name"),
										rs.getString("user_pass")
										);
							} catch (SQLException e) {
								e.printStackTrace();
							}
							return dto;
						}
					},
					userName
					);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return dto;
	}
}
