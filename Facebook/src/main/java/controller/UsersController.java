package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelException;
import model.User;
import model.dao.DAOFactory;
import model.dao.UserDAO;

@WebServlet(urlPatterns= {"", "/create"})
public class UsersController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override //doGet serve para pegar uma rota
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String action = req.getRequestURI();
		System.out.println(action);
		switch (action) {
		case "/facebook/": {
			listUsers(req);
			
//			redirecionar para pagina de exibição (index)
			RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
			rd.forward(req, resp);
			break;
		}
		
		case "/facebook/create" : {
			
			// recuperando os parametros da requisição
			String userName = req.getParameter("user_name");
			String userGender = req.getParameter("user_gender");
			String userEmail = req.getParameter("user_email");
			
			//cria e seta os valores do usuario
			User user = new User ();
			user.setName(userName);
			user.setGender(userGender);
			user.setEmail(userEmail);
			
			//salvar o usuario no banco
			UserDAO dao = DAOFactory.createDAO(UserDAO.class);
			try {
				dao.save(user);
			} catch (Exception e) {
				System.err.print("Erro ao salvar o usuario");
				e.printStackTrace();
			}
			
			resp.sendRedirect("/facebook");
			
			break;
		}
		
		
		default:
			throw new IllegalArgumentException("URL não reconhecida: " + action);
		}
		
	}

	private void listUsers(HttpServletRequest req) {
		
		UserDAO dao = DAOFactory.createDAO(UserDAO.class);
		
		List<User> users = new ArrayList<User>();
		
		try {
			users = dao.listAll();
			req.setAttribute("usuarios", users);
		} catch (Exception e) {
				
		e.printStackTrace();
		}
		
	}
	
	private void insertUser(HttpServletRequest req) {
		
		// recuperando os parametros da requisição 
		// imputs (name) do HTML
		String userName = req.getParameter("user_name");
		String userGender = req.getParameter("user_gender");
		String userEmail = req.getParameter("user_email");
		
		
		User user = new User();
		user.setName(userName);
		user.setGender(userGender);
		user.setEmail(userEmail);
	
		
		//salva o usuario no banco 
		
		UserDAO dao = DAOFactory.createDAO(UserDAO.class);
		try {
			dao.save(user);
		} catch (ModelException e) {
			System.err.println("Erro ao salvar usuario");
			e.printStackTrace();
		}
		
	}

}
