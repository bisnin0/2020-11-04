package com.bitcamp.webDbcp.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BoardController {
	@Autowired
	BoardDAO dDao;
	
	//게시판 리스트 페이지 컨트롤러
	@RequestMapping("/boardList")
	public ModelAndView boardList() {
		List<BoardVO> list = dDao.getAllRecord();
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.setViewName("board/boardList");
		
		return mav;
	}
	
	//글내용보기
	@RequestMapping("/boardView")
	public ModelAndView boardView(BoardVO vo) {
		//조회수 증가
		dDao.hitCount(vo.getNo());
		//vo에 데이터담기
		dDao.selectBoard(vo);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("vo", vo);
		mav.setViewName("board/boardView");
		
		return mav;
	}
	
	//글쓰기 폼으로 이동
	@RequestMapping("/boardWrite")
	public ModelAndView boardWrite() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/boardWrite");
		//만약 편집기(SKEDITOR)를 쓴다면 css폴더랑 같은 위치에 폴더 만들어서 써야함
		return mav;
	}
	
	//글쓰기 DB에 넣기
	@RequestMapping(value="/boardWriteOk", method=RequestMethod.POST)
	public ModelAndView boardWriteOk(BoardVO vo, HttpServletRequest r) {
		vo.setIp(r.getRemoteAddr());
		HttpSession session = r.getSession();
		
		vo.setUserid((String)session.getAttribute("userid"));
		int result = dDao.writeBoard(vo);

		ModelAndView mav = new ModelAndView();
		
		//글이 등록되면 --> 게시판 리스트
		//글 등록 실패되면 --> 글 등록 페이지
		if(result>0) { //등록된것
			mav.setViewName("redirect:boardList");
		}else { //등록 실패시
			mav.addObject("msg", "글등록");
			mav.setViewName("board/result");
		}
		
		return mav;
	}
	
	//글수정 폼으로가기
	@RequestMapping("/boardEdit")
	public ModelAndView boardEdit(BoardVO vo) {
		dDao.selectBoard(vo);
		ModelAndView mav = new ModelAndView();
		mav.addObject("vo", vo);
		mav.setViewName("board/boardEidt");
		
		return mav;
	}
	
	//글수정하기
	@RequestMapping(value="/boardEditOk", method=RequestMethod.POST)
	public ModelAndView boardEditOk(BoardVO vo) {
		int result = dDao.editBoard(vo);
		ModelAndView mav = new ModelAndView();
		mav.addObject("no", vo.getNo());
		if(result>0) {//수정된것
			mav.setViewName("redirect:boardView");
		}else {
			mav.addObject("msg", "글수정");
			mav.setViewName("board/result");
		}
		
		return mav;
	}
	
	
	//글삭제
	@RequestMapping("/boardDel")
	public ModelAndView boardDelete(@RequestParam("no") int no, HttpServletRequest req) {
		HttpSession session = req.getSession();
		String userid = (String)session.getAttribute("userid");
		
		int result = dDao.deleteBoard(no, userid);
		
		ModelAndView mav = new ModelAndView();
		if(result>0) { //글이 삭제된경우 보드리스트
			mav.setViewName("redirect:boardList");
		}else { //삭제 실패한경우 글내용보기
			mav.addObject("msg", "글삭제");
			mav.setViewName("board/result");
		}
		return mav; 
	}
	
	
}























