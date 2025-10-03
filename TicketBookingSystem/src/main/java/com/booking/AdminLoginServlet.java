package com.booking;

import java.io.*;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/adminLogin")

public class AdminLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		res.setContentType("text/html");
		PrintWriter out = res.getWriter();

		String email = req.getParameter("em");
		String password = req.getParameter("pass");

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			con = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/ticketBooking?user=root&password=Sagarraj@1904");
			pstmt = con.prepareStatement("SELECT * FROM admin WHERE email=? AND password=?");
			{

				pstmt.setString(1, email);
				pstmt.setString(2, password);

				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {
					out.println("""
							<!DOCTYPE html>
							<html>
							<head>
							<meta charset='UTF-8'>
							<title>Admin Welcome</title>
							<style>
							    body {
							        font-family: Arial, sans-serif;
							        background: #f0f4f8;
							        display: flex;
							        justify-content: center;
							        align-items: center;
							        height: 100vh;
							        margin: 0;
							        text-align: center;
							    }
							    h1 {
							        color: #00796b;
							        margin-bottom: 20px;
							    }
							    a {
							        display: inline-block;
							        padding: 10px 20px;
							        background-color: #00796b;
							        color: #fff;
							        text-decoration: none;
							        border-radius: 5px;
							        transition: 0.3s;
							    }
							    a:hover {
							        background-color: #004d40;
							    }
							</style>
							</head>
							<body>
							    <div>
							        <h1>Welcome to Admin Validation Page</h1>
							        <p><a href='validate.html'>Go to Ticket Validation</a></p>
							    </div>
							</body>
							</html>
							""");

				} else {
					out.println("""
							<!DOCTYPE html>
							<html>
							<head>
							<meta charset='UTF-8'>
							<title>Login Failed</title>
							<style>
							    body {
							        font-family: Arial, sans-serif;
							        background: #ffebee;  /* light red background */
							        display: flex;
							        justify-content: center;
							        align-items: center;
							        height: 100vh;
							        margin: 0;
							        text-align: center;
							    }
							    .message-card {
							        background: #fff;
							        padding: 30px 40px;
							        border-radius: 10px;
							        box-shadow: 0 4px 10px rgba(0,0,0,0.1);
							    }
							    h2 {
							        color: #d32f2f; /* dark red */
							        margin-bottom: 15px;
							    }
							    p {
							        color: #333;
							        margin-bottom: 20px;
							    }
							    a {
							        display: inline-block;
							        padding: 10px 20px;
							        background-color: #d32f2f;
							        color: #fff;
							        text-decoration: none;
							        border-radius: 5px;
							        transition: 0.3s;
							    }
							    a:hover {
							        background-color: #9a0007;
							    }
							</style>
							</head>
							<body>
							<div class="message-card">
							    <h2>Login Failed</h2>
							    <p>--- Invalid email or password ---</p>
							    <a href="AdminLogin.html">Back to Login</a>
							</div>
							</body>
							</html>
							""");

				}

			}

		} catch (ClassNotFoundException | SQLException e) {
			out.println("JDBC Driver not found: " + e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
