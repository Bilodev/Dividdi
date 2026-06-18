<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Carrello</title>
<style>
    table { border-collapse: collapse; width: 100%; }
    th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
    .actions { display: flex; gap: 8px; }
    button { padding: 5px 10px; cursor: pointer; }
</style>
</head>
<body>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.DVDInCart" %>
<%
    javax.servlet.http.HttpSession currentSession = request.getSession(false);
    ArrayList<DVDInCart> cartList = currentSession != null ? (ArrayList<DVDInCart>) currentSession.getAttribute("cart") : new ArrayList<DVDInCart>();
%>
<h1>Il Mio Carrello</h1>
<p><a href="<%= request.getContextPath() %>/home">Torna alla home</a></p>

<% if (cartList == null || cartList.isEmpty()) { %>
    <p>Il carrello è vuoto.</p>
<% } else { %>
    <table>
        <thead>
            <tr>
                <th>Nome</th>
                <th>Durata</th>
                <th>Regista</th> 
                <th>Prezzo</th>                               
                <th>Quantità</th>
                <th>Azioni</th>
            </tr>
        </thead>
        <tbody>
        <% for (int i = 0; i < cartList.size(); i++) { %>
            <tr>
                <td><%= cartList.get(i).getNome() %></td>
                <td><%= cartList.get(i).getDurata() %> min</td>
                <td><%= cartList.get(i).getRegista() %></td>
                <td><%= cartList.get(i).getPrezzo() %>€</td>
				<td>
					<%= cartList.get(i).getQuantity() %> 
					<button onclick="updateCart(<%= cartList.get(i).getId() %>, 1 )">+</button> 
					<% if (cartList.get(i).getQuantity() >= 2){ %>
						<button onclick="updateCart(<%= cartList.get(i).getId() %>, -1 )">-</button> 
					<% } %>
				</td>
                
                <td class="actions">
                    <button onclick="removeFromCart(<%= cartList.get(i).getId() %>)">Rimuovi</button>
                </td>
            </tr>
        <% } %>
        </tbody>
    </table>
<% } %>
	<br> <br>
	<% if (cartList != null && !cartList.isEmpty() && currentSession.getAttribute("nome") != null) { %>
		
		<% float sum = 0; for (int i = 0; i < cartList.size(); i++) sum += cartList.get(i).getPrezzo() * cartList.get(i).getQuantity(); %>
		<form action="<%= request.getContextPath() %>/checkout" method="get" style="display: inline;">
            <button type="submit">Checkout <%= sum %>€</button>
        </form>
		
	<%  } else if (cartList != null && !cartList.isEmpty())  {	%>
		<form action="<%= request.getContextPath() %>/login" method="get" style="display: inline;">
            <button type="submit">LogIn per acquistare</button>
        </form>
	<% } %>
	<% if (cartList != null && !cartList.isEmpty()) {%>
	<button onclick="svuotaCarrello()">Svuota Carrello</button>
	<% } %>
</body>
<script>

function svuotaCarrello(){
    fetch('<%= request.getContextPath() %>/cart', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: 'action=emptyCart'
        })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            location.reload();
        }
    })
    .catch(error => console.error('Errore:', error))
}

function removeFromCart(id) {
    fetch('<%= request.getContextPath() %>/cart', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: 'action=remove&id=' + id
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            location.reload();
        }
    })
    .catch(error => console.error('Errore:', error));
}

function updateCart(id, value) {
    fetch('<%= request.getContextPath() %>/cart', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: 'action=updateQuantity&id=' + id + "&qt=" + value
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            location.reload();
        }
    })
    .catch(error => console.error('Errore:', error));
}
</script>
</html>
