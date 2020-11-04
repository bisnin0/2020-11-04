package com.bitcamp.webDbcp.register;

public interface RegisterDaoInterface {
	//로그인  vo를 받아서 작성하고 리턴 안해도 같은 주소를 쓰기때문에 리턴안해도된다.
	public void loginCheck(RegisterVO vo);
	//회원가입
	public int regInsert(RegisterVO vo);
	
	//회원정보수정
	public int regUpdate(RegisterVO vo);
	
	//아이디 찾기
	public String idSearch(RegisterVO vo);
	
	//비밀번호찾기
	public int passwordUpdate(RegisterVO vo);
	
	//회원탈퇴
	public int regDeleter(RegisterVO vo);
	
}
