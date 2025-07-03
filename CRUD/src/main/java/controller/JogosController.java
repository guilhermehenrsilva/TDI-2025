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
			CommonsController.listUsers(req); // Carrega a lista de usuários para o select
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-jogos.jsp");
			break;
		}
		case "/crud-manager/jogos/update": {
			// Obtém o ID do jogo da requisição
			String idStr = req.getParameter("jogosId");
			int idJogos = Integer.parseInt(idStr);

			JogosDAO dao = DAOFactory.createDAO(JogosDAO.class);

			Jogos jogos = null;
			try {
				jogos = dao.findById(idJogos);
			} catch (ModelException e) {
				e.printStackTrace();
				ControllerUtil.errorMessage(req, "Jogo não encontrado para alteração."); // Mensagem de erro
			}

			CommonsController.listUsers(req); // Carrega a lista de usuários para o select
			req.setAttribute("action", "update");
			req.setAttribute("jogos", jogos); // Adiciona o objeto jogo à requisição
			ControllerUtil.forward(req, resp, "/form-jogos.jsp");
			break;
		}
		default:
			listJogos(req); // Chama o método para listar todos os jogos

			ControllerUtil.transferSessionMessagesToRequest(req); // Transfere mensagens de sucesso/erro

			ControllerUtil.forward(req, resp, "/jogos.jsp"); // Encaminha para a página de listagem
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
			req.setAttribute("jogos", listaDeJogos); // Define o atributo "jogos" para a JSP
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

		// Redireciona para a listagem de jogos após a operação
		ControllerUtil.redirect(resp, req.getContextPath() + "/jogos");
	}

	private void updateJogos(HttpServletRequest req, HttpServletResponse resp) {
		// Parâmetros do formulário
		String jogosIdStr = req.getParameter("jogosId");
		String nome = req.getParameter("nome");
		String genero = req.getParameter("genero");
		String plataforma = req.getParameter("plataforma");
		String anoStr = req.getParameter("ano");
		String precoStr = req.getParameter("preco");
		String desenvolvedora = req.getParameter("desenvolvedora");
		Integer usuarioId = Integer.parseInt(req.getParameter("usuario"));

		// Carrega o objeto Jogo existente
		Jogos jogos = loadJogos(req);
		if (jogos == null) {
			ControllerUtil.errorMessage(req, "Jogo não encontrado para atualização.");
			return;
		}

		// Atualiza os atributos do objeto Jogo
		jogos.setNome(nome);
		jogos.setGenero(genero);
		jogos.setPlataforma(plataforma);
		try {
			jogos.setAno(Integer.parseInt(anoStr));
		} catch (NumberFormatException e) {
			jogos.setAno(0); // Valor padrão em caso de erro de conversão
			System.err.println("Erro ao converter ano: " + anoStr + " para int.");
		}
		try {
			// Conversão de String para double
			jogos.setPreco(Double.parseDouble(precoStr));
		} catch (NumberFormatException e) {
			jogos.setPreco(0.0); // Valor padrão em caso de erro de conversão
			System.err.println("Erro ao converter preco: " + precoStr + " para double.");
		}
		jogos.setDesenvolvedora(desenvolvedora);
		jogos.setUsuario(new User(usuarioId)); // Associa o usuário pelo ID

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
				// Mensagem mais específica se houver dados relacionados (chave estrangeira)
				ControllerUtil.errorMessage(req,
						"Jogo '" + jogos.getNome() + "' não pode ser deletado. Há dados relacionados ao jogo.");
			}
		} catch (ModelException e) {
			e.printStackTrace();
			// Captura exceções de violação de integridade referencial
			if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
				ControllerUtil.errorMessage(req,
						"Não foi possível deletar o jogo, pois ele está associado a outros registros.");
			} else {
				ControllerUtil.errorMessage(req, e.getMessage());
			}
		}
	}

	private void insertJogos(HttpServletRequest req, HttpServletResponse resp) {
		// Parâmetros do formulário
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
			jogos.setAno(0); // Valor padrão em caso de erro de conversão
			System.err.println("Erro ao converter ano: " + anoStr + " para int.");
		}
		try {
			// Conversão de String para double
			jogos.setPreco(Double.parseDouble(precoStr));
		} catch (NumberFormatException e) {
			jogos.setPreco(0.0); // Valor padrão em caso de erro de conversão
			System.err.println("Erro ao converter preco: " + precoStr + " para double.");
		}
		jogos.setDesenvolvedora(desenvolvedora);
		jogos.setUsuario(new User(usuarioId)); // Associa o usuário pelo ID

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

	// Método auxiliar para carregar um objeto Jogos, usado em updateJogos
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