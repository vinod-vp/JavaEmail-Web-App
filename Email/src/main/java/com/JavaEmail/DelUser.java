package com.JavaEmail;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;


public class DelUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    Jwt jwt;
	public void init() {
		jwt = new Jwt();
	}    
   
    public DelUser() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		try
		{
			String sql= "delete from User where Uemail=?";
			String SQL= "delete from Inbox where Fmail=? or Tmail=?";
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Email","root","Vinsql@123");
            PreparedStatement ps = conn.prepareStatement(sql);
            PreparedStatement Ps = conn.prepareStatement(SQL);
            
            String token = (String) request.getSession().getAttribute("token");
    		if (token == null) {
    			
    			out.println("Ooopss!!! Login Again.");
    			return;
    		}
    		Claims claim = jwt.DecodeToken(token);
    		if (claim.get("user") != null)	 {
    		
    		ps.setString(1,(String) claim.get("user"));
    		Ps.setString(1,(String) claim.get("user"));
    		Ps.setString(2,(String) claim.get("user"));
            ps.executeUpdate();
            Ps.executeUpdate();
            
            out.println("Account Deleted Successfully!!!");
    		}
		}catch (ClassNotFoundException e) {

			e.printStackTrace();

		} catch (SQLException e) {

			 	e.printStackTrace();
		}		

	}
			
			
}
