package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ModelException;
import model.User;
import model.dao.DAOFactory;
import model.dao.UserDAO;


@WebServlet(urlPatterns = {"/login", "/logout"})
public class LoginController extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		String userLogin = req.getParameter("user_login");
		String userPW = req.getParameter("user_pw");

		UserDAO dao = DAOFactory.createDAO(UserDAO.class);
		User user = null;

		try {
			user = dao.findByEmail(userLogin); 
			
			if (user != null && user.getSenha().equals(userPW)) { 
				HttpSession session = req.getSession();
				session.setAttribute("usuarioLogado", user); 
				ControllerUtil.sucessMessage(req, "Login realizado com sucesso."); 
				ControllerUtil.redirect(resp, req.getContextPath() + "/"); 
			} else {
				ControllerUtil.errorMessage(req, "Usuário ou senha inválidos!"); 
				ControllerUtil.transferSessionMessagesToRequest(req); 
				ControllerUtil.forward(req, resp, "/login.jsp"); //
			}
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, "Erro ao tentar realizar login: " + e.getMessage()); 
			ControllerUtil.transferSessionMessagesToRequest(req); 
			ControllerUtil.forward(req, resp, "/login.jsp"); 
		}
	}

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		HttpSession session = req.getSession(false);
		
		if (session != null)
			session.invalidate(); 
		
		resp.sendRedirect(req.getContextPath() + "/login.jsp"); 
	}
}