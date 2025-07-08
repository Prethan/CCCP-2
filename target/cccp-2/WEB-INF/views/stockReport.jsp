<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<%@ page import="java.util.List" %>--%>
<%--<%@ page import="report.StockReport" %>--%>
<%--<%@ page import="model.StockBatch" %>--%>
<%--<%@ include file="/WEB-INF/views/navbar.jsp" %>--%>

<%--<html lang="en">--%>
<%--<head>--%>
<%--  <meta charset="UTF-8">--%>
<%--  <meta name="viewport" content="width=device-width, initial-scale=1.0">--%>
<%--  <title>Stock Report</title>--%>
<%--  <style>--%>
<%--    body {--%>
<%--      font-family: Arial, sans-serif;--%>
<%--      margin: 20px;--%>
<%--      background-color: #f4f4f4;--%>
<%--      color: #333;--%>
<%--    }--%>

<%--    h2 {--%>
<%--      text-align: center;--%>
<%--      color: #2c3e50;--%>
<%--      margin-bottom: 20px;--%>
<%--    }--%>

<%--    table {--%>
<%--      width: 100%;--%>
<%--      border-collapse: collapse;--%>
<%--      margin-bottom: 20px;--%>
<%--    }--%>

<%--    table, th, td {--%>
<%--      border: 1px solid #ddd;--%>
<%--    }--%>

<%--    th, td {--%>
<%--      padding: 12px;--%>
<%--      text-align: left;--%>
<%--    }--%>

<%--    th {--%>
<%--      background-color: #f4f4f4;--%>
<%--      color: #2c3e50;--%>
<%--    }--%>

<%--    tr:nth-child(even) {--%>
<%--      background-color: #f9f9f9;--%>
<%--    }--%>

<%--    tr:hover {--%>
<%--      background-color: #f1f1f1;--%>
<%--    }--%>

<%--    .container {--%>
<%--      max-width: 900px;--%>
<%--      margin: auto;--%>
<%--      background-color: #fff;--%>
<%--      padding: 20px;--%>
<%--      border-radius: 8px;--%>
<%--      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);--%>
<%--    }--%>
<%--  </style>--%>
<%--</head>--%>
<%--<body>--%>

<%--<div class="container">--%>
<%--  <%--%>
<%--    // Retrieve the stockReports list from the request--%>
<%--    List<StockReport> stockReports = (List<StockReport>) request.getAttribute("stockReports");--%>

<%--    if (stockReports != null && !stockReports.isEmpty()) {--%>
<%--      for (StockReport stockReport : stockReports) {--%>
<%--        List<StockBatch> stockBatches = stockReport.getStockBatches();--%>
<%--        if (stockBatches != null && !stockBatches.isEmpty()) {--%>
<%--  %>--%>
<%--  <!-- Display the purchase date for the first batch -->--%>
<%--  <h2>Stock Report for Batch: <%= stockBatches.get(0).getPurchaseDate() %></h2>--%>
<%--  <table>--%>
<%--    <thead>--%>
<%--    <tr>--%>
<%--      <th>Item Code</th>--%>
<%--      <th>Quantity</th>--%>
<%--      <th>Purchase Date</th>--%>
<%--      <th>Expiry Date</th>--%>
<%--    </tr>--%>
<%--    </thead>--%>
<%--    <tbody>--%>
<%--    <%--%>
<%--      for (StockBatch batch : stockBatches) {--%>
<%--    %>--%>
<%--    <tr>--%>
<%--      <td><%= batch.getItemCode() %></td>--%>
<%--      <td><%= batch.getQuantity() %></td>--%>
<%--      <td><%= batch.getPurchaseDate() %></td>--%>
<%--      <td><%= batch.getExpiryDate() %></td>--%>
<%--    </tr>--%>
<%--    <%--%>
<%--      }--%>
<%--    %>--%>
<%--    </tbody>--%>
<%--  </table>--%>
<%--  <%--%>
<%--      }--%>
<%--    }--%>
<%--  } else {--%>
<%--  %>--%>
<%--  <h2>No Stock Batch Data Available</h2>--%>
<%--  <%--%>
<%--    }--%>
<%--  %>--%>
<%--</div>--%>

<%--</body>--%>
<%--</html>--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="report.StockReport" %>
<%@ page import="model.StockBatch" %>
<%@ include file="/WEB-INF/views/navbar.jsp" %>

<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Stock Report</title>

  <!-- Google Fonts -->
  <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">

  <style>
    body {
      font-family: 'Roboto', sans-serif;
      margin: 0;
      background-color: #f4f6f9;
      color: #333;
    }

    h2 {
      text-align: center;
      color: #2c3e50;
      margin-bottom: 20px;
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

    .no-data {
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
  <%
    // Retrieve the stockReports list from the request
    List<StockReport> stockReports = (List<StockReport>) request.getAttribute("stockReports");

    if (stockReports != null && !stockReports.isEmpty()) {
      for (StockReport stockReport : stockReports) {
        List<StockBatch> stockBatches = stockReport.getStockBatches();
        if (stockBatches != null && !stockBatches.isEmpty()) {
  %>
  <!-- Display the purchase date for the first batch -->
  <h2>Stock Report for Batch: <%= stockBatches.get(0).getPurchaseDate() %></h2>
  <table>
    <thead>
    <tr>
      <th>Item Code</th>
      <th>Quantity</th>
      <th>Purchase Date</th>
      <th>Expiry Date</th>
    </tr>
    </thead>
    <tbody>
    <%
      for (StockBatch batch : stockBatches) {
    %>
    <tr>
      <td><%= batch.getItemCode() %></td>
      <td><%= batch.getQuantity() %></td>
      <td><%= batch.getPurchaseDate() %></td>
      <td><%= batch.getExpiryDate() %></td>
    </tr>
    <%
      }
    %>
    </tbody>
  </table>
  <%
      }
    }
  } else {
  %>
  <h2 class="no-data">No Stock Batch Data Available</h2>
  <%
    }
  %>
</div>

</body>
</html>
