package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.antlr.runtime.RecognitionException;

public class Settings {

	private JPanel configPane = new JPanel();

	private JTextField functionText1 = new JTextField("sin(x + t)");
	private JTextField functionText2 = new JTextField("cos(x + t)");
	private JTextField functionText3 = new JTextField("sin(x + t)*x");

	private JButton drawButton = new JButton("Draw");

	private Settings() {
		final JFrame settingsFrame = new JFrame("TopLevelDemo");
		settingsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setUpSizes();

		configPane.setLayout(new BoxLayout(configPane, BoxLayout.Y_AXIS));

		settingsFrame.setContentPane(configPane);
		configPane.add(functionText1);
		configPane.add(functionText2);
		configPane.add(functionText3);
		configPane.add(drawButton);

		drawButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				List<String> functions = new ArrayList<String>();
				functions.add(functionText1.getText());
				functions.add(functionText2.getText());
				functions.add(functionText3.getText());
				new Drawer(settingsFrame, functions);

			}
		});

		// Display the window.
		settingsFrame.pack();
		settingsFrame.setVisible(true);

	}

	private void setUpSizes() {
		functionText1.setPreferredSize(new Dimension(300, 20));
		functionText2.setPreferredSize(new Dimension(300, 20));
		functionText3.setPreferredSize(new Dimension(300, 20));
		drawButton.setPreferredSize(new Dimension(300, 20));

	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Settings();
			}
		});

	}

}
