package SessionCookieServlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/session")
public class SessionCookieServlet extends HttpServlet {

    private static final String COOKIE_NAME = "usuario";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        Cookie[] cookies = request.getCookies();
        String usuario = null;

        // Procura o cookie 'usuario'
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (COOKIE_NAME.equals(c.getName())) {
                    usuario = c.getValue();
                    break;
                }
            }
        }

        if (usuario == null) {
            usuario = "X";
            Cookie cookie = new Cookie(COOKIE_NAME, usuario);
            cookie.setMaxAge(60 * 60 * 24);
            response.addCookie(cookie);
            out.println("Cookie criado! Bem-vindo, usuário " + usuario + ".");
        } else {
            out.println("Bem-vindo de volta, usuário " + usuario + "!");
        }
    }
}
