package mail;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.sql.*;
public class DBConnect {

		private Connection con;
		private Statement st;
		private ResultSet rs;
		
		public void connect(){
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trig_email","root", "deep411ak");
				st = con.createStatement();
				System.out.println(st);
				
				
			} catch (Exception e) {
				System.out.println("execption in connection"+e);
			}
		}
		public ArrayList getData(String rule){
			ArrayList emails = new ArrayList();
			
			try {
				String q = "select distinct email_id from logs where rule ='"+rule+"';";
				//System.out.println(q);
				//String q1 = "select * from login_details;";
				System.out.println(q);
				rs =st.executeQuery(q);
				
				//PreparedStatement stmt=con.prepareStatement(q);  
				//rs = stmt.
				while(rs.next()){
					emails.add(rs.getString("email_id"));
					//System.out.println(rs.getString("email_id"));
				}
				
				
			} catch (Exception e) {
				//System.out.println("exception here "+e);
				e.printStackTrace();
				//throw new ExceptionInInitializerError(e);
			}
			finally {
			    try { rs.close(); } catch (Exception e) { /* ignored */ }
			    try { st.close(); } catch (Exception e) { /* ignored */ }
			    try { con.close(); } catch (Exception e) { /* ignored */ }
			}
			return emails;
		}
		
		
}