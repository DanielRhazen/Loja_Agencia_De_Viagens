package br.senac.tads3.pi03b.gruposete.servlets;

import br.senac.tads3.pi03b.gruposete.dao.VooDAO;
import br.senac.tads3.pi03b.gruposete.models.Voo;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Voo", urlPatterns = {"/voo"})
public class VooServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("voo.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean erro = false;

        String origem = request.getParameter("origem");
        if (origem == null || origem.length() < 1) {
            erro = true;
            request.setAttribute("erroOrigem", true);
        }
        String destino = request.getParameter("destino");
        if (destino == null || destino.length() < 1) {
            erro = true;
            request.setAttribute("erroDestino", true);
        }
        String data_ida = request.getParameter("dataIda");
        if (data_ida == null || !"  /  /    ".equals(data_ida)) {
            erro = true;
            request.setAttribute("erroData_ida", true);
        }
        String data_volta = request.getParameter("dataVolta");
        if (data_volta == null || !"  /  /    ".equals(data_volta)) {
            erro = true;
            request.setAttribute("erroData_volta", true);
        }
        int quantidade_passagens = Integer.parseInt(request.getParameter("quantidade"));
        if (destino == null || destino.length() < 1) {
            erro = true;
            request.setAttribute("erroDestino", true);
        }

        if (!erro) {
            Voo voo = new Voo(data_ida, data_volta, destino, origem, quantidade_passagens);
            try {
                VooDAO.inserir(voo);
            } catch (Exception ex) {
                Logger.getLogger(VooServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            HttpSession sessao = request.getSession();
            sessao.setAttribute("novoVoo", voo);
            response.sendRedirect("resultado.jsp");
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("entrada.jsp");
            dispatcher.forward(request, response);
        }

        //sessao.setAttribute("", );
        // Colocar pag de resposta
        response.sendRedirect("index.html");

    }

}
