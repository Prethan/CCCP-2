
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <style>
        /* Navbar Container */
        .navbar {
            background-color: #2c3e50;
            overflow: hidden;
            padding: 0 20px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
            font-family: 'Roboto', sans-serif;
        }

        /* Navbar links */
        .navbar a {
            float: left;
            display: block;
            color: white;
            text-align: center;
            padding: 16px 24px;
            text-decoration: none;
            font-size: 17px;
            transition: all 0.3s ease;
            border-radius: 4px;
        }

        /* Hover effect for navbar links */
        .navbar a:hover {
            background-color: #1abc9c;
            color: #fff;
            box-shadow: 0 5px 10px rgba(0, 0, 0, 0.15);
        }

        /* Active link styling */
        .navbar a.active {
            background-color: #2980b9;
            color: white;
            box-shadow: 0 5px 12px rgba(0, 0, 0, 0.2);
        }

        /* Responsive navbar for mobile devices */
        @media (max-width: 600px) {
            .navbar a {
                float: none;
                display: block;
                text-align: left;
                padding: 14px;
                font-size: 16px;
            }
        }

        /* Add a nice hover transition */
        .navbar a:hover {
            background-color: #1abc9c;
            color: #fff;
        }

        /* Add custom styling for hover and active effects */
        .navbar a:active {
            background-color: #16a085;
        }

        /* Styling for logout button */
        .navbar .logout-btn {
            float: right;
            background-color: #e74c3c;
            color: white;
            padding: 14px 24px;
            text-decoration: none;
            border-radius: 4px;
            font-size: 17px;
            margin-left: auto;
            transition: all 0.3s ease;
        }

        .navbar .logout-btn:hover {
            background-color: #c0392b;
            color: white;
            box-shadow: 0 5px 12px rgba(0, 0, 0, 0.15);
        }

    </style>
</head>
<body>

<!-- Navbar -->
<div class="navbar">
    <a href="http://localhost:8080/cccp_2_war/billReport" class="<%= request.getRequestURI().contains("billReport") ? "active" : "" %>">Bill Report</a>
    <a href="http://localhost:8080/cccp_2_war/reorderReport" class="<%= request.getRequestURI().contains("reorderReport") ? "active" : "" %>">Reorder Report</a>
    <a href="http://localhost:8080/cccp_2_war/stockReport" class="<%= request.getRequestURI().contains("stockReport") ? "active" : "" %>">Stock Report</a>
    <a href="http://localhost:8080/cccp_2_war/reshelvingReport" class="<%= request.getRequestURI().contains("reshelvingReport") ? "active" : "" %>">Reshelf Report</a>
    <a href="http://localhost:8080/cccp_2_war/selectDate" class="<%= request.getRequestURI().contains("selectDate") ? "active" : "" %>">Daily Sales Report</a>
    <a href="http://localhost:8080/cccp_2_war/createBill" class="<%= request.getRequestURI().contains("createBill") ? "active" : "" %>">Create Bill</a>
    <a href="http://localhost:8080/cccp_2_war/reshelfItems" class="<%= request.getRequestURI().contains("reshelfItems") ? "active" : "" %>">Re-shelf Items</a>
    <a href="http://localhost:8080/cccp_2_war/addItemToStock" class="<%= request.getRequestURI().contains("addItemToStock") ? "active" : "" %>">Add Item To Stock</a>

    <!-- Logout button placed at the right end -->
    <a href="logout" class="logout-btn">Logout</a>
</div>

</body>
</html>
