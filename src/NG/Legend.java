package NG;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class Legend extends JPanel{
	JLabel title,grb,grd,orb,ord,bl;
	int oldX,oldY;
	
	public Legend(){
		title=new JLabel("LEGEND FOR UTILIZATION");
		title.setBounds(10,5,150,30);
		title.setForeground(Color.GREEN);
		grb=new JLabel("< 0.2");
		grb.setBounds(80,30,40,40);
		grb.setForeground(Color.GREEN);
		grd=new JLabel("0.2-0.5");
		grd.setBounds(80,80,40,40);
		grd.setForeground(Color.GREEN);
		orb=new JLabel("0.5-0.75");
		orb.setBounds(80,130,80,40);
		orb.setForeground(Color.GREEN);
		ord=new JLabel("0.75-0.9");
		ord.setBounds(80,180,80,40);
		ord.setForeground(Color.GREEN);
		bl=new JLabel("> 0.9");
		bl.setBounds(80,230,40,40);
		bl.setForeground(Color.GREEN);
		
	}
	
	public Legend(int x,int y){
		oldX=x;
		oldY=y;
		title=new JLabel("LEGEND FOR UTILIZATION");
		title.setBounds(10+x,5+y,150,30);
		title.setForeground(Color.BLACK);
		grb=new JLabel("< 0.2");
		grb.setBounds(80+x,30+y,40,40);
		grb.setForeground(Color.BLACK);
		grd=new JLabel("0.2-0.5");
		grd.setBounds(80+x,80+y,40,40);
		grd.setForeground(Color.BLACK);
		orb=new JLabel("0.5-0.75");
		orb.setBounds(80+x,130+y,80,40);
		orb.setForeground(Color.BLACK);
		ord=new JLabel("0.75-0.9");
		ord.setBounds(80+x,180+y,80,40);
		ord.setForeground(Color.BLACK);
		bl=new JLabel("> 0.9");
		bl.setBounds(80+x,230+y,40,40);
		bl.setForeground(Color.BLACK);
		
	}

}
