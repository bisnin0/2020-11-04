package com.bitcamp.temp.register;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegisterController {
	@RequestMapping("/login")
	public String login() {
		
		return "register/login";
	}
	//로그인
	@RequestMapping(value="/loginOk", method=RequestMethod.POST)
	public String loginOk(RegisterVO vo, HttpServletRequest request) {
		
		RegisterDAO dao = new RegisterDAO(); //객체를 만드는 순간 DAO 생성자에 있는 template=Constants.jdbcTemplate;가 실행되고 jdbc객체가 DAO클래스의 template에 세팅된다.

		RegisterVO resultVO = dao.login(vo); //이름을 담아서 가져올거라서 VO를 리턴받는다.
		//		null은 로그인 실패  , null이 아닌경우 로그인 성공
		
		if(resultVO == null) { //로그인 실패
			return "register/login";
		}else { //로그인 성공
			HttpSession session = request.getSession();
			session.setAttribute("logId", resultVO.getUserid());
			session.setAttribute("logName", resultVO.getUsername());
			session.setAttribute("logStatus", "Y");
			return "home";
		}
	}
	//로그아웃
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		
		return "home";
	}
}
