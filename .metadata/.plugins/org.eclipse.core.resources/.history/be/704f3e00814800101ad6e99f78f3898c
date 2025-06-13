package controller;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Called;
import model.ModelException;
import model.User;
import model.dao.CalledDAO;
import model.dao.DAOFactory;

@WebServlet(urlPatterns = { "/calleds", "/called/form", "/called/insert", "/called/delete", "/called/update" })
public class CalledController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = req.getRequestURI();

		switch (action) {
		case "/crud-manager/called/form": {
			CommonsController.listUsers(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-called.jsp");
			break;
		}
		case "/crud-manager/called/update": {
			String idStr = req.getParameter("calledId");
			int idCalled = Integer.parseInt(idStr);

			CalledDAO dao = DAOFactory.createDAO(CalledDAO.class);

			Called called = null;
			try {
				called = dao.findById(idCalled);
			} catch (ModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			CommonsController.listUsers(req);
			req.setAttribute("action", "update");
			req.setAttribute("called", called);
			ControllerUtil.forward(req, resp, "/form-called.jsp");
			break;
		}
		default:
			listCalleds(req);

			ControllerUtil.transferSessionMessagesToRequest(req);

			ControllerUtil.forward(req, resp, "/calleds.jsp");
		}
	}

	private void listCalleds(HttpServletRequest req) {
		CalledDAO dao = DAOFactory.createDAO(CalledDAO.class);

		List<Called> calleds = null;
		try {
			calleds = dao.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}

		if (calleds != null)
			req.setAttribute("calleds", calleds);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = req.getRequestURI();

		switch (action) {
		case "/crud-manager/called/insert": {
			insertCalled(req, resp);
			break;
		}
		case "/crud-manager/called/delete": {

			deleteCalled(req, resp);

			break;
		}

		case "/crud-manager/called/update": {
			updateCalled(req, resp);
			break;
		}

		default:
			System.out.println("URL inválida " + action);
		}

		ControllerUtil.redirect(resp, req.getContextPath() + "/calleds");
	}

	private void updateCalled(HttpServletRequest req, HttpServletResponse resp) {
		String calledIdStr = req.getParameter("calledId");
		String calledTitle = req.getParameter("titulo");
		String descricao = req.getParameter("descricao");
		String dataAbertura = req.getParameter("dataAbertura");
		String status = req.getParameter("status");
		Integer userId = Integer.parseInt(req.getParameter("user"));

		Called called = new Called(Integer.parseInt(calledIdStr));
		called.setTitulo(calledTitle);
		called.setDescricao(descricao);
		called.setDataAbertura(ControllerUtil.formatDate(dataAbertura));
		called.setStatus(status);
		called.setUser(new User(userId));

		CalledDAO dao = DAOFactory.createDAO(CalledDAO.class);

		try {
			if (dao.update(called)) {
				ControllerUtil.sucessMessage(req, "Chamado '" + called.getTitulo() + "' atualizada com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Chamado '" + called.getTitulo() + "' não pode ser atualizada.");
			}
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void deleteCalled(HttpServletRequest req, HttpServletResponse resp) {
		String calledIdParameter = req.getParameter("id");

		int calledId = Integer.parseInt(calledIdParameter);

		CalledDAO dao = DAOFactory.createDAO(CalledDAO.class);

		try {
			Called called = dao.findById(calledId);

			if (called == null)
				throw new ModelException("Chamado não encontrada para deleção.");

			if (dao.delete(called)) {
				ControllerUtil.sucessMessage(req, "Chamado '" + called.getTitulo() + "' deletada com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Chamado" + called.getTitulo() + "' não pode ser deletado. "
						+ "Há dados relacionados à chamado.");
			}
		} catch (ModelException e) {
			// log no servidor
			if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
				ControllerUtil.errorMessage(req, e.getMessage());
			}
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void insertCalled(HttpServletRequest req, HttpServletResponse resp) {
		String calledTitulo = req.getParameter("titulo");
		String descricao = req.getParameter("descricao");
		String dataAbertura = req.getParameter("dataAbertura");
		String status = req.getParameter("status");
		Integer userId = Integer.parseInt(req.getParameter("user"));

		Called call = new Called();
		call.setTitulo(calledTitulo);
		call.setDescricao(descricao);
		call.setDataAbertura(ControllerUtil.formatDate(dataAbertura));
		call.setStatus(status);
		call.setUser(new User(userId));

		CalledDAO dao = DAOFactory.createDAO(CalledDAO.class);

		try {
			if (dao.save(call)) {
				ControllerUtil.sucessMessage(req, "Chamado '" + call.getTitulo() + "' salva com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Chamado '" + call.getTitulo() + "' não pode ser salva.");
			}
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

}
