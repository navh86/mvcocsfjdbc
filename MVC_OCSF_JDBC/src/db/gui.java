package db;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class gui {

	private JFrame frame;
	private JTextField host;
	private JTextField port;
    private JTextArea textArea;
	private SimpleClient client;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui window = new gui("localhost",12345);
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
	public gui(String h, int p) {
		initialize(h,p);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String h, int p) {
		frame = new JFrame();
		frame.setBounds(100, 100, 550, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "VCP Client", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Host:");
		lblNewLabel.setBounds(12, 254, 62, 26);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Port");
		lblNewLabel_1.setBounds(12, 292, 60, 15);
		panel.add(lblNewLabel_1);
		
		JButton btnOpen = new JButton("Open");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 open();
				 System.out.println("Opened");
			}
		});
		btnOpen.setBounds(81, 326, 106, 25);
		panel.add(btnOpen);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
				System.out.println("Connection Closed");
				
			}
		});
		btnClose.setBounds(81, 373, 106, 25);
		panel.add(btnClose);
		
		JButton btnNewButton = new JButton("Select Value");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send("select");
			}
		});
		btnNewButton.setBounds(199, 326, 127, 25);
		panel.add(btnNewButton);
		
		JButton btnInsertValue = new JButton("Insert Value");
		btnInsertValue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send("insert");
			}
		});
		btnInsertValue.setBounds(199, 373, 127, 25);
		panel.add(btnInsertValue);
		
		JButton btnQuit = new JButton(" Quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quit();
			}
		});
		btnQuit.setBounds(370, 350, 106, 25);
		panel.add(btnQuit);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(31, 50, 86, -25);
		panel.add(scrollPane);
		
		host = new JTextField();
		host.setText("Localhost");
		host.setBounds(54, 258, 114, 19);
		panel.add(host);
		host.setColumns(10);
		
		port = new JTextField();
		port.setText("12345");
		port.setColumns(10);
		port.setBounds(54, 292, 114, 19);
		panel.add(port);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 22, 526, 232);
		panel.add(scrollPane_1);
		
		textArea = new JTextArea();
		scrollPane_1.setViewportView(textArea);
		
		client = new SimpleClient(host.getText(), Integer.parseInt(port.getText()),textArea);	
	    }
	
	private void readFields()
	  {
	    int p = Integer.parseInt(port.getText());
	    client.setPort(p);
	    client.setHost(host.getText());
	  }

	  public void close()
	  {
	    try {
	      readFields();
	      client.closeConnection();
	    }
	    catch (Exception ex)
	    {
	      textArea.setBackground(Color.red);
		  textArea.setText(ex.toString());
	    }
	  }

	  public void open()
	  {
	    try {
	      readFields();
	      client.openConnection();
	      textArea.setBackground(Color.green);
	      textArea.setText("Connected To Server Succesfully\n");
	    }
	    catch (Exception ex)
	    {   
		  textArea.setBackground(Color.red);
	      textArea.setText(ex.toString());
	    }
	  }

	  public void send(String message)
	  {
	    try {
	    	
	      this.readFields();
	      client.sendToServer(message);
	    }
	    catch (Exception ex)
	    {
	      textArea.setBackground(Color.red);
		  textArea.setText(ex.toString());
	    }
	  }

	  public void quit()
	  {
	    System.exit(0);
	  }
}
