package filter;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

@WebFilter("/*") 
public class FilterLogin implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        
        String path = uri.substring(contextPath.length()); 
        if (!path.startsWith("/")) {
            path = "/" + path;
        }

        boolean isLoginPage = path.equals("/login.jsp") || path.equals("/login");
        boolean isStaticResource = path.startsWith("/css/") || 
                                   path.startsWith("/js/") || 
                                   path.startsWith("/images/") ||
                                   path.startsWith("/fonts/"); 

        
        boolean isHome = false; 
        HttpSession session = request.getSession(false); 

        boolean isLoggedIn = (session != null && session.getAttribute("usuarioLogado") != null);

        
        if (isLoggedIn || isLoginPage || isStaticResource) { 
            chain.doFilter(req, res); 
        } else {
            response.sendRedirect(contextPath + "/login.jsp"); 
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}