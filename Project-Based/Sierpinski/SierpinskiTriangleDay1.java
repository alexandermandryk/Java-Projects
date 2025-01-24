import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SierpinskiTriangle  extends JPanel implements KeyListener{

    JFrame frame;

    public SierpinskiTriangle(){
        frame=new JFrame("SierpinskiTriangle");
        frame.setSize(800,800);
        frame.setBackground(Color.BLACK);
        frame.add(this);
        frame.addKeyListener(this);
        sierpinskiProcess();


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    ArrayList<Point> points_arr=new ArrayList<>();
    Polygon triangle_polygon;

    public void sierpinskiProcess(){
        points_arr.add(new Point(frame.getWidth()/2,100,Color.WHITE));
        points_arr.add(new Point(100,frame.getHeight()-100, Color.WHITE));
        points_arr.add(new Point(frame.getWidth()-100, frame.getHeight()-100, Color.WHITE));

        triangle_polygon=new Polygon();
        for(Point point : points_arr)
            triangle_polygon.addPoint(point.getX(), point.getY());

        int x, y;
        do{
            x=(int)(Math.random()*frame.getWidth());
            y=(int)(Math.random()*frame.getHeight());
        }while(!triangle_polygon.contains(x, y));
        points_arr.add(new Point(x, y, Color.WHITE));
        System.out.println("X: "+points_arr.get(points_arr.size()-1).getX()+"\t\tY: "+points_arr.get(points_arr.size()-1).getY()+"\t\t"+((triangle_polygon.contains(points_arr.get(points_arr.size()-1).getX(),points_arr.get(points_arr.size()-1).getY()))?"TRUE":"FALSE"));
        addPoint(points_arr.get(points_arr.size()-1), points_arr.get((int)(Math.random()*3)));
        System.out.println("X: "+points_arr.get(points_arr.size()-1).getX()+"\t\tY: "+points_arr.get(points_arr.size()-1).getY()+"\t\t"+((triangle_polygon.contains(points_arr.get(points_arr.size()-1).getX(),points_arr.get(points_arr.size()-1).getY()))?"TRUE":"FALSE"));
        addPoint(points_arr.get(points_arr.size()-1), points_arr.get((int)(Math.random()*3)));
        System.out.println("X: "+points_arr.get(points_arr.size()-1).getX()+"\t\tY: "+points_arr.get(points_arr.size()-1).getY()+"\t\t"+((triangle_polygon.contains(points_arr.get(points_arr.size()-1).getX(),points_arr.get(points_arr.size()-1).getY()))?"TRUE":"FALSE"));
        addPoint(points_arr.get(points_arr.size()-1), points_arr.get((int)(Math.random()*3)));
        System.out.println("X: "+points_arr.get(points_arr.size()-1).getX()+"\t\tY: "+points_arr.get(points_arr.size()-1).getY()+"\t\t"+((triangle_polygon.contains(points_arr.get(points_arr.size()-1).getX(),points_arr.get(points_arr.size()-1).getY()))?"TRUE":"FALSE"));
        addPoint(points_arr.get(points_arr.size()-1), points_arr.get((int)(Math.random()*3)));
        System.out.println("X: "+points_arr.get(points_arr.size()-1).getX()+"\t\tY: "+points_arr.get(points_arr.size()-1).getY()+"\t\t"+((triangle_polygon.contains(points_arr.get(points_arr.size()-1).getX(),points_arr.get(points_arr.size()-1).getY()))?"TRUE":"FALSE"));
        addPoint(points_arr.get(points_arr.size()-1), points_arr.get((int)(Math.random()*3)));
        System.out.println("X: "+points_arr.get(points_arr.size()-1).getX()+"\t\tY: "+points_arr.get(points_arr.size()-1).getY()+"\t\t"+((triangle_polygon.contains(points_arr.get(points_arr.size()-1).getX(),points_arr.get(points_arr.size()-1).getY()))?"TRUE":"FALSE"));
        addPoint(points_arr.get(points_arr.size()-1), points_arr.get((int)(Math.random()*3)));
        System.out.println("X: "+points_arr.get(points_arr.size()-1).getX()+"\t\tY: "+points_arr.get(points_arr.size()-1).getY()+"\t\t"+((triangle_polygon.contains(points_arr.get(points_arr.size()-1).getX(),points_arr.get(points_arr.size()-1).getY()))?"TRUE":"FALSE"));
        addPoint(points_arr.get(points_arr.size()-1), points_arr.get((int)(Math.random()*3)));
        System.out.println("X: "+points_arr.get(points_arr.size()-1).getX()+"\t\tY: "+points_arr.get(points_arr.size()-1).getY()+"\t\t"+((triangle_polygon.contains(points_arr.get(points_arr.size()-1).getX(),points_arr.get(points_arr.size()-1).getY()))?"TRUE":"FALSE"));
        addPoint(points_arr.get(points_arr.size()-1), points_arr.get((int)(Math.random()*3)));
        System.out.println("X: "+points_arr.get(points_arr.size()-1).getX()+"\t\tY: "+points_arr.get(points_arr.size()-1).getY()+"\t\t"+((triangle_polygon.contains(points_arr.get(points_arr.size()-1).getX(),points_arr.get(points_arr.size()-1).getY()))?"TRUE":"FALSE"));
        addPoint(points_arr.get(points_arr.size()-1), points_arr.get((int)(Math.random()*3)));
        System.out.println("X: "+points_arr.get(points_arr.size()-1).getX()+"\t\tY: "+points_arr.get(points_arr.size()-1).getY()+"\t\t"+((triangle_polygon.contains(points_arr.get(points_arr.size()-1).getX(),points_arr.get(points_arr.size()-1).getY()))?"TRUE":"FALSE"));

    }

    public void addPoint(Point point, Point origin) {
        int x=(point.getX()+origin.getX())/2;
        int y=(point.getY()+origin.getY())/2;
        points_arr.add(new Point(x, y, Color.WHITE));

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,frame.getWidth(),frame.getHeight());
        g.setColor(Color.WHITE);
        for(Point point : points_arr){
            g.fillOval(point.getX(), point.getY(), 2, 2);
        }
    }

    public static void main(String[]args){
        SierpinskiTriangle app=new SierpinskiTriangle();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        if(e.getKeyCode()>48 && e.getKeyCode()<58){
            for(int a=0; a<e.getKeyCode()-48; a++)
                addPoint(points_arr.get(points_arr.size()-1), points_arr.get((int)(Math.random()*3)));
        }else{
            for(int a=0; a<100; a++){
                addPoint(points_arr.get(points_arr.size()-1), points_arr.get((int)(Math.random()*3)));
            }
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public class Point{
        int x,y;
        Color color;

        public Point(int x, int y, Color color){
            this.x=x;
            this.y=y;
            this.color=color;
        }

        public int getX(){return x;}
        public int getY(){return y;}

    }
}
