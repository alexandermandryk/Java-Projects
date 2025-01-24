import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

public class JuliaSetProgram extends JPanel implements AdjustmentListener{
    JFrame frame;
    int red, green, blue,w,h;
    Color color;
    JButton resetButton, randomButton, saveButton;
    JScrollBar aBar, bBar, brightBar, hueBar, satBar, zoomBar, xBar, yBar, equationBar, backgroundBar;
    JPanel scrollPanel, labelPanel, mainPanel, buttonPanel;
    JLabel aLabel,bLabel, brightLabel, hueLabel, satLabel, zoomLabel, equationLabel, backgroundLabel;
    BufferedImage juliaImage;
    JScrollPane juliaPane;
    final int MAX_ITERATIONS=300;
    float count_paint=0;
    double compA,compB, x_value, y_value;
    double part1=0;
    double part2=0;

    public BufferedImage drawJulia(){

        w=frame.getWidth();
        h=frame.getHeight();

        System.out.println("W: "+w+"\t\t\tH: "+h);
        juliaImage=new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        int[] colors=new int[3];

        double zoom=1;
        for(int a=0; a<w; a++){
            for(int b=0; b<h; b++){
                float iterations=MAX_ITERATIONS; //500-6000 /1000.0
                double zx=1.5*((a-(0.5*w))/(0.5*zoomBar.getValue()/1000.0*w))-x_value;
                double zy= ((b-(0.5*h))/(0.5*zoomBar.getValue()/1000.0*h))+y_value;

                double pt1=0;
                double pt2=0;
                //switch(getEquation){}
                changeEquation(zx, zy);

                while(((part1+part2)<4)&&(iterations>0)){ // pt1+pt2
                    double storage=(part1-part2)+compA; // pt1-pt2
                    // y=(
                    zy=2*zx*zy+compB;
                    zx=storage;
                    iterations--;
                    changeEquation(zx, zy);
                }

                int c;
                //HSBtoRGB(hue,saturation,brightness);
                if(iterations>0)
                    c=Color.HSBtoRGB(iterations/MAX_ITERATIONS*(backgroundBar.getValue()/1000.0f), 1,1);
                else c=Color.HSBtoRGB(hueBar.getValue()/1000.0f, satBar.getValue()/1000.0f, brightBar.getValue()/1000.0f);

                juliaImage.setRGB(a,b,c);
            }
        }
        return juliaImage;

    }

    public void changeEquation(double zx, double zy){
        switch(equationBar.getValue()){
            case 0:
                part1=zx*zx;
                part2=zy*zy;
                break;
            case 1:
                part1=zx;
                part2=zy;
                break;
            case 2:
                part1=zx*zx*zx;
                part2=zy*zy*zx;
                break;
            case 3:
                part1=Math.abs(zx);
                part2=Math.abs(zy);
                break;
        }
    }

    public JuliaSetProgram(){
        compA=0;
        compB=0;
        frame=new JFrame("Julia Set Program");
        frame.setSize(1000,600);
        frame.setBackground(Color.WHITE);

        String currDir=System.getProperty("user.dir");
        fileChooser=new JFileChooser(currDir);


        //orientation, initial value, size of doodad, minvalue, max value
        aBar=new JScrollBar(JScrollBar.HORIZONTAL, 0,0,-2000,2000);
        aBar.addAdjustmentListener(this);
        bBar=new JScrollBar(JScrollBar.HORIZONTAL, 0,0,-2000,2000);
        bBar.addAdjustmentListener(this);
        brightBar=new JScrollBar(JScrollBar.HORIZONTAL, 500,0,0,1000);
        brightBar.addAdjustmentListener(this);
        hueBar=new JScrollBar(JScrollBar.HORIZONTAL, 500,0,0,1000);
        hueBar.addAdjustmentListener(this);
        satBar=new JScrollBar(JScrollBar.HORIZONTAL, 500,0,0,1000);
        satBar.addAdjustmentListener(this);
        zoomBar=new JScrollBar(JScrollBar.HORIZONTAL, 1000,0,100,6000);
        zoomBar.addAdjustmentListener(this);
        xBar=new JScrollBar(JScrollBar.HORIZONTAL, 0,200,-2000,2000);
        xBar.addAdjustmentListener(this);
        yBar=new JScrollBar(JScrollBar.VERTICAL, 0,300,-2000,2000);
        yBar.addAdjustmentListener(this);
        equationBar=new JScrollBar(JScrollBar.HORIZONTAL, 0,0,0,3);
        equationBar.addAdjustmentListener(this);
        backgroundBar=new JScrollBar(JScrollBar.HORIZONTAL, 1000,0,0,1000);
        backgroundBar.addAdjustmentListener(this);
        x_value=0;
        y_value=0;
        //resetButton



        scrollPanel=new JPanel();
        scrollPanel.setLayout(new GridLayout(8, 1));
        scrollPanel.add(aBar);
        scrollPanel.add(bBar);
        scrollPanel.add(brightBar);
        scrollPanel.add(hueBar);
        scrollPanel.add(satBar);
        scrollPanel.add(zoomBar);
        scrollPanel.add(equationBar);
        scrollPanel.add(backgroundBar);

        aLabel=new JLabel("A: "+aBar.getValue()/1000.0);
        bLabel=new JLabel("B: "+bBar.getValue()/1000.0);
        brightLabel=new JLabel("Brightness: "+brightBar.getValue()/1000.0);
        hueLabel=new JLabel("Hue: "+hueBar.getValue()/1000.0);
        satLabel=new JLabel("Saturation: "+satBar.getValue()/1000.0);
        zoomLabel=new JLabel("Zoom: "+zoomBar.getValue()/1000.0);
        equationLabel=new JLabel("Equation: "+equationBar.getValue());
        backgroundLabel=new JLabel("Background: "+backgroundBar.getValue()/1000.0);



        labelPanel=new JPanel();
        labelPanel.setLayout(new GridLayout(8,1));
        labelPanel.setPreferredSize(new Dimension(120,120));
        labelPanel.add(aLabel);
        labelPanel.add(bLabel);
        labelPanel.add(brightLabel);
        labelPanel.add(hueLabel);
        labelPanel.add(satLabel);
        labelPanel.add(zoomLabel);
        labelPanel.add(equationLabel);
        labelPanel.add(backgroundLabel);

        resetButton=new JButton("RESET");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetAll();
            }
        } );

        randomButton=new JButton("RANDOM");
        randomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                randomAll();
            }
        } );

        saveButton=new JButton("SAVE");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveImage();
            }
        } );

        buttonPanel=new JPanel();
        buttonPanel.setLayout(new GridLayout(2,2));
        buttonPanel.add(resetButton);
        buttonPanel.add(randomButton);
        buttonPanel.add(saveButton);

        mainPanel=new JPanel();
        mainPanel.setForeground(Color.BLACK);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(xBar, BorderLayout.NORTH);
        mainPanel.add(labelPanel, BorderLayout.WEST);
        mainPanel.add(scrollPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.EAST);

        frame.add(this);
        frame.add(yBar, BorderLayout.EAST);
        frame.add(mainPanel, BorderLayout.SOUTH);


        mainPanel=new JPanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public void randomAll(){
        aBar.setValue((int)(Math.random()*2000)-1000);
        bBar.setValue((int)(Math.random()*2000)-1000);
        brightBar.setValue((int)(Math.random()*1000));
        hueBar.setValue((int)(Math.random()*1000));
        satBar.setValue((int)(Math.random()*1000));
        //zoomBar.setValue((int)(Math.random()*5901)+100);
        //xBar.setValue((int)(Math.random()*4000)-2000);
        //yBar.setValue((int)(Math.random()*4000)-2000);
        equationBar.setValue((int)(Math.random()*4));
        backgroundBar.setValue((int)(Math.random()*1000));
        x_value=0;
        y_value=0;
        resetAdapters();

        repaint();
    }
    public void resetAll(){
        aBar.setValue(0);
        bBar.setValue(0);
        brightBar.setValue(500);
        hueBar.setValue(500);
        satBar.setValue(500);
        zoomBar.setValue(1000);
        xBar.setValue(0);
        yBar.setValue(0);
        equationBar.setValue(0);
        backgroundBar.setValue(1000);
        x_value=0;
        y_value=0;
        resetAdapters();

        repaint();
    }

    public void resetAdapters(){
        aAdapter();
        bAdapter();
        brightAdapter();
        satAdapter();
        hueAdapter();
        zoomAdapter();
        equationAdapter();
        backgroundAdapter();
    }

    JFileChooser fileChooser;
    public void saveImage()
    {
        if(juliaImage!=null) //juliaImage is the BufferedImage I declared globally (and used in
        //the drawJulia method)
        {
            FileNameExtensionFilter filter=new FileNameExtensionFilter("*.png","png");
            fileChooser.setFileFilter(filter);
            if(fileChooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION)
            {
                File file=fileChooser.getSelectedFile();
                try
                {
                    String st=file.getAbsolutePath();
                    if(st.indexOf(".png")>=0)
                        st=st.substring(0,st.length()-4);
                    ImageIO.write(juliaImage,"png",new File(st+".png"));
                }catch(IOException e)
                {
                }

            }
        }
    }
    public void aAdapter(){aLabel.setText("A: "+aBar.getValue()/1000.0);}
    public void bAdapter(){bLabel.setText("B: "+bBar.getValue()/1000.0);}
    public void brightAdapter(){brightLabel.setText("Brightness: "+brightBar.getValue()/1000.0);}
    public void hueAdapter(){hueLabel.setText("Hue: "+hueBar.getValue()/1000.0);}
    public void satAdapter(){satLabel.setText("Saturation: "+satBar.getValue()/1000.0);}
    public void zoomAdapter(){zoomLabel.setText("Zoom: "+zoomBar.getValue()/1000.0);}
    public void equationAdapter(){equationLabel.setText("Equation: "+equationBar.getValue());}
    public void backgroundAdapter(){backgroundLabel.setText("Background: "+backgroundBar.getValue()/1000.0);}

    public void adjustmentValueChanged(AdjustmentEvent e){
        if(e.getSource()==aBar)
            aAdapter();
        if(e.getSource()==bBar)
            bAdapter();
        if(e.getSource()==brightBar)
            brightAdapter();
        if(e.getSource()==hueBar)
            hueAdapter();
        if(e.getSource()==satBar)
            satAdapter();
        if(e.getSource()==zoomBar)
            zoomAdapter();
        if(e.getSource()==equationBar)
            equationAdapter();
        compA=aBar.getValue()/1000.0;
        compB=bBar.getValue()/1000.0;
        x_value=xBar.getValue()/1000.0;
        y_value=yBar.getValue()/1000.0;
		/*if(e.getSource()==resetButton){
			resetValues();
			resetAdapters();
		}*/

        repaint();

    }

    float pct=0.5f;

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2=(Graphics2D) g;
        g.drawImage(drawJulia(), 0,0,null);

        //double zoom=2.0;

        //g2.translate(getWidth()/2, getHeight()/2);
        //g2.scale(zoom, zoom);
        //g2.translate(getWidth()/2, getHeight()/2);

    }

    public static void main(String[]args){
        JuliaSetProgram app=new JuliaSetProgram();
    }
}