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
	JPanel convPanel = new JPanel(); 
	JButton firstButton;
	JTextField amountInput, firstInput, secondInput;
	static StringBuffer response;
	
	
	//Setting up the overall window
	public Window() {
		super("Currency Exchange");
		JTabbedPane tabPane = CurrencyTabs();
	    add(tabPane);  

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(650, 350);
		Image icon = Toolkit.getDefaultToolkit().getImage("CCash.jpg");
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
        setLocation((d.width - frameSize.width) / 2, (d.height - frameSize.height) / 3);
        
        setResizable(false);
		setIconImage(icon);
		convPanel.add(TextSetup(), BorderLayout.NORTH);
		convPanel.add(Buttons(), BorderLayout.SOUTH);
		setVisible(true);
	}
	
	//Setup for conversion button
	public JPanel Buttons() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.green);
		firstButton = new JButton("Convert");
		firstButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int validity = ValidTest();
				try {
					switch (validity) {
						case 0:
							JOptionPane.showMessageDialog(buttonPanel, "Please Provide Input");
							break;
						case 1:
							JOptionPane.showMessageDialog(buttonPanel, "Please Provide Valid Amount");
							break;
						case 2:
							JOptionPane.showMessageDialog(buttonPanel, "Invalid Currency\nPlease refer to Info tab", "Currency Error", JOptionPane.ERROR_MESSAGE);
						default:
							System.out.print("EVERYTHING IS FINE");
							String test = GetCurrency(firstInput.getText(), secondInput.getText());
							double rate = Double.parseDouble(test.substring(test.lastIndexOf(" "), test.length() - 1));
							double convertedNum = Double.parseDouble(amountInput.getText()) * rate;
							JOptionPane.showMessageDialog(buttonPanel, convertedNum);
							//GetCurrency(firstInput.getText(), secondInput.getText());
					}		
				}
				catch (Exception ex) {
					System.out.print("BUTTON ERROR");
				}
			}
		});
		buttonPanel.add(firstButton);
		return buttonPanel;
	}
	
	//Check if users input is valid
	public int ValidTest() {
		if (amountInput.getText().isEmpty() || firstInput.getText().isEmpty() || secondInput.getText().isEmpty()) {
			return 0;
		}
		
		if (!(amountInput.getText().isEmpty())) {
			try {
				Integer.parseInt(amountInput.getText());
			}
			catch (Exception ex) {
				return 1;
				
			}
		}
		
		if ( "x" == "x") {
			return 3;
		}
		
		else {
			return 2;
		}
	}
	
	//Setup for various texts present in the window
	public JPanel TextSetup() {
		JPanel textPanel = new JPanel();
		//textPanel.setBackground(Color.BLACK);
		textPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		amountInput = new JTextField(5);
		firstInput = new JTextField(5);
		secondInput = new JTextField(5);
		
		
		
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
//		secondInput.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				try {
//					if (amountInput.getText().isEmpty() || firstInput.getText().isEmpty() || secondInput.getText().isEmpty()) {
//						JOptionPane.showMessageDialog(textPanel, "Please Provide Input");
//					}
//					else {
//						String test = GetCurrency(firstInput.getText(), secondInput.getText());
//						double rate = Double.parseDouble(test.substring(test.lastIndexOf(" "), test.length() - 1));
//						System.out.print(rate);
//						double convertedNum = Double.parseDouble(amountInput.getText()) * rate;
//						JOptionPane.showMessageDialog(textPanel, convertedNum);
//					}
//				}
//				catch(Exception ex) {
//					System.out.print("IN INPUT EXCEPTION");
//				}
//			}
//		});

		return textPanel;
		
	}
	
	//Setup tabs
	public JTabbedPane CurrencyTabs() {   
	    JPanel graphPanel = new JPanel();   
	    JPanel infoPanel = new JPanel();
	    JTabbedPane tabPane = new JTabbedPane();  
	    tabPane.add("Convert", convPanel);  
	    tabPane.add("Graph", graphPanel);
	    tabPane.add("Info", infoPanel); 
		
		return tabPane;
	}
	
	//Uses a URL GET request to acquire a JSON format of conversion rates
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
	        response = new StringBuffer();
	        while ((readLine = in.readLine()) != null) {
	            response.append(readLine);
	        }
	        
	        in.close();
	        output = response.toString();   
	    } 
	    else {
	        JOptionPane.showMessageDialog(popUp, "Invalid Currency\nPlease refer to Info tab", "Currency Error", JOptionPane.ERROR_MESSAGE);
	    }
	    
	    return output;
	}
	
	public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Window();
            }
        });
    }
	
	

}
