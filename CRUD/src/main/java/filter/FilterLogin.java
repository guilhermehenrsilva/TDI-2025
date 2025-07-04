//// Conteúdo de CRUD/src/main/java/filter/FilterLogin.java
//package filter;
//
//import java.io.IOException;
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.*;
//
//@WebFilter("/*") // Este filtro irá interceptar todas as requisições
//public class FilterLogin implements Filter {
//
//    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
//            throws IOException, ServletException {
//        
//        HttpServletRequest request = (HttpServletRequest) req;
//        HttpServletResponse response = (HttpServletResponse) res;
//
//        String uri = request.getRequestURI();
//        String contextPath = request.getContextPath();
//        
//        // Assegura que o 'path' comece com '/' após remover o contextPath
//        String path = uri.substring(contextPath.length()); 
//        if (!path.startsWith("/")) {
//            path = "/" + path;
//        }
//
//        // URLs que não precisam de autenticação
//        // IMPORTANTE: Adicione aqui todas as URLs de recursos estáticos e páginas de login
//        boolean isLoginPage = path.equals("/login.jsp") || path.equals("/login");
//        
//        // Verifique se a URL começa com os prefixos de recursos estáticos
//        boolean isStaticResource = path.startsWith("/css/") || 
//                                   path.startsWith("/js/") || 
//                                   path.startsWith("/images/") ||
//                                   path.startsWith("/fonts/"); // Adicionado /fonts/ caso use fontes do Bootstrap
//
//        // Páginas específicas da Home que devem ser acessíveis sem login
//        boolean isHome = path.equals("/") || path.equals("/index.jsp") || path.equals("/index"); 
//
//        HttpSession session = request.getSession(false); // Não cria uma nova sessão se não existir
//
//        boolean isLoggedIn = (session != null && session.getAttribute("usuarioLogado") != null);
//
//        // A lógica principal do filtro
//        if (isLoggedIn || isLoginPage || isStaticResource || isHome) {
//            chain.doFilter(req, res); // Permite que a requisição prossiga para o recurso/página
//        } else {
//            // Se não está logado e a página não é permitida sem login, redireciona para a página de login
//            response.sendRedirect(contextPath + "/login.jsp");
//        }
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        // Nada de especial para inicializar aqui
//    }
//
//    @Override
//    public void destroy() {
//        // Nada de especial para destruir aqui
//    }
//}