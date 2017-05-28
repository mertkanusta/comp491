package NG;

import java.awt.Color;

import javax.swing.JPanel;


public class Link extends JPanel{
	Pointing d,s;
	Color colour;
	double utilization;
	double length;
	public Link(Pointing desti,Pointing sourc,double ut){
		d=desti;
		s=sourc;
		utilization=ut;
	}
	
	public String getPath(){
		return d.getCity()+"-"+s.getCity();
	}
	
	public Pointing getDest(){
		return d;
	}
	
	public Pointing getSour(){
		return s;
	}
	
	public double getUtil(){
		return utilization;
	}
	
	public void setUtil(double u){
		utilization=u;
	}
	
	public void setColor(Color c){
		colour=c;
	}
	
	public Color getColor(){
		return colour;
	}
	
	public double getLength(){
		return length;
	}
}
