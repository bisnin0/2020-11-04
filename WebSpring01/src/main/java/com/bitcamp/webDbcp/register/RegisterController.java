package com.bitcamp.webDbcp.register;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegisterController {
	//beans객체를 자동으로 대입시킨다.
	@Autowired
	RegisterDAO regDao;
	
	//로그인 폼으로 이동하는 컨트롤러
	@RequestMapping("/login")
	public String login() {
		//		/WEB-INF/views/register/login.jsp
		return "register/login";
	}
	//로그인 
	@RequestMapping(value="/loginOk", method=RequestMethod.POST)
	public ModelAndView loginOk(RegisterVO vo, HttpServletRequest req) {

		regDao.loginCheck(vo);
		
		ModelAndView mav = new ModelAndView();
		
		//로그인 성공 .. 세션에 데이터 기록하기위해 매개변수에 request 추가하고 거기서 세션 구한다.
		if(vo.getLogStatus().equals("Y")) {
			HttpSession session = req.getSession();
			session.setAttribute("userid", vo.getUserid());
			session.setAttribute("username", vo.getUsername());
			session.setAttribute("logStatus", vo.getLogStatus());
			mav.setViewName("redirect:/"); //홈으로이동.. HomeController에 설정한것을 이용.. 뷰로 바로 가지 않고 다른 매핑을 불러서 이동해봄 
											//컨트롤러에서 다른 매핑주소를 호출해서 이동할수도있다.
											//왜냐면 다른페이지로 이동할때 데이터베이스작업을 해서 이동해야할때도있는데 다른 컨트롤러에서 이미 한 작업이면 그걸 이용하기위해서 그 컨트롤러호출
		}else {
			mav.setViewName("redirect:login");
		}
		return mav;
	}  
	
	//로그아웃
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpServletRequest r) {
		HttpSession session = r.getSession();
		session.invalidate();
		
		return "home"; //스트링에는 redirect 못쓴다.
	} 
	
	
}
