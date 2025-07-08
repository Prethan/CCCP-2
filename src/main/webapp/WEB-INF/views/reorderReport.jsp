
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Item" %>
<%@ page import="report.ReorderReport" %>
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
  <title>Reorder Report</title>

  <!-- Google Fonts -->
  <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">

  <style>
    body {
      font-family: 'Roboto', sans-serif;
      margin: 0;
      background-color: #f5f6f9;
      color: #333;
    }

    h2 {
      text-align: center;
      color: #2c3e50;
      margin-bottom: 40px;
      font-size: 28px;
      font-weight: 700;
      margin-top: 20px;
    }

    .container {
      max-width: 900px;
      margin: 20px auto;
      padding: 20px;
      background-color: #fff;
      border-radius: 10px;
      box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
      overflow-y: auto;
    }

    table {
      width: 100%;
      border-collapse: collapse;
      margin-bottom: 20px;
    }

    table, th, td {
      border: 1px solid #ddd;
    }

    th, td {
      padding: 12px;
      text-align: left;
      font-size: 16px;
    }

    th {
      background-color: #3498db;
      color: white;
      font-weight: 500;
      font-size: 16px;
    }

    td {
      color: #333;
      font-size: 14px;
    }

    tr:nth-child(even) {
      background-color: #f9f9f9;
    }

    tr:hover {
      background-color: #f1f1f1;
      transform: scale(1.01);
      transition: all 0.3s ease;
    }

    .no-items {
      text-align: center;
      font-size: 18px;
      color: #e74c3c;
    }

    @media (max-width: 600px) {
      .container {
        padding: 15px;
        margin: 20px 10px;
      }

      table, th, td {
        padding: 10px;
        font-size: 14px;
      }

      h2 {
        font-size: 24px;
      }
    }
  </style>
</head>
<body>

<div class="container">
  <h2>Reorder Report</h2>
  <table>
    <thead>
    <tr>
      <th>Item Code</th>
      <th>Item Name</th>
      <th>Price</th>
    </tr>
    </thead>
    <tbody>
    <%
      ReorderReport itemsBelowReorderLevel = (ReorderReport) request.getAttribute("itemsBelowReorderLevel");

      if (itemsBelowReorderLevel != null) {
        for (Item item : itemsBelowReorderLevel.getItemsBelowReorderLevel()) {
    %>
    <tr>
      <td><%= item.getCode() %></td>
      <td><%= item.getName() %></td>
      <td>$<%= String.format("%.2f", item.getPrice()) %></td>
    </tr>
    <%
      }
    } else {
    %>
    <tr>
      <td colspan="3" class="no-items">No items below reorder level.</td>
    </tr>
    <%
      }
    %>
    </tbody>
  </table>
</div>

</body>
</html>
