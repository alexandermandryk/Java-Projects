import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.geom.Rectangle2D;

public class MazeProgram extends JPanel implements KeyListener{

	JFrame frame;
	String[][] maze;
	Hero hero;
	int endR, endC;
	ArrayList<Wall> walls;
	ArrayList<Wall> final_wall;
	boolean in2D=true;
	final int[] finalRowCol={22,59};
	int[][] visited=new int[100][100];
	ArrayList<Wall> floors;
	Monster monster;
	private static boolean death_screen=false;

	public void paintComponent(Graphics g){
		//g.setColor(Color.RED);

		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
        if((death_screen && in2D)||(hero.getR()==22 && hero.getC()==59)){
			Color bg=new Color(255,50,50);
			g.setColor(bg);
			g.fillRect(0,0,frame.getWidth(),frame.getHeight());
			Font font=new Font("Verdana", Font.BOLD, 100);
			g.setFont(font);
			g.setColor(Color.BLACK);
			g.drawString((hero.getR()==22 && hero.getC()==59)?"WINNER!":"GAME OVER",50,300);
        }else{
			Color bg=new Color(100,200,200);
			g.setColor(bg);
			g.fillRect(0,0,frame.getWidth(),frame.getHeight());

			g.setColor(Color.BLACK);

			int w=(int)(frame.getWidth()*.9);
			int h=(int)(frame.getHeight()*.9);
			if(in2D){
				for(int a=0; a<maze.length; a++){
					for(int b=0; b<maze[0].length; b++){
						if(maze[a][b].equals(" ") && visited[a][b]==1){
							g.fillRect(b*10, a*10,10,10);
						}
						//g.fillRect((int)((w/maze[0].length)*b)+10, (int)((h/maze.length)*a)+10, (int)(w/(maze[0].length*2)), (int)(h/(maze.length*2)));
					}
				}
				g.setColor(Color.GREEN);
				g.fillRect(59*10, 22*10, 10, 10);
				g.setColor(bg);
				g.fillOval(monster.getC()*10, monster.getR()*10, 9, 9);
                g.setColor(Color.BLACK);
                g.drawOval(monster.getC()*10, monster.getR()*10, 9, 9);
				g.setColor(Color.RED);
				g.fillOval(hero.getC()*10, hero.getR()*10,9,9);
			}else{
				Color color_color=(color_flip)?Color.WHITE:Color.BLACK;
				g2.setColor(color_color);
				int[] x_x={100,650,650,100};
				int[] y_y={300,300,550,550};
				g2.fillPolygon(new Polygon(x_x,y_y,4));
				g2.setColor(color_color);
				int[] y_y_2={50,50,300,300};
				g2.fillPolygon(new Polygon(x_x,y_y_2,4));
				for(Wall wall:floors){
					g2.setPaint(wall.getPaint());
					g2.fillPolygon(wall.getPoly());
				}
				for(Wall wall:walls){
					//g.setColor(Color.BLACK);
					g2.setPaint(wall.getPaint());
					g.fillPolygon(wall.getPoly());
					g.setColor(new Color(155,155,155,50));
					g.drawPolygon(wall.getPoly());
				}
				if(final_wall.size()>0){
					g.setColor(Color.GREEN);
					g.fillPolygon(final_wall.get(0).getPoly());
					g.setColor(Color.LIGHT_GRAY);
					g.drawPolygon(final_wall.get(0).getPoly());
				}
				int[] overallX={0,100,100,0};
				int[] overallY={300,300,650,650};
				g.setColor(bg);
				g.fillPolygon(new Polygon(overallX, overallY, 4));
			}
			Font font = new Font("Verdana", Font.BOLD, 45);
   			g.setFont(font);
   			g.setColor(Color.BLACK);
			//g.drawString(String.valueOf(hero.getDir()), frame.getWidth()*1/200, frame.getHeight()*2/3);
			int wi=(String.valueOf(hero.getDir()).equals("W"))?30:(String.valueOf(hero.getDir()).equals("N"))?36:39;
			g.drawString(String.valueOf(hero.getDir()), wi, 86);
        }
	}

	public class Monster{
		int r,c;
		public Monster(int r, int c){
			this.r=r;
			this.c=c;
		}

		public int getR(){return r;}
		public int getC(){return c;}

		public void move(int h_r, int h_c){
			int row_diff=r-h_r; //positive means monster is more RIGHT
			int col_diff=c-h_c; //positive means monster is more DOWN
			//System.out.println("hero row: "+h_r+"\t\tmonster row: "+r+"\t\thero col: "+h_c+"\t\tmonster col: "+c+"\t\t row diff: +"+row_diff+"\t\tcol diff: "+col_diff);
			int cd=((col_diff>0)?1:-1);
			int rd=((row_diff>0)?-1:1);
			if(Math.abs(row_diff)>Math.abs(col_diff)){
				System.out.println("this");
				if(maze[r+rd][c].equals(" "))
					r=r+rd;
				else if(maze[r-rd][c].equals(" "))
					r=r-rd;
				if(maze[r][c-cd].equals(" "))
					c=c-cd;
				else if(maze[r][c+cd].equals(" "))
					c=c+cd;
			}else if(Math.abs(row_diff)<Math.abs(col_diff)){
				if(maze[r][c-cd].equals(" "))
					c=c-cd;
				else if(maze[r][c+cd].equals(" "))
					c=c+cd;
				if(maze[r+rd][c].equals(" "))
					r=r+rd;
				else if(maze[r-rd][c].equals(" "))
					r=r-rd;
			}
		}
	}

	public class Hero{

		int r, c;
		char dir;

		public Hero(int r, int c, char dir){
			this.r=r;
			this.c=c;
			this.dir=dir;
		}

		public int getR(){return r;}
		public int getC(){return c;}
		public char getDir(){return dir;}
		public void move(int key){
			switch(key){
				//turn left
				case 37:
					switch(dir){
						case 'E':
							dir='N';
						break;
						case 'N':
							dir='W';
						break;
						case 'W':
							dir='S';
						break;
						case 'S':
							dir='E';
						break;
					}
				break;

				//forward
				case 38:
					switch(dir){
						case 'E':
							try{
								if(!maze[r][c+1].equals("*"))
									c++;
							}catch(ArrayIndexOutOfBoundsException e){
								System.out.println("outta bounds");
							}

						break;
						case 'N':
							try{
								if(!maze[r-1][c].equals("*"))
									r--;
							}catch(ArrayIndexOutOfBoundsException e){
								System.out.println("outta bounds");
							}

						break;
						case 'W':
							try{
								if(!maze[r][c-1].equals("*"))
									c--;
							}catch(ArrayIndexOutOfBoundsException e){
								System.out.println("outta bounds");
							}

						break;
						case 'S':
							try{
								if(!maze[r+1][c].equals("*"))
									r++;
							}catch(ArrayIndexOutOfBoundsException e){
								System.out.println("outta bounds");
							}

						break;
					}
				break;

				//turn right
				case 39:
					switch(dir){
						case 'E':
							dir='S';
						break;
						case 'S':
							dir='W';
						break;
						case 'W':
							dir='N';
						break;
						case 'N':
							dir='E';
						break;
					}
				break;

				//40 = back
			}
		}
	}

	public MazeProgram(){
		frame=new JFrame("MazeProgram");
		frame.add(this);
		frame.setSize(800,600);

		setMaze();

		frame.addKeyListener(this);

		//last - el finale - null pointers incoming
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setResizable(false);
		frame.setVisible(true);
	}

	public void setMaze(){
		maze=new String[24][0];
		try{
			String temp;
			int r=0;
			BufferedReader buffRead=new BufferedReader(new FileReader(new File("maze.txt")));
			boolean first=true;
			while((temp=buffRead.readLine())!=null){

				if(first){
					String[] pieces=temp.split(" ");
					int row=Integer.parseInt(pieces[0]);
					int col=Integer.parseInt(pieces[1]);
					char direction=pieces[2].charAt(0);
					endR=Integer.parseInt(pieces[3]);
					endC=Integer.parseInt(pieces[4]);
					hero=new Hero(22, 58, direction);
					monster=new Monster(5,40);
					first=false;
				}else{

					maze[r]=temp.split("");
					r++;
				}
			}
		}catch(IOException ioe){
			System.out.println("IOException");
		}

		for(int a=0; a<maze.length; a++){
			for(int b=0; b<maze[0].length; b++)
				System.out.print(maze[a][b]);
			System.out.println();
		}
	}

	static boolean color_flip=false;

	public void keyPressed(KeyEvent e){
        if(!death_screen){
	    	if(e.getKeyCode()==70)
	    		color_flip=!color_flip;
	    	if(e.getKeyCode()==32)
	    		in2D=!in2D;
	    		hero.move(e.getKeyCode());
	    	if(!in2D){
                death_screen=false;
		    	set3D();
            }

	    	//in2D=(e.getKeyCode()==32)?in2D:in2D;

		    //System.out.println(e.getKeyCode());
		    //hero.move(e.getKeyCode());
		    visited[hero.getR()][hero.getC()]=1;
		    if(e.getKeyCode()==38)
		    	monster.move(hero.getR(), hero.getC());
	    	repaint();

    		if(monster.getR()==hero.getR() && monster.getC()==hero.getC())
			    death_screen=true;
		    if(hero.getR()==22 && hero.getC()==59)
	    		death_screen=true;
        }
	}
	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e){}

	public void set3D(){
		walls=new ArrayList<Wall>();
		final_wall=new ArrayList<Wall>();
		floors=new ArrayList<Wall>();
		for(int a=0; a<4; a++){
			int[] xL={100+(50*a),150+(50*a),150+(50*a),100+(50*a)};
			int[] yL={100+(50*a),100+(50*a),500-(50*a),500-(50*a)};
			walls.add(new Wall(xL,yL,a,"LeftPath"));
			int[] xR={650-50*a, 600-50*a, 600-50*a, 650-50*a};
			int[] yR={100+50*a, 100+50*a, 500-50*a, 500-50*a};
			walls.add(new Wall(xR,yR,a,"RightPath"));

		}


		int row=hero.getR();
		int col=hero.getC();
		//visited[row][col]=1;
		for(int a=4; a>=0; a--){
			switch(hero.getDir()){
				case 'E':try{
					if(maze[row-1][col+a].equals("*"))
						leftWall(a);
					if(maze[row+1][col+a].equals("*"))
						rightWall(a);
					if(visited[row][col+a]==1)
						frontFloor(a);
					if(visited[row-1][col+a]==1)
						leftFloor(a);
					if(visited[row+1][col+a]==1)
						rightFloor(a);
					}catch(ArrayIndexOutOfBoundsException e){}
				break;
				case 'N':
				try{
					if(maze[row-a][col-1].equals("*"))
						leftWall(a);
					if(maze[row-a][col+1].equals("*"))
						rightWall(a);
					if(visited[row-a][col]==1)
						frontFloor(a);
					if(visited[row-a][col-1]==1)
						leftFloor(a);
					if(visited[row-a][col+1]==1)
						rightFloor(a);
						}catch(ArrayIndexOutOfBoundsException e){}
				break;
				case 'W':try{
					if(maze[row+1][col-a].equals("*"))
						leftWall(a);
					if(maze[row-1][col-a].equals("*"))
						rightWall(a);
					if(visited[row][col-a]==1)
						frontFloor(a);
					if(visited[row-1][col-a]==1)
						leftFloor(a);
					if(visited[row+1][col-a]==1)
						rightFloor(a);
						}catch(ArrayIndexOutOfBoundsException e){}
				break;
				case 'S':try{
					if(maze[row+a][col+1].equals("*"))
						leftWall(a);
					if(maze[row+a][col-1].equals("*"))
						rightWall(a);
					if(visited[row+a][col]==1)
						frontFloor(a);
					if(visited[row+a][col+1]==1)
						leftFloor(a);
					if(visited[row+a][col-1]==1)
						rightFloor(a);
						}catch(ArrayIndexOutOfBoundsException e){}
				break;
			}
		}
		for(int b=4; b>=0; b--){
			switch(hero.getDir()){
				case 'E':try{
					if(maze[row][col+(b)].equals("*"))
						frontWall(b, false);
					if(row==22 && (col+(b))==59)
						frontWall(b, true);
						}
						catch(ArrayIndexOutOfBoundsException e){}
				break;
				case 'N':try{
					if(maze[row-(b)][col].equals("*"))
						frontWall(b, false);
						}catch(ArrayIndexOutOfBoundsException e){}
				break;
				case 'W':try{
					if(maze[row][col-(b)].equals("*"))
						frontWall(b, false);
					if(row==1 && (col-(b))==0)
						frontWall(b, false);
						}catch(ArrayIndexOutOfBoundsException e){}
				break;
				case 'S':try{
					if(maze[row+(b)][col].equals("*"))
						frontWall(b, false);
						}catch(ArrayIndexOutOfBoundsException e){}
				break;
			}
		}
	}
	public void leftFloor(int a){
		int[] x={100+50*a, 150+50*a, 150+50*a,150+50*a};
		int[] y={550-50*a, 500-50*a, 500-50*a, 500-50*a};
		floors.add(new Wall(x,y,a,"LeftFloor"));
	}

	public void rightFloor(int a){
		int[] x={650-50*a, 600-50*a, 600-50*a,650-50*a};
		int[] y={550-50*a, 500-50*a, 500-50*a, 500-50*a};
		floors.add(new Wall(x,y,a,"RightFloor"));
	}


	public void frontFloor(int a){
		int[] x={50+50*a, 100-50*a, 600-50*a, 650-50*a};
		int[] y={550-50*a, 500-50*a, 500-50*a, 550-50*a};
		floors.add(new Wall(x,y,a,"FrontFloor"));
	}

	public void frontWall(int a, boolean choice){
		int[] x={100+50*a, 650-50*a, 650-50*a, 100+50*a};
		int[] y={50+50*a, 50+50*a, 550-50*a, 550-50*a};
		if(!choice)
			walls.add(new Wall(x,y,a,"FrontWall"));
		else
			final_wall.add(new Wall(x,y,a,"FrontWall"));
	}

	public void rightWall(int a){
		int[] x={650-50*a, 600-50*a, 600-50*a, 650-50*a};
		int[] y={50+50*a, 100+50*a, 500-50*a, 550-50*a};
		walls.add(new Wall(x,y,a,"RightWall"));
	}

	public void leftWall(int a){
		int[] x={100+(50*a),150+(50*a),150+(50*a),100+(50*a)};
		int[] y={50+(50*a),100+(50*a),500-(50*a),550-(50*a)};
		walls.add(new Wall(x,y,a,"LeftWall"));
	}

	//starting tL,tR,bL,bR == (100,100);(150,100);(100,500);(150,500)
	public class Wall{
		int[] x, y;
		int color;
		String type;
		public Wall(int[] x, int[] y, int color, String type){
			this.x=x;
			this.y=y;
			this.color=(color_flip)?50*color:240-50*color;
			this.type=type;
		}

		public Polygon getPoly(){
			return new Polygon(x,y,4);
		}
		public GradientPaint getPaint()
		{
			Color startColor=new Color(color ,color,color);
			int endC=color+((color_flip)?50:-50);
			if(endC<0)
				endC=0;
			if(endC>255)
				endC=255;
			Color endColor=new Color(endC,endC,endC);
			Color floor_tile=new Color((!color_flip)?98:208, (!color_flip)?75:175, (!color_flip)?102:202, (!color_flip)?100:100);
			System.out.println(visited[8][40]);
			if(type.equals("LeftWall") || type.equals("RightWall"))
				return new GradientPaint(x[0], y[0], startColor,x[1], y[0], endColor);
			if(type.equals("FrontWall") || type.equals("FrontWall"))
				return new GradientPaint(x[0], y[0], startColor,x[1], y[0], startColor);
			if(type.equals("TopWall") || type.equals("BottomWall"))
				return new GradientPaint(x[0], y[0], startColor,x[0], y[1], endColor);
			if(type.equals("FrontFloor") || type.equals("RightFloor") || type.equals("LeftFloor"))
				return new GradientPaint(x[0],y[0],floor_tile,x[1],y[0],floor_tile);

			return new GradientPaint(x[0], y[0], startColor,x[1], y[0], endColor);
		}


	}


	public static void main(String[]args){
		MazeProgram app = new MazeProgram();
	}
}

/*

	***************************
	**	       *****	     **
	* *	       *****	    * *
	*  *	   *****       *  *
	*   *	   *****      *   *
	*    *	   *****     *    *
	*     *	   *****    *     *
	*      *   *****   *      *
	*       *  *****  *       *
	***************************
	***************************
	***************************
	*          *****          *
	*          *****          *
	*          *****          *
	*          *****          *
	*          *****          *
	*          *****          *
	*          *****          *
	*          *****          *
	***************************






*/