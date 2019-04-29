import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import java.lang.ProcessBuilder;
import java.util.*;
import java.io.*;

public class GUI extends JComponent implements ActionListener, ChangeListener
{

	public static void main(String... args)
	{
		new GUI();
	}
	
	ProcessBuilder pb;
	Process p;
	JFrame f;
	JTextField[] textArea;
	JSlider[] sliders;
	JLabel[] labels;
	JTextField textField;

	public GUI()
	{
		f = new JFrame("SARndbox GUI");
		JButton button;
		JLabel temp = new JLabel("Tropical Cyclone Warning Signals");
		
		Border blackline;
		blackline = BorderFactory.createLineBorder(Color.black);

		JPanel top = new JPanel();
		top.setLayout(new FlowLayout(FlowLayout.CENTER));
		top.add(button = new JButton("Yellow"));
			button.addActionListener(this);
		top.add(button = new JButton("Orange"));
			button.addActionListener(this);
		top.add(button = new JButton("Red"));
			button.addActionListener(this);
		f.add(top, BorderLayout.NORTH);

		JPanel left = new JPanel();
		left.setLayout(new GridLayout(0,1));
		left.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5), left.getBorder() ));

		labels = new JLabel[6];
		labels[0] = new JLabel("Rain Strength    ");
		labels[1] = new JLabel("Evaporation Rate ");
		labels[2] = new JLabel("Water Speed      ");
		labels[3] = new JLabel("Water Opacity    ");
		labels[4] = new JLabel("Contour Lines    ");
		labels[5] = new JLabel("Scale                    1:");
		for(int i=0; i<6; i++)
		{
			left.add(labels[i]);
		}
		f.add(left,BorderLayout.WEST);

		JPanel right = new JPanel();
		right.setLayout(new GridLayout(0,1));
		right.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5), right.getBorder() ));
		textArea = new JTextField[5];
		textArea[0] = new JTextField("0.25");
		textArea[1] = new JTextField("0.0");
		textArea[2] = new JTextField("1.0");
		textArea[3] = new JTextField("2.0");
		textArea[4] = new JTextField("0.75");
		for(int i = 0; i<5; i++) textArea[i].setHorizontalAlignment(JTextField.CENTER);
		textField = new JTextField("100.0");
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setPreferredSize(new Dimension(120, 40));
		textField.setBorder(blackline);
		for(int i=0; i<5; i++)
		{
			textArea[i].setBorder(blackline);
			right.add(textArea[i]);
			textArea[i].setEditable(false);
			
		}
		right.add(textField);
		f.add(right,BorderLayout.EAST);

		textField.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent ke)
			{
				String typed = textField.getText();
				int sf = (int)(Double.parseDouble(typed)/100.0);
        		textArea[0].setText("" + 0.25*sf); //rain strength
				textArea[1].setText("" + 0.0*sf); //evaporation rate
				textArea[2].setText("" + 1.0); //water speed
				if(sf > 100) textArea[3].setText("" + 2.0/sf); //water opacity
				textArea[4].setText("" + 0.75*sf); //contour lines
			}
		});	
		
		JPanel bot = new JPanel();
		bot.setLayout(new FlowLayout(FlowLayout.CENTER));
		bot.add(button = new JButton("Simulate"));
		f.add(bot,BorderLayout.SOUTH);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				Double s = Double.valueOf(textField.getText());
				pb = new ProcessBuilder(new String[]{"/bin/bash", "-c", 
				"make && ./bin/SARndbox -s " + s + " > out.txt"});
				try
				{
					p = pb.start();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		
		f.setSize(250,350);
		f.setResizable(false);
		f.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String action = ae.getActionCommand();
		Double value;
		switch(action){
			case "Yellow":
				value = Math.random() * 7.5 + 7.5;
				value = value / 360;
				textArea[1].setText(value.toString());
				break;
			case "Orange":
				value = Math.random() * 15 + 15;
				value = value / 360;
				textArea[1].setText(value.toString());
				break;
			case "Red":
				value = Math.random() * 15 + 30;
				value = value / 360;
				textArea[1].setText(value.toString());
				break;
			default:
				break;
		}
	}
	
	public void stateChanged(ChangeEvent ce)
	{
		JSlider source = (JSlider)ce.getSource();
        if (source.getValueIsAdjusting()) {
            System.out.println(ce.getSource().getClass());
        }  
	}
	
	
	
	
	
	
	
	
}