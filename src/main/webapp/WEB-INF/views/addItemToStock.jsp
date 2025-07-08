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
  <title>Add Item to Stock</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 20px;
      background-color: #f9f9f9;
    }

    .container {
      max-width: 600px;
      margin: auto;
      padding: 20px;
      background-color: #fff;
      border-radius: 8px;
      box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    }

    h2 {
      text-align: center;
      color: #2c3e50;
    }

    .form-group {
      margin-bottom: 15px;
    }

    .form-group label {
      display: block;
      margin-bottom: 5px;
    }

    .form-group input {
      width: 100%;
      padding: 8px;
      border: 1px solid #ddd;
      border-radius: 4px;
    }

    .btn {
      width: 100%;
      padding: 10px;
      background-color: #27ae60;
      color: white;
      border: none;
      border-radius: 4px;
      cursor: pointer;
    }

    .btn:hover {
      background-color: #2ecc71;
    }

    .message {
      text-align: center;
      color: #27ae60;
      font-weight: bold;
      margin-top: 20px;
    }
  </style>
</head>
<body>
<div class="container">
  <h2>Add Item to Stock</h2>
  <form action="addItemToStock" method="post">
    <div class="form-group">
      <label for="itemCode">Item Code:</label>
      <input type="text" id="itemCode" name="itemCode" required>
    </div>
    <div class="form-group">
      <label for="quantity">Quantity:</label>
      <input type="number" id="quantity" name="quantity" required>
    </div>
    <div class="form-group">
      <label for="purchaseDate">Purchase Date (yyyy-mm-dd):</label>
      <input type="date" id="purchaseDate" name="purchaseDate" required>
    </div>
    <div class="form-group">
      <label for="expiryDate">Expiry Date (yyyy-mm-dd):</label>
      <input type="date" id="expiryDate" name="expiryDate" required>
    </div>
    <button type="submit" class="btn">Add to Stock</button>
  </form>
  <div class="message">
    <%= request.getAttribute("message") != null ? request.getAttribute("message") : "" %>
  </div>
</div>
</body>
</html>
