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
 * Servlet implementation class DoTest
 */
@WebServlet("*.do") //.do 로 끝나는 요청 잡기 
public class DoTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoTest() {
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
		request.setCharacterEncoding("utf-8");
		
		String uri = request.getRequestURI(); //클라이언트가 요청한 url
		//uri -> http://localhost:8888/jsp_webServlet-20250807/login.do
		String con = request.getContextPath();
		//con -> http://localhost:8888/jsp_webServlet-20250807/
		String command = uri.substring(con.length());
		//command -> /login.do
		
		String viewPage = "";
		if (command.equals("/login.do")) {
			System.out.println("login.do 요청 감지!!!");
			viewPage = "login.jsp";
			
		} else if(command.equals("/loginOk.do")) {
			String mid = request.getParameter("mid");
			//System.out.println("login.jsp에서 넘겨 받은 mid값:"+ mid);
			String mpw = request.getParameter("mpw");
			//System.out.println("login.jsp에서 넘겨 받은 mpw값:"+ mpw);
			
			if(mid.equals("tiger") && mpw.equals("12345")) { //참이면 로그인 성공 처리
				//session에 id값을 저장->로그인 성공
				HttpSession session = request.getSession(); //세션 생성
				session.setAttribute("sid", mid); //세션에 id값 올리기
				//response.sendRedirect("welcome.jsp"); //로그인 성공 페이지로 이동
				viewPage = "welcome.jsp";
			} else { //로그인 실패
				//response.sendRedirect("loginFail.jsp"); //로그인 실패 페이지로 이동
				//request.setAttribute("failId", mid); //mid = tiger11(로그인 실패한 id)
				request.setAttribute("errorMsg", "아이디 또는 비밀번호가 잘못 되었습니다. 다시 확인하세요.");
				viewPage = "login.jsp";
			}
//			viewPage = "loginOk.jsp";
		} else if(command.equals("/welcome.do")) {
			System.out.println("welcome.do 요청 감지!!!");
			viewPage = "welcome.jsp";
		}
		//viewPage 값으로 request 객체를 포함해서 이동
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);	
		
	}

}
