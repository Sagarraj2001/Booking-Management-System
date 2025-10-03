package com.booking;

import java.io.*;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/show")
public class ShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		res.setContentType("text/html");
		PrintWriter out = res.getWriter();

		out.println(
				"""
						<!DOCTYPE html>
						<html>
						<head>
						<meta charset='UTF-8'>
						<title>All User Tickets</title>
						<style>
						    body { font-family: Arial, sans-serif; background: #f0f4f8; padding: 20px; }
						    h1 { text-align: center; color: #333; }
						    table { width: 100%; border-collapse: collapse; margin-top: 20px; }
						    th, td { padding: 10px; text-align: left; border: 1px solid #ccc; }
						    th { background-color: #00796b; color: white; }
						    tr:nth-child(even) { background-color: #f2f2f2; }
						    tr:hover { background-color: #d9f2e6; }
						    a { display: inline-block; margin-top: 20px; color: #00796b; text-decoration: none; }
						    a:hover { text-decoration: underline; }
						</style>
						</head>
						<body>
						<h1>All User Tickets</h1>
						<table>
						<tr><th>Ticket ID</th><th>First Name</th><th>Last Name</th><th>Email</th><th>Contact</th><th>Address</th><th>Status</th></tr>
						""");

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/ticketBooking?user=root&password=Sagarraj@1904");

			pstmt = con.prepareStatement(
					"SELECT ticket_id, first_name, last_name, email, contact, address, status FROM tickets");
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				out.println("<tr>" + "<td>" + rs.getInt("ticket_id") + "</td>" + "<td>" + rs.getString("first_name")
						+ "</td>" + "<td>" + rs.getString("last_name") + "</td>" + "<td>" + rs.getString("email")
						+ "</td>" + "<td>" + rs.getString("contact") + "</td>" + "<td>" + rs.getString("address")
						+ "</td>" + "<td>" + rs.getString("status") + "</td>" + "</tr>");
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			out.println("<tr><td colspan='7'>Error: " + e.getMessage() + "</td></tr>");
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		out.println("""
				</table>
				<a href='validate.html'>Back to Validation Form</a>
				</body>
				</html>
				""");
	}
}
