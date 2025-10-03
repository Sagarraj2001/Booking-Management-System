package com.booking;

import java.io.*;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/checkStatus")
public class CheckStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		String t_id = req.getParameter("ticket_id");
		String phone = req.getParameter("co");
		int ticketId = Integer.parseInt(t_id);
		String status = "Not Found";

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/ticketBooking?user=root&password=Sagarraj@1904");

			pstmt = con.prepareStatement("SELECT status FROM tickets WHERE ticket_id=? AND contact=?");
			pstmt.setInt(1, ticketId);
			pstmt.setString(2, phone);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				status = rs.getString("status");
			} else {
				status = "Ticket ID  or Contact details is wrong";
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			status = "Error: " + e.getMessage();
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

		 out.println("""
			        <!DOCTYPE html>
			        <html>
			        <head>
			        <meta charset='UTF-8'>
			        <title>Ticket Status</title>
			        <style>
			    """);
		 out.println("""
		            body { font-family: Arial, sans-serif; background: #f0f4f8; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
		            .status-card { background: #fff; padding: 25px 35px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); text-align: center; }
		            .status-card h2 { color: #333; margin-bottom: 10px; }
		            .status-card h3 { color: #4CAF50; margin-bottom: 20px; }
		            .status-card a { text-decoration: none; color: #fff; background-color: #4CAF50; padding: 8px 15px; border-radius: 5px; }
		            .status-card a:hover { background-color: #45a049; }
		        </style>
		        </head>
		        <body>
		        <div class='status-card'>
		    """);
		 out.println(
			        "<h2>Ticket ID: " + ticketId + "</h2>" +
			        "<h3>Status: " + status + "</h3>" +
			        "<p><a href='checkStatus.html'>Check Another Ticket</a></p>" +
			        "</div></body></html>"
			    );
	}
}
