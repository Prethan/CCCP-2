<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/navbar.jsp" %>

<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Select Date for Sales Report</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 50px;
      background-color: #f4f4f4;
    }

    .form-container {
      max-width: 400px;
      margin: auto;
      padding: 20px;
      background-color: #fff;
      border-radius: 8px;
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    }

    h2 {
      text-align: center;
      color: #333;
    }

    form {
      display: flex;
      flex-direction: column;
    }

    label {
      margin-bottom: 10px;
      font-weight: bold;
    }

    input[type="date"] {
      padding: 10px;
      margin-bottom: 20px;
      border: 1px solid #ccc;
      border-radius: 4px;
      font-size: 16px;
    }

    input[type="submit"] {
      padding: 10px;
      border: none;
      border-radius: 4px;
      background-color: #28a745;
      color: white;
      font-size: 16px;
      cursor: pointer;
    }

    input[type="submit"]:hover {
      background-color: #218838;
    }
  </style>
</head>
<body>

<div class="form-container">
  <h2>Select a Date for the Daily Sales Report</h2>
  <form action="dailySalesReport" method="GET">
    <label for="salesDate">Select Date:</label>
    <input type="date" id="salesDate" name="salesDate" required>
    <input type="submit" value="Generate Report">
  </form>
</div>

</body>
</html>
