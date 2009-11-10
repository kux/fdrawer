package ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import model.FunctionEvaluator;
import model.Matrix;

class CalculatingWorker extends SwingWorker<Void, List<Matrix<Double>>> {

	private final List<FunctionEvaluator> evaluators;
	private final LinkedHashMap<String, double[]> varMap;
	private volatile boolean calculate = true;

	private double timeStart;
	private double timeIncrement;

	private DrawsFunctions drawer;

	public CalculatingWorker(List<FunctionEvaluator> evaluators,
			LinkedHashMap<String, double[]> varMap, double timeStart,
			double timeIncrement, DrawsFunctions drawer) {
		this.evaluators = evaluators;
		// copy the received map, this makes this class completely
		// thread-safe
		this.varMap = deepCopyLinkedHashMap(varMap);
		this.timeStart = timeStart;
		this.timeIncrement = timeIncrement;

		this.drawer = drawer;
	}

	private LinkedHashMap<String, double[]> deepCopyLinkedHashMap(
			LinkedHashMap<String, double[]> mapToCopy) {
		LinkedHashMap<String, double[]> newMap = new LinkedHashMap<String, double[]>();

		for (Map.Entry<String, double[]> entry : mapToCopy.entrySet()) {
			newMap.put(entry.getKey(), entry.getValue().clone());
		}
		return newMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Void doInBackground() throws Exception {
		double time = timeStart;

		while (calculate) {

			synchronized (this) {
				varMap.put("t", new double[] { time });
			}

			List<Matrix<Double>> results = new ArrayList<Matrix<Double>>();
			for (FunctionEvaluator feval : evaluators) {

				synchronized (this) {
					results.add(feval.calculate(varMap));
				}

			}
			publish(results);
			time += timeIncrement;
		}

		return null;
	}

	@Override
	protected void process(List<List<Matrix<Double>>> matrixes) {
		drawer.drawMatrixes(matrixes.get(matrixes.size() - 1));
		if (matrixes.size() > 5)
			matrixes.clear();
	}

	@Override
	protected void done() {
		try {
			get();
		} catch (InterruptedException e) {
			e.printStackTrace(); // can't happen
		} catch (ExecutionException e) {
			JOptionPane.showMessageDialog(null, "Incorrect function\n"
					+ e.getMessage()
					+ "\nOnly x, y, t variables are supported !!", "Error",
					JOptionPane.ERROR_MESSAGE);
			this.stop();
		}
	}

	public void stop() {
		calculate = false;
	}

	public synchronized void modifyIntervals(double xvalues[], double[] yvalues) {
		varMap.put("x", xvalues.clone());
		varMap.put("y", yvalues.clone());
	}

}