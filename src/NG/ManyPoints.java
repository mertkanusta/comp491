package NG;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ManyPoints extends JPanel{
	ArrayList<Pointing> points = new ArrayList<Pointing>();
	 ArrayList<Link> links=new ArrayList<Link>();
	 HashMap<Pointing,ArrayList<Pointing>> allNeighbour;
	Line2D.Double line;
	JLabel stats=new JLabel();
	Pointing sou,des,sou1,des1;

	Pointing oldFrom=new Pointing(0,0,"",null);
	HashMap<String,Pointing> citypoints=new HashMap<String,Pointing>();
	static ArrayList<Double> xone=new ArrayList<Double>();
	static ArrayList<Double> yone=new ArrayList<Double>();
	static ArrayList<Double> xtwo=new ArrayList<Double>();
	static ArrayList<Double> ytwo=new ArrayList<Double>();


	public void add(Pointing p) {
		points.add(p);
		citypoints.put(p.getCity(),p);
	}

	public void resetElements(){
		points.clear();
		links.clear();
		citypoints.clear();
		oldFrom=null;
	}

	public void readProp(String text_name) throws IOException{
		
		
		FileReader in7=new FileReader(text_name+"_host.txt");
		BufferedReader br7=new BufferedReader(in7);
		String string7=br7.readLine();
		while(string7!=null){
			String firstParse,cit;
			int a=0;
			while(string7.charAt(a)!='('){
				a++;
			}
			cit=string7.substring(0,a-1);
			firstParse=string7.substring(a+1);
			int b=0;
			while(firstParse.charAt(b)!=' '){
				b++;
			}
			double longitude=Double.parseDouble(firstParse.substring(0,b));
			double latitude=Double.parseDouble(firstParse.substring(b+1,firstParse.length()-1));
			Pointing p=new Pointing(longitude,latitude,cit,Color.RED);
			add(p);
			System.out.println(p.getCity()+" xcor:"+p.getX());
			System.out.println(p.getCity()+" ycor:"+p.getY());
			string7=br7.readLine();
		}
		br7.close();
		System.out.println("-------------------------------------------");
		
		FileReader in8=new FileReader(text_name+"_link.txt");
		BufferedReader br8=new BufferedReader(in8);
		String string8=br8.readLine();
		while(string8!=null){
			String dest,sour;
			int a=0;
			while(string8.charAt(a)!='_'){
				a++;
			}
			dest=string8.substring(0,a);
			sour=string8.substring(a+1,string8.indexOf(' '));
			sou=citypoints.get(dest);
			des=citypoints.get(sour);
			links.add(new Link(sou,des,0));
			string8=br8.readLine();
		}
		System.out.println("Links size: "+links.size());
		br8.close();
		for(int aaa=0; aaa<links.size();aaa++){
			links.get(aaa).setUtil(Math.random());
		}

		allNeighbour=new HashMap<Pointing,ArrayList<Pointing>>(points.size());
		for(Pointing po:points){
			for(Link ld:links){
				if(ld.getSour()==po){
					po.setNeighbour(ld.getDest());
				}
			}
			allNeighbour.put(po, po.getNeighbour());

		}
		
		
		//djikstraShort(points.get(4),points.get(21).getCity());
		//System.out.println("Greedy: "+greedyShort(points.get(4),points.get(21)));
		
		
	}




	public double distance(Pointing p1,Pointing p2){
		return Math.sqrt(Math.pow(p1.getX()-p2.getX(),2)+Math.pow(p1.getY()-p2.getY(),2))*10;
	}

	public void djikstraShort(Pointing p1,String destination){
		double[] dist=new double[points.size()];
		int[] prev=new int[points.size()];
		ArrayList<Pointing> qq=new ArrayList<Pointing>();
		int i=0;
		for(Pointing p:points){
			dist[i]=Double.MAX_VALUE;
			prev[i]=-1;
			qq.add(p);
			i++;
		}
		dist[points.indexOf(p1)]=0;
		while(!qq.isEmpty()){
			double min;
			int min_index;
			if(points.indexOf(p1)!=0 || qq.size()==1){
				min=dist[0];
				min_index=0;
			}else{
				min=dist[1];
				min_index=1;
			}
			for(int a=0; a<qq.size(); a++){
				if(dist[a]<min){
					min=dist[a];
					min_index=a;
				}
			}
			Pointing u=qq.remove(min_index);
			for(Map.Entry<Pointing, ArrayList<Pointing>> entry : allNeighbour.entrySet()){
				ArrayList<Pointing> p=entry.getValue();
				for(Pointing n:p){
					//System.out.println("Neighbour: "+n.getCity()+" Source:"+u.getCity());
					/*double ut=0.0;
				for(Link link:links){
					if(link.getSour()==u && link.getDest()==n){
						ut=link.getUtil();
					}
				}*/
					double alt=dist[points.indexOf(u)]+distance(u,n);
					if(alt<dist[points.indexOf(n)]){
						dist[points.indexOf(n)]=alt;
						prev[points.indexOf(n)]=min_index;
					}
				}
			}
		}
		for(int b=0; b<dist.length; b++){
			if(points.get(b).getCity().equals(destination))
				System.out.println(p1.getCity()+" - "+points.get(b).getCity()+":"+dist[b]+"km");
		}
	}

	public int greedyShort(Pointing p1,Pointing destination){
		double m=-1.0;
		double tmp_dist=0.0;
		Pointing min_node=null;
		int distance=0;
		ArrayList<Pointing> tmp_to=new ArrayList<Pointing>();


		//Map<Vertex, Vertex> map = myAdjList;
		for (Map.Entry<Pointing, ArrayList<Pointing>> entry : allNeighbour.entrySet())
		{
			ArrayList<Pointing> nList=entry.getValue();
			if (entry.getKey().equals(p1)){
				tmp_to=nList;
				for(Pointing t:tmp_to){
					tmp_dist=distance(p1,t);
					if(!t.equals(oldFrom)){
						if(t.equals(destination)){
							distance+=tmp_dist;
							return distance;
						}else{
							if (m==-1) m = tmp_dist;
							if (tmp_dist<=m) {
								m = tmp_dist;
								min_node = t; //EMÝN DEÐÝLÝM???
							}
						}
					}
				}
			}	
		}
		if (m!=-1){
			distance+=m;
		}
		if (!min_node.equals(destination) && !min_node.getCity().equals("")){
			oldFrom=p1;
			greedyShort(min_node,destination);
		}
		return distance;
	}

	//Constructor da, readProp da parametreli olacak!!!

	public ManyPoints(String text_n) throws IOException{
		//setPreferredSize(new Dimension(360,180));
		setLayout(null);
		resetElements();
		readProp(text_n);

	}
}