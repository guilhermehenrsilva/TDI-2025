package HeadersServlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/headers")
public class HeaderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        out.println("User-Agent: " + request.getHeader("User-Agent"));
        out.println("Referer: " + request.getHeader("Referer"));
        out.println("Accept-Language: " + request.getHeader("Accept-Language"));
        out.println("Accept-Encoding: " + request.getHeader("Accept-Encoding"));
        out.println("IP: " + request.getRemoteAddr());
        out.println("Versão HTTP: " + request.getProtocol());
    }
}
