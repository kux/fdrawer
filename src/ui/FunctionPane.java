package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

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
	private HashMap<String,Double> memory = new HashMap<String,Double>();
	private double t;
	private Eval eval;

	public FunctionPane(BorderLayout borderLayout, String function) throws RecognitionException{
		
		super(borderLayout);
		
		ANTLRStringStream input = new ANTLRStringStream(function);
		
		ExprLexer lexer = new ExprLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ExprParser parser = new ExprParser(tokens);
		ExprParser.entry_return r = parser.entry();
		CommonTree tree = (CommonTree) r.getTree();
		BufferedTreeNodeStream bTree = new BufferedTreeNodeStream(tree);
		eval = new Eval(bTree);
		
		eval.setMemory(memory);
						
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
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	
		g.setColor(Color.blue);

		memory.put("t", Double.valueOf(t));
		
		int widht = this.getWidth();
		
		try {
			double start = -10;
			double end = 10;
			double inc = (end-start)/widht; //inc = 0.02 

			int height = this.getHeight();

			int i1 = 0; int j1 = 0;
			for (int i = 0; i < widht; ++i) {
				
				double x = start+i*inc;				
				memory.put("x", Double.valueOf(x));
				
				eval.reset();
				double y = eval.entry();
				
				int j = height/2 + (int)(y/inc);
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

}