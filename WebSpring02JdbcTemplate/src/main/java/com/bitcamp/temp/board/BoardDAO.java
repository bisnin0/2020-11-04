package com.bitcamp.temp.board;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.bitcamp.temp.Constants;

public class BoardDAO {
	public JdbcTemplate template;
	public BoardDAO() {
		template = Constants.jdbcTemplate;
	}
	/* PreparedStatementSetter()를 이용해서 세팅하는방법.
	public int insertBoard(final BoardVO vo) {
		int cnt=0;
		try {
			String sql ="insert into freeboard(no, userid, subject, content, ip) values(a_sq.nextval, ?, ?, ?, ?)";
			
			cnt = template.update(sql, new PreparedStatementSetter() {
				
				@Override //익명의내부클래스. 위에 new PreparedStatementSetter()를 컨트롤 스페이스바로 지정해서 만들면 자동작성됨. 오버라이딩
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, vo.getUserid());
					ps.setString(2, vo.getSubject()); //vo 빨간줄 이유 = 새로운 하나의 클래스가 만들어진건데 내부클래스에서 쓰고있는 변수는 final변수로 선언해줘야한다.
					ps.setString(3, vo.getContent());
					ps.setString(4, vo.getIp());
				}
			}); //새로운 하나의 클래스가 만들어진건데 내부클래스에서 쓰고있는 변수는 final변수로 선언해줘야한다.
		}catch(Exception e) {e.printStackTrace();}
		return cnt;
	}*/
	
	public int insertBoard(BoardVO vo) {
		String sql ="insert into freeboard(no, userid, subject, content, ip) values(a_sq.nextval,?,?,?,?)";
		return template.update(sql, vo.getUserid(), vo.getSubject(), vo.getContent(), vo.getIp());
	}
	
	public List<BoardVO> selectAll() {
		String sql ="select no, subject, userid, hit, to_char(writedate, 'MM-DD HH:MI') writedate "
				+ "from freeboard order by no desc"; //jdbc에선 반드시 별명이있어야하고 원래 필드명으로 해야한다.
		
		return template.query(sql, new BeanPropertyRowMapper<BoardVO>(BoardVO.class)); //이렇게하면 list로 리턴
		
		
	}
}
