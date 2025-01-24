import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;
import java.io.*;
public class Minesweeper extends JFrame implements MouseListener{

    JPanel buttonPanel, resetPanel;
    boolean restart_timer=false;
    JButton resetFlags;
    int INTEGER_ROWS=10;
    int INTEGER_COLS=10;
    JToggleButton[][] buttons;
    int numMines=10;
    boolean firstClick=true;
    boolean gameOver=false;
    int selectedCount=0;
    ImageIcon[] numbers=new ImageIcon[8];
    ImageIcon  flag, mine, smile, win, wait, dead, blank, cross;
    Timer timer;
    int timePassed=0;
    JTextField timeField;
    GraphicsEnvironment ge;
    Font clockFont;
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem beginner, intermediate, expert;
    JButton reset;


    public Minesweeper(){
        setGrid(INTEGER_ROWS,INTEGER_COLS);

        for(int x=0; x<8; x++){
            numbers[x]=new ImageIcon("C:\\Users\\alexm\\IdeaProjects\\Minesweeper\\assets\\"+(x+1)+".png");
            numbers[x]=new ImageIcon(numbers[x].getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
        }

        flag=new ImageIcon("C:\\Users\\alexm\\IdeaProjects\\Minesweeper\\assets\\flag.png");
        flag=new ImageIcon(flag.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));

        mine=new ImageIcon("C:\\Users\\alexm\\IdeaProjects\\Minesweeper\\assets\\mine.png");
        mine=new ImageIcon(mine.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));

        blank=new ImageIcon("C:\\Users\\alexm\\IdeaProjects\\Minesweeper\\assets\\default.png");
        blank=new ImageIcon(blank.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));

        cross=new ImageIcon("C:\\Users\\alexm\\IdeaProjects\\Minesweeper\\assets\\x.png");
        cross=new ImageIcon(cross.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));

        try {
            ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            clockFont=Font.createFont(Font.TRUETYPE_FONT,
                    new File("C:\\Users\\alexm\\IdeaProjects\\Minesweeper\\assets\\"+"digital-7.ttf"));
            ge.registerFont(clockFont);
        } catch (IOException|FontFormatException e) {}

        timeField=new JTextField("  "+timePassed);
        timeField.setFont(clockFont.deriveFont(18f));
        timeField.setBackground(Color.BLACK);
        timeField.setForeground(Color.GREEN);

        menuBar=new JMenuBar();
        menuBar.setLayout(new GridLayout(1, 3));
        menu=new JMenu("Menu");
        menu.setLayout(new GridLayout(3, 1));
        beginner=new JMenuItem("Beginner");
        intermediate=new JMenuItem("Intermediate");
        expert=new JMenuItem("Expert");
        reset=new JButton("RESET");
        beginner.addMouseListener(this);
        intermediate.addMouseListener(this);
        expert.addMouseListener(this);
        reset.addMouseListener(this);


        menu.add(beginner);
        menu.add(intermediate);
        menu.add(expert);
        menuBar.add(menu);
        menuBar.add(reset);
        menuBar.add(timeField);
        this.add(menuBar, BorderLayout.NORTH);



        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void resetMethod(boolean reset_b){
        timePassed=0;
        timeField.setText("  "+timePassed);
        timer.cancel();
        restart_timer=reset_b;
    }

    public void mouseReleased(MouseEvent e){
        if(e.getSource()==beginner){
            numMines=10;
            INTEGER_ROWS=9;
            INTEGER_COLS=9;
            setGrid(INTEGER_ROWS, INTEGER_COLS);
            firstClick=true;
            resetMethod(false);
        }else if(e.getSource()==intermediate){
            numMines=40;
            INTEGER_ROWS=16;
            INTEGER_COLS=16;
            setGrid(INTEGER_ROWS, INTEGER_COLS);
            firstClick=true;
            resetMethod(false);
        }else if(e.getSource()==expert){
            numMines=99;
            INTEGER_ROWS=16;
            INTEGER_COLS=40;
            setGrid(INTEGER_ROWS, INTEGER_COLS);
            firstClick=true;
            resetMethod(false);
        }else if(e.getSource()==reset){
            resetMethod(true);
        }else {
            if(restart_timer) {
                timer=new Timer();
                timer.schedule(new UpdateTimer(), 0, 1000);
                restart_timer=false;
            }
            int rowClicked = (int) ((JToggleButton) e.getComponent()).getClientProperty("row");
            int colClicked = (int) ((JToggleButton) e.getComponent()).getClientProperty("col");
            if (!gameOver) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (firstClick) {
                        dropMines(rowClicked, colClicked);
                        firstClick = false;
                        timer = new Timer();
                        System.out.println("restarting timer again");
                        timer=new Timer();
                        timer.schedule(new UpdateTimer(), 0, 1000);
                    }
                    if (buttons[rowClicked][colClicked].isEnabled()) {
                        if ((int) buttons[rowClicked][colClicked].getClientProperty("state") == -1) { //lost
                            gameOver = true;
                            buttons[rowClicked][colClicked].setIcon(mine);
                            displayAndDisable();
                            timer.cancel();

                            JOptionPane.showMessageDialog(null, "You lost.");
                        } else {
                            selectedCount++;
                            expand(rowClicked, colClicked);

                            if (selectedCount == buttons.length * buttons[0].length - numMines) {
                                JOptionPane.showMessageDialog(null, "You lost.");
                                displayAndDisable();
                                gameOver = true;
                                timer.cancel();
                            }
                        }
                    }
                }
                if (!firstClick && e.getButton() == MouseEvent.BUTTON3) {
                    if (buttons[rowClicked][colClicked].getIcon() == null && !buttons[rowClicked][colClicked].isSelected()) {
                        buttons[rowClicked][colClicked].setIcon(flag);
                        buttons[rowClicked][colClicked].setDisabledIcon(flag);
                        buttons[rowClicked][colClicked].setEnabled(false);
                    } else if (flag.equals(buttons[rowClicked][colClicked].getIcon())) {
                        buttons[rowClicked][colClicked].setIcon(null);
                        buttons[rowClicked][colClicked].setDisabledIcon(null);
                        buttons[rowClicked][colClicked].setEnabled(true);

                    }
                }

            } else {
                //resetGame();
                //gameOver
            }
        }

    }

    public void displayAndDisable(){
        for (int r = 0; r < INTEGER_ROWS; r++) {
            for (int c = 0; c < INTEGER_COLS; c++) {
                int state = (int) buttons[r][c].getClientProperty("state");
                if (state == -1) {
                    buttons[r][c].setIcon(mine);
                    buttons[r][c].setDisabledIcon(mine);
                }else if(flag.equals(buttons[r][c].getIcon()))
                    buttons[r][c].setDisabledIcon(cross);
                buttons[r][c].setEnabled(false);
            }
        }
    }

    public void resetGame(){
        removeButtons();
        setButtons(INTEGER_ROWS,INTEGER_COLS);
        gameOver=false;
        firstClick=true;
        selectedCount=0;
    }



    public void expand(int row, int col){
        if(!flag.equals(buttons[row][col].getIcon())) {
            if (!buttons[row][col].isSelected()) {
                selectedCount++;
                buttons[row][col].setSelected(true);
            }

            int state = (int) buttons[row][col].getClientProperty("state");
            if (state > 0) {
                buttons[row][col].setIcon(numbers[state - 1]);
                buttons[row][col].setDisabledIcon(numbers[state - 1]);
            } else {
                for (int r = row - 1; r <= row + 1; r++) {
                    for (int c = col - 1; c <= col + 1; c++) {
                        try {
                            if (!buttons[r][c].isSelected() /*&& (int)buttons[row][col].getClientProperty("state")!=-1*/)
                                expand(r, c);
                        } catch (ArrayIndexOutOfBoundsException error) {
                        }
                    }
                }
            }
        }
    }

    public void dropMines(int row, int col){
        int count=numMines;
        while(count>0){
            int r=(int)(Math.random()*buttons.length);
            int c=(int)(Math.random()*buttons[0].length);
            int state=(int)buttons[r][c].getClientProperty("state");
            if(Math.abs(r-row)>1 || Math.abs(c-col)>1 && state==0){
                buttons[r][c].putClientProperty("state", -1);
                count--;
            }
        }

        for(int r=0;r<buttons.length;r++){
            for(int c=0;c<buttons[0].length;c++){
                int state=(int)(buttons[r][c].getClientProperty("state"));
                count=0;
                if(state!=-1){
                    for(int a=r-1; a<=r+1; a++){
                        for(int b=c-1; b<=c+1; b++){
                            try {
                                state = (int) (buttons[a][b].getClientProperty("state"));
                                if (state == -1)
                                    count++;
                            }catch(ArrayIndexOutOfBoundsException e){}
                        }
                    }
                    buttons[r][c].putClientProperty("state", count);
                }
            }
        }
/*
        for(int r=0;r<buttons.length;r++){
            for(int c=0;c<buttons[0].length;c++){
                buttons[r][c].setText(String.valueOf(buttons[r][c].getClientProperty("state")));
            }
        }

 */
    }

    public void setGrid(int rows, int cols){
        if(buttonPanel!=null)
            this.remove(buttonPanel);

        gameOver=false;

        setPanels(rows, cols);
        setButtons(rows, cols);

        /*resetFlags=new JButton("RESET FLAGS");
        resetFlags.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int r=0; r<INTEGER_ROWS; r++){
                    for(int c=0; c<INTEGER_COLS; c++){
                        if(!buttons[r][c].isEnabled()){
                            buttons[r][c].setEnabled(true);
                            buttons[r][c].setIcon(blank);
                            buttons[r][c].setSelected(false);
                        }
                    }
                }
            }
        });
        resetPanel=new JPanel();
        resetPanel.setLayout(new GridLayout(1,1));
        resetPanel.setPreferredSize(new Dimension(cols*50,50));
        resetPanel.add(resetFlags);*/

        this.add(buttonPanel, BorderLayout.CENTER);
        //this.add(resetPanel, BorderLayout.SOUTH);

        this.setSize(cols*50,(rows*50));
        this.revalidate();
    }

    public void setPanels(int rows, int cols){
        buttonPanel=new JPanel();
        buttonPanel.setLayout(new GridLayout(INTEGER_ROWS,INTEGER_COLS));
        buttonPanel.setSize(cols*50, rows*50);
        buttons=new JToggleButton[rows][cols];
    }

    public void removeButtons(){
        for(int r=0; r<INTEGER_ROWS; r++){
            for(int c=0; c<INTEGER_COLS; c++){
                buttonPanel.remove(buttons[r][c]);
            }
        }
    }

    public void setButtons(int rows, int cols){
        for(int r=0;r<rows;r++){
            for(int c=0; c<cols;c++){
                buttons[r][c]=new JToggleButton();
                buttons[r][c].putClientProperty("row", r);
                buttons[r][c].putClientProperty("col", c);
                buttons[r][c].putClientProperty("state", 0);
                buttons[r][c].putClientProperty("flag", true);
                buttonPanel.add(buttons[r][c]);
                buttons[r][c].addMouseListener(this);
                buttons[r][c].setIcon(blank);
                buttons[r][c].setSelected(false);
            }
        }
    }

    public class UpdateTimer extends TimerTask{

        @Override
        public void run() {
            if(!gameOver){
                timePassed++;
                timeField.setText("  "+timePassed);
            }
        }
    }

    public static void main(String[]args){Minesweeper app=new Minesweeper();}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseClicked(MouseEvent e){}
}