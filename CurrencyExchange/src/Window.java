import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.lang.Math;


public class Window extends JFrame{

	public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Window();
            }
        });
    }
	
	//Setting up the overall window
	public Window() {
		super("Currency Exchange");
		
		JPanel convPanel = new JPanel();    
	    JPanel graphPanel = new JPanel();   
	    JPanel infoPanel = new JPanel();
	    JTabbedPane tabPane = new JTabbedPane();  
	    tabPane.setBounds(0, 0, 750, 280);  
	    tabPane.add("Convert", convPanel);  
	    tabPane.add("Graph", graphPanel);
	    tabPane.add("Info", infoPanel); 
	    add(tabPane);  

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(750, 350);
		Image icon = Toolkit.getDefaultToolkit().getImage("CCash.jpg");
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
        setLocation((d.width - frameSize.width) / 2, (d.height - frameSize.height) / 3);
        
        setResizable(false);
		setIconImage(icon);
		convPanel.add(Buttons());
		convPanel.add(TextSetup(), BorderLayout.NORTH);
		setVisible(true);
	}
	
	//Setup for conversion button
	public JPanel Buttons() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.green);
		JButton firstButton = new JButton("Convert");
		buttonPanel.add(firstButton, BorderLayout.SOUTH);
		return buttonPanel;
	}
	
	//Setup for various texts present in the window
	public JPanel TextSetup() {
		JPanel textPanel = new JPanel();
		//textPanel.setBackground(Color.BLACK);
		textPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JTextField amountInput = new JTextField(10);
		JTextField firstInput = new JTextField(10);
		JTextField secondInput = new JTextField(10);
		
		
		
		textPanel.add(new JLabel("Enter Amount:"));
		textPanel.add(amountInput);
		amountInput.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
            		Integer.parseInt(amountInput.getText());
            		System.out.print(amountInput.getText());
            	}
            	catch(Exception ex) {
            		JOptionPane.showMessageDialog(textPanel, "Please Enter A Valid Amount");
            	}
            	
            }
            });
		
		
		textPanel.add(new JLabel("  Enter First Currency:"));
		textPanel.add(firstInput);
		
		
		textPanel.add(new JLabel("  Enter Second Currency:"));
		textPanel.add(secondInput);
		secondInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (amountInput.getText().isEmpty() || firstInput.getText().isEmpty() || secondInput.getText().isEmpty()) {
						JOptionPane.showMessageDialog(textPanel, "Please Provide Input");
					}
					else {
						String test = GetCurrency(firstInput.getText(), secondInput.getText());
						double rate = Double.parseDouble(test.substring(test.lastIndexOf(" "), test.length() - 1));
						System.out.print(rate);
						double convertedNum = Double.parseDouble(amountInput.getText()) * rate;
						JOptionPane.showMessageDialog(textPanel, Math.round(convertedNum));
					}
				}
				catch(Exception ex) {
					System.out.print("IN INPUT EXCEPTION");
				}
			}
		});

		return textPanel;
		
	}
	
	public JTabbedPane CurrencyTabs() {
		JTabbedPane tab = new JTabbedPane();
		
		return tab;
	}
	
	//Uses a URL GET request to acquire a JSON format of given conversion rates
	public static String GetCurrency(String firstA, String secondA) throws IOException {
		String output = null;
		JPanel popUp = new JPanel();
	    URL currencyUrl = new URL("https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/");
	    String newUrlString = currencyUrl.toString().concat(firstA.toLowerCase() + "/" + secondA.toLowerCase() + ".json");
	    URL newUrl = new URL(newUrlString);
	    String readLine = null;
	    HttpURLConnection conection = (HttpURLConnection) newUrl.openConnection();
	    conection.setRequestMethod("GET");
	    int responseCode = conection.getResponseCode();


	    if (responseCode == HttpURLConnection.HTTP_OK) {
	        BufferedReader in = new BufferedReader(
	            new InputStreamReader(conection.getInputStream()));
	        StringBuffer response = new StringBuffer();
	        while ((readLine = in.readLine()) != null) {
	            response.append(readLine);
	        }
	        
	        in.close();
	        output = response.toString();   
	    } 
	    else {
	        JOptionPane.showMessageDialog(popUp, "Please Try Again");
	    }
	    
	    return output;
	}
	
	

}
