package controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelException;
import model.Post;
import model.User;
import model.dao.DAOFactory;
import model.dao.PostDAO;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/post", "/post/form", "/post/delete", "/post/insert", "/post/update" })
public class PostsController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();

		switch (action) {
		case "/crud-manager/post/form": {
			CommonsController.listUsers(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-post.jsp");
			break;
		}
		case "/crud-manager/post/update": {
			CommonsController.listUsers(req);
			Post p = loadPost(req);
			req.setAttribute("post", p);
			req.setAttribute("action", "update");
			ControllerUtil.forward(req, resp, "/form-post.jsp");
			break;
		}
		case "/crud-manager/post": { 
			listPosts(req);
			ControllerUtil.transferSessionMessagesToRequest(req);
			ControllerUtil.forward(req, resp, "/post.jsp");
			break;
		}
		default:
			System.out.println("URL inválida ou ação não especificada para PostsController: " + action);
			ControllerUtil.redirect(resp, req.getContextPath() + "/");
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();

		if (action == null || action.equals("")) {
			ControllerUtil.forward(req, resp, "/index.jsp");
			return;
		}

		switch (action) {
		case "/crud-manager/post/delete":
			deletePost(req, resp);
			break;
		case "/crud-manager/post/insert": {
			insertPost(req, resp);
			break;
		}
		case "/crud-manager/post/update": {
			updatePost(req, resp);
			break;
		}
		default:
			System.out.println("URL inválida " + action);
		}

		ControllerUtil.redirect(resp, req.getContextPath() + "/post");
	}

	private Post loadPost(HttpServletRequest req) {
		String postIdParameter = req.getParameter("postId");

		int postId = Integer.parseInt(postIdParameter);

		PostDAO dao = DAOFactory.createDAO(PostDAO.class);

		try {
			Post p = dao.findById(postId);

			if (p == null)
				throw new ModelException("Post não encontrado para alteração");

			return p;
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}

		return null;
	}

	private void updatePost(HttpServletRequest req, HttpServletResponse resp) {
		String postContent = req.getParameter("content");
		Integer userId = Integer.parseInt(req.getParameter("user"));

		Post post = loadPost(req);
		post.setContent(postContent);
		post.setUser(new User(userId));

		PostDAO dao = DAOFactory.createDAO(PostDAO.class);

		try {
			if (dao.update(post)) {
				ControllerUtil.sucessMessage(req, "Post '" + post.getContent() + "' atualizado com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Post '" + post.getContent() + "' não pode ser atualizado.");
			}
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void insertPost(HttpServletRequest req, HttpServletResponse resp) {
		String postContent = req.getParameter("content");
		Integer userId = Integer.parseInt(req.getParameter("user"));

		Post post = new Post();
		post.setContent(postContent);
		post.setPostDate(Calendar.getInstance().getTime());
		post.setUser(new User(userId));

		PostDAO dao = DAOFactory.createDAO(PostDAO.class);

		try {
			if (dao.save(post)) {
				ControllerUtil.sucessMessage(req, "Post '" + post.getContent() + "' salvo com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Post '" + post.getContent() + "' não pode ser salvo.");
			}

		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void deletePost(HttpServletRequest req, HttpServletResponse resp) {
		String postIdParameter = req.getParameter("id");

		int postId = Integer.parseInt(postIdParameter);

		PostDAO dao = DAOFactory.createDAO(PostDAO.class);

		try {
			Post p = dao.findById(postId);

			if (p == null)
				throw new ModelException("Post não encontrado para deleção");

			if (dao.delete(p)) {
				ControllerUtil.sucessMessage(req, "Post '" + p.getContent() + "' deletado com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Post '" + p.getContent() + "' não pode ser deletado.");
			}
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void listPosts(HttpServletRequest req) {
		PostDAO dao = DAOFactory.createDAO(PostDAO.class);

		List<Post> posts = null;
		try {
			posts = dao.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}

		if (posts != null)
			req.setAttribute("posts", posts);
	}
}