package com.JavaEmail;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		try{
			   String name= request.getParameter("Uname"); 
			   String email= request.getParameter("Uemail"); 
			   String pass= request.getParameter("Upass");
			   String sql= "insert into User(Uname,Uemail,Upass) values(?,?,?)";
			   Class.forName("com.mysql.cj.jdbc.Driver");
			   Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Email","root","Vinsql@123");
               PreparedStatement ps = conn.prepareStatement(sql);
               ps.setString(1,name);
               ps.setString(2,email);
               ps.setString(3,pass);
               ps.executeUpdate();

               out.println("You have successfully Signed In!!!!");
       
			} catch (ClassNotFoundException e) {

			e.printStackTrace();
			} catch (SQLException e) {

				out.println("Email-Id Already Exists. Try Again!!!!");
				e.printStackTrace();
			}	

		    out.flush();

	}

}

 
