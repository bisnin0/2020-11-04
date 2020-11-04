package com.bitcamp.temp.register;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bitcamp.temp.Constants;

public class RegisterDAO {
	public JdbcTemplate template=null;
	
	public RegisterDAO() {
		template = Constants.jdbcTemplate; //이 생성자를 객체로 생성하는 순간 template가 JdbcTemplate로 세팅된다. 
										   //서버 접속하는순간 Constants.jdbcTemplate가 beans에서 가져온 값으로 세팅되게 되어있으니까
										   //이 세팅은 RegisterController에 세터로 되어있다.
	}
	
	public RegisterVO login(RegisterVO vo) {
		String sql = "select count(userid) cnt from register where userid=? and userpwd=?"; //VO안에 count를 저장할 변수가 있어야한다. 
									// VO에 세팅한 그 값을 별명으로 만든다.cnt 
									//template.queryForObject는 리턴값이 T라서(VO객체) 값을 확인하려면 int 변수가 필요하다.

		//queryForObject : 선택된 레코드가 없을경우 에러발생! 이거 주의해야함. 예외로 처리해도 되긴 한다..
		
																				//결과를 여기에 담아서 리턴		
		RegisterVO vo1 = template.queryForObject(sql, new BeanPropertyRowMapper<RegisterVO>(RegisterVO.class), 
																				vo.getUserid(), vo.getUserpwd());
																			//첫번째 물음표에는 이걸 세팅	두번째 물음표엔 이걸세팅
		
			//////////이렇게 하는 이유는 template.queryForObject로 값을 구할때 값이 없으면 에러가 생기기때문이다.
				/////	그래서 일단 값이 있는지를 count로 확인하고 구하는것
		
		
		if(vo1.getCnt()<=0) { //일치하는 레코드가 없을때
			return null;
		}else {
			String sql2 = "select userid, username from register where userid=? and userpwd=?";
			return template.queryForObject(sql2, new BeanPropertyRowMapper<RegisterVO>(RegisterVO.class), vo.getUserid(), vo.getUserpwd());
		}
	}
}
