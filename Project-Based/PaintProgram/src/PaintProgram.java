import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.ImageGraphicAttribute;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class PaintProgram extends JPanel implements MouseListener, MouseMotionListener, ActionListener, AdjustmentListener, ChangeListener, KeyListener {
    public static void main(String[]args){PaintProgram app=new PaintProgram();}

    JFrame frame;
    ArrayList<Point> points=new ArrayList<>();
    
    //Stack<ArrayList<Point>> freeLines;
    Stack<Object> shapes, undo, redo;
    boolean drawingFreeLine=true;
    boolean drawingStraightLine=false;
    boolean drawingTriangle=false;
    boolean drawingRectangle=false;
    boolean drawingOval=false;
    boolean erasing=false;
    JButton rectButton, eraserButton, ovalButton, freeLineButton, straightLineButton, triangleButton, undoButton, redoButton, joinRoundButton, joinMiterButton;
    ImageIcon rectImg, eraserImg, ovalImg, freeLineImg, straightLineImg, triangleImg, undoImg, redoImg, joinRoundImg, joinMiterImg;
    Color backgroundColor=Color.WHITE;
    JScrollBar penWidthBar;
    JMenuBar bar, penWidthJMenuBar;
    JMenu colorMenu, file;
    Color[] colors;
    JMenuItem[] colorOptions;
    JFileChooser fileChooser;
    BufferedImage loadedImage;
    JMenuItem clear, exit, load, save;

    public void newDrawing(){
        undo=new Stack<Object>();
        redo=new Stack<Object>();
    }
    public PaintProgram(){
        frame=new JFrame();
        frame=new JFrame("PaintProgram");
        frame.setSize(800,800);
        frame.setBackground(Color.WHITE);
        frame.add(this);

        doYaButtonsAndStuff();

        penWidthBar=new JScrollBar(JScrollBar.HORIZONTAL, 1, 0, 1, 100);
        penWidthBar.addAdjustmentListener(this);
        penWidth=penWidthBar.getValue();



        //freeLines=new Stack<ArrayList<Point>>();
        shapes=new Stack<Object>();
        undo=new Stack<Object>();
        redo=new Stack<Object>();
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        bar=new JMenuBar();
        penWidthJMenuBar=new JMenuBar();
        colorMenu=new JMenu("Color options");
        file=new JMenu("File");
        colors=new Color[]{Color.RED, Color.ORANGE, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA};
        colorMenu.setLayout(new GridLayout(8,1));
        colorOptions=new JMenuItem[colors.length];
        for(int a=0; a<colors.length; a++){
            colorOptions[a]=new JMenuItem();
            colorOptions[a].setOpaque(true);
            colorOptions[a].setBackground(colors[a]);
            colorOptions[a].addActionListener(this);
            colorOptions[a].setPreferredSize(new Dimension(50, 40));
            colorOptions[a].putClientProperty("colorIndex", a);
            colorMenu.add(colorOptions[a]);
        }
        save=new JMenuItem("Save", KeyEvent.VK_S);
        clear=new JMenuItem("Clear");
        exit=new JMenuItem("Exit");
        load=new JMenuItem("Load", KeyEvent.VK_L);
        save.addActionListener(this);
        clear.addActionListener(this);
        exit.addActionListener(this);
        load.addActionListener(this);

        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
        load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_MASK));
        saveImg=new ImageIcon("C:\\Users\\alexm\\IdeaProjects\\PaintProgram\\src\\resource\\saveImg.png");
        saveImg=new ImageIcon(saveImg.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));
        loadImg=new ImageIcon("C:\\Users\\alexm\\IdeaProjects\\PaintProgram\\src\\resource\\loadImg.png");
        loadImg=new ImageIcon(loadImg.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));
        //loadImg=new ImageIcon("C:\\Users\\alexm\\IdeaProjects\\PaintProgram\\src\\resource\\loadImg.png");
        //loadImg=new ImageIcon(loadImg.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));
        //loadImg=new ImageIcon("C:\\Users\\alexm\\IdeaProjects\\PaintProgram\\src\\resource\\loadImg.png");
        //loadImg=new ImageIcon(loadImg.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));
        save.setIcon(saveImg);
        load.setIcon(loadImg);
        file.add(clear);
        file.add(load);
        file.add(save);
        file.add(exit);


        bar.add(file);
        bar.add(colorMenu);
        bar.add(freeLineButton);
        bar.add(straightLineButton);
        bar.add(ovalButton);
        bar.add(rectButton);
        bar.add(triangleButton);
        bar.add(eraserButton);
        bar.add(undoButton);
        bar.add(redoButton);
        penWidthJMenuBar.add(joinRoundButton);
        penWidthJMenuBar.add(joinMiterButton);
        penWidthJMenuBar.add(penWidthBar);

        currColor=colors[0];
        colorChooser=new JColorChooser();
        colorChooser.getSelectionModel().addChangeListener(this);
        colorMenu.add(colorChooser);
        frame.add(penWidthJMenuBar, BorderLayout.SOUTH);
        frame.add(bar, BorderLayout.NORTH);

        String currDir=System.getProperty("user.dir");
        fileChooser=new JFileChooser(currDir);
        //this.addKeyListener(this);
        frame.addKeyListener(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    ImageIcon saveImg, loadImg;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0,0,frame.getWidth(), frame.getHeight());
        Graphics2D g2=(Graphics2D) g;

        Iterator it=shapes.iterator();
        while(it.hasNext()){
            Object shape=it.next();

            if(shape instanceof BufferedImage){
                g.drawImage(loadedImage, 0,0,this);
            }
            else if(shape instanceof ArrayList<?>) {
                ArrayList<Point> p=(ArrayList<Point>)shape;
                g2.setStroke(new BasicStroke(p.get(0).getPenWidth(), p.get(0).getCap(), p.get(0).getJoin()));

                g.setColor(p.get(0).getColor());
                for (int a = 0; a < p.size() - 1; a++) {
                    g2.setStroke(new BasicStroke(p.get(a).getPenWidth(), p.get(a).getCap(), p.get(a).getJoin()));
                    Point p1 = p.get(a);
                    Point p2 = p.get(a + 1);
                    g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());

                }
            }
            else if(shape instanceof Rectangle){
                Rectangle rect=(Rectangle)shape;
                g2.setStroke(new BasicStroke(rect.getPenWidth(), rect.getCap(), rect.getJoin()));
                g.setColor(rect.getColor());
                g2.draw(rect.getShape());
            }else if(shape instanceof Oval){
                Oval oval=(Oval)shape;
                g2.setStroke(new BasicStroke(oval.getPenWidth(), oval.getCap(), oval.getJoin()));
                g.setColor(oval.getColor());
                g2.draw(oval.getShape());
            }else if(shape instanceof Triangle){
                Triangle triangle=(Triangle)shape;
                g2.setStroke(new BasicStroke(triangle.getPenWidth(), triangle.getCap(), triangle.getJoin()));
                g.setColor(triangle.getColor());
                g2.draw(triangle.getShape());
            }
        }


        if(drawingFreeLine && shapes.size()>0 && points.size()>0){
            g2.setStroke(new BasicStroke(points.get(0).getPenWidth(), points.get(0).getCap(), points.get(0).getJoin()));
            g.setColor(points.get(0).getColor());
            /*for(Point point : points){
                g.fillOval(point.getX(), point.getY(), point.getPenWidth(), point.getPenWidth());
            }*/
            for(int a=0; a<points.size()-1; a++){
                g2.setStroke(new BasicStroke(points.get(a).getPenWidth(), points.get(a).getCap(), points.get(a).getJoin()));
                Point p1=points.get(a);
                Point p2=points.get(a+1);
                g.drawLine(p1.getX(), p1.getY(), p2.getX(),p2.getY());

            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==freeLineButton){
            erasing=false;
            drawingFreeLine=true;
            drawingStraightLine=false;
            drawingTriangle=false;
            drawingRectangle=false;
            drawingOval=false;
        }else if(e.getSource()==eraserButton) {
            erasing=true;
            drawingFreeLine=true;
            drawingStraightLine=false;
            drawingTriangle=false;
            drawingRectangle=false;
            drawingOval=false;
        }else if(e.getSource()==ovalButton) {
            erasing=false;
            drawingFreeLine=false;
            drawingStraightLine=false;
            drawingTriangle=false;
            drawingRectangle=false;
            drawingOval=true;
        }else if(e.getSource()==rectButton) {
            erasing=false;
            drawingFreeLine=false;
            drawingStraightLine=false;
            drawingTriangle=false;
            drawingRectangle=true;
            drawingOval=false;
        }else if(e.getSource()==straightLineButton) {
            erasing=false;
            drawingFreeLine=false;
            drawingStraightLine=true;
            drawingTriangle=false;
            drawingRectangle=false;
            drawingOval=false;
        }else if(e.getSource()==triangleButton) {
            erasing=false;
            drawingFreeLine=false;
            drawingStraightLine=false;
            drawingTriangle=true;
            drawingRectangle=false;
            drawingOval=false;
        }else if(e.getSource()==undoButton){
            if(shapes.size()>0)
                undo.push(shapes.pop());
            repaint();
        }else if(e.getSource()==redoButton){
            if(undo.size()>0)
                shapes.push(undo.pop());
            repaint();
        }else if(e.getSource()==joinRoundButton){
            strokeCap=BasicStroke.CAP_ROUND;
            strokeJoin=BasicStroke.JOIN_ROUND;
        }else if(e.getSource()==joinMiterButton){
            strokeCap=BasicStroke.CAP_SQUARE;
            strokeJoin=BasicStroke.JOIN_MITER;
        }else if(e.getSource()==exit)
            System.exit(0);
        else if(e.getSource()==clear) {
            shapes=new Stack<Object>();
            loadedImage=null;
            repaint();
        }else if(e.getSource()==save){
            FileFilter filter=new FileNameExtensionFilter("*.png","png");
            fileChooser.setFileFilter(filter);
            if(fileChooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
                File file=fileChooser.getSelectedFile();
                try{
                    String st=file.getAbsolutePath();
                    if(st.indexOf(".png")>0)
                        st=st.substring(0,st.length()-4);
                    ImageIO.write(createImage(),"png", new File(st+".png") );
                }catch(IOException ioe){}
            }
        }
        else if(e.getSource()==load){
            fileChooser.showOpenDialog(null);
            File imgFile=fileChooser.getSelectedFile();
            if(imgFile!=null && imgFile.toString().indexOf(".png")>=0){
                try{
                    loadedImage=ImageIO.read(imgFile);
                }catch(IOException ioe){}
                shapes=new Stack<Object>();
                repaint();
            }else{
                if(imgFile!=null)
                    JOptionPane.showMessageDialog(null, "Wrong file type. Please select a PNG File.");
            }
        }
        else{
            int index = (int) ((JMenuItem) e.getSource()).getClientProperty("colorIndex");
            currColor = colors[index];
        }
    }

    public BufferedImage createImage(){
        int width=this.getWidth();
        int height=this.getHeight();
        BufferedImage img=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2=img.createGraphics();
        this.paint(g2);
        g2.dispose();
        return img;
    }

    //height of a triangle: h = ½(√3a)
    //h = 1/2 (sqrt(3)b)

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        penWidth=penWidthBar.getValue();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (drawingFreeLine) {
            if (points.size() > 0) {
                shapes.push(points);
                points = new ArrayList<>();
            }
        }else if(drawingStraightLine){
            points.set(1, new Point(e.getX(), e.getY(), currColor, penWidth, strokeCap, strokeJoin));
            points=new ArrayList<>();
        }
        repaint();
        firstClick=true;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    Color currColor=Color.MAGENTA;
    int penWidth=5;
    int strokeCap=BasicStroke.CAP_ROUND;
    int strokeJoin=BasicStroke.JOIN_ROUND;
    JColorChooser colorChooser;
    Point prevPoint=null;
    boolean perfectDimensions=false;

    @Override
    public void mouseDragged(MouseEvent e) {
        if(drawingFreeLine)
            points.add(new Point(e.getX(),e.getY(), (erasing)?backgroundColor:currColor, penWidth, strokeCap, strokeJoin));
        else if(drawingRectangle){
            if(firstClick){
                initX=e.getX();
                initY=e.getY();
                shapes.push(new Rectangle(initX, initY, currColor, penWidth, 0,0, strokeCap, strokeJoin));
                firstClick=false;
            }else{
                Rectangle rect=(Rectangle)shapes.peek();
                int width=Math.abs(initX-e.getX());
                int height=Math.abs(initY-e.getY());
                if(perfectDimensions){
                    width= Math.max(width, height);
                    height= Math.max(height, width);
                }
                rect.setWidth(width);
                rect.setHeight(height);

                if(!perfectDimensions) {
                    if (e.getX() < initX) {
                        rect.setX(e.getX());
                    }
                    if (e.getY() < initY) {
                        rect.setY(e.getY());
                    }
                }
                rect.setXDir((e.getX()<initX)?-1:1);
                rect.setYDir((e.getY()<initY)?-1:1);
            }
        }else if(drawingOval){
            if(firstClick){
                initX=e.getX();
                initY=e.getY();
                shapes.push(new Oval(initX, initY, currColor, penWidth, 0,0, strokeCap, strokeJoin));
                firstClick=false;
            }else{
                Oval oval=(Oval)shapes.peek();
                int width=Math.abs(initX-e.getX());
                int height=Math.abs(initY-e.getY());
                if(perfectDimensions){
                    width= Math.max(width, height);
                    height= Math.max(height, width);
                }
                oval.setWidth(width);
                oval.setHeight(height);

                if(!perfectDimensions) {
                    if (e.getX() < initX) {
                        oval.setX(e.getX());
                    }
                    if (e.getY() < initY) {
                        oval.setY(e.getY());
                    }
                }
                oval.setXDir((e.getX()<initX)?-1:1);
                oval.setYDir((e.getY()<initY)?-1:1);
            }
        }else if(drawingTriangle){
            if(firstClick){
                initX=e.getX();
                initY=e.getY();
                shapes.push(new Triangle(initX, initY, currColor, penWidth, 0,0, strokeCap, strokeJoin));
                firstClick=false;
            }else{
                Triangle triangle=(Triangle)shapes.peek();
                int width=Math.abs(initX-e.getX());
                int height=Math.abs(initY-e.getY());
                if(perfectDimensions){
                    width= Math.max(width, height);
                    height= Math.max(height, width);
                }
                triangle.setWidth(width);
                triangle.setHeight(height);
                triangle.setXDir((e.getX()<initX)?-1:1);
                triangle.setYDir((e.getY()<initY)?-1:1);
            }
        }else if(drawingStraightLine){
            if(firstClick){
                initX=e.getX();
                initY=e.getY();
                points.add(new Point(initX, initY, currColor, penWidth, strokeCap, strokeJoin));
                points.add(points.get(0));
                shapes.push(points);
                firstClick=false;
            }else{
                points.set(1, new Point(e.getX(), e.getY(), currColor, penWidth, strokeCap, strokeJoin));
            }
        }
        /*else if(drawingStraightLine){
            if(firstClick){
                initX=e.getX();
                initY=e.getY();
                shapes.push(new Line(initX, initY, currColor, penWidth, 0,0));
                firstClick=false;
            }else{
                int finalX=e.getX();
                int finalY=e.getY();
                Line line=(Line)shapes.peek();
                line.setFinalX(finalX);
                line.setFinalY(finalY);
            }
        }*/
        repaint();
    }

    boolean firstClick=true;
    int initX, initY;

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        currColor=colorChooser.getColor();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SHIFT) {
            perfectDimensions = true;
            System.out.println("pressed shift");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SHIFT) {
            perfectDimensions = false;
            System.out.println("unpressed shift");
        }
    }

    public class Shape{
        int x, y, penWidth, width, height, cap, join;
        Color color;
        public Shape(int x, int y, Color color, int penWidth, int width, int height, int cap, int join){
            this.x=x;
            this.y=y;
            this.color=color;
            this.penWidth=penWidth;
            this.width=width;
            this.height=height;
            this.cap=cap;
            this.join=join;
        }
        public int getCap(){return cap;}
        public int getJoin(){return join;}

        public int getX(){return x;}
        public int getY(){return y;}
        public int getPenWidth(){return penWidth;}
        public int getWidth(){return width;}
        public int getHeight(){return height;}
        public Color getColor(){return color;}
        public void setX(int x){this.x=x;}
        public void setY(int y){this.y=y;}
        public void setWidth(int width){this.width=width;}
        public void setHeight(int height){this.height=height;}

    }
    public class Rectangle extends Shape{

        public Rectangle(int x, int y, Color color, int penWidth, int width, int height, int cap, int join) {
            super(x, y, color, penWidth, width, height, cap, join);
        }

        public void setXDir(int xDir){this.xDir=xDir;}
        public void setYDir(int yDir){this.yDir=yDir;}
        int xDir=1;
        int yDir=1;

        public Rectangle2D.Double getShape(){
            return new Rectangle2D.Double((xDir==1)?getX():(getX()-getWidth()), (yDir==1)?getY():(getY()-getHeight()), getWidth(), getHeight());
        }

    }
    public class Oval extends Shape{

        public Oval(int x, int y, Color color, int penWidth, int width, int height, int cap, int join) {
            super(x, y, color, penWidth, width, height, cap, join);
        }

        public void setXDir(int xDir){this.xDir=xDir;}
        public void setYDir(int yDir){this.yDir=yDir;}
        int xDir=1;
        int yDir=1;
        public Ellipse2D.Double getShape(){ return new Ellipse2D.Double((xDir==1)?getX():(getX()-getWidth()), (yDir==1)?getY():(getY()-getHeight()), getWidth(), getHeight()); }

    }public class Triangle extends Shape{
        public Triangle(int x, int y, Color color, int penWidth, int width, int height, int cap, int join) {
            super(x, y, color, penWidth, width, height, cap, join);
        }

        public Polygon getShape(){
            int x[]=new int[]{getX(), (getX()+((xDir*getWidth())/2)), (getX()+(xDir*getWidth()))};
            //1/2 (sqrt(3)b)
            int y[]=new int[]{getY(), (int)(getY()+(((.5)*((getHeight())*(Math.sqrt(3))))*yDir)), getY()};
            return new Polygon(x, y, 3);
        }

        public void setXDir(int xDir){this.xDir=xDir;}
        public void setYDir(int yDir){this.yDir=yDir;}
        int xDir=1;
        int yDir=1;
    }


    public class Point{
        int x, y, penWidth, cap, join;
        Color color;
        public Point(int x, int y, Color color, int penWidth, int cap, int join){
            this.x=x;
            this.y=y;
            this.color=color;
            this.penWidth=penWidth;
            this.cap=cap;
            this.join=join;
        }
        public int getCap(){return cap;}
        public int getJoin(){return join;}
        public int getX(){return x;}
        public int getY(){return y;}
        public Color getColor(){return color;}
        public int getPenWidth(){return penWidth;}
    }

    public void doYaButtonsAndStuff(){
        freeLineButton=new JButton();
        freeLineImg=new ImageIcon("C:\\Users\\alexm\\IdeaProjects\\PaintProgram\\src\\resource\\freeLineImg.png");
        freeLineImg=new ImageIcon(freeLineImg.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));
        freeLineButton.setIcon(freeLineImg);
        freeLineButton.addActionListener(this);
        freeLineButton.setFocusable(false);
        eraserButton=new JButton();
        eraserImg=new ImageIcon("C:\\Users\\alexm\\IdeaProjects\\PaintProgram\\src\\resource\\eraserImg.png");
        eraserImg=new ImageIcon(eraserImg.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));
        eraserButton.setIcon(eraserImg);
        eraserButton.addActionListener(this);
        eraserButton.setFocusable(false);
        ovalButton=new JButton();
        ovalImg=new ImageIcon("C:\\Users\\alexm\\IdeaProjects\\PaintProgram\\src\\resource\\ovalImg.png");
        ovalImg=new ImageIcon(ovalImg.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));
        ovalButton.setIcon(ovalImg);
        ovalButton.addActionListener(this);
        ovalButton.setFocusable(false);
        rectButton=new JButton();
        rectImg=new ImageIcon("C:\\Users\\alexm\\IdeaProjects\\PaintProgram\\src\\resource\\rectImg.png");
        rectImg=new ImageIcon(rectImg.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));
        rectButton.setIcon(rectImg);
        rectButton.addActionListener(this);
        rectButton.setFocusable(false);
        straightLineButton=new JButton();
        straightLineImg=new ImageIcon("C:\\Users\\alexm\\IdeaProjects\\PaintProgram\\src\\resource\\straightLineImg.png");
        straightLineImg=new ImageIcon(straightLineImg.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));
        straightLineButton.setIcon(straightLineImg);
        straightLineButton.addActionListener(this);
        straightLineButton.setFocusable(false);
        triangleButton=new JButton();
        triangleImg=new ImageIcon("C:\\Users\\alexm\\IdeaProjects\\PaintProgram\\src\\resource\\triangleImg.png");
        triangleImg=new ImageIcon(triangleImg.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));
        triangleButton.setIcon(triangleImg);
        triangleButton.addActionListener(this);
        triangleButton.setFocusable(false);
        joinMiterButton=new JButton();
        joinMiterImg=new ImageIcon("C:\\Users\\alexm\\IdeaProjects\\PaintProgram\\src\\resource\\joinMiterImg.png");
        joinMiterImg=new ImageIcon(joinMiterImg.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));
        joinMiterButton.setIcon(joinMiterImg);
        joinMiterButton.addActionListener(this);
        joinMiterButton.setFocusable(false);
        joinRoundButton=new JButton();
        joinRoundImg=new ImageIcon("C:\\Users\\alexm\\IdeaProjects\\PaintProgram\\src\\resource\\joinRoundImg.png");
        joinRoundImg=new ImageIcon(joinRoundImg.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));
        joinRoundButton.setIcon(joinRoundImg);
        joinRoundButton.addActionListener(this);
        joinRoundButton.setFocusable(false);
        undoButton=new JButton();
        undoImg=new ImageIcon("C:\\Users\\alexm\\IdeaProjects\\PaintProgram\\src\\resource\\undoImg.png");
        undoImg=new ImageIcon(undoImg.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));
        undoButton.setIcon(undoImg);
        undoButton.addActionListener(this);
        undoButton.setFocusable(false);
        undoButton.getActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_MASK));
        redoButton=new JButton();
        //redoButton.setMnemonic('Z');
        redoImg=new ImageIcon("C:\\Users\\alexm\\IdeaProjects\\PaintProgram\\src\\resource\\redoImg.png");
        redoImg=new ImageIcon(redoImg.getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH));
        redoButton.setIcon(redoImg);
        redoButton.addActionListener(this);
        redoButton.setFocusable(false);

        KeyStroke undoKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK);
        KeyStroke redoKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK);

        Action undoAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                undoRedo(true);
                System.out.println("undoAction");
            }
        };
        Action redoAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                undoRedo(false);
                System.out.println("redoAction");
            }
        };

        this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(undoKeyStroke, "undoAction");
        this.getRootPane().getActionMap().put("undoAction", undoAction);
        this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(redoKeyStroke, "redoAction");
        this.getRootPane().getActionMap().put("redoAction", redoAction);
    }

    public void undoRedo(boolean check){
        if(check){
            if(shapes.size()>0)
                undo.push(shapes.pop());
            repaint();
        }else {
            if(undo.size()>0)
                shapes.push(undo.pop());
            repaint();
        }
    }
}
