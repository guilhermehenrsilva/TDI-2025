//package controller;
//
//import java.io.IOException;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import model.ModelException;
//import model.User;
//import model.dao.DAOFactory;
//import model.dao.UserDAO;
//
//
//@WebServlet(urlPatterns = {"/login", "/logout"})
//public class LoginController extends HttpServlet{
//
//	private static final long serialVersionUID = 1L;
//
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
//			throws ServletException, IOException {
//
//		String userLogin = req.getParameter("user_login");
//		String userPW = req.getParameter("user_pw");
//
//		UserDAO dao = DAOFactory.createDAO(UserDAO.class);
//		User user = null;
//
//		try {
//			user = dao.findByEmail(userLogin);
//		} catch (ModelException e) {
//			e.printStackTrace();
//		}
//	}
//
//	
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
//			throws ServletException, IOException {
//		
//		// O att false indica que não é para criar uma 
//		// sessão caso ela não exista
//		HttpSession session = req.getSession(false);
//		
//		if (session != null)
//			session.invalidate();
//		
//		resp.sendRedirect("/CRUD-Manager/login.jsp");
//	}
//}
