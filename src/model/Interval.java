package model;

public class Interval {
	
	private double start = 0;
	private double end = 1;
	
	public Interval(){
		
	}
	
	public Interval(double start, double end) {
		super();
		this.start = start;
		this.end = end;
	}

	public double getStart() {
		return start;
	}

	public void setStart(double start) {
		this.start = start;
	}

	public double getEnd() {
		return end;
	}

	public void setEnd(double end) {
		this.end = end;
	}
	
	

}
