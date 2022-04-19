/**
 * Author: Ravi Tiwari
 * Filename: ManagerAddEmployee.java
 * Specifications: A Java file with JFrame which allows manager to add employees
 * For: CSE 205 - Final Project
  */


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ManagerAddEmployee extends JFrame {

	//instance variables
	private JLabel welcomeLabel = new JLabel();
	private JLabel fillLabel = new JLabel();
	private JLabel fLabel = new JLabel();
	private JLabel lLabel = new JLabel();
	private JLabel positionLabel = new JLabel();
	private JTextField positionTF = new JTextField();
	private JLabel uLabel = new JLabel();
	private JLabel pwdLabel = new JLabel();
	private JLabel confirmPwdLabel = new JLabel();
	private JLabel eLabel = new JLabel();
	private JLabel phLabel = new JLabel();
	private JLabel addLabel = new JLabel();
	private JLabel posLabel = new JLabel();
	private JTextField fTF = new JTextField();
	private JTextField lTF = new JTextField();
	private JTextField uTF = new JTextField();
	private JTextField eTF = new JTextField();
	private JPasswordField pwdPF = new JPasswordField();
	private JPasswordField cPwdPF = new JPasswordField();
	private JTextField phTF = new JTextField();
	private JTextField addTF = new JTextField();
	private JTextField posTF = new JTextField();
	private JButton addEmployeeButton = new JButton();
	private JCheckBox showPwdCB = new JCheckBox();
	private JButton backButton = new JButton();

	static Connection c = null;
	static Statement stmt = null;

	//default constructor
	public ManagerAddEmployee() {
		design();
	}

	public static void main(String args[]) {
		viewFrame();
		databaseConnection();
	}

	//set look and feel of frame and set frame visible
	private static void databaseConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users","postgres","123");
			System.out.println("You are connected boy");
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ":" + e.getMessage());
			System.exit(0);
		}
	}

	private static void viewFrame() {
		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Nimbus not found");
		}

		ManagerAddEmployee frame = new ManagerAddEmployee();
		frame.setTitle("Casino Coffee");
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(ManagerAddEmployee.EXIT_ON_CLOSE);
		frame.setResizable(false);
	}

	private void design() {

		//set font, foreground and text of components
		welcomeLabel.setFont(new Font("Harrington", 1, 36)); 
		welcomeLabel.setForeground(new Color(180, 155, 0));
		welcomeLabel.setText("Welcome To The Casino Coffee");

		fillLabel.setFont(new Font("Arial", 0, 18)); 
		fillLabel.setForeground(new Color(0, 102, 102));
		fillLabel.setText("Fill all the details to add Employee");

		fLabel.setFont(new Font("Arial", 0, 14)); 
		fLabel.setText("First Name");

		lLabel.setFont(new Font("Arial", 0, 14)); 
		lLabel.setText("Last Name");

		positionLabel.setFont(new Font("Arial", 0, 14)); 
		positionLabel.setText("Position");

		uLabel.setFont(new Font("Arial", 0, 14)); 
		uLabel.setText("Username");

		pwdLabel.setFont(new Font("Arial", 0, 14)); 
		pwdLabel.setText("Password");

		confirmPwdLabel.setFont(new Font("Arial", 0, 14)); 
		confirmPwdLabel.setText("Confirm Password");

		eLabel.setFont(new Font("Arial", 0, 14)); 
		eLabel.setText("Email Address");

		phLabel.setFont(new Font("Arial", 0, 14)); 
		phLabel.setText("Phone Number");

		addLabel.setFont(new Font("Arial", 0, 14)); 
		addLabel.setText("Street Address");

		posLabel.setFont(new Font("Arial", 0, 14)); 
		posLabel.setText("Postal Code");

		showPwdCB.setFont(new Font("Arial", 0, 14)); 
		showPwdCB.setText("Show Password");

		backButton.setFont(new Font("Arial", 1, 14)); 
		backButton.setText("Back");

		backButton.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				//change frame
				ManagerLogIn f = new ManagerLogIn();
				f.setResizable(false);
				f.setTitle("Casino Coffee");
				f.setDefaultCloseOperation(ManagerLogIn.EXIT_ON_CLOSE);	
				f.setLocationRelativeTo(null);
				f.setVisible(true);
				dispose();
			}
		});

		addEmployeeButton.setFont(new Font("Arial", 1, 14)); 
		addEmployeeButton.setText("Add Employee");

		addEmployeeButton.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {

				//check if all fields are filled and passwords match
				if (fTF.getText().equals("") || lTF.getText().equals("") ||positionTF.getText().equals("") || uTF.getText().equals("")
						||String.valueOf(pwdPF.getPassword()).equals("") || eTF.getText().equals("") || phTF.getText().equals("")
						|| addTF.getText().equals("") || posTF.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please Fill All The Details", "" + "Message", JOptionPane.INFORMATION_MESSAGE);

				}
				else if (!(String.valueOf(pwdPF.getPassword()).equals(String.valueOf(cPwdPF.getPassword())))) {
					JOptionPane.showMessageDialog(null, "Passwords didn't match. Try Again", "" + "Message", JOptionPane.INFORMATION_MESSAGE);
					pwdPF.setText("");
					cPwdPF.setText("");
				}
				//check if phonenumber, email or username already exists n the databasae
				else {
					try {
						c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users","postgres","123");
						stmt = c.createStatement();

						final String queryCheck = "SELECT * from customers";
						ResultSet rs = stmt.executeQuery(queryCheck);
						while(rs.next()) {
							if(rs.getString("emailaddress").equals(eTF.getText())) {
								JOptionPane.showMessageDialog(null,"Email is already registered","",JOptionPane.INFORMATION_MESSAGE);
								eTF.setText("");
								break;
							}
						} 
						final String queryCheck1 = "SELECT * from customers";
						ResultSet rs1 = stmt.executeQuery(queryCheck1);
						while(rs1.next()) {
							if(rs1.getString("phonenumber").equals(phTF.getText())) {
								JOptionPane.showMessageDialog(null,"Phone Number is already registered","",JOptionPane.INFORMATION_MESSAGE);
								phTF.setText("");
								break;
							}
						}
						final String queryCheck2 = "SELECT * from customers";
						ResultSet rs2 = stmt.executeQuery(queryCheck2);
						while(rs2.next()) {
							if(rs2.getString("username").equals(uTF.getText())) {
								JOptionPane.showMessageDialog(null,"Username is already taken","",JOptionPane.INFORMATION_MESSAGE);
								uTF.setText("");
								break;
							}

						}					
						final String queryCheck3 = "SELECT * from employees";

						ResultSet rs3 = stmt.executeQuery(queryCheck3);
						while(rs3.next()) {
							if(rs3.getString("emailaddress").equals(eTF.getText())) {
								JOptionPane.showMessageDialog(null,"Email is already registered","",JOptionPane.INFORMATION_MESSAGE);
								eTF.setText("");
								break;
							}
						} 

						final String queryCheck4 = "SELECT * from employees";

						ResultSet rs4 = stmt.executeQuery(queryCheck4);
						while(rs4.next()) {
							if(rs4.getString("phonenumber").equals(phTF.getText())) {
								JOptionPane.showMessageDialog(null,"Phone Number is already registered","",JOptionPane.INFORMATION_MESSAGE);
								phTF.setText("");
								break;
							}
						} 

						final String queryCheck5 = "SELECT * from employees";
						ResultSet rs5 = stmt.executeQuery(queryCheck5);
						while(rs5.next()) {
							if(rs5.getString("username").equals(uTF.getText())) {
								JOptionPane.showMessageDialog(null,"Username is already taken","",JOptionPane.INFORMATION_MESSAGE);
								uTF.setText("");
								break;
							}
						} 
					}
					catch (Exception e1) {
						e1.printStackTrace();
						System.err.println(e1.getClass().getName() + ":" + e1.getMessage());
						System.exit(0);
					}
					String fName = fTF.getText();
					String lName = lTF.getText();
					String position = positionTF.getText();
					String username = uTF.getText();
					String password = String.valueOf(pwdPF.getPassword());
					String email = eTF.getText();
					String phone = phTF.getText();
					String address = addTF.getText();
					String zip = posTF.getText();

					if((fName.equals("") || lName.equals("")|| position.equals("") ||username.equals("") || password.equals("") || email.equals("") || phone.equals("")
							|| address.equals("")|| zip.equals(""))) {
						JOptionPane.showMessageDialog(null,"Please Fill All the Required Details","",JOptionPane.INFORMATION_MESSAGE);
					}
					//add data to the database
					else {
						try {
							c.setAutoCommit(false);
							stmt = c.createStatement();
							String sql = "INSERT INTO EMPLOYEES (" + "FIRSTNAME, LASTNAME, POSITION, USERNAME, PASSWORD, EMAILADDRESS, PHONENUMBER, ADDRESS, POSTALCODE, STATUS)" 
									+ "VALUES('"+ fName +"','" + lName +"' ,'"+ position +"' ,'" + username + "','" + password +"' ,'" + email +"','" 
									+ phone +"','" + address +"','" + zip +"', 'SIGNED OUT');";
							
							stmt.executeLargeUpdate(sql);
							c.commit();
							System.out.println("Data added to Employees");

							//change frame
							ManagerAddedEmployeeMessage f = new ManagerAddedEmployeeMessage();
							f.setResizable(false);
							f.setTitle("Casino Coffee");
							f.setDefaultCloseOperation(ManagerAddedEmployeeMessage.EXIT_ON_CLOSE);
							f.setLocationRelativeTo(null);
							f.setVisible(true);
							dispose();
						}
						
						catch (Exception e1) {
							e1.printStackTrace();
							System.err.println(e1.getClass().getName() + ":" + e1.getMessage());
							System.exit(0);
						}
					}
				}
			}
		});

		//add actionListener and KeyListener
		phTF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') ||
						(c == KeyEvent.VK_BACK_SPACE) ||
						(c == KeyEvent.VK_DELETE))) {
					getToolkit().beep();
					e.consume();
				}
				if (phTF.getText().length() >= 10) // limit textfield to 10 characters
					e.consume(); 
			}
		});
		posTF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') ||
						(c == KeyEvent.VK_BACK_SPACE) ||
						(c == KeyEvent.VK_DELETE))) {
					getToolkit().beep();
					e.consume();
				}
				if (posTF.getText().length() >= 6) // limit textfield to 6 characters
					e.consume(); 
			}
		});
		showPwdCB.addActionListener (new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				if (showPwdCB.isSelected()) {
					pwdPF.setEchoChar((char) 0);
					cPwdPF.setEchoChar((char)0);
				} else {
					pwdPF.setEchoChar('*');
					cPwdPF.setEchoChar('*');
				}
			}
		});

		pwdPF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) { 
				if (String.valueOf(pwdPF.getPassword()).length() >= 12 ) // limit textfield to 12 characters
					e.consume(); 
			}  
		});

		cPwdPF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) { 
				if (String.valueOf(cPwdPF.getPassword()).length() >= 12 ) // limit textfield to 12 characters
					e.consume(); 
			}  
		});


		uTF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) { 
				if (uTF.getText().length() >= 8 ) // limit textfield to 8 characters
					e.consume(); 
			}  
		});
		fTF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) { 

				if (fTF.getText().length() >= 10 ) // limit textfield to 10 characters
					e.consume(); 
			}  
		});
		lTF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (lTF.getText().length() >= 10 ) // limit textfield to 10 characters
					e.consume(); 
			}  
		});

		addTF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) { 
				if (addTF.getText().length() >= 50 ) // limit textfield to 50 characters
					e.consume(); 
			}  
		});

		eTF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) { 
				if (eTF.getText().length() >= 30 ) // limit textfield to 30 characters
					e.consume(); 
			}  
		});
		
		positionTF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) { 
				if (positionTF.getText().length() >= 15 ) // limit textfield to 15 characters
					e.consume(); 
			}  
		});

		//Using GroupLayout to add components
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(welcomeLabel)
						.addGap(40, 40, 40))
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addContainerGap()
										.addComponent(backButton)
										.addGap(82, 82, 82)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
												.addComponent(fillLabel, GroupLayout.Alignment.TRAILING)
												.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
														.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
																.addComponent(confirmPwdLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																.addComponent(positionLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																.addComponent(fLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																.addGroup(layout.createSequentialGroup()
																		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
																				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
																						.addComponent(pwdLabel)
																						.addComponent(phLabel, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																						.addComponent(addLabel)
																						.addComponent(posLabel)
																						.addComponent(uLabel)
																						.addComponent(eLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
																				.addComponent(lLabel, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
																		.addGap(0, 0, Short.MAX_VALUE)))
														.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
														.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
																.addComponent(pwdPF)
																.addComponent(fTF)
																.addComponent(lTF)
																.addComponent(uTF)
																.addComponent(eTF)
																.addComponent(cPwdPF)
																.addComponent(phTF)
																.addComponent(addTF)
																.addComponent(posTF)
																.addComponent(positionTF, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))))
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(showPwdCB))
								.addGroup(layout.createSequentialGroup()
										.addGap(228, 228, 228)
										.addComponent(addEmployeeButton)))
						.addContainerGap(42, Short.MAX_VALUE)));

		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(welcomeLabel)
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(fillLabel)
								.addComponent(backButton))
						.addGap(32, 32, 32)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(fLabel)
								.addComponent(fTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(lLabel)
								.addComponent(lTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(positionLabel)
								.addComponent(positionTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(uLabel)
								.addComponent(uTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(pwdLabel)
								.addComponent(pwdPF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(showPwdCB))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(confirmPwdLabel)
								.addComponent(cPwdPF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(eLabel)
								.addComponent(eTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(phLabel)
								.addComponent(phTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(addLabel)
								.addComponent(addTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(posLabel)
								.addComponent(posTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addComponent(addEmployeeButton)
						.addContainerGap(63, Short.MAX_VALUE)));
		pack();
	}
}

