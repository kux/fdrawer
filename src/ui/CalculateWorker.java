package ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.SwingWorker;

import model.FunctionEvaluator;
import model.Matrix;

/**
 * @author kux
 * 
 */
public class CalculateWorker extends SwingWorker<Void, List<Matrix<Double>>> {

	private FDrawComponent dcomp;
	private List<FunctionEvaluator> fevals;
	private LinkedHashMap<String, double[]> varMap;
	private double tstart;
	private double tinc;

	public CalculateWorker(FDrawComponent dcomp, List<FunctionEvaluator> fevals,
			LinkedHashMap<String, double[]> varMap, double tstart, double tinc) {
		this.dcomp = dcomp;
		this.fevals = fevals;
		this.varMap = varMap;
		this.tstart = tstart;
		this.tinc = tinc;

	}

	@Override
	protected Void doInBackground() throws Exception {
		double time = tstart;
		while (true) {
			varMap.put("t", new double[] { time });
			List<Matrix<Double>> results = new ArrayList<Matrix<Double>>();
			for (FunctionEvaluator feval : fevals) {
				results.add(feval.calculate(varMap));
			}
			publish(results);
		}
	}
	
    @Override
    protected void process(List<List<Matrix<Double>>> matrixes) {
    	List<Matrix<Double>> drawNow = matrixes.get(matrixes.size() - 1);
    	dcomp.setMatrixsToDraw(drawNow);
    }



	public void setVarMap(LinkedHashMap<String, double[]> varMap) {
		this.varMap = varMap;
	}

}
