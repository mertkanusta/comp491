package NG;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Pointing extends JPanel{
	
	ArrayList<Pointing> neighbourList=new ArrayList<Pointing>();
	static final double SCALE=20.0;
	int radius=20;
	private String city;
	private double x,y;
	private Color color;
	
	public Pointing(double x,double y,String ci,Color c){
		//this.x=(SCALE/3)*(180+x);
		//this.y=(SCALE/2)*(90-y);
		this.x=SCALE*(180+x);
		this.y=SCALE*(90-y);
		city=ci;
		color=c;
	}
	
	public String getCity(){
		return city;
	}
	
	public Color getColor(){
		return color;
	}
	
	public int getX(){
		return (int)x;
	}
	
	public int getY(){
		return (int)y;
	}
	
	public void setX(int x1){
		x=x1;
	}
	
	public void setY(int y1){
		y=y1;
	}
	
	public int getRadius(){
		return radius;
	}
	
	public void setNeighbour(Pointing p){
		if(!neighbourList.contains(p)){
			neighbourList.add(p);
			resizeNeigList();
		}
	}
	
	public ArrayList<Pointing> getNeighbour(){
		return neighbourList;
	}
	
	public void resizeNeigList(){
		int sizing=neighbourList.size();
		ArrayList<Pointing> newNList=new ArrayList<Pointing>(sizing);
		for(int a=0; a<sizing; a++){
			if(!newNList.contains(neighbourList.get(a))){
				newNList.add(neighbourList.get(a));
				
				}
		}
		neighbourList=new ArrayList<Pointing>(sizing);
		neighbourList=newNList;
	}
}

