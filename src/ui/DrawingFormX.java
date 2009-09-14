package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;

import javax.swing.JButton;
import javax.swing.JLabel;
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


@SuppressWarnings("serial")
public class DrawingFormX extends javax.swing.JFrame {
	private JPanel mainPanel;
	private JPanel drawingPanel;
	private JScrollPane functionScroll;
	private JSlider precisionSlider;
	private JLabel precisionLabel;
	private JButton drawButton;
	private JTable functionTable;
	private JPanel configPanel;
	private FDrawComponent functionDrawer;


	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				DrawingFormX inst = new DrawingFormX();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

	public DrawingFormX() {
		super();
		initGUI();
		associateListners();
	}

	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			{
				mainPanel = new JPanel();
				BorderLayout mainPanelLayout = new BorderLayout();
				getContentPane().add(mainPanel, BorderLayout.CENTER);
				mainPanel.setLayout(mainPanelLayout);
				{
					configPanel = new JPanel();
					GridBagLayout configPanelLayout = new GridBagLayout();
					configPanelLayout.rowWeights = new double[] { 0.0, 0.0,
							0.0, 0.0, 0.1, 0.0 };
					configPanelLayout.rowHeights = new int[] { 10, 98, 20, 20,
							105, 10 };
					configPanelLayout.columnWeights = new double[] { 0.0, 0.0,
							0.0, 0.1 };
					configPanelLayout.columnWidths = new int[] { 10, 90, 130,
							10 };
					mainPanel.add(configPanel, BorderLayout.WEST);
					configPanel.setLayout(configPanelLayout);
					configPanel.setPreferredSize(new java.awt.Dimension(240,
							350));
					{
						functionScroll = new JScrollPane();
						configPanel.add(functionScroll, new GridBagConstraints(
								1, 1, 2, 1, 0.0, 0.0,
								GridBagConstraints.CENTER,
								GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 0), 0, 0));
						{

							TableModel functionTableModel = new AbstractTableModel() {
								private String[] columns = { "functions",
										"draw" };

								private Object[][] data = {
										{ "sin(x+t)", new Boolean(true) },
										{ "sin(x+t/2)*pow(x,2)",
												new Boolean(true) },
										{ "cos(x+t/4)*pow(x,2)",
												new Boolean(true) },
										{ "pow(x,2)", new Boolean(true) },
										{ "-pow(x,2)", new Boolean(true) } };

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
								public Class getColumnClass(int c) {
									return getValueAt(0, c).getClass();
								}

								@Override
								public boolean isCellEditable(int row, int col) {
									return true;
								}

								@Override
								public void setValueAt(Object value, int row,
										int col) {
									data[row][col] = value;
									fireTableCellUpdated(row, col);
								}
							};

							functionTable = new JTable();
							functionScroll.setViewportView(functionTable);
							functionTable.setModel(functionTableModel);
							functionTable.getColumnModel().getColumn(0)
									.setPreferredWidth(220);

						}
					}
					{
						drawButton = new JButton();
						configPanel.add(drawButton, new GridBagConstraints(1,
								2, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER,
								GridBagConstraints.HORIZONTAL, new Insets(0, 0,
										0, 0), 0, 0));
						drawButton.setText("Draw");

					}
					{
						precisionLabel = new JLabel();
						configPanel.add(precisionLabel, new GridBagConstraints(
								1, 3, 1, 1, 0.0, 0.0,
								GridBagConstraints.LINE_END,
								GridBagConstraints.NONE,
								new Insets(0, 0, 0, 0), 0, 0));
						precisionLabel.setText("precision");
					}
					{
						precisionSlider = new JSlider();
						configPanel.add(precisionSlider,
								new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
										GridBagConstraints.CENTER,
										GridBagConstraints.HORIZONTAL,
										new Insets(0, 0, 0, 0), 0, 0));
						precisionSlider.setMinimum(10);
						precisionSlider.setMaximum(20);
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
					DrawingFormX.this.functionDrawer.zoomIn();
				else
					DrawingFormX.this.functionDrawer.zoomOut();

			}

			@Override
			public void mouseDragged(MouseEvent e) {
				int xant = x;
				int yant = y;
				x = e.getX();
				y = e.getY();
				if (xant != -1 && yant != -1 && (Math.abs(xant - x) > 1 || Math.abs(yant - y) > 1)) {
					DrawingFormX.this.functionDrawer.moveWith(x - xant, y
							- yant);
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

		functionDrawer.addMouseWheelListener(mouseListener);
		functionDrawer.addMouseMotionListener(mouseListener);

		drawButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TableModel tm = functionTable.getModel();
				List<String> functions = new ArrayList<String>();
				for (int i = 0; i < tm.getRowCount(); ++i) {
					if ((Boolean) tm.getValueAt(i, 1))
						functions.add((String) tm.getValueAt(i, 0));
				}

				functionDrawer.startDrawing(functions, -3, 5, getActualPrecsion());

			}
		});
		
		precisionSlider.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				functionDrawer.modifyPrecsion(getActualPrecsion());
				
			}
		});
	}
	
	private int getActualPrecsion(){
		return (int)Math.pow(1.5,precisionSlider.getValue());
	}

}
