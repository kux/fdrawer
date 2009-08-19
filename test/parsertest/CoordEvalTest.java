package parsertest;

import model.CoordEval;
import model.Interval;

import org.junit.Test;

public class CoordEvalTest {

	@Test
	public void basicTest() {

		CoordEval.IntervalBreaker xbr = new CoordEval.IntervalBreaker("x",
				new Interval(-1, 1), 2);
		CoordEval.IntervalBreaker ybr = new CoordEval.IntervalBreaker("y",
				new Interval(-1, 1), 2);
		CoordEval.IntervalBreaker tbr = new CoordEval.IntervalBreaker("t",
				new Interval(-1, 1), 2);

		CoordEval ev = new CoordEval("x+y+t", xbr, ybr, tbr);
//		CoordMatrix coords = ev.getCoordinates();
//		coords.size
		

	}

}
