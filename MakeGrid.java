import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class MakeGrid extends JPanel{

	int currentCellExchanges = 0;
	Exchange[] exchangeArray;
	// ArrayList<String> cellExchanges = new ArrayList<String>();
	Version1p4p1 makeApp = new Version1p4p1();
	
	int currentInitialExchangeCounter = 0;
	
	int currentEndX = 0;
	int currentEndY = 0;
	
	int startX = 0, startY = 0;
	int currentX = 0, currentY = 0;
	
	int widthSmall = 5, lengthSmall = 5;
	int widthMedium = 8, lengthMedium = 8;
	int widthLarge = 10, lengthLarge = 10;

	int width = widthMedium;
	int length = lengthMedium;
	
	JTextField chooseXLocation;
	JTextField chooseYLocation;
	
	JLabel blankLabel;
	
	JFrame frame;
	
	public void go() {
		frame = new JFrame("Exchange Grid");
		
		blankLabel = new JLabel("                              ");
		blankLabel.setForeground(Color.white);
		
		JPanel panelToggle = new JPanel();
		JPanel panelZoom = new JPanel();
		JPanel panelMove = new JPanel();
		JPanel panelChooseLocation = new JPanel();
		
		JPanel panelMap = new JPanel();
		
		JButton buttonUp = new JButton("Up");
		JButton buttonDown = new JButton("Down");
		JButton buttonLeft = new JButton("Left");
		JButton buttonRight = new JButton("Right");
		
		buttonUp.setFont(new Font("Calibri", Font.PLAIN, 16));
		buttonDown.setFont(new Font("Calibri", Font.PLAIN, 16));
		buttonLeft.setFont(new Font("Calibri", Font.PLAIN, 16));
		buttonRight.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		JButton buttonZoomIn = new JButton("+");
		JButton buttonZoomOut = new JButton("-");
		
		buttonZoomIn.setFont(new Font("Calibri", Font.PLAIN, 16));
		buttonZoomOut.setFont(new Font("Calibri", Font.PLAIN, 16));

		JButton buttonHome = new JButton("Home");
		
		buttonHome.setFont(new Font("Calibri", Font.PLAIN, 16));

		chooseXLocation = new JTextField("Enter x " + "\u2265" + " 0");
		chooseYLocation = new JTextField("Enter y " + "\u2265" + " 0");
		
		chooseXLocation.setFont(new Font("Calibri", Font.PLAIN, 16));
		chooseYLocation.setFont(new Font("Calibri", Font.PLAIN, 16));
		
		panelToggle.setBackground(Color.white);
		panelZoom.setBackground(Color.white);
		panelMove.setBackground(Color.white);
		panelMap.setBackground(Color.white);
		panelChooseLocation.setBackground(Color.white);
		
		panelMap.setLayout(new BoxLayout(panelMap, BoxLayout.X_AXIS));
		panelMap.setLayout(new GridLayout(width,length));
				
		panelZoom.add(buttonZoomIn);
		panelZoom.add(buttonZoomOut);
		panelZoom.add(blankLabel);
				
		panelMove.add(buttonUp);
		panelMove.add(buttonDown);
		panelMove.add(buttonLeft);
		panelMove.add(buttonRight);
		panelMove.add(blankLabel);		
		
		panelChooseLocation.add(chooseXLocation);
		panelChooseLocation.add(chooseYLocation);
		
		panelToggle.add(panelZoom);
		panelToggle.add(buttonHome);
		panelToggle.add(panelMove);
		panelToggle.add(panelChooseLocation);		
		
		
		for (int exchangeCounter = 0; exchangeCounter < width * length; exchangeCounter++) {
			MyJButton buttonExchange = new MyJButton();
			
			int[] cellID = new int[2];
			cellID[0] = currentX;
			cellID[1] = currentY;
			
			// set cell data in the MyJButton
			buttonExchange.setcoordinateX(currentX);
			buttonExchange.setcoordinateY(currentY);
			buttonExchange.setCellID(cellID);
			buttonExchange.setDistanceCustomer(currentX + currentY);
			
			exchangeArray = Version1p4p1.getExchangeArray();
			
			// store data for the exchanges occupying current cell (tricky)
			
			int numExchanges = Version1p4p1.getNumExchanges();
						
			currentCellExchanges = 0;
			
			for (Exchange exchange : exchangeArray) {
				if (exchange.getxLoc() == currentX &&  exchange.getyLoc() == currentY ) {
					currentCellExchanges++;
					buttonExchange.setExchangeID(exchange.getExchangeID());
					buttonExchange.setExchangeCapacity(exchange.getCapacity());
				}
			}
			
			// sets the label on the button according to the number of exchanges in the cell
			
			if (currentCellExchanges > 0) {
			buttonExchange.setText(Integer.toString(currentCellExchanges));
			}
			
			buttonExchange.setFont(new Font("Calibri", Font.BOLD, 20));
			
			if (currentX == 0 && currentY == 0) {
				buttonExchange.setText(buttonExchange.getText() + " (C)");
			}
			
			buttonExchange.setToolTipText("(" + Integer.toString(currentX) + 
					", " + Integer.toString(currentY) + ")");
			buttonExchange.setForeground(Color.gray);
			buttonExchange.addActionListener(new cellListener()); 
			
			// store data in button
			buttonExchange.setcoordinateX(currentX);
			buttonExchange.setcoordinateY(currentY);
			
			int[] exchangeCellID = new int[2];
			
			exchangeCellID[0] = currentX;
			exchangeCellID[1] = currentY;
			
			buttonExchange.setCellID(exchangeCellID);
			
			buttonExchange.setDistanceCustomer(currentX + currentY);
			
			buttonExchange.setNumExchanges(currentCellExchanges);
			
		/*	String[] exchangeIDArray = new String[currentCellExchanges];	
			int index = 0;
			for (Exchange exchange : exchangeArray) {
				if (exchange.getxLoc() == currentX &&  exchange.getyLoc() == currentY ) {
					exchangeIDArray[index] = exchange.getExchangeID();
				}
			} */
			
			// here it actually sets the button to store the exchange IDs - done already!
						
			// sets up coordinates for next cell
			// importantly, does not increment currentX or currentY at last cell. 
			if (currentX < startX + width - 1) {
				currentX++;
			}
			else {
				if (currentY < startY +  length - 1) {
					currentX = startX;
					currentY++;
				}
			}		
			
			panelMap.add(buttonExchange);
		}
		
		currentEndX = currentX;
		currentEndY = currentY;
		
		frame.getContentPane().add(BorderLayout.SOUTH, panelToggle);
		frame.getContentPane().add(panelMap);
		
		buttonUp.addActionListener(new UpListener()); 
		buttonDown.addActionListener(new DownListener()); 
		buttonLeft.addActionListener(new LeftListener()); 
		buttonRight.addActionListener(new RightListener()); 

		buttonZoomIn.addActionListener(new ZoomInListener()); 
		buttonZoomOut.addActionListener(new ZoomOutListener()); 
		
		chooseXLocation.addActionListener(new XListener());
		chooseYLocation.addActionListener(new YListener());
		
		buttonHome.addActionListener(new HomeListener());
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(750,750);
		frame.setVisible(true);
	}
	
	class HomeListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			currentX = 0;
			currentY = 0;
			
			startX = currentX;
			startY = currentY;
			
			frame.setVisible(false);
			frame.dispose();
			go();
		}
	}
	
	class UpListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			currentX = currentEndX - width + 1;
			currentY = currentEndY - 2*length + 1;
			
			if (currentX < 0 ) {
				currentX = 0;
			}
			
			if (currentY < 0) {
				currentY = 0;
			}
			startX = currentX;
			startY = currentY;
			
			frame.setVisible(false);
			frame.dispose();
			go();
		}
	}
	
	class DownListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			currentX = currentEndX - width + 1;
			currentY = currentEndY + 1;

			if (currentX < 0 ) {
				currentX = 0;
			}
			
			if (currentY < 0) {
				currentY = 0;
			}
			startX = currentX;
			startY = currentY;
			
			frame.setVisible(false);
			frame.dispose();
			go();			
		}
	}
	
	class LeftListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			currentX = currentEndX - 2*width + 1;
			currentY = currentEndY - length + 1;

			if (currentX < 0 ) {
				currentX = 0;
			}
			
			if (currentY < 0) {
				currentY = 0;
			}
			startX = currentX;
			startY = currentY;
			
			frame.setVisible(false);
			frame.dispose();
			go();	
		}
	}
	
	class RightListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			currentX = currentEndX + 1;
			currentY = currentEndY - length + 1;

			if (currentX < 0 ) {
				currentX = 0;
			}
			
			if (currentY < 0) {
				currentY = 0;
			}
			startX = currentX;
			startY = currentY;
			
			frame.setVisible(false);
			frame.dispose();
			go();	
		}
	}
	
	class XListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String inputLocX = chooseXLocation.getText();
			String inputLocY = chooseYLocation.getText();

			try {
				int integerLocX = Integer.parseInt(inputLocX); 
				int integerLocY = Integer.parseInt(inputLocY); 
				
				if (integerLocX >= 0) {
					currentX = integerLocX;
				}
				else {
					currentX = 0;
				}	
			
				if (integerLocY >= 0) {
					currentY = integerLocY;
				}
				else {
					currentY = 0;
				}
			}
			catch (Exception e) {
				chooseXLocation.setText("Enter x " + "\u2265" + " 0");
				chooseYLocation.setText("Enter y " + "\u2265" + " 0");
			}
						
			startX = currentX;
			startY = currentY;
			
			frame.setVisible(false);
			frame.dispose();
			go();	
		}
	}
	
	class YListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String inputLocX = chooseXLocation.getText();
			String inputLocY = chooseYLocation.getText();
			
			try {
				int integerLocX = Integer.parseInt(inputLocX); 
				int integerLocY = Integer.parseInt(inputLocY); 
				
				if (integerLocX >= 0) {
					currentX = integerLocX;
				}
				else {
					currentX = 0;
				}	
			
				if (integerLocY >= 0) {
					currentY = integerLocY;
				}
				else {
					currentY = 0;
				}
			}
			catch (Exception e) {
				chooseXLocation.setText("Enter x " + "\u2265" + " 0");
				chooseYLocation.setText("Enter y " + "\u2265" + " 0");
			}
			
			startX = currentX;
			startY = currentY;
			
			frame.setVisible(false);
			frame.dispose();
			go();	
		}
	}

	class ZoomInListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			currentX = currentEndX - width + 1;
			currentY = currentEndY - length + 1;

			startX = currentX;
			startY = currentY;
			
			if (width == widthLarge) {
				width = widthMedium;
				length = lengthMedium;
			}
			else if (width == widthMedium) {
				width = widthSmall;
				length = lengthSmall;
			}
			
			frame.setVisible(false);
			frame.dispose();
			go();
		}
	}
	
	class ZoomOutListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			currentX = currentEndX - width + 1;
			currentY = currentEndY - length + 1;

			startX = currentX;
			startY = currentY;
			
			if (width == widthSmall) {
				width = widthMedium;
				length = lengthMedium;
			}
			else if (width == widthMedium) {
				width = widthLarge;
				length = lengthLarge;
			}
			
			frame.setVisible(false);
			frame.dispose();
			go();
		}
	}
	
	class cellListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			// event.getSource() returns the actual button (as an object) pressed
			MyJButton buttonPressed = (MyJButton) event.getSource();
			
			int buttonNumExchanges = buttonPressed.getNumExchanges();			
			
			// initialise String ArrayList to store exchange IDs associated to that button (hence cell)
			ArrayList<String> copyExchangeIDs = new ArrayList<>();
			ArrayList<Integer> copyExchangeCapacities = new ArrayList<>();
						
			for (int k = 0; k < buttonNumExchanges; k++) {
				copyExchangeIDs.add(buttonPressed.getExchangeID(k));
				copyExchangeCapacities.add(buttonPressed.getExchangeCapacity(k));
			}
			
			int buttonXCoordinate = buttonPressed.getcoordinateX();
			int buttonYCoordinate = buttonPressed.getcoordinateY();
			int buttonCustomerDistance = buttonPressed.getDistanceCustomer();
			
			if (buttonNumExchanges == 0) {
				JFrame frameCell = new JFrame("Cell Information");
				if (buttonXCoordinate == 0 && buttonYCoordinate == 0) {
					JOptionPane.showMessageDialog(frameCell, "Cell (" + buttonXCoordinate + ", " +
							buttonYCoordinate + ") contains no exchanges.\n" +
							"The customer is contained in this cell.");
				}
				else {
					JOptionPane.showMessageDialog(frameCell, "Cell (" + buttonXCoordinate + ", " +
							buttonYCoordinate + ") contains no exchanges.");
				}
			}
			else if (buttonNumExchanges == 1) {
				JFrame frameCell = new JFrame("Cell Information");
				if (buttonXCoordinate == 0 && buttonYCoordinate == 0) {
					JOptionPane.showMessageDialog(frameCell, "Cell (" + buttonXCoordinate + ", " +
							buttonYCoordinate + ") contains 1 exchange.\n" +
									"Its ID is " + copyExchangeIDs.get(0) + ". " + "Its capacity is " + Integer.toString(copyExchangeCapacities.get(0)) + ".\n" +
									"The customer is contained in this cell.\n" +
									"The distance of the exchange from the customer is " + buttonCustomerDistance + ".");
				}
				else {
					JOptionPane.showMessageDialog(frameCell, "Cell (" + buttonXCoordinate + ", " +
				buttonYCoordinate + ") contains 1 exchange.\n" +
						"Its ID is " + copyExchangeIDs.get(0) + ". " + "Its capacity is " + Integer.toString(copyExchangeCapacities.get(0)) + ".\n" +
				"The distance of the exchange from the customer is " + buttonCustomerDistance + ".");
				}
			}
			else {
				String stringOfCells = "";
				String stringOfCapacities = "";
				
				if (buttonXCoordinate == 0 && buttonYCoordinate == 0) {
			
				for (int i = 0; i < buttonNumExchanges; i++) {
					if (i != buttonNumExchanges - 1) {
						stringOfCells = stringOfCells + copyExchangeIDs.get(i) + ", ";
						stringOfCapacities = stringOfCapacities + Integer.toString(copyExchangeCapacities.get(i)) + ", ";
					}
					else {
						stringOfCells = stringOfCells + copyExchangeIDs.get(i);
						stringOfCapacities = stringOfCapacities + Integer.toString(copyExchangeCapacities.get(i));
					}
				}
				JFrame frameCell = new JFrame("Cell Information");
				JOptionPane.showMessageDialog(frameCell, "Cell (" + buttonXCoordinate + ", " +
				buttonYCoordinate + ") contains " + buttonNumExchanges + " exchanges.\n" +
						"Their IDs are: " + stringOfCells  + ".\n" +
				"Their respective capacities are: " + stringOfCapacities + ".\n" +
				"The customer is contained in this cell.\n" +
				"The distance of these exchanges from the customer is " + buttonCustomerDistance + ".");
				}
				
				else {
					for (int i = 0; i < buttonNumExchanges; i++) {
						if (i != buttonNumExchanges - 1) {
							stringOfCells = stringOfCells + copyExchangeIDs.get(i) + ", ";
							stringOfCapacities = stringOfCapacities + Integer.toString(copyExchangeCapacities.get(i)) + ", ";
						}
						else {
							stringOfCells = stringOfCells + copyExchangeIDs.get(i);
							stringOfCapacities = stringOfCapacities + Integer.toString(copyExchangeCapacities.get(i));
						}
					}
					JFrame frameCell = new JFrame("Cell Information");
					JOptionPane.showMessageDialog(frameCell, "Cell (" + buttonXCoordinate + ", " +
					buttonYCoordinate + ") contains " + buttonNumExchanges + " exchanges.\n" +
							"Their IDs are: " + stringOfCells  + ".\n" +
					"Their respective capacities are: " + stringOfCapacities + ".\n" +
					"The distance of these exchanges from the customer is " + buttonCustomerDistance + ".");
				}
				
			}
			
		}
						
	}
}
