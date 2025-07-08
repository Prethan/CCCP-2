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
    <title>Create Bill</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .container {632+3
            max-width: 600px;
            margin: auto;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
        }
        input[type="text"], input[type="number"] {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
        }
        button {
            padding: 10px 15px;
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
        }
        button.add-item {
            background-color: #f39c12;
        }
    </style>
    <script>
        function addItemRow() {
            const itemRow = `
                <div class="form-group">
                    <label>Item Code</label>
                    <input type="text" name="itemCode" required>
                    <label>Quantity</label>
                    <input type="number" name="quantity" required min="1">
                </div>`;
            document.getElementById('itemList').insertAdjacentHTML('beforeend', itemRow);
        }
    </script>
</head>
<body>
<div class="container">
    <h2>Create Bill</h2>
    <form action="createBill" method="post">
        <div id="itemList">
            <div class="form-group">
                <label>Item Code</label>
                <input type="text" name="itemCode" required>
                <label>Quantity</label>
                <input type="number" name="quantity" required min="1">
            </div>
        </div>
        <button type="button" class="add-item" onclick="addItemRow()">Add More Items</button>

        <div class="form-group">
            <label>Cash Tendered</label>
            <input type="number" name="cashTendered" required step="0.01" min="0">
        </div>

        <button type="submit">Create Bill</button>
    </form>
</div>
</body>
</html>
