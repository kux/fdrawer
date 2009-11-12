package ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import model.FunctionEvaluator;
import model.Matrix;

import org.antlr.runtime.RecognitionException;

class CalculatingWorker extends SwingWorker<Void, List<Matrix<Double>>> {

	private final LinkedHashMap<String, double[]> varMap = new LinkedHashMap<String, double[]>();

	private List<FunctionEvaluator> evaluators = new ArrayList<FunctionEvaluator>();
	private volatile boolean calculate = true;
	private volatile boolean pauzed;

	private double time = 0;
	private double timeIncrement = 0.1;

	private DrawsFunctions drawer;
	private DrawingForm form;

	public CalculatingWorker(DrawsFunctions drawer, DrawingForm form) {
		this.drawer = drawer;
		this.form = form;
		varMap.put("x", new double[] {});
		varMap.put("y", new double[] {});
		varMap.put("t", new double[] {});
	}

	/**
	 * 
	 * @param drawer
	 * @throws IllegalStateException
	 *             if not called from edt
	 */
	public void setDrawer(DrawsFunctions drawer) {
		if (!SwingUtilities.isEventDispatchThread()) {
			throw new IllegalStateException();
		}
		this.drawer = drawer;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Void doInBackground() throws Exception {

		while (calculate) {
			if (!pauzed) {
				synchronized (this) {
					varMap.put("t", new double[] { time });

					List<Matrix<Double>> results = new ArrayList<Matrix<Double>>();
					Date start = new Date();
					for (FunctionEvaluator feval : evaluators) {

						results.add(feval.calculate(varMap));

					}
					Date end = new Date();
					if (end.getTime() - start.getTime() > 1)
						publish(results);
					time += timeIncrement;
				}
			}
		}

		return null;
	}

	@Override
	protected void process(List<List<Matrix<Double>>> matrixes) {
		if (drawer != null)
			drawer.drawMatrixes(matrixes.get(matrixes.size() - 1));
		if (matrixes.size() > 5)
			matrixes.clear();
		form.setTime(time);
	}

	@Override
	protected void done() {
		try {
			get();
		} catch (InterruptedException e) {
			// interrupting edt? shouldn't happen
			Thread.currentThread().interrupt();
		} catch (ExecutionException e) {
			JOptionPane.showMessageDialog(this.form, "Incorrect function\n"
					+ e.getMessage()
					+ "\nOnly x, y, t variables are supported !!", "Error",
					JOptionPane.ERROR_MESSAGE);
			this.stop();
		}
	}

	public void stop() {
		this.calculate = false;
	}

	public void pause() {
		this.pauzed = true;
	}

	public void resume() {
		this.pauzed = false;
	}

	public synchronized void modifyVarMap(String variable, double[] values) {
		varMap.put(variable, values);
	}

	public synchronized void setTime(double time) {
		this.time = time;
	}

	public synchronized void setTimeIncrement(double timeIncrement) {
		this.timeIncrement = timeIncrement;
	}

	/**
	 * 
	 * @param function
	 *            function to add to the ones being evaluated
	 * @return list of variables the function has
	 * @throws RecognitionException
	 */
	public synchronized List<String> addFunction(String function)
			throws RecognitionException {
		FunctionEvaluator evaluator = new FunctionEvaluator(function);
		List<String> variables = evaluator.getVariables();
		this.evaluators.add(evaluator);
		return variables;
	}

}