<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Login</title>

  <style>
    /* Google Fonts Import */
    @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap');

    body {
      font-family: 'Roboto', sans-serif;
      background: linear-gradient(135deg, #74ebd5, #ACB6E5);
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
      margin: 0;
    }

    .login-container {
      width: 100%;
      max-width: 400px;
      padding: 40px;
      background-color: #fff;
      border-radius: 10px;
      box-shadow: 0 8px 25px rgba(0, 0, 0, 0.2);
      text-align: center;
    }

    h2 {
      color: #333;
      font-size: 26px;
      margin-bottom: 25px;
      font-weight: 500;
    }

    form {
      display: flex;
      flex-direction: column;
    }

    label {
      font-size: 16px;
      font-weight: 500;
      text-align: left;
      margin-bottom: 8px;
      color: #555;
    }

    input[type="text"], input[type="password"] {
      padding: 14px;
      margin-bottom: 20px;
      border: 1px solid #ccc;
      border-radius: 6px;
      font-size: 16px;
      width: 100%;
      box-sizing: border-box;
    }

    input[type="text"]:focus, input[type="password"]:focus {
      outline: none;
      border-color: #28a745;
      box-shadow: 0 0 6px rgba(40, 167, 69, 0.2);
    }

    input[type="submit"] {
      padding: 14px;
      border: none;
      border-radius: 6px;
      background-color: #28a745;
      color: white;
      font-size: 16px;
      font-weight: bold;
      cursor: pointer;
      transition: background-color 0.3s ease;
    }

    input[type="submit"]:hover {
      background-color: #218838;
    }

    .error {
      color: red;
      font-size: 14px;
      margin-bottom: 20px;
    }

    .login-container p {
      margin-top: 20px;
      font-size: 14px;
      color: #777;
    }

    .login-container p a {
      color: #28a745;
      text-decoration: none;
      font-weight: bold;
    }

    .login-container p a:hover {
      text-decoration: underline;
    }
  </style>
</head>
<body>

<div class="login-container">
  <h2>Login</h2>
  <%
    String error = request.getParameter("error");
    if (error != null) {
  %>
  <div class="error">Invalid Username or Password</div>
  <%
    }
  %>
  <form action="login" method="post">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username" required>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required>

    <input type="submit" value="Login">
  </form>

  <!-- Optional extra: Link for password reset -->
  <p>Forgot your password? <a href="#">Reset here</a></p>
</div>

</body>
</html>
