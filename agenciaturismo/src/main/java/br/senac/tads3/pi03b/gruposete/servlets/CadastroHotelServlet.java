package br.senac.tads3.pi03b.gruposete.servlets;

import br.senac.tads3.pi03b.gruposete.dao.HotelDAO;
import br.senac.tads3.pi03b.gruposete.models.Hotel;
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

@WebServlet(name = "hotel", urlPatterns = {"/hotel"})
public class CadastroHotelServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/Cadastrar/CadastroHotel.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        boolean erro = false;

        String nome_hotel = request.getParameter("nome_hotel");
        if (nome_hotel == null || nome_hotel.length() < 1) {
            erro = true;
            request.setAttribute("erroNome_hotel", true);
        }
        String data_entrada = request.getParameter("data_entrada");
        if (data_entrada == null || !"  /  /    ".equals(data_entrada)) {
            erro = true;
            request.setAttribute("erroData_entrada", true);
        }
        String data_saida = request.getParameter("data_saida");
        if (data_saida == null || !"  /  /    ".equals(data_saida)) {
            erro = true;
            request.setAttribute("erroData_saida", true);
        }
        String quantidade_quartos = request.getParameter("quantidade_quartos");
        if (quantidade_quartos == null || quantidade_quartos.length() < 1) {
            erro = true;
            request.setAttribute("erroQuantidade_quartos", true);
        }
        String quantidade_hospedes = request.getParameter("quantidade_hospedes");
        if (quantidade_hospedes == null || quantidade_hospedes.length() < 1) {
            erro = true;
            request.setAttribute("erroQuantidade_hospedes", true);
        }
        double preco = Double.parseDouble(request.getParameter("preco"));
        if (preco < 0) {
            erro = true;
            request.setAttribute("erroPreco", true);
        }

        if (!erro) {
            Hotel hotel = new Hotel(nome_hotel, data_entrada, data_saida, quantidade_quartos, quantidade_hospedes, preco, true);
            try {
                HotelDAO dao = new HotelDAO();
                dao.inserir(hotel);
                HttpSession sessao = request.getSession();
                sessao.setAttribute("novoHotel", hotel);
                response.sendRedirect("index.html");

            } catch (Exception ex) {
                Logger.getLogger(CadastroHotelServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("entrada.jsp");
            dispatcher.forward(request, response);
        }
    }
}
