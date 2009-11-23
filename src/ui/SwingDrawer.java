package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import model.CalculatingWorker;
import model.FunctionEvaluator;
import model.Matrix;

import org.antlr.runtime.RecognitionException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import parser.UncheckedParserException;

@SuppressWarnings("serial")
public class SwingDrawer implements DrawingView {

	private static Logger logger = Logger.getLogger(SwingDrawer.class);

	private CalculatingWorker cworker;

	private JFrame applicationFrame;

	private JPanel mainPanel;
	private JPanel drawingPanel;
	private JSlider precisionSlider;
	private JTable functionTable;
	private JPanel configPanel;
	private FDrawComponent functionDrawer;

	private JLabel timeLabel = new JLabel("t = ");
	private JButton pauseButton = new JButton("Pause");
	private JButton resetButton = new JButton("Reset");
	private JProgressBar drawingProgress = new JProgressBar(0, 100);
	private JCheckBox enableRealTime = new JCheckBox("real time drawing");

	private NumberFormat nformatter = NumberFormat.getInstance();

	public SwingDrawer(String title) {
		applicationFrame = new JFrame(title);
		this.cworker = new CalculatingWorker();
		this.functionDrawer = FDrawComponent.createInstance(cworker);
		cworker.setFeedbackReceiver(this);
		initGUI();
		nformatter.setMaximumFractionDigits(1);
		nformatter.setMaximumIntegerDigits(4);
		functionTable.setValueAt(Boolean.TRUE, 0, 1);

	}

	/**
	 * 
	 */
	public void setTime(final double time) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				SwingDrawer.this.timeLabel.setText("t = " + nformatter.format(time));
			}
		});
	}

	public void setProgress(final int progress) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				drawingProgress.setValue(progress);

			}
		});
	}

	private void initGUI() {
		try {
			applicationFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			{
				this.mainPanel = new JPanel();
				BorderLayout mainPanelLayout = new BorderLayout();
				this.applicationFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
				mainPanel.setLayout(mainPanelLayout);
				{
					this.configPanel = new JPanel();
					this.configPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
					BoxLayout configPanelLayout = new BoxLayout(configPanel, BoxLayout.Y_AXIS);

					this.mainPanel.add(configPanel, BorderLayout.WEST);
					this.configPanel.setLayout(configPanelLayout);
					this.configPanel.setPreferredSize(new java.awt.Dimension(270, 350));
					{

						JScrollPane functionScroll = new JScrollPane();
						functionScroll.setPreferredSize(new Dimension(300, 99));
						functionScroll.setMaximumSize(new Dimension(300, 99));

						this.configPanel.add(Box.createRigidArea(new Dimension(0, 10)));
						this.configPanel.add(functionScroll);

						{

							TableModel functionTableModel = new DrawingTableModel();
							this.functionTable = new JTable();
							this.functionTable.setAlignmentX(JComponent.CENTER_ALIGNMENT);
							functionScroll.setViewportView(functionTable);
							this.functionTable.setModel(functionTableModel);
							this.functionTable.getColumnModel().getColumn(0).setPreferredWidth(220);

						}
					}

					{
						JPanel precisionPanel = new JPanel();
						precisionPanel.setMaximumSize(new Dimension(300, 30));
						precisionPanel.setLayout(new BoxLayout(precisionPanel, BoxLayout.X_AXIS));

						precisionPanel.add(new JLabel("precision:"));

						this.precisionSlider = new JSlider();
						this.precisionSlider.setMinimum(15);
						this.precisionSlider.setMaximum(30);
						this.precisionSlider.setValue(20);
						this.precisionSlider.setPreferredSize(new Dimension(150, 20));
						this.precisionSlider.setMaximumSize(new Dimension(90, 20));
						precisionPanel.add(this.precisionSlider);
						precisionPanel.add(Box.createHorizontalGlue());

						this.configPanel.add(precisionPanel);
					}

					{
						JPanel timePanel = new JPanel();
						timePanel.setMaximumSize(new Dimension(300, 30));
						timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.X_AXIS));

						this.timeLabel.setMinimumSize(new Dimension(70, 20));
						this.timeLabel.setPreferredSize(new Dimension(70, 20));
						this.timeLabel.setMaximumSize(new Dimension(70, 20));
						timePanel.add(this.timeLabel);
						timePanel.add(Box.createHorizontalGlue());

						timePanel.add(this.resetButton);
						this.resetButton.setPreferredSize(new Dimension(80, 20));
						this.resetButton.setMaximumSize(new Dimension(80, 20));
						timePanel.add(Box.createHorizontalGlue());

						timePanel.add(this.pauseButton);
						this.pauseButton.setPreferredSize(new Dimension(90, 20));
						this.pauseButton.setMaximumSize(new Dimension(90, 20));

						this.configPanel.add(timePanel);

					}

				}
				{
					drawingPanel = new JPanel();
					BorderLayout drawingPanelLayout = new BorderLayout();
					mainPanel.add(drawingPanel, BorderLayout.CENTER);
					drawingPanel.setLayout(drawingPanelLayout);
					{
						functionDrawer.setPreferredSize(new Dimension(500, 400));
						drawingPanel.add(functionDrawer, BorderLayout.CENTER);
						functionDrawer.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1,
								false));
					}

					JPanel drawingBottom = new JPanel();
					drawingBottom.setLayout(new BorderLayout());

					drawingProgress = new JProgressBar(0, 100);
					drawingProgress.setValue(0);

					drawingBottom.add(drawingProgress, BorderLayout.CENTER);
					drawingBottom.add(enableRealTime, BorderLayout.EAST);
					enableRealTime.setSelected(true);
					drawingPanel.add(drawingBottom, BorderLayout.SOUTH);
				}
			}
			applicationFrame.pack();
			applicationFrame.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void associateListners() {

		this.precisionSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (functionDrawer.getType() == FDrawComponent.Type.DRAW3D)
					functionDrawer.modifyPrecsion3d(get3dPrecision());
				else
					functionDrawer.modifyPrecsion2d(get2dPrecision());
			}
		});

		this.pauseButton.addActionListener(new ActionListener() {
			private boolean pauzed = false;

			@Override
			public void actionPerformed(ActionEvent e) {
				pauzed = !pauzed;
				if (pauzed) {
					SwingDrawer.this.cworker.pause();
					SwingDrawer.this.pauseButton.setText("Resume");
				} else {
					SwingDrawer.this.cworker.resume();
					SwingDrawer.this.pauseButton.setText("Pause");
				}
			}
		});

		this.resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SwingDrawer.this.cworker.setTime(0);
			}
		});

		enableRealTime.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					cworker.setRealTime(true);
				} else if (e.getStateChange() == ItemEvent.DESELECTED) {
					cworker.setRealTime(false);
				}

			}
		});
	}

	private int get3dPrecision() {
		return (int) Math.pow(1.2, this.precisionSlider.getValue());
	}

	private int get2dPrecision() {
		return (int) Math.pow(1.4, this.precisionSlider.getValue());
	}

	private void start() {
		this.cworker.start();
	}

	private class DrawingTableModel extends AbstractTableModel {
		private String[] columns = { "functions", "draw" };
		private List<String> allowedVariables = Arrays.asList(new String[] { "x", "y", "t" });

		private Object[][] data = new Object[5][];

		private DrawingTableModel() {
			try {
				data[0] = new Object[] { new FunctionEvaluator("sin(x+t)+cos(y+t)"), Boolean.FALSE,
						FDrawComponent.Type.DRAW3D };
				data[1] = new Object[] { new FunctionEvaluator("sin(x+t/2)*pow(y,2)"),
						Boolean.FALSE, FDrawComponent.Type.DRAW3D };
				data[2] = new Object[] { new FunctionEvaluator("cos(x+t/4)*pow(x,2)"),
						Boolean.FALSE, FDrawComponent.Type.DRAW2D };
				data[3] = new Object[] { new FunctionEvaluator("pow(x,2)"), Boolean.FALSE,
						FDrawComponent.Type.DRAW2D };
				data[4] = new Object[] { new FunctionEvaluator("-pow(x,2)"), Boolean.FALSE,
						FDrawComponent.Type.DRAW2D };
			} catch (UncheckedParserException e) {
				assert true;
			} catch (RecognitionException e) {
				assert true;
			}
		}

		@Override
		public int getColumnCount() {
			return columns.length;
		}

		@Override
		public int getRowCount() {
			return data.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			switch (columnIndex) {
			case 0:
				return ((FunctionEvaluator) data[rowIndex][0]).getFunction();
			default:
				return data[rowIndex][columnIndex];
			}
		}

		@Override
		public String getColumnName(int col) {
			return columns[col];
		}

		@Override
		public Class<?> getColumnClass(int c) {
			switch (c) {
			case 0:
				return String.class;
			case 1:
				return Boolean.class;
			default:
				return Object.class;
			}
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			return true;
		}

		@Override
		public void setValueAt(Object value, int row, int col) {
			if (col == 0) {
				String function = (String) value;
				try {
					FunctionEvaluator evaluator = new FunctionEvaluator(function);
					Set<String> variables = evaluator.getVariables();
					if (this.allowedVariables.containsAll(variables)) {
						data[row][0] = evaluator;
						if (variables.contains("x") && variables.contains("y")) {
							data[row][2] = FDrawComponent.Type.DRAW3D;
						} else {
							data[row][2] = FDrawComponent.Type.DRAW2D;
						}
						if ((Boolean) data[row][1] == Boolean.TRUE)
							setValueAt(Boolean.TRUE, row, 1);
					} else {
						JOptionPane.showMessageDialog(applicationFrame,
								"Only x, y, and t variables are supported !", "Error",
								JOptionPane.ERROR_MESSAGE);
					}

					fireTableCellUpdated(row, col);

				} catch (UncheckedParserException e) {
					JOptionPane.showMessageDialog(applicationFrame, e.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				} catch (RecognitionException e) {
					JOptionPane.showMessageDialog(applicationFrame, e.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}

			if (col == 1) {
				if (value == Boolean.TRUE) {
					if (data[row][2] == FDrawComponent.Type.DRAW3D)
						unselectAll();
					else
						unsellectAll3d();
				}

				data[row][1] = value;

				requestDrawing();
			}
		}

		private void unselectAll() {
			for (int i = 0; i < data.length; ++i) {
				data[i][1] = Boolean.FALSE;
				fireTableCellUpdated(i, 1);
			}
		}

		private void unsellectAll3d() {
			for (int i = 0; i < data.length; ++i) {
				if (data[i][2] == FDrawComponent.Type.DRAW3D) {
					data[i][1] = Boolean.FALSE;
					fireTableCellUpdated(i, 1);
				}
			}
		}

		private void requestDrawing() {
			cworker.removeAllDrawnFunctions();

			int drawn = 0;
			boolean has3d = false;
			for (int i = 0; i < data.length; ++i) {
				if ((Boolean) data[i][1] == Boolean.TRUE) {
					List<FunctionEvaluator> existingEvaluators = cworker.getDrawnFunctions();
					existingEvaluators.add((FunctionEvaluator) data[i][0]);
					cworker.setDrawnFunctions(existingEvaluators);

					if (data[i][2] == FDrawComponent.Type.DRAW3D)
						has3d = true;
					++drawn;
				}
			}

			if (has3d) {
				functionDrawer
						.set3dDrawingProperties(5, new double[] { 0, 0, 0 }, get3dPrecision());
			} else {
				functionDrawer.set2dDrawingProperties(-5, 5, get2dPrecision());
			}

			if (drawn == 0) {
				cworker.setTime(0);
				cworker.pause();
			} else {
				cworker.resume();
			}

		}
	}

	public static void main(String[] args) {
		PropertyConfigurator.configure(SwingDrawer.class.getClassLoader().getResource(
				"logging.properties"));

		logger.info("started");

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SwingDrawer inst = new SwingDrawer("Simple math tool");

				inst.associateListners();
				inst.start();
			}
		});
	}

	@Override
	public void drawMatrixes(LinkedHashMap<String, double[]> variableMap,
			List<Matrix<Double>> toDraw) {
		functionDrawer.drawMatrixes(variableMap, toDraw);

	}

}
