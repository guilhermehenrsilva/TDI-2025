package StatusServlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/status")
public class StatusCodeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String codeParam = request.getParameter("code");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if (codeParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
            out.println("<h2>Erro: Parâmetro 'code' não fornecido.</h2>");
            return;
        }

        try {
            int code = Integer.parseInt(codeParam);
            response.setStatus(code);

            switch (code) {
                case 200:
                    out.println("<h1>Status 200 - OK</h1><p>Tudo certo por aqui! ✅</p>");
                    break;

                case 404:
                    out.println("<h1>Status 404 - Página Não Encontrada</h1><p>Oops! Nada foi encontrado nesse caminho. ❌</p>");
                    break;

                case 500:
                    out.println("<h1>Status 500 - Erro Interno do Servidor</h1><p>Algo deu errado. 😢</p>");
                    break;

                case 418:
                    out.println("<h1>Status 418 - I'm a teapot</h1><p>🫖 Desculpe, sou apenas um bule de chá!</p>");
                    break;

                default:
                    out.println("<h1>Status " + code + "</h1><p>Esse código foi retornado com sucesso.</p>");
            }

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
            out.println("<h2>Erro: Parâmetro inválido. Use um número como 200, 404, 500 etc.</h2>");
        }
    }
}
