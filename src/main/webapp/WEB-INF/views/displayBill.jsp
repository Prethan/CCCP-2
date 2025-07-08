<%@ page import="model.Bill" %>
<%@ page import="java.util.List" %>
<%@ include file="/WEB-INF/views/navbar.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Check if the user is logged in
    if (session == null || session.getAttribute("user") == null) {
        // Redirect to login if not logged in
        response.sendRedirect("login.jsp");
        return;
    }
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bill Summary</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .container {
            max-width: 600px;
            margin: auto;
            border: 1px solid #ddd;
            padding: 20px;
            border-radius: 5px;
        }
        h2 {
            text-align: center;
        }
        .bill-items {
            margin-top: 20px;
        }
        .bill-items div {
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Bill Summary</h2>
    <%
        Bill bill = (Bill) request.getAttribute("bill");
        if (bill != null) {
    %>
    <p><strong>Bill ID:</strong> <%= bill.getBillId() %></p>
    <p><strong>Bill Date:</strong> <%= bill.getBillDate() %></p>

    <div class="bill-items">
        <h3>Items:</h3>
        <%
            for (Bill.BillItem item : bill.getBillItems()) {
        %>
        <div>
            <strong>Item:</strong> <%= item.getItemName() %> |
            <strong>Quantity:</strong> <%= item.getQuantity() %> |
            <strong>Unit Price:</strong> <%= item.getUnitPrice() %> |
            <strong>Total:</strong> <%= item.getTotalPrice() %>
        </div>
        <%
            }
        %>
    </div>

    <h3>Total: <%= bill.getTotalPrice() %></h3>
    <h3>Cash Tendered: <%= bill.getCashTendered() %></h3>
    <h3>Change: <%= bill.getChangeAmount() %></h3>
    <%
    } else {
    %>
    <p>No bill available.</p>
    <%
        }
    %>
</div>
</body>
</html>
