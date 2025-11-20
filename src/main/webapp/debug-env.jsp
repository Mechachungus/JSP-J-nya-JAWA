<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html>
<head><title>Debug Environment</title></head>
<body>
    <h1>Render Environment Debugger</h1>
    <table border="1">
        <tr>
            <th>Variable</th>
            <th>Value</th>
        </tr>
        <tr>
            <td>DB_URL</td>
            <td><%= System.getenv("DB_URL") %></td>
        </tr>
        <tr>
            <td>DB_USER</td>
            <td><%= System.getenv("DB_USER") %></td>
        </tr>
        <tr>
            <td>DB_PASSWORD</td>
            <td><%= System.getenv("DB_PASSWORD") == null ? "NULL" : "******" %></td>
        </tr>
    </table>

    <h2>DatabaseConfig.getDbUrl() Output:</h2>
    <p>
        <%= com.example.config.DatabaseConfig.getDbUrl() %>
    </p>
</body>
</html>