package com.booking;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class UserFormServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String firstName = request.getParameter("fn");
		String lastName = request.getParameter("lm");
		String email = request.getParameter("em");
		String contact = request.getParameter("co");
		String address = request.getParameter("add");

		int ticketId = 0;
		PrintWriter out = response.getWriter();
		
		
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			con = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/ticketBooking?user=root&password=Sagarraj@1904");

			pstmt = con.prepareStatement(
					"INSERT INTO tickets(first_name, last_name, email, contact, address) VALUES (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, firstName);
			pstmt.setString(2, lastName);
			pstmt.setString(3, email);
			pstmt.setString(4, contact);
			pstmt.setString(5, address);

			pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				ticketId = rs.getInt(1);
			}

			

		} catch (ClassNotFoundException |SQLException e) {
			e.printStackTrace();
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

		response.setContentType("text/html");

		out.println("""
				<!DOCTYPE html>
				<html>
				<head>
				<meta charset='UTF-8'>
				<title>Ticket Submitted</title>
				<style>
				    body { font-family: Arial, sans-serif; background: #e8f5e9; display: flex; justify-content: center; align-items: center; min-height: 100vh; margin: 0; }
				    .popup-card { background: #fff; padding: 30px 40px; border-radius: 10px; box-shadow: 0 4px 10px rgba(0,0,0,0.2); text-align: center; width: 400px; animation: fadeIn 0.5s ease-in-out; }
				    h2 { color: #2e7d32; margin-bottom: 15px; }
				    h3 { color: #00796b; margin-bottom: 20px; }
				    a { display: inline-block; padding: 10px 20px; background-color: #00796b; color: #fff; text-decoration: none; border-radius: 5px; transition: 0.3s; }
				    a:hover { background-color: #004d40; }
				    @keyframes fadeIn { from { opacity: 0; transform: translateY(-20px); } to { opacity: 1; transform: translateY(0); } }
				</style>
				</head>
				<body>
				<div class="popup-card">
				<h2>Ticket submitted successfully!</h2>
				""");

				out.println("<h3>Your Ticket ID: " + ticketId + "</h3>");
				out.println("""
				<a href='checkStatus.html'>Check Status</a>
				</div>
				</body>
				</html>
				""");

	}
}
