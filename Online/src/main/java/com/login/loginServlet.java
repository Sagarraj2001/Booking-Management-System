package com.login;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/login")
public class loginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String myemail = req.getParameter("email");
		String pass = req.getParameter("password");
		PrintWriter out = res.getWriter();
		RequestDispatcher rd = null;
		if (myemail.equals("sagarraj123@gmail.com") && pass.equals("sagar@123")) {
			HttpSession session=req.getSession();
			session.setAttribute("name_key", "Sagar Raj");
			rd = req.getRequestDispatcher("/profile.jsp");
			rd.forward(req, res);
		} else {
			out.println("<h3 style='color:red'>Email id or password is incorrect</h3>");
			res.setContentType("text/html");
			rd = req.getRequestDispatcher("/index.html");
			rd.include(req, res);
		}
	}
}
