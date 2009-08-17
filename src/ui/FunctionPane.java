package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;

import javax.swing.JPanel;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.BufferedTreeNodeStream;
import org.antlr.runtime.tree.CommonTree;

import parser.Eval;
import parser.ExprLexer;
import parser.ExprParser;

public class FunctionPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FunctionPane(BorderLayout borderLayout) {
		super(borderLayout);
		Thread time = new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					while(true){
						Thread.sleep(100);
						t += 0.002;
						syncRepaint();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		});
		time.start();
		
	}
	
	private synchronized void syncRepaint(){
		repaint();
	}

	private double t = 1;
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	
		g.setColor(Color.blue);

		Eval eval = initEvaluator();
		HashMap<String, Double> memory = new HashMap<String, Double>();
		memory.put("t", Double.valueOf(t));
		try {
			double start = -5;
			double end = 5;
			double inc = (end-start)/800; //inc = 0.02 


			int i1 = 0; int j1 = 0;
			for (int i = 0; i < 800; ++i) {
				
				double x = start+i*inc;				
				memory.put("x", Double.valueOf(x));
				eval.setMemory(memory);

				eval.reset();
				double y = eval.entry();
				
				int j = 300 + (int)(y/inc);
				if(i1!=0&&j1!=0&&(Math.abs(i-i1)<300)&&(Math.abs(j-j1)<300))g.drawLine(i1, j1, i, j);
		
				i1 = i;
				j1 = j;
				
			}
			
			t += 0.1;
			
		} catch (RecognitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Eval initEvaluator() {
		Eval eval = null;

		try {
			ANTLRStringStream input = new ANTLRStringStream("cos(x/2+(cos t*1.3)/2)/cos(x) + 1/(x*(cos(t/5)+1))");
			
			ExprLexer lexer = new ExprLexer(input);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			ExprParser parser = new ExprParser(tokens);
			ExprParser.entry_return r = parser.entry();
			CommonTree t = (CommonTree) r.getTree();
			BufferedTreeNodeStream nodes = new BufferedTreeNodeStream(t);
			eval = new Eval(nodes);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{

		}

		return eval;

	}

}