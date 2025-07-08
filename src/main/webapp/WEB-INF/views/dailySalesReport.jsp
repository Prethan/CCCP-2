<%@ page import="report.DailySalesReport" %>
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
    <title>Daily Sales Report</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
            color: #333;
        }

        h2 {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 40px;
        }

        .report-container {
            max-width: 900px;
            margin: auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        table th, table td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        table th {
            background-color: #f4f4f4;
            color: #2c3e50;
            font-size: 16px;
            font-weight: bold;
        }

        table tbody tr:nth-child(odd) {
            background-color: #f9f9f9;
        }

        table tbody tr:nth-child(even) {
            background-color: #ffffff;
        }

        table td {
            font-size: 14px;
            color: #555;
        }

        .total-summary {
            text-align: right;
            font-weight: bold;
            font-size: 16px;
            margin-top: 20px;
            color: #333;
        }

        @media (max-width: 600px) {
            .report-container {
                padding: 10px;
            }

            table th, table td {
                padding: 10px 8px;
                font-size: 14px;
            }
        }
    </style>
</head>
<body>
<h2>DAILY SALES REPORT</h2>
<div class="report-container">
    <table>
        <thead>
        <tr>
            <th>Item Name</th>
            <th>Item Code</th>
            <th>Quantity Sold</th>
            <th>Revenue</th>
        </tr>
        </thead>
        <tbody>
        <%
            DailySalesReport dailySalesReport = (DailySalesReport) request.getAttribute("dailySalesReport");
            if (dailySalesReport != null) {
                for (DailySalesReport.SalesSummary summary : dailySalesReport.getSalesSummary().values()) {
        %>
        <tr>
            <td><%= summary.getItemName() %></td>
            <td><%= summary.getItemCode() %></td>
            <td><%= summary.getTotalQuantity() %></td>
            <td>$<%= String.format("%.2f", summary.getTotalRevenue()) %></td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="4" style="text-align: center; color: #e74c3c;">No sales data available for today.</td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>
</body>
</html>
