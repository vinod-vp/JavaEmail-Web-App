package com.JavaEmail;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import io.jsonwebtoken.Claims;


public class Sendemail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Jwt jwt;
	

	public void init() {
		jwt = new Jwt();
	}
    
    public Sendemail() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		try{

			String To= request.getParameter("Tmail"); 
		    String Sub= request.getParameter("Subject");
	   	    String Cont= request.getParameter("Message");
	   	    int Sender =1;
	   	    int  receiver = 1;
	   	    java.util.Date utilDate = new java.util.Date();
	   	    java.sql.Timestamp Dtime = new Timestamp(utilDate.getTime());

	   	    
	   	    String SQL ="select Uemail from User where Uemail=?";
	   	    String sql= "insert into Inbox(Fmail,Tmail,Subject,Message,Dtime,Sender,Receiver) values(?,?,?,?,?,?,?)";

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Email","root","Vinsql@123");

    
			String token = (String) request.getSession().getAttribute("token");
			if (token == null) {
				
				out.println("Ooopss!!! Login Again.");
				return;
			}
			Claims claim = jwt.DecodeToken(token);
			if (claim.get("user") != null)	  
			{	
				PreparedStatement Ps = conn.prepareStatement(SQL);
				Ps.setString(1,To);
			    ResultSet rs=Ps.executeQuery();
			    
			    if(rs.next()==true) {
				
				PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,(String) claim.get("user"));
                ps.setString(2,To);
                ps.setString(3,Sub);
                ps.setString(4,Cont);
                ps.setTimestamp(5,Dtime);
                ps.setInt(6,Sender);
                ps.setInt(7,receiver);
                ps.executeUpdate();
                out.print("Email-Sent-Successfullyy!!!!");
			     }
			    else
			    {
			    	out.println("Invalid Mail-Id");
			    }
			  }
			else
			{
				out.println("Login-Once-Again!!!");
			}
    
    		out.flush();

			} catch(ClassNotFoundException e) {

			e.printStackTrace();
			} catch(SQLException e) {

				out.print("Incorrect Mail ID!! ");
				out.flush();
				e.printStackTrace();

			}		
	}


}

