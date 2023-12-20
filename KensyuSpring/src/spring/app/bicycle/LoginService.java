package spring.app.bicycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.LoginDao;
import dto.LoginDto;

@Service
public class LoginService {

	@Autowired
	private LoginDao loginDao;
	
	public int loginCheck(LoginForm loginForm) {
		LoginDto dto = loginDao.loginSelect(loginForm.getUserName());
		if(dto != null) {
			if(loginForm.getPass().equals(dto.getPass())) {
				return 1;
			} else {
				return 0;
			}
		} else {
			return -1;
		}
	}
}
