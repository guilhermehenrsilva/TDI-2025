package controller;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException; // Import necessário para tratar a exceção
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Jogos; // Importe a classe Jogos
import model.ModelException;
import model.User; // Importe a classe User para a chave estrangeira
import model.dao.DAOFactory;
import model.dao.JogosDAO; // Importe a interface JogosDAO

@WebServlet(urlPatterns = { "/jogos", "/jogos/form", "/jogos/delete", "/jogos/insert", "/jogos/update" })
public class JogosController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = req.getRequestURI();

		switch (action) {
		case "/crud-manager/jogos/form": {
			CommonsController.listUsers(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-jogos.jsp");
			break;
		}
		
		case "/crud-manager/jogos/update": {
			String idStr = req.getParameter("jogosId");
			int idJogos = Integer.parseInt(idStr);

			JogosDAO dao = DAOFactory.createDAO(JogosDAO.class);

			Jogos jogos = null;
			try {
				jogos = dao.findById(idJogos);
			} catch (ModelException e) {
				e.printStackTrace();
				ControllerUtil.errorMessage(req, "Jogo não encontrado para alteração."); 
			}

			CommonsController.listUsers(req); 
			req.setAttribute("action", "update");
			req.setAttribute("jogos", jogos);
			ControllerUtil.forward(req, resp, "/form-jogos.jsp");
			break;
		}
		default:
			listJogos(req); 

			ControllerUtil.transferSessionMessagesToRequest(req); 

			ControllerUtil.forward(req, resp, "/jogos.jsp"); 
		}
	}

	private void listJogos(HttpServletRequest req) {
		JogosDAO dao = DAOFactory.createDAO(JogosDAO.class);

		List<Jogos> listaDeJogos = null;
		try {
			listaDeJogos = dao.listAll();
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, "Erro ao listar jogos.");
		}

		if (listaDeJogos != null)
			req.setAttribute("jogos", listaDeJogos); 
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = req.getRequestURI();

		switch (action) {
		case "/crud-manager/jogos/insert": {
			insertJogos(req, resp);
			break;
		}
		case "/crud-manager/jogos/delete": {
			deleteJogos(req, resp);
			break;
		}
		case "/crud-manager/jogos/update": {
			updateJogos(req, resp);
			break;
		}
		default:
			System.out.println("URL inválida " + action);
		}

		
		ControllerUtil.redirect(resp, req.getContextPath() + "/jogos");
	}

	private void updateJogos(HttpServletRequest req, HttpServletResponse resp) {
		String jogosIdStr = req.getParameter("jogosId");
		String nome = req.getParameter("nome");
		String genero = req.getParameter("genero");
		String plataforma = req.getParameter("plataforma");
		String anoStr = req.getParameter("ano");
		String precoStr = req.getParameter("preco");
		String desenvolvedora = req.getParameter("desenvolvedora");
		Integer usuarioId = Integer.parseInt(req.getParameter("usuario"));

		
		Jogos jogos = loadJogos(req);
		if (jogos == null) {
			ControllerUtil.errorMessage(req, "Jogo não encontrado para atualização.");
			return;
		}

		
		jogos.setNome(nome);
		jogos.setGenero(genero);
		jogos.setPlataforma(plataforma);
		try {
			jogos.setAno(Integer.parseInt(anoStr));
		} catch (NumberFormatException e) {
			jogos.setAno(0);
			System.err.println("Erro ao converter ano: " + anoStr + " para int.");
		}
		try {
			
			jogos.setPreco(Double.parseDouble(precoStr));
		} catch (NumberFormatException e) {
			jogos.setPreco(0.0); 
			System.err.println("Erro ao converter preco: " + precoStr + " para double.");
		}
		jogos.setDesenvolvedora(desenvolvedora);
		jogos.setUsuario(new User(usuarioId)); 

		JogosDAO dao = DAOFactory.createDAO(JogosDAO.class);

		try {
			if (dao.update(jogos)) {
				ControllerUtil.sucessMessage(req, "Jogo '" + jogos.getNome() + "' atualizado com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Jogo '" + jogos.getNome() + "' não pode ser atualizado.");
			}
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void deleteJogos(HttpServletRequest req, HttpServletResponse resp) {
		String jogosIdParameter = req.getParameter("id");

		int jogosId = Integer.parseInt(jogosIdParameter);

		JogosDAO dao = DAOFactory.createDAO(JogosDAO.class);

		try {
			Jogos jogos = dao.findById(jogosId);

			if (jogos == null)
				throw new ModelException("Jogo não encontrado para deleção.");

			if (dao.delete(jogos)) {
				ControllerUtil.sucessMessage(req, "Jogo '" + jogos.getNome() + "' deletado com sucesso.");
			} else {
				ControllerUtil.errorMessage(req,
						"Jogo '" + jogos.getNome() + "' não pode ser deletado. Há dados relacionados ao jogo.");
			}
		} catch (ModelException e) {
			e.printStackTrace();
			if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
				ControllerUtil.errorMessage(req,
						"Não foi possível deletar o jogo, pois ele está associado a outros registros.");
			} else {
				ControllerUtil.errorMessage(req, e.getMessage());
			}
		}
	}

	private void insertJogos(HttpServletRequest req, HttpServletResponse resp) {
		String nome = req.getParameter("nome");
		String genero = req.getParameter("genero");
		String plataforma = req.getParameter("plataforma");
		String anoStr = req.getParameter("ano");
		String precoStr = req.getParameter("preco");
		String desenvolvedora = req.getParameter("desenvolvedora");
		Integer usuarioId = Integer.parseInt(req.getParameter("usuario"));

		Jogos jogos = new Jogos();
		jogos.setNome(nome);
		jogos.setGenero(genero);
		jogos.setPlataforma(plataforma);
		try {
			jogos.setAno(Integer.parseInt(anoStr));
		} catch (NumberFormatException e) {
			jogos.setAno(0); 
			System.err.println("Erro ao converter ano: " + anoStr + " para int.");
		}
		try {
			jogos.setPreco(Double.parseDouble(precoStr));
		} catch (NumberFormatException e) {
			jogos.setPreco(0.0); 
			System.err.println("Erro ao converter preco: " + precoStr + " para double.");
		}
		jogos.setDesenvolvedora(desenvolvedora);
		jogos.setUsuario(new User(usuarioId)); 

		JogosDAO dao = DAOFactory.createDAO(JogosDAO.class);

		try {
			if (dao.save(jogos)) {
				ControllerUtil.sucessMessage(req, "Jogo '" + jogos.getNome() + "' salvo com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Jogo '" + jogos.getNome() + "' não pode ser salvo.");
			}
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

		private Jogos loadJogos(HttpServletRequest req) {
		String jogosIdParameter = req.getParameter("jogosId");

		if (jogosIdParameter == null || jogosIdParameter.isEmpty()) {
			ControllerUtil.errorMessage(req, "ID do jogo não fornecido.");
			return null;
		}

		try {
			int jogosId = Integer.parseInt(jogosIdParameter);
			JogosDAO dao = DAOFactory.createDAO(JogosDAO.class);
			Jogos jogos = dao.findById(jogosId);

			if (jogos == null) {
				throw new ModelException("Jogo não encontrado para alteração.");
			}
			return jogos;
		} catch (NumberFormatException e) {
			ControllerUtil.errorMessage(req, "ID do jogo inválido.");
			e.printStackTrace();
		} catch (ModelException e) {
			ControllerUtil.errorMessage(req, e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}