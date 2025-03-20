package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import model.dao.DAOFactory;
import model.dao.UserDAO;

@WebServlet(urlPatterns = {""})
public class UsersController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req/*requisição*/, HttpServletResponse resp)
							throws ServletException, IOException {
		
			String action = req.getRequestURI();
			System.out.println(action);
			
			switch(action ) {
			case "/facebook":{
				
			//litagem dos usuarios 
				
				listUsers(req);
				resp.sendRedirect("index.jsp");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
}
