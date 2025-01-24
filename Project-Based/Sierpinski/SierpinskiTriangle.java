import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SierpinskiTriangle  extends JPanel implements KeyListener, AdjustmentListener{

    JFrame frame;
    JScrollBar scrollBar;
    JPanel scrollPanel;
    JLabel scrollLabel;

    public SierpinskiTriangle(){
        frame=new JFrame("SierpinskiTriangle");
        frame.setSize(800,800);
        frame.setBackground(Color.BLACK);
        frame.add(this);
        frame.addKeyListener(this);
        scrollPanel=new JPanel();
        //scrollPanel.setLayout(/*new GridLayout(11, 2)*/);

        scrollBar=new JScrollBar(JScrollBar.HORIZONTAL, 50,0,1,99);
        scrollBar.addAdjustmentListener(this);
        scrollLabel=new JLabel("Percent: "+scrollBar.getValue()+"%");
        scrollPanel.add(scrollLabel);
        scrollPanel.add(scrollBar);
        this.add(scrollPanel, BorderLayout.SOUTH);
        sierpinskiProcess();


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    ArrayList<Point> points_arr=new ArrayList<>();
    Polygon hexagon_polygon;

    public void sierpinskiProcess(){
        points_arr.add(new Point(frame.getWidth()/2,100,Color.WHITE));
        points_arr.add(new Point(100,frame.getHeight()-100, Color.WHITE));
        points_arr.add(new Point(frame.getWidth()-100, frame.getHeight()-100, Color.WHITE));

        //points_arr.add(new Point(240, 80,Color.WHITE));
        //points_arr.add(new Point(560, 80,Color.WHITE));
        //points_arr.add(new Point(80, 360,Color.WHITE));
        //points_arr.add(new Point(720, 360,Color.WHITE));
        //points_arr.add(new Point(240, 640,Color.WHITE));
        //points_arr.add(new Point(560, 640,Color.WHITE));

        hexagon_polygon=new Polygon();
        for(Point point : points_arr)
            hexagon_polygon.addPoint(point.getX(), point.getY());

        int x, y;
        do{
            x=(int)(Math.random()*frame.getWidth());
            y=(int)(Math.random()*frame.getHeight());
        }while(!hexagon_polygon.contains(x, y));
        points_arr.add(new Point(x, y, Color.WHITE));
    }

    public void addPoint(Point point, Point origin) {
        //int x=(point.getX()+origin.getX())/2;
        int x=(int)((origin.getX()-point.getX())*(scrollBar.getValue()/100.0)+point.getX());
        //int y=(point.getY()+origin.getY())/2;
        int y=(int)((origin.getY()-point.getY())*(scrollBar.getValue()/100.0)+point.getY());
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
                addPoint(points_arr.get(points_arr.size()-1), points_arr.get((int)(Math.random()*6)));
        }else{
            for(int a=0; a<100; a++){
                addPoint(points_arr.get(points_arr.size()-1), points_arr.get((int)(Math.random()*6)));
            }
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        scrollLabel.setText("Percent: "+scrollBar.getValue()+"%");
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
