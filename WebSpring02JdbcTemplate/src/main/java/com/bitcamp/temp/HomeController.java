package com.bitcamp.temp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	public JdbcTemplate jdbcTemplate; //이걸 JdbxTemplate로 만드는 방법 = 게터세터 생성.. 세터에 어노테이션 세팅
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	// servlet-context.xml의 jdbcTemplate bean 객체를(세팅한거를)대입시키는 어노테이션.(게터세터에 넣으면 그렇단거) 
	@Autowired								//이 변수 이름 게터세터 생성할때 주의할것. servlet-context.xml에 세팅한 name과 같아야함
	public void setJdbcTemplate(JdbcTemplate Template) { //이 매개변수가 servlet-context.xml에서 세팅한 beans:bean name="template"를 가져온다
		this.jdbcTemplate = Template;
		Constants.jdbcTemplate = Template; 
		
		//홈페이지로 들어오면 이 객체가 자동으로 실행되고 여기있는 매개변수는 servlet-context.xml에 있는 name을 가져오고 세팅한다.
	}


	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {

		
		return "home";
	}
	
}
