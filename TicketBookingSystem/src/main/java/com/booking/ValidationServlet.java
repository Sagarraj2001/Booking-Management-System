package com.booking;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/adminTicket")
public class ValidationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        int ticketId = Integer.parseInt(req.getParameter("ticket_id"));
        String action = req.getParameter("action");

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ticketBooking?user=root&password=Sagarraj@1904");

            String checkSql = "SELECT first_name, last_name, email, contact, address, status FROM tickets WHERE ticket_id=?";
            pstmt = con.prepareStatement(checkSql);
            pstmt.setInt(1, ticketId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String fname = rs.getString("first_name");
                String lname = rs.getString("last_name");
                String email = rs.getString("email");
                String contact = rs.getString("contact");
                String address = rs.getString("address");
                String currentStatus = rs.getString("status");

                // Ticket details card
                out.println("""
                        <!DOCTYPE html>
                        <html>
                        <head>
                        <meta charset='UTF-8'>
                        <title>Ticket Details</title>
                        <style>
                            body { font-family: Arial, sans-serif; background: #f0f4f8; display: flex; justify-content: center; align-items: center; min-height: 100vh; margin: 0; }
                            .ticket-card { background: #fff; padding: 25px 30px; border-radius: 10px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); width: 400px; }
                            h2 { text-align: center; color: #333; margin-bottom: 20px; }
                            p { font-size: 16px; color: #555; margin: 8px 0; }
                            p b { color: #00796b; display: inline-block; width: 120px; }
                            .status { margin-top: 15px; padding: 10px; font-weight: bold; text-align: center; border-radius: 5px; }
                            .pending { background-color: #fff9c4; color: #f57f17; }
                            .approved { background-color: #dcedc8; color: #33691e; }
                            .rejected { background-color: #ffcdd2; color: #c62828; }
                            hr { border: 0; height: 1px; background: #00796b; margin: 20px 0; }
                            a { display: block; text-align: center; margin-top: 15px; text-decoration: none; color: #00796b; }
                            a:hover { text-decoration: underline; }
                        </style>
                        </head>
                        <body>
                        <div class="ticket-card">
                        """);

                out.println("<h2>Ticket ID: " + ticketId + "</h2>");
                out.println("<p><b>Name:</b> " + fname + " " + lname + "</p>");
                out.println("<p><b>Email:</b> " + email + "</p>");
                out.println("<p><b>Contact:</b> " + contact + "</p>");
                out.println("<p><b>Address:</b> " + address + "</p>");

                if (!"PENDING".equals(currentStatus)) {
                    String statusClass = currentStatus.equals("APPROVED") ? "approved" : "rejected";
                    out.println("<div class='status " + statusClass + "'>This ticket has already been " + currentStatus + "</div>");
                } else {
                    pstmt = con.prepareStatement("UPDATE tickets SET status=? WHERE ticket_id=?");
                    pstmt.setString(1, action);
                    pstmt.setInt(2, ticketId);
                    int updated = pstmt.executeUpdate();

                    String statusClass = action.equals("APPROVED") ? "approved" : "rejected";
                    if (updated > 0) {
                        out.println("<div class='status " + statusClass + "'>Ticket has been " + action + "</div>");
                    } else {
                        out.println("<div class='status pending'>Failed to update ticket status</div>");
                    }
                }

                out.println("<hr>");
                out.println("<a href='validate.html'>Back to Validation Page</a>");
                out.println("</div></body></html>");
            } else {
                out.println("<h2 style='color: red; text-align: center; margin-top: 20px;'>Ticket ID " + ticketId + " not found!</h2>");
                out.println("<p style='text-align: center;'><a href='validate.html'>Back to Validation Page</a></p>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3 style='color: red; text-align: center;'>Error: " + e.getMessage() + "</h3>");
        } finally {
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}
