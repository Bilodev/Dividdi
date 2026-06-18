package control;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.IndirizzoDAO;
import DAO.OrdineDAO;
import model.DVDInCart;
import model.Indirizzo;
import model.Ordine;
import model.Utente;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet("/checkout")
public class Checkout extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IndirizzoDAO indirizzoDAO = new IndirizzoDAO();
    private OrdineDAO ordineDAO = new OrdineDAO();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            HttpSession session = request.getSession(false);
            request.setAttribute("session", session != null ? session : null);
            request.getRequestDispatcher("/WEB-INF/view/Checkout.jsp").forward(request, response);
        }
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String seqId = UUID.randomUUID().toString();
        int userId = (int) session.getAttribute("utenteId");
        
        ArrayList<DVDInCart> cartList = session != null ? (ArrayList<DVDInCart>) session.getAttribute("cart") : new ArrayList<DVDInCart>();
        
        String nome      = request.getParameter("nome");
        String cognome   = request.getParameter("cognome");
        String indirizzo = request.getParameter("indirizzo");
        String citta     = request.getParameter("citta");
        String paese     = request.getParameter("paese");
        String cap       = request.getParameter("cap");

        try {
            // 1. Prima gli ordini (così seqId esiste in ORDINE)
            if (cartList != null)
        	for (DVDInCart dvd : cartList) {
                ordineDAO.insert(new Ordine(0, seqId, userId, dvd.getId(), dvd.getPrezzo(), null, dvd.getQuantity()));
            }

            // 2. Poi l'indirizzo (foreign key su seqId ora soddisfatta)
            indirizzoDAO.insert(new Indirizzo(0, seqId, userId, nome, cognome, indirizzo, citta, cap, paese));
            if (cartList != null)
            cartList.clear();
            session.setAttribute("cart", cartList);
            response.sendRedirect(request.getContextPath() + "/home");

        } catch (SQLException e) {
            // Ora vedi l'errore reale
            throw new ServletException("Errore DB nel checkout: " + e.getMessage(), e);
        }
    }

}
