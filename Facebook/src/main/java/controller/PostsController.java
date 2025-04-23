package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
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

@WebServlet(urlPatterns = {
    "/posts",           // listar
    "/post/save",       // salvar ou atualizar
    "/post/update",     // carregar para edição
    "/post/delete"      // excluir
})
public class PostsController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String action = req.getRequestURI();

		switch (action) {
			case "/facebook/posts": {
				loadPosts(req);
				RequestDispatcher rd = req.getRequestDispatcher("posts.jsp");
				rd.forward(req, resp);
				break;
			}
			case "/facebook/post/update": {
				loadPost(req);
				RequestDispatcher rd = req.getRequestDispatcher("form_post.jsp");
				rd.forward(req, resp);
				break;
			}
			case "/facebook/post/delete": {
				deletePost(req);
				resp.sendRedirect("/facebook/posts");
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected action: " + action);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String postId = req.getParameter("post_id");

		if (postId != null && !postId.equals("")) {
			updatePost(req);
		} else {
			insertPost(req);
		}

		resp.sendRedirect("/facebook/posts");
	}

	private void loadPosts(HttpServletRequest req) {
		PostDAO dao = DAOFactory.createDAO(PostDAO.class);

		try {
			List<Post> posts = dao.listAll();
			req.setAttribute("posts", posts);
		} catch (ModelException e) {
			e.printStackTrace();
		}
	}

	private void loadPost(HttpServletRequest req) {
		int postId = Integer.parseInt(req.getParameter("postId"));
		PostDAO dao = DAOFactory.createDAO(PostDAO.class);

		try {
			Post post = dao.findById(postId);
			if (post == null)
				throw new ModelException("Post não encontrado para edição.");
			
			req.setAttribute("post", post);
		} catch (ModelException e) {
			e.printStackTrace();
		}
	}

	private void insertPost(HttpServletRequest req) {
		Post post = createPost(req);

		PostDAO dao = DAOFactory.createDAO(PostDAO.class);

		try {
			dao.save(post);
		} catch (ModelException e) {
			e.printStackTrace();
		}
	}

	private void updatePost(HttpServletRequest req) {
		Post post = createPost(req);

		PostDAO dao = DAOFactory.createDAO(PostDAO.class);

		try {
			dao.update(post);
		} catch (ModelException e) {
			e.printStackTrace();
		}
	}

	private void deletePost(HttpServletRequest req) {
		int postId = Integer.parseInt(req.getParameter("postId"));
		Post post = new Post(postId);

		PostDAO dao = DAOFactory.createDAO(PostDAO.class);

		try {
			dao.delete(post);
		} catch (ModelException e) {
			e.printStackTrace();
		}
	}

	private Post createPost(HttpServletRequest req) {
		String postIdStr = req.getParameter("post_id");
		String content = req.getParameter("post_content");
		String userIdStr = req.getParameter("user_id");

		Post post = postIdStr != null && !postIdStr.equals("")
			? new Post(Integer.parseInt(postIdStr))
			: new Post();

		post.setContent(content);

		if (userIdStr != null && !userIdStr.isEmpty()) {
			User user = new User(Integer.parseInt(userIdStr));
			post.setUser(user);
		}

		post.setPostDate(new java.util.Date()); // atualiza a data automaticamente
		return post;
	}
}
