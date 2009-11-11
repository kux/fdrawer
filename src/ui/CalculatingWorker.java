package ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import model.FunctionEvaluator;
import model.Matrix;

import org.antlr.runtime.RecognitionException;

import parser.UncheckedParserException;

class CalculatingWorker extends SwingWorker<Void, List<Matrix<Double>>> {

	private final List<FunctionEvaluator> evaluators;
	private final LinkedHashMap<String, double[]> varMap = new LinkedHashMap<String, double[]>();
	private volatile boolean calculate = true;
	private volatile boolean pauzed;

	private double timeStart;
	private double timeIncrement;

	private DrawsFunctions drawer;

	public CalculatingWorker(List<String> functions, double timeStart,
			double timeIncrement, DrawsFunctions drawer)
			throws UncheckedParserException, RecognitionException {

		this.evaluators = createEvaluators(functions);

		this.timeStart = timeStart;
		this.timeIncrement = timeIncrement;

		this.drawer = drawer;

	}

	@SuppressWarnings("unchecked")
	@Override
	protected Void doInBackground() throws Exception {
		double time = timeStart;

		while (calculate) {
			if (!pauzed) {
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
			// interrupting edt? shouldn't happen
			Thread.currentThread().interrupt();
		} catch (ExecutionException e) {
			JOptionPane.showMessageDialog(null, "Incorrect function\n"
					+ e.getMessage()
					+ "\nOnly x, y, t variables are supported !!", "Error",
					JOptionPane.ERROR_MESSAGE);
			this.stop();
		}
	}

	public void stop() {
		this.calculate = false;
	}

	public void pauze() {
		this.pauzed = true;
	}

	public void resume() {
		this.pauzed = false;
	}

	public synchronized void modifyVarMap(String variable, double[] values) {
		varMap.put(variable, values);
	}

	private List<FunctionEvaluator> createEvaluators(List<String> functions)
			throws UncheckedParserException, RecognitionException {

		List<FunctionEvaluator> evaluators = new ArrayList<FunctionEvaluator>();

		for (String function : functions) {
			evaluators.add(new FunctionEvaluator(function));
		}

		return evaluators;
	}

}