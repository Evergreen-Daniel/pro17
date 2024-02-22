package sec02.ex01;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/member/*")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MemberDAO memberDAO;

	public void init() throws ServletException {
		memberDAO = new MemberDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nextPage = null;
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String action = request.getPathInfo();       // url 요청에 따라 아래와 같은 조건에 맞춰 연결해 준다 (servlet 또는 url 통제소 같음)
		
		System.out.println("action:" + action);      // servlet 또는 url 요청이 무엇인지 확인하는 역할
		
		if (action == null || action.equals("/listMembers.do")) {    // listMembers.do 로 연결되는 경우
			List<MemberVO> membersList = memberDAO.listMembers();
			request.setAttribute("membersList", membersList);
			nextPage = "/test02/listMembers.jsp";
			
		} else if (action.equals("/addMember.do")) {                 // addMember.do 로 연결되는 경우
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			MemberVO memberVO = new MemberVO(id, pwd, name, email);
			memberDAO.addMember(memberVO);
			nextPage = "/member/listMembers.do";

		} else if (action.equals("/memberForm.do")) {                   // memberForm.do 로 연결되는 경우
			nextPage = "/test02/memberForm.jsp";
			
		} else {
			List<MemberVO> membersList = memberDAO.listMembers();       // 위의 조건이 전부 아닌경우는 기본적으로 보여주는 페이지
			request.setAttribute("membersList", membersList);
			nextPage = "/member/listMembers.do";
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
		dispatch.forward(request, response);
	}

}