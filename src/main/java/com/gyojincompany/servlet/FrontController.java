package com.gyojincompany.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gyojincompany.dto.BoardDto;

/**
 * Servlet implementation class FrontController
 */
@WebServlet("*.do")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FrontController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}
	
	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request->mid, mpw
		request.setCharacterEncoding("utf-8");
		
		//StringBuffer url = request.getRequestURL();
		String uri = request.getRequestURI(); //클라이언트가 요청한 URI
		//System.out.println(uri); // /jsp_webServlet-20250807/loginOk.do
		String con = request.getContextPath(); //컨텍스트 패스 가져오기
		//System.out.println(con); // /jsp_webServlet-20250807
		//uri - con = loginOk.do
		String command = uri.substring(con.length()); //컨텍스트 패스의 길이 인덱스 부터 끝까지 추출
		System.out.println("클라이언트 요청 : " + command); // /loginOk.do
		
		String viewPage = ""; //실제 클라이언트에게 전송될 jsp파일의 이름이 저장될 변수
		HttpSession session = null;
		
		if(command.equals("/loginOk.do")) {
			String mid = request.getParameter("mid");
			String mpw = request.getParameter("mpw");
			
			if(mid.equals("tiger") && mpw.equals("12345")) { //참이면 로그인 성공
				session = request.getSession(); //세션을 선언(생성)				
				session.setAttribute("sid", mid); //세션에 id값 올리기->로그인 상태로 변경
				System.out.println(session.getAttribute("sid")); //세션 값 확인
				// response.sendRedirect("welcom.jsp"); 
				request.setAttribute("mid", mid);
				viewPage = "welcome.jsp";
			} else { //로그인 실패
				System.out.println("로그인 실패!!");
				
				request.setAttribute("errorMsg", "아이디 또는 비밀번호가 잘못되었습니다. 다시 확인하세요.");
				viewPage = "login.jsp";
			}
		} else if(command.equals("/login.do")) { //login.jsp로 이동
			viewPage = "login.jsp";
		} else if(command.equals("/welcome.do")) { //login.jsp로 이동
			viewPage = "welcome.jsp";
		} else if(command.equals("/logout.do")) {
			session = request.getSession(); //세션을 선언(생성)
			session.invalidate(); //세션 삭제->로그 아웃
			request.setAttribute("errorMsg", "로그아웃하신 후에 다시 로그인하시려면, 아이디 비번을 입력하세요.");
			viewPage = "login.jsp";
		} else if(command.equals("/freeboard.do")) { //게시판 보여달라는 요청
			//게시판 더미 데이터로 만들기
			List<BoardDto> boardList = new ArrayList<BoardDto>();
			
			boardList.add(new BoardDto("오늘 날씨가 덥습니다!!", "이순신", "2025-08-01"));
			boardList.add(new BoardDto("오늘 가입했어요. 목요일이네요.", "김유신", "2025-08-02"));
			boardList.add(new BoardDto("저는 고려의 장군입니다. 반갑습니다.", "강감찬", "2025-08-03"));
			boardList.add(new BoardDto("벌써 8월입니다. 건강유의하세요.", "홍길동", "2025-08-04"));
			boardList.add(new BoardDto("오늘 배운 프론트컨트롤러 패턴 꼭 기억하세요!", "선생님", "2025-08-05"));
			
			request.setAttribute("boardList", boardList); //request 객체에 게시판 목록 싣기
			
			viewPage = "boardList.jsp";
		}
		
		//response.sendRedirect(viewPage); -> 이동시킬때 request 객체 포함 안됨
		
		//viewPage에 저장된 jsp 페이지로 이동시킬때 request 객체를 포함해서 이동 시키는 방법
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
		
		
		
	}

}
