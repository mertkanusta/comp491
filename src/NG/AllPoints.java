package NG;

import NG.Link;
import NG.Legend;
import NG.LinkStatus;
import NG.ManyPoints;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.swing.BoundedRangeModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AllPoints extends JPanel{
	Point click;
	JFrame ap;
	ManyPoints map;
	JScrollPane sp;
	LinkStatus ls;
	Legend l;
	Legend oldL;
	String ty;

	BufferedImage surface;
	Graphics g;
	JLabel il;
	File rout;
	int xp=0;
	int yp=0;


	public AllPoints(String type,String tn) throws IOException{
		createResize();
		createResize2();
		ap=new JFrame();
		ty=type;
		l=new Legend();
		ap.setTitle(type);
		ap.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//ap.setBounds(0, 0, 1200, 900);
		ap.setBounds(0, 0, 3600, 1800);

		//map=new ManyPoints(txt_file_name);
		map=new ManyPoints(tn);
		ls=new LinkStatus(map);
		paintComponent(g);

		il.add(map);
		il.add(ls);
		sp=new JScrollPane();
		if(sp.getViewport().getView()==null) sp.setViewportView(il);
		sp.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				xp=(int)sp.getViewport().getViewPosition().getX();
				yp=(int)sp.getViewport().getViewPosition().getY();
				System.out.println("viewport x: "+xp);
				System.out.println("viewport x: "+yp);
				System.out.println();
				sp.getViewport().setViewPosition(new Point(xp,yp));
				System.out.println(ty);
				System.out.println(ty);
				System.out.println(ty);
				System.out.println(ty);
				oldL=l;
				il.remove(l.title);
				il.remove(l.grb);
				il.remove(l.grd);
				il.remove(l.orb);
				il.remove(l.ord);
				il.remove(l.bl);
				l=new Legend((int)sp.getViewport().getViewPosition().getX(),(int)sp.getViewport().getViewPosition().getY());
				il.add(l.title);
				il.add(l.grb);
				il.add(l.grd);
				il.add(l.orb);
				il.add(l.ord);
				il.add(l.bl);
				for(int a=0; a<map.points.size(); a++){
					il.remove(ls.routers.get(a));
				}
				for(int a=0; a<map.links.size(); a++){
					il.remove(ls.c.get(a));
					il.remove(ls.b.get(a));
					il.remove(ls.u.get(a));
				}
				click=e.getPoint();
				System.out.println("x: "+(click.getX()+sp.getViewport().getViewPosition().getX())+" "+"y: "+(click.getY()+sp.getViewport().getViewPosition().getY()));
				for(int a=0; a<map.points.size();a++){
					System.out.println("Router dist:"+distanceCalc(map.points.get(a),click,sp.getViewport().getViewPosition().getX(),sp.getViewport().getViewPosition().getY()));
				}
				il.remove(ls);
				ls=new LinkStatus(map,(int)sp.getViewport().getViewPosition().getX(),(int)sp.getViewport().getViewPosition().getY());
				il.add(ls);
				for(int a=0; a<map.links.size(); a++){
					if(pointToLineDistance(map.links.get(a).getSour(),map.links.get(a).getDest(),click,sp.getViewport().getViewPosition().getX(),sp.getViewport().getViewPosition().getY())<=2.0){
						il.add(ls.c.get(a));
						il.add(ls.b.get(a));
						il.add(ls.u.get(a));
					}
				}
				for(int a=0; a<map.points.size(); a++){
					if(distanceCalc(map.points.get(a),click,sp.getViewport().getViewPosition().getX(),sp.getViewport().getViewPosition().getY())<=5.0){
						il.add(ls.routers.get(a));
					}
				}
				sp.setViewportView(il);
				
				paintComponent(g);
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
		sp.getViewport().setViewPosition(new Point(xp,yp));
		ap.getContentPane().add(sp);
	}
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter for a map: ");
		String map=br.readLine();
		int typing=Integer.parseInt(map);
		while(typing<1 || typing>=5){
			System.out.println("Invalid map!");
			System.out.print("Enter for a map: ");
			map=br.readLine();
			typing=Integer.parseInt(map);
		}
		switch(typing){
		case 1:
			AllPoints a1=new AllPoints("Germany","germany50");
			a1.ap.setVisible(true);
			break;
		case 2:
			AllPoints a2=new AllPoints("USA","abiline");
			a2.ap.setVisible(true);
			break;
		case 3:
			AllPoints a3=new AllPoints("GEANT","geant");
			a3.ap.setVisible(true);
			break;
		case 4:
			AllPoints a4=new AllPoints("COST266","cost266");
			a4.ap.setVisible(true);
			break;
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(sp!=null){
			g.clearRect(oldL.oldX, oldL.oldY, 160, 270);
			g.clearRect(oldL.oldX, oldL.oldY+270, 200, 90);
			g.clearRect(oldL.oldX, oldL.oldY+360, 200, 90);
			Graphics gg3=g;
			Graphics g2dd=(Graphics2D) gg3;
			try {
				g2dd.drawImage(ImageIO.read(new File("new_world.png")).getSubimage(oldL.oldX, oldL.oldY, 160, 270),oldL.oldX, oldL.oldY, this);
				g2dd.drawImage(ImageIO.read(new File("new_world.png")).getSubimage(oldL.oldX, oldL.oldY+270, 200, 90),oldL.oldX, oldL.oldY+270, this);
				g2dd.drawImage(ImageIO.read(new File("new_world.png")).getSubimage(oldL.oldX, oldL.oldY+360, 200, 90),oldL.oldX, oldL.oldY+360, this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Graphics gg=g;
			Graphics gg2=gg;
			legendFrame(g);
			gg.setColor(Color.white);
			gg.fillRect(0+(int)sp.getViewport().getViewPosition().getX(),270+(int)sp.getViewport().getViewPosition().getY(),200,90);
			
			gg.setColor(Color.white);
			gg.fillRect(0+(int)sp.getViewport().getViewPosition().getX(),360+(int)sp.getViewport().getViewPosition().getY(),200,90);
			
			gg2.setColor(Color.green.brighter());
			gg2.drawLine(20+(int)sp.getViewport().getViewPosition().getX(),50+(int)sp.getViewport().getViewPosition().getY(),70+(int)sp.getViewport().getViewPosition().getX(),50+(int)sp.getViewport().getViewPosition().getY());
			
			gg2.setColor(Color.green.darker());
			gg2.drawLine(20+(int)sp.getViewport().getViewPosition().getX(),100+(int)sp.getViewport().getViewPosition().getY(),70+(int)sp.getViewport().getViewPosition().getX(),100+(int)sp.getViewport().getViewPosition().getY());
			
			gg2.setColor(Color.ORANGE.brighter());
			gg2.drawLine(20+(int)sp.getViewport().getViewPosition().getX(),150+(int)sp.getViewport().getViewPosition().getY(),70+(int)sp.getViewport().getViewPosition().getX(),150+(int)sp.getViewport().getViewPosition().getY());
			
			gg2.setColor(Color.ORANGE);
			gg2.drawLine(20+(int)sp.getViewport().getViewPosition().getX(),200+(int)sp.getViewport().getViewPosition().getY(),70+(int)sp.getViewport().getViewPosition().getX(),200+(int)sp.getViewport().getViewPosition().getY());
			
			gg2.setColor(Color.RED);
			gg2.drawLine(20+(int)sp.getViewport().getViewPosition().getX(),250+(int)sp.getViewport().getViewPosition().getY(),70+(int)sp.getViewport().getViewPosition().getX(),250+(int)sp.getViewport().getViewPosition().getY());
			
		}


		for (Pointing circle: map.points) {
			try {
				g.drawImage(ImageIO.read(new File("new_router.png")), circle.getX()-10, circle.getY()-5, this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (Link li: map.links){
			if(li.getUtil()>=0.0 && li.getUtil()<0.2){
				li.setColor(Color.GREEN.brighter());
			}else if(li.getUtil()>=0.2 && li.getUtil()<0.5){
				li.setColor(Color.GREEN.darker());
			}else if(li.getUtil()>=0.5 && li.getUtil()<0.75){
				li.setColor(Color.ORANGE.brighter());
			}else if(li.getUtil()>=0.75 && li.getUtil()<0.90){
				li.setColor(Color.ORANGE);
			}else{
				li.setColor(Color.RED);
			}
			Graphics ggg=g;
			Graphics2D g3d=(Graphics2D) ggg;
			g3d.setStroke(new BasicStroke((float)(5*li.getUtil())));
			g3d.setColor(li.getColor());
			g3d.drawLine(li.getSour().getX(), li.getSour().getY(), li.getDest().getX(), li.getDest().getY());
			
			Line2D.Double lin=new Line2D.Double((double)li.getDest().getX(),(double)li.getDest().getY(),(double)li.getSour().getX(),(double)li.getSour().getY());
			map.xone.add(lin.x1);
			map.yone.add(lin.y1);
			map.xtwo.add(lin.x2);
			map.ytwo.add(lin.y2);
			
			if(click!=null){
				if(pointToLineDistance(li.getSour(),li.getDest(),click,sp.getViewport().getViewPosition().getX(),sp.getViewport().getViewPosition().getY())<=5.0){
					System.out.println(li.getSour().getCity()+" "+li.getDest().getCity());	
				}
			}
		}
		ap.pack();


	}

	private void createResize() throws IOException{
		
		surface = ImageIO.read(new File("world.200406.3x3600x1800.png"));
		int typ = surface.getType() == 0? BufferedImage.TYPE_INT_ARGB : surface.getType();
		BufferedImage resizeImageJpg = resizeImage(surface, typ,7200,3600);
		ImageIO.write(resizeImageJpg, "png", new File("new_world.png"));
		
		surface = ImageIO.read(new File("new_world.png"));
		g = surface.getGraphics();
		il=new JLabel(new ImageIcon(surface));
	}

	private static BufferedImage resizeImage(BufferedImage originalImage, int type,int a,int b){
		BufferedImage resizedImage = new BufferedImage(a, b, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, a, b, null);
		g.dispose();

		return resizedImage;
	}

	private void createResize2() throws IOException{
		BufferedImage rou = ImageIO.read(new File("router-29825_960_720.png"));
		int typ = rou.getType() == 0? BufferedImage.TYPE_INT_ARGB : rou.getType();
		BufferedImage resizeImageJpg = resizeImage(rou, typ,31,22);
		ImageIO.write(resizeImageJpg, "png", new File("new_router.png"));


	}

	public double distanceCalc(Pointing p1,Point p2,double spx,double spy){
		return Math.sqrt(Math.pow(p1.getX()-(p2.getX()+spx),2)+Math.pow(p1.getY()-(p2.getY()+spy),2));
	}

	public double pointToLineDistance(Pointing A, Pointing B, Point P,double spx,double spy) {
		double normalLength = Math.sqrt((B.getX()-A.getX())*(B.getX()-A.getX())+(B.getY()-A.getY())*(B.getY()-A.getY()));
		return Math.abs(((P.getX()+spx)-A.getX())*(B.getY()-A.getY())-((spy+P.getY())-A.getY())*(B.getX()-A.getX()))/normalLength;
	}
	
	public void legendFrame(Graphics gr){
		gr.setColor(Color.white);
		gr.fillRect(0+(int)sp.getViewport().getViewPosition().getX(),0+(int)sp.getViewport().getViewPosition().getY(),160,270);
	}
}
