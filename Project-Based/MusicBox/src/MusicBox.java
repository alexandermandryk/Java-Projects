import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.*;
import java.net.URL;

public class MusicBox extends JFrame implements Runnable, ActionListener, AdjustmentListener {

    int COLUMNS=50;
    JToggleButton[][] toggleButtons=new JToggleButton[37][COLUMNS];
    JPanel buttonPanel, playStopPanel, tempoPanel;
    JScrollPane scrollPane;
    JLabel tempoLabel;
    JScrollBar tempoBar;
    int tempo=200;
    JButton playStopButton, clearButton;
    boolean currentlyPlaying=false;
    ImageIcon disabledIcon=new ImageIcon("C:\\Users\\alexm\\IdeaProjects\\MusicBox\\src\\disabled.png");
    Thread timing;
    String currentDirectory;
    JFileChooser fileChooser;
    String[] musicNotes;
    JMenuBar menuBar;
    String[] clipNames;
    JMenu instrumentMenu, fileMenu, addRemove;
    String[] instrumentNames;
    JMenuItem saveItem, loadItem, addColumn, add10Column, removeColumn, remove10Column;



    public MusicBox(){

        buttonPanel=new JPanel();
        buttonPanel.setLayout(new GridLayout(37, 50));
        //musicNotes=new String[]{"C", "B","A#", "A","G#", "G","F#", "F","E", "D#","D", "C#"};
        //or

        musicNotes=new String[]{
                "C4",
                "B3", "A#3", "A3", "G#3", "G3", "F#3", "F3", "E3", "D#3", "D3", "C#3", "C3",
                "B2", "A#2", "A2", "G#2", "G2", "F#2", "F2", "E2", "D#2", "D2", "C#2", "C2",
                "B1", "A#1", "A1", "G#1", "G1", "F#1", "F1", "E1", "D#1", "D1", "C#1", "C1"
        };

        int count=0;
        for(int row=0; row<toggleButtons.length; row++){
            count++;
            for(int col=0; col<toggleButtons[0].length; col++) {
                toggleButtons[row][col] = new JToggleButton(musicNotes[row]);
                toggleButtons[row][col].setPreferredSize(new Dimension(30,30));
                toggleButtons[row][col].setMargin(new Insets(0,0,0,0));
                buttonPanel.add(toggleButtons[row][col]);
                buttonPanel.add(toggleButtons[row][col]);
            }
        }

        System.out.println("row count: "+count);
        scrollPane=new JScrollPane(buttonPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.add(scrollPane, BorderLayout.CENTER);
        this.setSize(1200,600);



        instrumentNames=new String[]{"Bell", "Glockenspiel", "Marimba", "Oboe", "Oh_Ah", "Piano"};
        clipNames=new String[]{
                "C4",
                "B3", "ASharp3", "A3", "GSharp3", "G3", "FSharp3", "F3", "E3", "DSharp3", "D3", "CSharp3", "C3",
                "B2", "ASharp2", "A2", "GSharp2", "G2", "FSharp2", "F2", "E2", "DSharp2", "D2", "CSharp2", "C3",
                "B1", "ASharp1", "A1", "GSharp1", "G1", "FSharp1", "F1", "E1", "DSharp1", "D1", "CSharp1", "C1"
        };
        clip=new Clip[clipNames.length];
        loadTones(instrumentNames[0]);


        playStopPanel=new JPanel();
        playStopPanel.setLayout(new GridLayout(1, 2));
        playStopButton=new JButton("Play");
        playStopButton.addActionListener(this);

        clearButton=new JButton("Clear");
        clearButton.addActionListener(this);

        playStopPanel.add(playStopButton);
        playStopPanel.add(clearButton);

        instrumentMenu=new JMenu("Instruments");

        for(int instrument=0; instrument<instrumentNames.length; instrument++){
            JMenuItem temp=new JMenuItem(instrumentNames[instrument]);
            instrumentMenu.add(temp);
            temp.putClientProperty("name", instrumentNames[instrument]);
            temp.addActionListener(this);
        }/*
        bell=new JMenuItem("Bell");
        glockenspiel=new JMenuItem("Glockenspiel");
        marimba=new JMenuItem("Marimba");
        oh_ah=new JMenuItem("Oh_Ah");
        oboe=new JMenuItem("Oboe");
        piano=new JMenuItem("Piano");
        instrumentMenu.add(bell);
        instrumentMenu.add(glockenspiel);
        instrumentMenu.add(marimba);
        instrumentMenu.add(oh_ah);
        instrumentMenu.add(oboe);
        instrumentMenu.add(piano);
        bell.putClientProperty("name", bell.getName());
        glockenspiel.putClientProperty("name", "Glockenspiel");
        marimba.putClientProperty("name", marimba.getName());
        oh_ah.putClientProperty("name", oh_ah.getName());
        oboe.putClientProperty("name", oboe.getName());
        piano.putClientProperty("name", piano.getName());
        bell.addActionListener(this);
        glockenspiel.addActionListener(this);
        marimba.addActionListener(this);
        oh_ah.addActionListener(this);
        oboe.addActionListener(this);
        piano.addActionListener(this);*/


        tempoBar=new JScrollBar(JScrollBar.HORIZONTAL, tempo, 0,50,350);
        tempoBar.addAdjustmentListener(this);
        tempoLabel=new JLabel("Tempo: "+tempoBar.getValue());
        tempoPanel=new JPanel();
        tempoPanel.setLayout(new GridLayout(1,2));
        tempoPanel.add(tempoLabel);
        tempoPanel.add(tempoBar);
        currentDirectory=System.getProperty("user.dir");
        fileChooser=new JFileChooser(currentDirectory);


        fileMenu=new JMenu("File");
        saveItem=new JMenuItem("Save");
        saveItem.addActionListener(this);
        loadItem=new JMenuItem("Load");
        loadItem.addActionListener(this);
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);

        addColumn=new JMenuItem("Add Column");
        add10Column=new JMenuItem("Add 10 Column");
        removeColumn=new JMenuItem("Remove Column");
        remove10Column=new JMenuItem("Remove 10 Column");
        addColumn.addActionListener(this);
        add10Column.addActionListener(this);
        removeColumn.addActionListener(this);
        remove10Column.addActionListener(this);
        addRemove=new JMenu("Add / Remove");
        addRemove.add(addColumn);
        addRemove.add(add10Column);
        addRemove.add(removeColumn);
        addRemove.add(remove10Column);

        menuBar=new JMenuBar();
        menuBar.setLayout(new GridLayout(1, 4));
        menuBar.add(fileMenu);
        menuBar.add(playStopPanel);
        menuBar.add(instrumentMenu);
        menuBar.add(addRemove);
        this.add(menuBar, BorderLayout.NORTH);
        this.add(tempoPanel, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        timing=new Thread(this);

        timing.start();
    }

    public void loadTones(String initInstrument){
        try{
            //if(initInstrument.equals(""))
              //  initInstrument=instrumentNames[0];

            for(int x=0; x<clipNames.length; x++){
                System.out.print(initInstrument+" - instrument");
                String str="Music\\"+initInstrument+"\\"+initInstrument+" - "+clipNames[x]+".wav";
                URL url=this.getClass().getResource(str);
                System.out.println(str);
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                clip[x]=AudioSystem.getClip();
                clip[x].open(audioIn);
            }
        }catch(UnsupportedAudioFileException| IOException| LineUnavailableException e){}

        System.out.println("column after load notes: "+COLUMNS);
    }



    Clip[] clip;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playStopButton)
            playStopButton.setText((currentlyPlaying = !currentlyPlaying) ? "Stop" : "Play");
        else if (e.getSource() == clearButton) {
            turnAllOff();
            reset();
        }else if(e.getSource() == saveItem) {
            reset();
            saveSong();
        }else if(e.getSource() == loadItem) {
            reset();
            loadFile();
        }else if(e.getSource()==addColumn){
            addCol(1);
        }else if(e.getSource()==add10Column){
            addCol(10);
        }else if(e.getSource()==removeColumn){
            removeCol(1);
        }else if(e.getSource()==remove10Column){
            removeCol(10);
        }else{
            reset();
            System.out.println("im in da 148th line cuh");
            loadTones(((JMenuItem)e.getSource()).getClientProperty("name").toString());
            //loadTones((String)instrumentMenu.getClientProperty("name"));
        }

    }

    public void addCol(int num){
        scrollPane.remove(buttonPanel);
        buttonPanel=new JPanel();
        COLUMNS+=num;
        JToggleButton[][] tempButtons=new JToggleButton[toggleButtons.length][COLUMNS];
        buttonPanel.setLayout(new GridLayout(tempButtons.length, tempButtons[0].length));
        for(int row=0; row<tempButtons.length; row++) {
            for (int col = 0; col < tempButtons[0].length; col++) {
                tempButtons[row][col] = new JToggleButton(musicNotes[row]);
                tempButtons[row][col].setPreferredSize(new Dimension(30, 30));
                tempButtons[row][col].setMargin(new Insets(0, 0, 0, 0));
            }
        }
        for(int row=0; row<tempButtons.length; row++) {
            for (int col = 0; col < tempButtons[0].length; col++) {
                try{
                    tempButtons[row][col].setSelected(toggleButtons[row][col].isSelected());
                }catch(ArrayIndexOutOfBoundsException error){}
                buttonPanel.add(tempButtons[row][col]);
            }
        }
        this.remove(scrollPane);
        scrollPane=new JScrollPane(buttonPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.add(scrollPane, BorderLayout.CENTER);
        toggleButtons=tempButtons;
        this.revalidate();
    }

    public void removeCol(int nums){
        if(COLUMNS>20){
            buttonPanel=new JPanel();
            COLUMNS= Math.max(COLUMNS - nums, 20);
            JToggleButton[][] tempButtons=new JToggleButton[toggleButtons.length][COLUMNS];
            buttonPanel.setLayout(new GridLayout(tempButtons.length, tempButtons[0].length));
            for(int row=0; row<tempButtons.length; row++){
                for(int col=0; col<tempButtons[0].length; col++){
                    tempButtons[row][col]=toggleButtons[row][col];
                    buttonPanel.add(tempButtons[row][col]);
                }
            }
            this.remove(scrollPane);
            scrollPane=new JScrollPane(buttonPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            this.add(scrollPane);
            toggleButtons=tempButtons;
            this.revalidate();
        }
    }

    public void loadFile(){
        if(fileChooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
            try{
                File loadFile=fileChooser.getSelectedFile();
                BufferedReader input=new BufferedReader(new FileReader(loadFile));
                String readStr=input.readLine();
                String[] readStrArr=readStr.split(" ");
                tempo=Integer.parseInt(readStrArr[0]);
                COLUMNS=Integer.parseInt(readStrArr[1]);
                Character[][] song=new Character[37][COLUMNS];
                int row=0;
                String temp="";
                while((temp=input.readLine())!=null){
                    for(int col=2; col<COLUMNS+2; col++){
                        song[row][col-2]=temp.charAt(col);
                    }
                    row++;
                }
                setNotes(song);
            }catch(Exception ieo){

            }
        }
    }

    public void setNotes(Character[][] song){
        scrollPane.remove(buttonPanel);
        buttonPanel=new JPanel();
        toggleButtons=new JToggleButton[song.length][song[0].length];
        COLUMNS=toggleButtons[0].length;
        buttonPanel.setLayout(new GridLayout(song.length, song[0].length));
        for(int row=0; row<toggleButtons.length; row++){
            for(int col=0; col<toggleButtons[0].length; col++){
                toggleButtons[row][col] = new JToggleButton(musicNotes[row]);
                toggleButtons[row][col].setPreferredSize(new Dimension(30,30));
                toggleButtons[row][col].setMargin(new Insets(0,0,0,0));
                buttonPanel.add(toggleButtons[row][col]);
            }
        }
        this.remove(scrollPane);
        scrollPane=new JScrollPane(buttonPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.add(scrollPane,BorderLayout.CENTER);
        for(int row=0; row<toggleButtons.length; row++) {
            for (int col = 0; col < toggleButtons[0].length; col++) {
                toggleButtons[row][col].setSelected(song[row][col]=='x');
            }
        }
        this.revalidate();
    }

    String currSong="";
    String nameOfSong;
    String[] noteNames;

    public void saveSong(){
        FileNameExtensionFilter filter=new FileNameExtensionFilter("*.txt", ".txt");
        fileChooser.setFileFilter(filter);
        if(fileChooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
            File f=fileChooser.getSelectedFile();
            try {
                nameOfSong = f.getAbsolutePath();
                if(nameOfSong.contains(".txt"))
                    nameOfSong=nameOfSong.substring(0,nameOfSong.length()-4);
                noteNames=new String[]{"c ","b ","a-","a ","g-","g ","f-","f ","e ","d-","d ","c-","c ","b ","a-","a ","g-","g ",
                        "f-","f ","e ","d-","d ","c-","c ","b ","a-","a ","g-","g ","f-","f ","e ","d-","d ","c-","c "};
                COLUMNS=toggleButtons[0].length;
                for(int row=0; row<toggleButtons.length; row++){
                    if(row==0)
                        currSong+=tempo+" "+COLUMNS+"\n";



                        currSong += noteNames[row];
                        for (int col = 0; col < toggleButtons[0].length; col++) {
                            if (toggleButtons[row][col].isSelected())
                                currSong += "x";
                            else
                                currSong += "-";

                        }
                        currSong += "\n";

                }


                    BufferedWriter outputStream=new BufferedWriter(new FileWriter(nameOfSong+".txt"));
                    outputStream.write(currSong);
                    outputStream.close();




            }catch(Exception e){
                e.printStackTrace();

            }

            //HERE HERE DAY #3 STEP #13
        }

    }
    public void reset(){
        COLUMNS= toggleButtons.length;
        currentlyPlaying=false;
        playStopButton.setText("Play");

    }

    public void turnAllOff(){
        for(int row=0; row<toggleButtons.length; row++){
            for(int col=0; col<toggleButtons[0].length; col++){
                toggleButtons[row][col].setSelected(false);
            }
        }
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        tempo=((JScrollBar)(e.getSource())).getValue();
        tempoLabel.setText("Tempo: "+tempo);
    }

    boolean isZero=false;


    @Override
    public void run() {
        //System.out.println("yeah");
        int tempColumns=0;

        while(true){

            try{
                if(!currentlyPlaying){
                    //System.out.println("here");
                    timing.sleep(0);
                }else {
                    //System.out.println("here");
                    for (int row = 0; row < toggleButtons.length; row++) {
                        toggleButtons[row][tempColumns].setEnabled(false);
                        //start clip
                        System.out.println("boom: "+tempColumns);
                        if(toggleButtons[row][tempColumns].isSelected())
                            clip[row].start();
                    }
                    timing.sleep(tempo);
                    for (int row = 0; row < toggleButtons.length; row++) {
                        //start clip

                        toggleButtons[row][tempColumns].setEnabled(true);
                        if(toggleButtons[row][tempColumns].isSelected()) {
                            clip[row].stop();
                            clip[row].setFramePosition(0);
                        }
                    }
                    tempColumns = (tempColumns + 1) % COLUMNS;
                }
            }catch(InterruptedException ie){}
        }
    }

    public static void main(String[] args) {new MusicBox();}
}
