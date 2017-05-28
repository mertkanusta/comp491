package NG;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class LinkStatus extends JPanel{
	public ArrayList<Link> linkings=new ArrayList<Link>();
	public ArrayList<Pointing> pointings=new ArrayList<Pointing>();
	public ArrayList<JLabel> c=new ArrayList<JLabel>();
	public ArrayList<JLabel> b=new ArrayList<JLabel>();
	public ArrayList<JLabel> u=new ArrayList<JLabel>();
	public ArrayList<JLabel> routers=new ArrayList<JLabel>();
	public ArrayList<Line2D.Double> lineCheck = new ArrayList<Line2D.Double>();								

	public LinkStatus(ManyPoints m){
		//setLayout(null);
		pointings=m.points;
		linkings=m.links;
		
		routers.clear();
		c.clear();
		b.clear();
		u.clear();
		lineCheck.clear();
		for(Link i:linkings){
			lineCheck.add(new Line2D.Double((double)i.getDest().getX(),(double)i.getDest().getY(),(double)i.getSour().getX(),(double)i.getSour().getY()));
			JLabel cities=new JLabel();
			JLabel bandwidth=new JLabel();
			JLabel util=new JLabel();
			//cities.setText(i.getPath());
			cities.setBounds(20,215,140,140);
			cities.setForeground(Color.MAGENTA);
			bandwidth.setText("Bandwidth: 40 MBits");
			bandwidth.setBounds(20,245,140,140);
			bandwidth.setForeground(Color.MAGENTA);
			util.setText("Utilization: "+i.utilization);
			util.setBounds(20,275,140,140);
			util.setForeground(Color.MAGENTA);
			c.add(cities);
			b.add(bandwidth);
			u.add(util);
		}
		for(Pointing p:pointings){
			JLabel point=new JLabel();
			point.setText(p.getCity());
			point.setBounds(20,315,140,140);
			point.setForeground(Color.MAGENTA);
			routers.add(point);
		}
		
	}
	
	public LinkStatus(ManyPoints m,int x,int y){
		//setLayout(null);
		pointings=m.points;
		linkings=m.links;
		
		routers.clear();
		c.clear();
		b.clear();
		u.clear();
		lineCheck.clear();
		for(Link i:linkings){
			lineCheck.add(new Line2D.Double((double)i.getDest().getX(),(double)i.getDest().getY(),(double)i.getSour().getX(),(double)i.getSour().getY()));
			JLabel cities=new JLabel();
			JLabel bandwidth=new JLabel();
			JLabel util=new JLabel();
			//cities.setText(i.getPath());
			cities.setBounds(20+x,215+y,140,140);
			cities.setForeground(Color.BLACK);
			bandwidth.setText("Bandwidth: 40 MBits");
			bandwidth.setBounds(20+x,245+y,140,140);
			bandwidth.setForeground(Color.BLACK);
			util.setText("Utilization: "+i.utilization);
			util.setBounds(20+x,275+y,140,140);
			util.setForeground(Color.BLACK);
			c.add(cities);
			b.add(bandwidth);
			u.add(util);
		}
		for(Pointing p:pointings){
			JLabel point=new JLabel();
			point.setText(p.getCity());
			point.setBounds(20+x,315+y,140,140);
			point.setForeground(Color.BLACK);
			routers.add(point);
		}
		
	}

}


