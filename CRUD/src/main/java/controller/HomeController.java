package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/", "/index" }) // Mapeia para a URL raiz e para /index
public class HomeController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Apenas encaminha a requisição para a página index.jsp
		ControllerUtil.forward(req, resp, "/index.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Para requisições POST para a Home, também encaminha para index.jsp ou trata
		// conforme necessário
		ControllerUtil.forward(req, resp, "/index.jsp");
	}
}