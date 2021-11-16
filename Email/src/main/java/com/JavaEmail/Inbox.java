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


public class Inbox extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    Jwt jwt;
	

	public void init() {
		jwt = new Jwt();
	}    
   
	public Inbox() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		try {
	
		String sql= "select Fmail,Subject,Message,Dtime from Inbox where Tmail=? and Receiver=1";
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Email","root","Vinsql@123");
        
        String token = (String) request.getSession().getAttribute("token");
		if (token == null) {
			
			out.println("Ooopss!!! Login Again.");
			return;
		}
		Claims claim = jwt.DecodeToken(token);
		if (claim.get("user") != null)	 { 
        
		PreparedStatement ps = conn.prepareStatement(sql);
	    ps.setString(1,(String) claim.get("user"));
		ResultSet rs = ps.executeQuery();
     
        if (rs.next() == false) 
        { 
        	out.println("Emptyyy boxxx!!!!"); 
        }
        else
        {

              do {
                  out.print("From: "+rs.getString("Fmail")+", ");
                  out.print("Sub: "+rs.getString("Subject")+", ");
                  out.print("Msg: "+rs.getString("Message")+", ");
                  out.print(rs.getTimestamp("Dtime"));
                  out.println();
                  }while(rs.next());
        }}			
	}catch(ClassNotFoundException e) {

		e.printStackTrace();

	} catch (SQLException e) {

		 	e.printStackTrace();
	}		


}

	}

