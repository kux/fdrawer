package model;

import java.util.HashMap;
import java.util.List;

import parser.Eval;

public class CoordEval {
	

	 
	private HashMap<String, Double> memory = new HashMap<String, Double>();
	private Eval eval;
	
	public CoordEval(String function, IntervalBreaker... intervals){
		
	}
		
	public static class IntervalBreaker{
		
		private String var;
		private Interval interval;
		private int splits;
		
		public IntervalBreaker(String var, Interval interval, int splits) {
			super();
			this.var = var;
			this.interval = interval;
			this.splits = splits;
		}

		public String getVar() {
			return var;
		}

		public void setVar(String var) {
			this.var = var;
		}

		public Interval getInterval() {
			return interval;
		}

		public void setInterval(Interval interval) {
			this.interval = interval;
		}

		public int getSplits() {
			return splits;
		}

		public void setSplits(int splits) {
			this.splits = splits;
		}
	}

}
