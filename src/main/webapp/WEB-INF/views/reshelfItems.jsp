<%@ page import="model.Item" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/navbar.jsp" %>

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
    <title>Reshelf Items</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f9;
        }

        h2 {
            text-align: center;
            color: #2c3e50;
        }

        .container {
            max-width: 600px;
            margin: auto;
            padding: 20px;
            border: 1px solid #ddd;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        .message {
            color: #27ae60;
            font-weight: bold;
            text-align: center;
        }

        .item-list {
            margin-top: 20px;
        }

        .item {
            padding: 10px;
            border-bottom: 1px solid #eee;
        }

        .item:last-child {
            border-bottom: none;
        }

        .item-code {
            font-weight: bold;
        }

        .item-quantity {
            color: #e74c3c;
        }
    </style>
</head>
<body>
<h2>Reshelf Items</h2>
<div class="container">
    <p class="message">
        <%= request.getAttribute("message") %>
    </p>

    <div class="item-list">
        <%
            List<Item> restockedItems = (List<Item>) request.getAttribute("restockedItems");
            if (restockedItems != null && !restockedItems.isEmpty()) {
                for (Item item : restockedItems) {
        %>
        <div class="item">
            <span class="item-code">Item Code: <%= item.getCode() %></span> -
            <span class="item-quantity"><%= 20 - item.getQuantity() %> units added</span>
        </div>
        <%
            }
        } else {
        %>
        <p>No items were restocked.</p>
        <%
            }
        %>
    </div>
</div>
</body>
</html>
