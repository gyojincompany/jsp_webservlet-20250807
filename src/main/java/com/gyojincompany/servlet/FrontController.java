package com.gyojincompany.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

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
		
		if(command.equals("/loginOk.do")) {
			String mid = request.getParameter("mid");
			String mpw = request.getParameter("mpw");
			
			if(mid.equals("tiger") && mpw.equals("12345")) { //참이면 로그인 성공
				HttpSession session = request.getSession(); //세션을 선언(생성)				
				session.setAttribute("sid", mid); //세션에 id값 올리기->로그인 상태로 변경
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
		}
		
		//response.sendRedirect(viewPage); -> 이동시킬때 request 객체 포함 안됨
		
		//viewPage에 저장된 jsp 페이지로 이동시킬때 request 객체를 포함해서 이동 시키는 방법
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
		
		
		
	}

}
