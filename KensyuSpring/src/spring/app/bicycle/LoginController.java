package spring.app.bicycle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("loginName")
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String dspLogin() {
		return "../jsp/Login";
	}
	
	@RequestMapping(value="/LoginAction", method=RequestMethod.POST)
	public String loginCheck(Model model, @ModelAttribute("LoginForm")LoginForm loginForm) {
		String msg = "";
		int ret = loginService.loginCheck(loginForm);
		switch(ret) {
		case -1:	msg = "ユーザー名またはパスワードが違います";
					break;
		case 0:		msg = "パスワードが違います";
					break;
		case 1:		break;
		}
		
		if(msg.equals("")) {
			model.addAttribute("loginName", loginForm.getUserName());
			return "redirect:/dspSearch";
		} else {
			model.addAttribute("msg", msg);
			return "../jsp/Login";
		}
		
		
	}
	
	@GetMapping("/Logout")
	public String logout(Model model, HttpServletRequest request,HttpSession httpSession,SessionStatus sessionStatus) {
		//セッション情報を破棄
		httpSession.removeAttribute("loginName");
		httpSession.invalidate();
		sessionStatus.setComplete();
		return "redirect:/";
	}
	
}
