package com.bitcamp.webDbcp.board;

import java.util.List;

public interface BoardDaoInterface {
	//전체리스트 구하기
	public List<BoardVO> getAllRecord();
	//글쓰기
	public int writeBoard(BoardVO vo);
	//글수정
	public int editBoard(BoardVO vo);
	//글삭제
	public int deleteBoard(int no, String userid);
	//글선택
	//public BoardVO selectBoard(int no); 둘중 하나 고르면된다.
	public void selectBoard(BoardVO vo);
	//조회수증가
	public void hitCount(int no);
}
