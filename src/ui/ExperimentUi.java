package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ExperimentUi {
	
	private JPanel drawingPane;
	
	private ExperimentUi(){
		JFrame drawingFrame = new JFrame("TopLevelDemo");
        drawingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		drawingPane = new FunctionPane(new BorderLayout());
		drawingPane.setPreferredSize(new Dimension(800, 600));
        drawingFrame.setContentPane(drawingPane);
                
        //Display the window.
        drawingFrame.pack();
        drawingFrame.setVisible(true);		
       
	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new ExperimentUi();
			}
		});

	}

}
