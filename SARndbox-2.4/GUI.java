import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.lang.ProcessBuilder;
import java.util.*;
import java.io.*;

public class GUI extends JFrame implements ActionListener, ChangeListener
{

	public static void main(String... args)
	{
		GUI g = new GUI();
	}
	
	ProcessBuilder pb;
	Process p;
	JFrame f;
	JTextField[] textField;
	JSlider[] sliders;

	public GUI()
	{
		f = new JFrame("SARndbox GUI");
		JPanel left = new JPanel();
		left.setLayout(new GridLayout(0,1));
		JButton button;
		JLabel temp = new JLabel("Tropical Cyclone Warning Signals");
		temp.setPreferredSize(new Dimension(200,75));
		left.add(temp);
		left.add(button = new JButton("Yellow"));
			button.addActionListener(this);
		left.add(button = new JButton("Orange"));
			button.addActionListener(this);
		left.add(button = new JButton("Red"));
			button.addActionListener(this);
		f.add(left, BorderLayout.WEST);
		
		JPanel centre = new JPanel();
		centre.setLayout(new BoxLayout(centre, BoxLayout.Y_AXIS));
		sliders = new JSlider[6];
		//values must be int; convert to multiples of 100
		sliders[0] = new JSlider(JSlider.HORIZONTAL, 0, 50, 25); //rain strength 0.25
		sliders[1] = new JSlider(JSlider.HORIZONTAL, 0, 100, 50); //evaporation rate 0.0
		sliders[2] = new JSlider(JSlider.HORIZONTAL, 0, 200, 100); //water speed 1.0
		sliders[3] = new JSlider(JSlider.HORIZONTAL, 0, 400, 200); //water opacity 2.0
		sliders[4] = new JSlider(JSlider.HORIZONTAL, 0, 150, 75); //contour lines 0.75
		sliders[5] = new JSlider(JSlider.HORIZONTAL, 0, 0, 0); //placeholder
		JLabel[] labels = new JLabel[6];
		labels[0] = new JLabel("Rain Strength");
		labels[1] = new JLabel("Evaporataion Rate");
		labels[2] = new JLabel("Water Speed");
		labels[3] = new JLabel("Water Opacity");
		labels[4] = new JLabel("Contour Lines");
		labels[5] = new JLabel("Scale   1:");
		JPanel panel;
		textField = new JTextField[6];
		textField[0] = new JTextField("0.25");
		textField[1] = new JTextField("0.0");
		textField[2] = new JTextField("1.0");
		textField[3] = new JTextField("2.0");
		textField[4] = new JTextField("0.75");
		textField[5] = new JTextField("100");

		for(int i = 0; i < 6; i++)
		{
			panel = new JPanel();
			panel.setLayout(new FlowLayout(FlowLayout.CENTER));
			panel.add(labels[i]);
			if(i != 5) panel.add(sliders[i]);
			panel.add(textField[i]);
			// textField[i].addActionListener(this);
			textField[i].setHorizontalAlignment(JTextField.CENTER);
			textField[i].setPreferredSize(new Dimension(40,20));
			sliders[i].setMajorTickSpacing(25);
			centre.add(panel);
		}

		// rain strength
		sliders[0].addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent ce)
			{
				Double value = Double.valueOf(sliders[0].getValue())/100;
				textField[0].setText("" + value);
			}
		});
		textField[0].addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent ke)
			{
				String typed = textField[0].getText();
				if(!typed.matches("\\d[.]\\d+")){
					return;
				}
				int value = (int)(Double.parseDouble(typed)*100);
        	sliders[0].setValue(value);
			}
		});

		// evaporation rate
		sliders[1].addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent ce)
			{
				Double value = Double.valueOf(sliders[1].getValue()-50)/100;
				textField[1].setText("" + value);
			}
		});
		textField[1].addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent ke)
			{
				String typed = textField[1].getText();
				if(!typed.matches("^[-+]?\\d[.]\\d+")){
					return;
				}
				int value = (int)((Double.parseDouble(typed)*100)+50);
        	sliders[1].setValue(value);
			}
		});

		// water speed
		sliders[2].addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent ce)
			{
				Double value = Double.valueOf(sliders[2].getValue())/100;
				textField[2].setText("" + value);
			}
		});
		textField[2].addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent ke)
			{
				String typed = textField[2].getText();
				if(!typed.matches("\\d[.]\\d+")){
					return;
				}
				int value = (int)(Double.parseDouble(typed)*100);
        	sliders[2].setValue(value); 
			}
		});		

		// water opacity
		sliders[3].addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent ce)
			{
				Double value = Double.valueOf(sliders[3].getValue())/100;
				textField[3].setText("" + value);
			}
		});
		textField[3].addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent ke)
			{
				String typed = textField[3].getText();
				if(!typed.matches("\\d[.]\\d+")){
					return;
				}
				int value = (int)(Double.parseDouble(typed)*100);
        	sliders[3].setValue(value); 
			}
		});	

		// contour lines
		sliders[4].addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent ce)
			{
				Double value = Double.valueOf(sliders[4].getValue())/100;
				textField[4].setText("" + value);
			}
		});
		textField[4].addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent ke)
			{
				String typed = textField[4].getText();
				if(!typed.matches("\\d[.]\\d+")){
					return;
				}
				int value = (int)(Double.parseDouble(typed)*100);
        	sliders[4].setValue(value); 
			}
		});		
		
		centre.add(button = new JButton("Simulate"));
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				Double rs = Double.valueOf(sliders[0].getValue())/100;
				Double evr = Double.valueOf(sliders[1].getValue())/100 - 0.5;
				Double ws1 = Double.valueOf(sliders[2].getValue())/100;
				Double wo = Double.valueOf(sliders[3].getValue())/100;
				Double ucl = Double.valueOf(sliders[4].getValue())/100;
				Double s = Double.valueOf(textField[5].getText());
				pb = new ProcessBuilder(new String[]{"/bin/bash", "-c", 
				"make && ./bin/SARndbox -uhm -fpv -rs " + rs + 
				" -ws " + ws1 + " -evr " + evr +
				" -wo " + wo +  " -ucl " + ucl + " > out.txt"});
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
		f.add(centre, BorderLayout.CENTER);
		
		JPanel right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		temp = new JLabel("Presets and Scenarios");
		temp.setPreferredSize(new Dimension(200,75));
		right.add(temp);
		panel = new JPanel();
		panel.setLayout(new GridLayout(4,2));
		for(int i = 0; i < 8; i++)
		{
			panel.add(button = new JButton(""+i));
			button.addActionListener(this);
		}
		right.add(panel, BorderLayout.SOUTH);
		f.add(right, BorderLayout.EAST);
		
		f.setSize(1024,720);
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
				textField[1].setText(value.toString());
				sliders[1].setValue((int)(value * 100.0)+50);
				break;
			case "Orange":
				value = Math.random() * 15 + 15;
				value = value / 360;
				textField[1].setText(value.toString());
				sliders[1].setValue((int)(value * 100.0)+50);
				break;
			case "Red":
				value = Math.random() * 15 + 30;
				value = value / 360;
				textField[1].setText(value.toString());
				sliders[1].setValue((int)(value * 100.0)+50);
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