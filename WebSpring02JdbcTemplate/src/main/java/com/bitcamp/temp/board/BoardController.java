package com.bitcamp.temp.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class BoardController {
	@RequestMapping("/boardList")
	public ModelAndView boardList() {
		//레코드 선택
		BoardDAO dao = new BoardDAO();
		List<BoardVO> list= dao.selectAll();
		
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/boardList");
		mav.addObject("list", list);
		return mav;
	}
	//글쓰기이동
	@RequestMapping("/boardWrite")
	public String boardWrite() {
		
		return "board/boardWrite";
	}
	
	@RequestMapping(value="/boardWriteOk", method=RequestMethod.POST)
	public ModelAndView boardWriteOk(BoardVO vo, HttpServletRequest request) {
		//아이디
		HttpSession session = request.getSession();
		vo.setUserid((String)session.getAttribute("logId"));
		//ip
		vo.setIp(request.getRemoteAddr());
		BoardDAO dao = new BoardDAO();
		int result = dao.insertBoard(vo);
		ModelAndView mav = new ModelAndView();
		if(result>0) {
			//레코드가 추가
			mav.setViewName("redirect:boardList");
		}else {
			mav.addObject("msg", "글등록이 실패하였습니다.");
			mav.setViewName("board/resultPage");
		}
		return mav;
	}
}
