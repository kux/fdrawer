package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import org.antlr.runtime.RecognitionException;

import parser.UncheckedParserException;

@SuppressWarnings("serial")
public class DrawingForm extends javax.swing.JFrame {

	private CalculatingWorker cworker;

	private JPanel mainPanel;
	private JPanel drawingPanel;
	private JSlider precisionSlider;
	private JButton drawButton;
	private JTable functionTable;
	private JPanel configPanel;
	private FDrawComponent functionDrawer;

	private JLabel timeLabel = new JLabel("t = ");
	private JButton pauseButton = new JButton("Pause");

	private NumberFormat nformatter = NumberFormat.getInstance();

	public DrawingForm() {
		super();
		initGUI();
		this.cworker = new CalculatingWorker(functionDrawer, this);
		nformatter.setMaximumFractionDigits(2);
	}

	public void setTime(double time) {
		this.timeLabel.setText("t = " + nformatter.format(time));
	}

	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			{
				this.mainPanel = new JPanel();
				BorderLayout mainPanelLayout = new BorderLayout();
				this.getContentPane().add(mainPanel, BorderLayout.CENTER);
				mainPanel.setLayout(mainPanelLayout);
				{
					this.configPanel = new JPanel();
					BoxLayout configPanelLayout = new BoxLayout(configPanel,
							BoxLayout.Y_AXIS);

					this.mainPanel.add(configPanel, BorderLayout.WEST);
					this.configPanel.setLayout(configPanelLayout);
					this.configPanel.setPreferredSize(new java.awt.Dimension(
							270, 350));
					{

						JScrollPane functionScroll = new JScrollPane();
						functionScroll.setPreferredSize(new Dimension(250, 99));
						functionScroll.setMaximumSize(new Dimension(250, 99));

						this.configPanel.add(Box.createRigidArea(new Dimension(
								0, 10)));
						this.configPanel.add(functionScroll);

						{

							TableModel functionTableModel = new AbstractTableModel() {
								private String[] columns = { "functions",
										"draw" };

								private Object[][] data = {
										{ "sin(x+t)+cos(y+t)",
												new Boolean(true) },
										{ "sin(x+t/2)*pow(x,2)",
												new Boolean(false) },
										{ "cos(x+t/4)*pow(x,2)",
												Boolean.valueOf(false) },
										{ "pow(x,2)", Boolean.valueOf(false) },
										{ "-pow(x,2)", Boolean.valueOf(false) } };

								@Override
								public int getColumnCount() {
									return columns.length;
								}

								@Override
								public int getRowCount() {
									return data.length;
								}

								@Override
								public Object getValueAt(int rowIndex,
										int columnIndex) {
									return data[rowIndex][columnIndex];
								}

								@Override
								public String getColumnName(int col) {
									return columns[col];
								}

								@Override
								public Class<?> getColumnClass(int c) {
									return getValueAt(0, c).getClass();
								}

								@Override
								public boolean isCellEditable(int row, int col) {
									return true;
								}

								@Override
								public void setValueAt(Object value, int row,
										int col) {
									if (col == 1) {
										for (int i = 0; i < data.length; ++i) {
											data[i][1] = Boolean.FALSE;
											if (i == row) {
												data[i][1] = value;
											}
											fireTableCellUpdated(i, col);
										}
										requestDrawing();
									} else {
										data[row][col] = value;
										fireTableCellUpdated(row, col);
									}
								}

							};

							this.functionTable = new JTable();
							this.functionTable.setAlignmentX(CENTER_ALIGNMENT);
							functionScroll.setViewportView(functionTable);
							this.functionTable.setModel(functionTableModel);
							this.functionTable.getColumnModel().getColumn(0)
									.setPreferredWidth(220);

						}
					}

					{
						JPanel precisionPanel = new JPanel();
						precisionPanel.setMaximumSize(new Dimension(250, 30));

						precisionPanel.add(new JLabel("precision:"));
						this.precisionSlider = new JSlider();
						this.precisionSlider.setMinimum(15);
						this.precisionSlider.setMaximum(30);
						this.precisionSlider.setValue(20);
						this.precisionSlider.setPreferredSize(new Dimension(80,
								20));

						precisionPanel.add(this.precisionSlider);
						this.drawButton = new JButton();
						this.drawButton.setPreferredSize(new Dimension(90, 20));
						this.drawButton.setText("Draw");

						precisionPanel.add(drawButton);

						this.configPanel.add(precisionPanel);
					}

					{
						JPanel timePanel = new JPanel();
						timePanel.setMaximumSize(new Dimension(240, 30));
						timePanel.setLayout(new BoxLayout(timePanel,
								BoxLayout.X_AXIS));

						this.timeLabel.setMinimumSize(new Dimension(50, 20));
						timePanel.add(this.timeLabel);
						timePanel.add(Box.createHorizontalGlue());

						timePanel.add(this.pauseButton);
						this.pauseButton
								.setPreferredSize(new Dimension(90, 20));
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
						functionDrawer = new FDrawComponent();
						functionDrawer
								.setPreferredSize(new Dimension(500, 400));
						drawingPanel.add(functionDrawer, BorderLayout.CENTER);
						functionDrawer.setBorder(new LineBorder(
								new java.awt.Color(0, 0, 0), 1, false));
					}
				}
			}
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void associateListners() {

		class DrawerMouseListener implements MouseWheelListener,
				MouseMotionListener, MouseListener {
			int x = -1;
			int y = -1;

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				int rotDir = e.getWheelRotation();
				if (rotDir == 1)
					DrawingForm.this.functionDrawer.zoomIn();
				else
					DrawingForm.this.functionDrawer.zoomOut();

			}

			@Override
			public void mouseDragged(MouseEvent e) {
				int xant = x;
				int yant = y;
				x = e.getX();
				y = e.getY();
				if (xant != -1 && yant != -1) {
					DrawingForm.this.functionDrawer.modifAngles(
							(double) (y - yant) / 100,
							(double) (x - xant) / 100);
				}

			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				x = -1;
				y = -1;
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				this.x = -1;
				this.y = -1;
			}

		}

		DrawerMouseListener mouseListener = new DrawerMouseListener();

		this.functionDrawer.addMouseWheelListener(mouseListener);
		this.functionDrawer.addMouseMotionListener(mouseListener);

		this.drawButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				requestDrawing();

			}
		});

		this.precisionSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				functionDrawer.modifyPrecsion(getActualPrecsion());

			}
		});

		this.pauseButton.addActionListener(new ActionListener() {
			private boolean pauzed = false;

			@Override
			public void actionPerformed(ActionEvent e) {
				pauzed = !pauzed;
				if (pauzed) {
					DrawingForm.this.cworker.pause();
					DrawingForm.this.pauseButton.setText("Resume");
				} else {
					DrawingForm.this.cworker.resume();
					DrawingForm.this.pauseButton.setText("Pause");
				}
			}
		});
	}

	private int getActualPrecsion() {
		return (int) Math.pow(1.2, this.precisionSlider.getValue());
	}

	private void requestDrawing() {
		TableModel tm = functionTable.getModel();
		List<String> functions = new ArrayList<String>();
		for (int i = 0; i < tm.getRowCount(); ++i) {
			if ((Boolean) tm.getValueAt(i, 1))
				functions.add((String) tm.getValueAt(i, 0));
		}

		try {
			this.cworker.changeDrawnFunctions(functions);
			functionDrawer.startDrawing(cworker, 5, new double[] { Math.PI / 2,
					-Math.PI / 4, 0 }, getActualPrecsion());
		} catch (UncheckedParserException e) {
			JOptionPane.showMessageDialog(this, "Incorrect function\n"
					+ e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} catch (RecognitionException e) {
			JOptionPane.showMessageDialog(this, "Incorrect function\n"
					+ e.getMessage());
		}
	}

	private void start() {
		this.cworker.execute();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				DrawingForm inst = new DrawingForm();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);

				inst.associateListners();
				inst.requestDrawing();
				inst.start();
			}
		});
	}

}
