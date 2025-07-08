<%@ page import="report.BillReport" %>
<%@ page import="model.Bill" %>
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
    <title>Bill Report</title>

    <!-- Google Fonts for better typography -->
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
            margin-bottom: 40px;
            font-size: 28px;
            font-weight: 700;
            margin-top: 20px;
        }

        .bill-container {
            max-width: 900px;
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
            overflow-y: auto;
        }

        .bill-item {
            border-bottom: 1px solid #ddd;
            padding-bottom: 20px;
            margin-bottom: 20px;
            transition: all 0.3s ease;
        }

        .bill-item:last-child {
            border-bottom: none;
        }

        .bill-item:hover {
            background-color: #f9f9f9;
            transform: translateY(-3px);
            box-shadow: 0 3px 8px rgba(0, 0, 0, 0.1);
        }

        .bill-item h3 {
            font-size: 22px;
            margin-bottom: 10px;
            color: #3498db;
            font-weight: 500;
        }

        .bill-details {
            font-size: 16px;
            color: #555;
        }

        .bill-details p {
            margin: 5px 0;
        }

        .bill-details strong {
            color: #2c3e50;
        }

        .item-list {
            margin-top: 20px;
            border: 1px solid #ddd;
            border-radius: 6px;
            background-color: #fafafa;
            padding: 15px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
        }

        .item-list table {
            width: 100%;
            border-collapse: collapse;
        }

        .item-list th, .item-list td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        .item-list th {
            background-color: #3498db;
            color: white;
            font-size: 16px;
        }

        .item-list td {
            font-size: 14px;
            color: #333;
        }

        .total-summary {
            text-align: right;
            font-weight: bold;
            margin-top: 20px;
            font-size: 18px;
            color: #e74c3c;
        }

        .no-bill-message {
            text-align: center;
            font-size: 18px;
            color: #e74c3c;
        }

        @media (max-width: 600px) {
            .bill-container {
                padding: 15px;
                margin: 20px 10px;
            }

            .bill-item h3 {
                font-size: 20px;
            }

            .bill-details {
                font-size: 14px;
            }

            .item-list th, .item-list td {
                padding: 8px;
            }

            h2 {
                font-size: 24px;
            }
        }
    </style>
</head>
<body>

<h2>BILL REPORT</h2>

<div class="bill-container">
    <%
        BillReport billReport = (BillReport) request.getAttribute("billReport");
        if (billReport != null) {
            for (Bill bill : billReport.getBills()) {
    %>
    <div class="bill-item">
        <h3>Bill ID: <%= bill.getBillId() %></h3>
        <div class="bill-details">
            <p><strong>Bill Date:</strong> <%= bill.getBillDate() %></p>
            <p><strong>Total Price:</strong> $<%= String.format("%.2f", bill.getTotalPrice()) %></p>
            <p><strong>Cash Tendered:</strong> $<%= String.format("%.2f", bill.getCashTendered()) %></p>
            <p><strong>Change Amount:</strong> $<%= String.format("%.2f", bill.getChangeAmount()) %></p>
        </div>

        <div class="item-list">
            <h4>Bill Items:</h4>
            <table>
                <thead>
                <tr>
                    <th>Item Name</th>
                    <th>Quantity</th>
                    <th>Unit Price</th>
                    <th>Total Price</th>
                </tr>
                </thead>
                <tbody>
                <%
                    for (Bill.BillItem item : bill.getBillItems()) {
                %>
                <tr>
                    <td><%= item.getItemName() %></td>
                    <td><%= item.getQuantity() %></td>
                    <td>$<%= String.format("%.2f", item.getUnitPrice()) %></td>
                    <td>$<%= String.format("%.2f", item.getTotalPrice()) %></td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>

        <div class="total-summary">
            <strong>Grand Total: $<%= String.format("%.2f", bill.getTotalPrice()) %></strong>
        </div>
    </div>
    <%
        }
    } else {
    %>
    <p class="no-bill-message">No bills available.</p>
    <%
        }
    %>
</div>

</body>
</html>
