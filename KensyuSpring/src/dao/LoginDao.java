package dao;

import dto.LoginDto;

public interface LoginDao {

	public LoginDto loginSelect(String userName);
}
