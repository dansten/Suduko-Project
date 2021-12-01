package sudoku;

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SudokuBoard {
	private int counter = 0;
	
	public SudokuBoard(Sudoku sudoku) {
		SwingUtilities.invokeLater(() -> createWindow(sudoku, "Sudoku", 600, 720));
	}
	
	private void createWindow(Sudoku sudoku, String title, int width, int height) {
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(width, height));
		
		Container pane = frame.getContentPane();
		JPanel buttonPanel = new JPanel();
		JPanel boardPanel = new JPanel();
		
		pane.add(buttonPanel, BorderLayout.SOUTH);
		pane.add(boardPanel, BorderLayout.CENTER);
		
		GridLayout layout = new GridLayout(9,9);
		boardPanel.setLayout(layout);
		
		JButton solveButton = new JButton("Solve");
		JButton clearButton = new JButton("Clear");
		JButton discoButton = new JButton("Disco");
		buttonPanel.add(solveButton);
		buttonPanel.add(clearButton);
		buttonPanel.add(discoButton);
		frame.getRootPane().setDefaultButton(solveButton);
		
		
		//bygger GUI med alla startvärden på sudokut
		JTextField[][] fields = new JTextField[9][9];
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				fields[i][j] = new JTextField();
				boardPanel.add(fields[i][j]);
				fields[i][j].setHorizontalAlignment(JTextField.CENTER);
				
				int nbr = sudoku.getNumber(i, j);
				if(nbr != 0) {
					fields[i][j].setText(String.valueOf(nbr));
				}
				
				//lägger till bakgrundsfärg på varannan box
				if((i >= 0 && i <= 2) && (j >= 0 && j <= 2) 
						|| (i >= 6 && i <= 8) && (j >= 0 && j <= 2) 
						|| (i >= 3 && i <= 5) && (j >= 3 && j <= 5) 
						|| (i >= 0 && i <= 2) && (j >= 6 && j <= 8) 
						|| (i >= 6 && i <= 8) && (j >= 6 && j <= 8)) {
					fields[i][j].setBackground(new Color(255, 114, 0));
				}
			}
		}
		discoButton.addActionListener(event -> {
			for(int i = 0; i < 9; i++) {
				for(int j = 0; j < 9; j++) { 
					if(counter % 2 == 0) {
						double r = Math.random();
						double g = Math.random();
						double b = Math.random();
						fields[i][j].setBackground(new Color((int) (r*255), (int) (g*255), (int) (b*255)));
					} else {
						if((i >= 0 && i <= 2) && (j >= 0 && j <= 2) 
								|| (i >= 6 && i <= 8) && (j >= 0 && j <= 2) 
								|| (i >= 3 && i <= 5) && (j >= 3 && j <= 5) 
								|| (i >= 0 && i <= 2) && (j >= 6 && j <= 8) 
								|| (i >= 6 && i <= 8) && (j >= 6 && j <= 8)) {
							fields[i][j].setBackground(new Color(255, 114, 0));
						} else {
							fields[i][j].setBackground(new Color(255, 255, 255));
						}
					}
					
				}
			}
			counter++;
		});
		solveButton.addActionListener(event -> {
			//hämtar alla värden från GUI och uppdaterar sudokut

			for(int i = 0; i < 9; i++) {
				for(int j = 0; j < 9; j++) {
					//gör en check om bara siffror har skrivit in (ignorerar "", då detta är en tom ruta)
					try {
						if(!fields[i][j].getText().equals("")) {
							int nbr = Integer.parseInt(fields[i][j].getText());
							if(nbr < 1 || nbr > 9) {
								throw new NumberFormatException("Out of bounds");
							}
						}
					
					}
					catch (NumberFormatException e) {
						//ger meddelande om att sträng fanns
						JOptionPane.showMessageDialog(null, "Only numbers (between 1-9 allowed)");
						return;
						//hoppar ut ur båda looparna, för att undivka fortsatt lösning av sudokut
					}
					if(fields[i][j].getText() == null || fields[i][j].getText().equals("")) {
						sudoku.clearNumber(i, j); 
					} else {
						int nbr = Integer.parseInt(fields[i][j].getText());
						sudoku.setNumber(i, j, nbr); 
					}
				}
			}
			
			//löser sudokut
			solve(sudoku, fields);
			
		});
		clearButton.addActionListener(event -> {
			//rensar sudukot
			sudoku.clear();
			
			//rensar GUI
			for(int i = 0; i < 9; i++) {
				for(int j = 0; j < 9; j++) {
					fields[i][j].setText("");
				}
			}
		});
		
		frame.pack();
		frame.setVisible(true);
	}
	
	//update interface
	private void solve(Sudoku sudoku, JTextField[][] fields) {
		if(sudoku.isAllValid()) {
			//ta hand om true/false
			if(sudoku.solve()) {
				for(int i = 0; i < 9; i++) {
					for(int j = 0; j < 9; j++) {
						int nbr = sudoku.getNumber(i, j);
						if(nbr != 0) {
							fields[i][j].setText(String.valueOf(nbr));
						}
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "Faluty solution, take another look...");
			}
			
			
			//uppdaterar GUI med lösta sudukot
			
		} else {
			//message prompt
			JOptionPane.showMessageDialog(null, "Faluty solution, take another look...");
		}
	}
	
	public static void main(String[] args) {
		Sudoku sudoku = new Sudoku();
		sudoku.setNumber(0, 0, 8);
		sudoku.setNumber(0, 1, 8);
		
		
		new SudokuBoard(sudoku);
		sudoku.solve();
		System.out.println(sudoku.toString());
	}

}
