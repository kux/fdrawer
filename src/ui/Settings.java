package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.antlr.runtime.RecognitionException;

public class Settings {

	private JPanel configPane = new JPanel();

	private JTextField functionText = new JTextField("sin(x + t)");
	private JButton drawButton = new JButton("Draw");

	private Settings() {
		final JFrame settingsFrame = new JFrame("TopLevelDemo");
		settingsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setUpSizes();
		
//		configPane.setLayout(new BoxLayout(configPane, BoxLayout.Y_AXIS));

		settingsFrame.setContentPane(configPane);
		configPane.add(functionText);
		configPane.add(drawButton);

		drawButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					FDrawComponent drawing = new FDrawComponent(
							functionText.getText());
					JDialog drawingD = new JDialog(settingsFrame,true);
					
					drawing.setPreferredSize(new Dimension(500,500));
					drawingD.add(drawing);
					drawingD.pack();
					drawingD.setVisible(true);
					
				} catch (RecognitionException e1) {
					// TODO Add custom error dialog
					e1.printStackTrace();
				}

			}
		});

		// Display the window.
		settingsFrame.pack();
		settingsFrame.setVisible(true);

	}
	

	private void setUpSizes() {
		functionText.setPreferredSize(new Dimension(300, 20));
		drawButton.setPreferredSize(new Dimension(100, 20));

	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Settings();
			}
		});

	}

}
