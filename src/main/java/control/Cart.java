	package control;
	
	import javax.servlet.ServletException;
	import javax.servlet.annotation.WebServlet;
	import javax.servlet.http.HttpServlet;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;
	import javax.servlet.http.HttpSession;
	import java.io.IOException;
	import java.io.PrintWriter;
	
	import java.util.ArrayList;
	import model.DVDInCart;
	
	@WebServlet("/cart")
	public class Cart extends HttpServlet {
	    private static final long serialVersionUID = 1L;
	
	    @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        request.getRequestDispatcher("/WEB-INF/view/Cart.jsp").forward(request, response);
	    }
	
	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        PrintWriter out = response.getWriter();
	
	        HttpSession session = request.getSession(false);
	
	        String action = request.getParameter("action");
	        ArrayList<DVDInCart> cartList = (ArrayList<DVDInCart>)session.getAttribute("cart");
	        if (cartList == null) cartList = new ArrayList<>(); 
	
	        
	        if ("add".equals(action)) {
	            int id = Integer.parseInt(request.getParameter("id"));
	            String nome = request.getParameter("nome");
	            int durata = Integer.parseInt(request.getParameter("durata"));
	            String regista = request.getParameter("regista");
	            float prezzo = Float.parseFloat(request.getParameter("prezzo"));
	
	            boolean found = false;
	            for (DVDInCart e : cartList) {
	                if (e.getId() == id) {
	                    e.setQuantity(e.getQuantity() + 1);
	                    found = true;
	                    break;
	                }
	            }
	            if (!found) cartList.add(new DVDInCart(id, nome, durata, regista, prezzo, true, 1));
	 
	        } else if ("remove".equals(action)) {
		        int id = Integer.parseInt(request.getParameter("id"));
		        cartList.removeIf(e -> e.getId() == id);
	        } else if ("updateQuantity".equals(action)) {
		        int id = Integer.parseInt(request.getParameter("id"));
		        int qt = Integer.parseInt(request.getParameter("qt"));
		        for (DVDInCart e: cartList) {
		        	if (e.getId() == id) e.setQuantity(e.getQuantity() + qt);
		        }
	        }
		      else if ("emptyCart".equals(action)) {
		    	  cartList.clear();
		      }
	
	        session.setAttribute("cart", cartList);
	        out.print("{\"success\":true }");
	    }
	}
