package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.antlr.runtime.RecognitionException;

/**
 * 
 * @author kux
 */
public class DrawingForm extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel drawingPane;
	private FDrawComponent functionDrawing;

	private javax.swing.JPanel feedbackPane;

	private javax.swing.JPanel functionPane;
	private javax.swing.JTextField function1;
	private javax.swing.JTextField function2;
	private javax.swing.JTextField function3;
	private javax.swing.JTextField function4;
	private javax.swing.JCheckBox check1;
	private javax.swing.JCheckBox check2;
	private javax.swing.JCheckBox check3;
	private javax.swing.JCheckBox check4;
	private javax.swing.JButton drawButton;
	private boolean checked1 = true;
	private boolean checked2 = true;
	private boolean checked3 = true;
	private boolean checked4 = true;

	private javax.swing.JPanel movementPane;
	private javax.swing.JButton zoomIn;
	private javax.swing.JButton zoomOut;
	private javax.swing.JButton moveRight;
	private javax.swing.JButton moveLeft;
	private javax.swing.JButton moveDown;
	private javax.swing.JButton moveUp;

	/** Creates new form DrawingForm */
	public DrawingForm() {
		initComponents();
		associateListners();
		draw();
	}

	private void associateListners() {
		drawButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				draw();
			}
		});

		zoomIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				functionDrawing.zoomIn();
			}

		});

		zoomOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				functionDrawing.zoomOut();
			}
		});

		moveLeft.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				functionDrawing.moveLeft();
			}
		});

		moveRight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				functionDrawing.moveRight();
			}
		});

		moveDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				functionDrawing.moveDown();
			}
		});

		moveUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				functionDrawing.moveUp();
			}
		});

		check1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				checked1 = !checked1;
			}
		});
		check2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				checked2 = !checked2;
			}
		});

		check3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				checked3 = !checked3;
			}
		});

		check4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				checked4 = !checked4;
			}
		});

	}

	private void draw() {
		List<String> functions = new ArrayList<String>();

		if (checked1)
			functions.add(function1.getText());
		if (checked2)
			functions.add(function2.getText());
		if (checked3)
			functions.add(function3.getText());
		if (checked4)
			functions.add(function4.getText());
		
		try {
			functionDrawing.startDrawing(functions);
		} catch (RecognitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initComponents() {

		functionPane = new javax.swing.JPanel();
		function1 = new javax.swing.JTextField("sin (x+t)*x*x/5");
		function2 = new javax.swing.JTextField("cos (x+t)*x*x/5");
		function3 = new javax.swing.JTextField("x*x/5");
		function4 = new javax.swing.JTextField("-x*x/5");
		check1 = new javax.swing.JCheckBox();
		check2 = new javax.swing.JCheckBox();
		check3 = new javax.swing.JCheckBox();
		check4 = new javax.swing.JCheckBox();
		drawButton = new javax.swing.JButton();
		movementPane = new javax.swing.JPanel();
		zoomIn = new javax.swing.JButton();
		zoomOut = new javax.swing.JButton();
		moveRight = new javax.swing.JButton();
		moveLeft = new javax.swing.JButton();
		moveDown = new javax.swing.JButton();
		moveUp = new javax.swing.JButton();
		feedbackPane = new javax.swing.JPanel();
		drawingPane = new javax.swing.JPanel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		functionPane.setBorder(javax.swing.BorderFactory
				.createLineBorder(new java.awt.Color(0, 0, 0)));

		drawButton.setText("Draw !");

		javax.swing.GroupLayout functionPaneLayout = new javax.swing.GroupLayout(
				functionPane);
		functionPane.setLayout(functionPaneLayout);
		functionPaneLayout
				.setHorizontalGroup(functionPaneLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								functionPaneLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												functionPaneLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																drawButton,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																209,
																Short.MAX_VALUE)
														.addGroup(
																functionPaneLayout
																		.createSequentialGroup()
																		.addGroup(
																				functionPaneLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addComponent(
																								function4,
																								javax.swing.GroupLayout.Alignment.LEADING,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								183,
																								Short.MAX_VALUE)
																						.addComponent(
																								function2,
																								javax.swing.GroupLayout.Alignment.LEADING,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								183,
																								Short.MAX_VALUE)
																						.addComponent(
																								function3,
																								javax.swing.GroupLayout.Alignment.LEADING,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								183,
																								Short.MAX_VALUE)
																						.addComponent(
																								function1,
																								javax.swing.GroupLayout.Alignment.LEADING,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								183,
																								Short.MAX_VALUE))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				functionPaneLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addComponent(
																								check1)
																						.addComponent(
																								check2)
																						.addComponent(
																								check3)
																						.addComponent(
																								check4))))
										.addContainerGap()));
		functionPaneLayout
				.setVerticalGroup(functionPaneLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								functionPaneLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												functionPaneLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(check1)
														.addComponent(
																function1,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												functionPaneLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																function2,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(check2))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												functionPaneLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																function3,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(check3))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												functionPaneLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																function4,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(check4))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(drawButton)
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		movementPane.setBorder(javax.swing.BorderFactory
				.createLineBorder(new java.awt.Color(0, 0, 0)));

		zoomIn.setText("+");

		zoomOut.setText("-");

		moveRight.setText(">");

		moveLeft.setText("<");

		moveDown.setText("v");

		moveUp.setText("^");

		javax.swing.GroupLayout movementPaneLayout = new javax.swing.GroupLayout(
				movementPane);
		movementPane.setLayout(movementPaneLayout);
		movementPaneLayout
				.setHorizontalGroup(movementPaneLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								movementPaneLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												zoomIn,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												58,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												zoomOut,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												53,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												203, Short.MAX_VALUE)
										.addComponent(
												moveUp,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												50,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												moveDown,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												48,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												moveLeft,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												52,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												moveRight,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												46,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));
		movementPaneLayout
				.setVerticalGroup(movementPaneLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								movementPaneLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												movementPaneLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(zoomIn)
														.addComponent(moveRight)
														.addComponent(moveDown)
														.addComponent(moveLeft)
														.addComponent(moveUp)
														.addComponent(zoomOut))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		feedbackPane.setBorder(javax.swing.BorderFactory
				.createLineBorder(new java.awt.Color(0, 0, 0)));

		javax.swing.GroupLayout feedbackPaneLayout = new javax.swing.GroupLayout(
				feedbackPane);
		feedbackPane.setLayout(feedbackPaneLayout);
		feedbackPaneLayout.setHorizontalGroup(feedbackPaneLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 233, Short.MAX_VALUE));
		feedbackPaneLayout.setVerticalGroup(feedbackPaneLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 257, Short.MAX_VALUE));

		drawingPane.setBorder(javax.swing.BorderFactory
				.createLineBorder(new java.awt.Color(0, 0, 0)));

		BorderLayout drawingPaneLayout = new BorderLayout();

		drawingPane.setLayout(drawingPaneLayout);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout
				.setHorizontalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																false)
														.addComponent(
																functionPane,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																feedbackPane,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																drawingPane,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																movementPane,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addContainerGap()));
		layout
				.setVerticalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addComponent(
																				functionPane,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				18,
																				18,
																				18)
																		.addComponent(
																				feedbackPane,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE))
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addComponent(
																				movementPane,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(
																				18,
																				18,
																				18)
																		.addComponent(
																				drawingPane,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)))
										.addContainerGap()));

		functionDrawing = new FDrawComponent();
		drawingPane.add(functionDrawing, BorderLayout.CENTER);

		check1.setSelected(true);
		check2.setSelected(true);
		check3.setSelected(true);
		check4.setSelected(true);

		pack();
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new DrawingForm().setVisible(true);
			}
		});
	}

}
