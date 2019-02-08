// import java.awt.*;
// import javax.swing.*;
import java.awt.event.*;
import java.lang.ProcessBuilder;
import java.util.*;
// import java.io.*;

public class tsis{
	
	public tsis(){
		
	}
	
	public static void main(String args[]){
		String x = "0.5";
		String[] cmd = {"/bin/bash", "-c", "make && ./bin/SARndbox -uhm -fpv -evr " + x};
		ProcessBuilder pb = new ProcessBuilder(cmd);
		try {
		  Process p = pb.start();
			System.out.println("workd?");
		}
		catch(Exception e) {
		  e.printStackTrace();
		}
		
	}
}