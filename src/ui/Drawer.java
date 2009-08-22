package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

import org.antlr.runtime.RecognitionException;

public class Drawer {

	public Drawer(JFrame pFrame, List<String> functions) {

		final FDrawComponent drawing;
		try {
			drawing = new FDrawComponent(functions);

			JDialog drawingD = new JDialog(pFrame, true);
			drawingD.setLayout(new BorderLayout());
			
			JButton goLeft = new JButton("<");
			drawingD.add(goLeft, BorderLayout.WEST);
			goLeft.setPreferredSize(new Dimension(100,100));
			
			JButton goRight = new JButton(">");
			drawingD.add(goRight, BorderLayout.EAST);
			
			JButton goUp = new JButton("^");
			drawingD.add(goUp, BorderLayout.NORTH);
			
			JButton goDown = new JButton("v");
			drawingD.add(goDown, BorderLayout.SOUTH);

			drawing.setPreferredSize(new Dimension(500, 500));
			drawingD.add(drawing, BorderLayout.CENTER);
			
			goLeft.addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent e) {
					drawing.setXleft( drawing.getXleft() - 1);
					drawing.setXright(drawing.getXright() - 1);
					
				}
			});
			
			drawingD.pack();
			drawingD.setVisible(true);

		} catch (RecognitionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
