package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
import model.dao.UserDAO;

@WebServlet(urlPatterns = { "/posts", "/post/save", "/post/update", "/post/delete" , "/post/create"})
public class PostsController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getRequestURI();

        switch (action) {
        case "/facebook/posts": {
            loadPosts(req);
            RequestDispatcher rd = req.getRequestDispatcher("posts.jsp");
            rd.forward(req, resp);
            break;
        }
        case "/facebook/post/save": {
        	String postId = req.getParameter("post_id");
			if (postId != null && !postId.equals(""))
				updatePost(req);
			else insertPost(req);		  
			
			resp.sendRedirect("/facebook/posts");
        	//loadUsers(req);
            //RequestDispatcher rd = req.getRequestDispatcher("/form_post.jsp");
            //rd.forward(req, resp);
            
          
            break;
        }
        case "/facebook/post/update": {
            loadPost(req);
            RequestDispatcher rd = req.getRequestDispatcher("/form_post.jsp");
            rd.forward(req, resp);
            break;
        }
        case "/facebook/post/create": {
            loadUsers(req);
            RequestDispatcher rd = req.getRequestDispatcher("/form_post.jsp");
            rd.forward(req, resp);
            break;
        }
        case "/facebook/post/delete": {
            deletePost(req);
            resp.sendRedirect("/facebook/posts");
            break;
        }
        default:
            throw new IllegalArgumentException("Unexpected value: " + action);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getRequestURI();

        switch (action) {
        case "/facebook/post/save": {
            String postId = req.getParameter("post_id");
            if (postId != null && !postId.equals("")) {
                updatePost(req);
            } else {
                insertPost(req);
            }
            resp.sendRedirect("/facebook/posts");
            break;
        }
        default:
            throw new IllegalArgumentException("Unexpected value: " + action);
        }
    }

    private void deletePost(HttpServletRequest req) {
        String postIdString = req.getParameter("postId");
        int postId = Integer.parseInt(postIdString);

        Post post = new Post(postId);
        PostDAO dao = DAOFactory.createDAO(PostDAO.class);

        try {
            dao.delete(post);
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

    private Post createPost(HttpServletRequest req) {
        String postId = req.getParameter("post_id");
        String postContent = req.getParameter("post_content");
        String userId = req.getParameter("user_id");
        
        UserDAO userDao = DAOFactory.createDAO(UserDAO.class);
        try {
            List<User> usuarios = userDao.listAll();
            req.setAttribute("usuarios", usuarios);
        } catch (ModelException e) {
            e.printStackTrace();
        }
        
        Post post;
        if (postId == null || postId.equals("")) {
            post = new Post();
        } else {
            post = new Post(Integer.parseInt(postId));
        }

        post.setContent(postContent);

        if (userId != null && !userId.equals("")) {
            User user = new User(Integer.parseInt(userId));
            post.setUser(user);
        }

        post.setPostDate(new java.util.Date());
        
        return post;
        
    }

    private void loadPost(HttpServletRequest req) {
        String postIdParameter = req.getParameter("postId");
        int postId = Integer.parseInt(postIdParameter);

        PostDAO dao = DAOFactory.createDAO(PostDAO.class);
        UserDAO userDao = DAOFactory.createDAO(UserDAO.class);

        try {
            Post post = dao.findById(postId);
            if (post == null) throw new ModelException("Post não encontrado");

            List<User> usuarios = userDao.listAll();
            req.setAttribute("post", post);
            req.setAttribute("usuarios", usuarios);

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = sdf.format(post.getPostDate());
            req.setAttribute("formattedPostDate", formattedDate);
        } catch (ModelException e) {
            e.printStackTrace();
        }
    }
    
    private void listUsers(HttpServletRequest req) {
    	UserDAO userDao = DAOFactory.createDAO(UserDAO.class);
    	try {
           
            List<User> usuarios = userDao.listAll();
            req.setAttribute("usuarios", usuarios);

        } catch (ModelException e) {
            e.printStackTrace();
        }
    }

    private void insertPost(HttpServletRequest req) {
        UserDAO userDao = DAOFactory.createDAO(UserDAO.class);
        try {
            List<User> usuarios = userDao.listAll();
            req.setAttribute("usuarios", usuarios);
        } catch (ModelException e) {
            e.printStackTrace();
        }

        Post post = createPost(req);
        PostDAO dao = DAOFactory.createDAO(PostDAO.class);

        try {
            dao.save(post);
        } catch (ModelException e) {
            e.printStackTrace();
        }
    }

    private void loadPosts(HttpServletRequest req) {
        PostDAO dao = DAOFactory.createDAO(PostDAO.class);
        List<Post> posts = null;
        try {
            posts = dao.listAll();
        } catch (ModelException e) {
            e.printStackTrace();
        }

        if (posts != null) {
            req.setAttribute("posts", posts);
        }
    }

    private void loadUsers(HttpServletRequest req) {
        UserDAO userDao = DAOFactory.createDAO(UserDAO.class);
        try {
            List<User> usuarios = userDao.listAll();
            if(usuarios == null || usuarios.isEmpty()) {
                System.out.println("AVISO: Nenhum usuário encontrado no banco!");
            }
            req.setAttribute("usuarios", usuarios);
        } catch (ModelException e) {
            System.err.println("ERRO ao carregar usuários: " + e.getMessage());
            req.setAttribute("error", "Não foi possível carregar a lista de usuários");
        }
    }
}
