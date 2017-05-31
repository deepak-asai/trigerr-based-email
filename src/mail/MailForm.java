package mail;

import java.awt.EventQueue;
import java.awt.TextArea;

import javax.mail.Message;
import javax.mail.internet.*;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.*;
import javax.swing.JFrame;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.util.ArrayList;
import java.util.Iterator;


public class MailForm {

	final JTextArea textArea = new JTextArea();
	
	JRadioButton rule1 = new JRadioButton("rule1");
	JRadioButton rule2 = new JRadioButton("rule 2");
	JRadioButton rule3 = new JRadioButton("rule 3");
	JRadioButton rule4 = new JRadioButton("rule 4");
	JRadioButton rule5 = new JRadioButton("rule 5");
	
	final ButtonGroup bg = new ButtonGroup();
	
	private JFrame frame;
	private final JTextArea subject = new JTextArea();
	private final JTextArea mess = new JTextArea();

	DBConnect dbc = new DBConnect();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MailForm window = new MailForm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MailForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
				
		frame = new JFrame();
		frame.setBounds(100, 100, 1091, 775);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		textArea.setBounds(239, 406, 703, 279);
		frame.getContentPane().add(textArea);
		
		JButton btnSend = new JButton("Send");
		
		btnSend.setBounds(521, 718, 117, 25);
		frame.getContentPane().add(btnSend);
		
		rule1.setBounds(35, 35, 76, 23);
		frame.getContentPane().add(rule1);
		
		
		rule2.setBounds(130, 35, 76, 23);
		frame.getContentPane().add(rule2);
		
		
		rule3.setBounds(239, 35, 82, 23);
		frame.getContentPane().add(rule3);
		
		
		rule4.setBounds(365, 35, 82, 23);
		frame.getContentPane().add(rule4);
		
		
		rule5.setBounds(467, 35, 82, 23);
		frame.getContentPane().add(rule5);
		
		
		rule1.setActionCommand("rule1");
		rule2.setActionCommand("rule2");
		rule3.setActionCommand("rule3");
		rule4.setActionCommand("rule4");
		rule5.setActionCommand("rule5");
		
		bg.add(rule5);
		bg.add(rule4);
		bg.add(rule3);
		bg.add(rule2);
		bg.add(rule1);
		subject.setBounds(352, 132, 454, 50);
		
		frame.getContentPane().add(subject);
		mess.setBounds(419, 263, 276, 113);
		
		frame.getContentPane().add(mess);
		

		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendMessage();
			} 
		
		});
		
		
	}
	private void sendMessage(){
		
		String rule_reqd = bg.getSelection().getActionCommand();
		System.out.println(rule_reqd);
		
		dbc.connect();
		ArrayList emails = dbc.getData(rule_reqd);
		Iterator it = emails.iterator();
		int numOfMails = emails.size();
		String toMail = "", oneMail ="";

		while(it.hasNext()){
			oneMail = (String)it.next();
			//toMail.concat(oneMail);
			toMail = toMail + oneMail + ", ";
			//System.out.println((String)it.next());
			
		}
		System.out.println(toMail);
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
				
		Session session = Session.getDefaultInstance(props, 
					new javax.mail.Authenticator(){
						protected PasswordAuthentication getPasswordAuthentication(){
							return new PasswordAuthentication("deepak.a.1996@gmail.com", "ethernet");
						}
					}
				);
		try{
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("deepak.a.1996@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toMail));
			message.setSubject(subject.getText());
			message.setText(mess.getText());
			Transport.send(message);
			textArea.append("sent to " + toMail);				
		} 
		catch(Exception e){
			textArea.append(e.toString());
		}
	
	}
	
}
