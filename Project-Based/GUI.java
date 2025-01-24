import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener {
    JFrame frame;
    JPanel buttonPanel, bigPanel;
    JMenuBar menuBar;
    GridLayout buttonGrid, menuGrid, bigGrid;
    JButton north, east, south, west, reset;
    JMenu fontOptions, fontSizes, textColors, textBackgroundColors, buttonOutlineColors;
    JMenuItem[] fontOptionsArr, fontSizesArr, textColorsArr, textBackgroundColorsArr, buttonOutlineColorsArr;
    String[] fontNamesStr, backgroundColorNamesStr, textColorNamesStr, buttonOutlineColorsStr;
    JTextArea textArea;
    Font currentFont;

    int currentFontSize;
    int[] fontSizesInt;

    Font[] allFonts;
    Color[] borders, texts, outlines, backgrounds;


    public GUI() {
        frame=new JFrame("GUI");
        frame.setLayout(new BorderLayout());
        frame.setSize(new Dimension(800,800));
        menuBar=new JMenuBar();

        fontOptions=new JMenu("Font");
        fontSizes=new JMenu("Size");
        textColors=new JMenu("Color");
        textBackgroundColors=new JMenu("Background Color");
        buttonOutlineColors=new JMenu("Outline Color");

        fontOptions.setLayout(new GridLayout(1,6));
        fontSizes.setLayout(new GridLayout(1,6));
        textColors.setLayout(new GridLayout(1,6));
        textBackgroundColors.setLayout(new GridLayout(1,6));
        buttonOutlineColors.setLayout(new GridLayout(1,6));

        fontOptionsArr=new JMenuItem[3];
        fontSizesArr=new JMenuItem[3];
        textColorsArr=new JMenuItem[3];
        textBackgroundColorsArr=new JMenuItem[3];
        buttonOutlineColorsArr=new JMenuItem[4];

        menuBar.add(fontOptions);
        menuBar.add(fontSizes);
        menuBar.add(textColors);
        menuBar.add(textBackgroundColors);
        menuBar.add(buttonOutlineColors);

        fontNamesStr= new String[]{"Times New Roman", "Arial", "Consolas"};
        allFonts=new Font[3];
        fontSizesInt=new int[]{10, 15, 20};

        textColorNamesStr=new String[]{"Red", "Blue", "Random"};
        texts=new Color[]{Color.RED, Color.BLUE, new Color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256))};


        for(int a=0; a < allFonts.length; a++){

            //this will be a problem
            currentFontSize=fontSizesInt[0];
            allFonts[a]=new Font(fontNamesStr[a], Font.PLAIN, currentFontSize);
            currentFont = allFonts[0];

            fontOptionsArr[a]=new JMenuItem(fontNamesStr[a]);
            fontOptionsArr[a].setFont(allFonts[a]);
            fontOptionsArr[a].addActionListener(this);
            fontOptions.add(fontOptionsArr[a]);

            fontSizesArr[a]=new JMenuItem(""+fontSizesInt[a]);
            fontSizesArr[a].setFont(new Font(currentFont.getName(), Font.PLAIN, fontSizesInt[a]));
            fontSizesArr[a].addActionListener(this);
            fontSizes.add(fontSizesArr[a]);

            textColorsArr[a]=new JMenuItem(textColorNamesStr[a]);
            textColorsArr[a].setForeground(texts[a]);
            textColorsArr[a].addActionListener(this);
            textColors.add(textColorsArr[a]);
        }
        backgroundColorNamesStr=new String[]{"Red", "Green", "Blue", "Random"};
        backgrounds=new Color[]{Color.RED, Color.GREEN, Color.BLUE, new Color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256))};
        for(int a=0; a<textBackgroundColorsArr.length; a++){
            textBackgroundColorsArr[a]=new JMenuItem(backgroundColorNamesStr[a]);
            textBackgroundColorsArr[a].setBackground(backgrounds[a]);
            textBackgroundColorsArr[a].addActionListener(this);
            textBackgroundColors.add(textBackgroundColorsArr[a]);
        }

        buttonOutlineColorsStr=new String[]{"None", "Red", "Blue", "Random"};
        outlines=new Color[]{Color.WHITE, Color.RED, Color.BLUE, new Color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256))};

        for(int a=0; a<buttonOutlineColorsArr.length; a++){
            buttonOutlineColorsArr[a]=new JMenuItem(buttonOutlineColorsStr[a]);
            buttonOutlineColorsArr[a].setBorder(new LineBorder(outlines[a]));
            buttonOutlineColorsArr[a].addActionListener(this);
            buttonOutlineColors.add(buttonOutlineColorsArr[a]);
        }



        reset = new JButton("RESET");
        reset.addActionListener(this);
        menuBar.add(reset);

        north = new JButton("NORTH");
        south = new JButton("SOUTH");
        east = new JButton("EAST");
        west = new JButton("WEST");

        bigGrid = new GridLayout(1,2);
        bigPanel = new JPanel(bigGrid);
        buttonGrid = new GridLayout(1,4);
        buttonPanel = new JPanel(buttonGrid);
        buttonPanel.add(north);
        buttonPanel.add(south);
        buttonPanel.add(east);
        buttonPanel.add(west);

        north.addActionListener(this);
        south.addActionListener(this);
        east.addActionListener(this);
        west.addActionListener(this);

        north.setBorder(new LineBorder(outlines[0]));
        south.setBorder(new LineBorder(outlines[0]));
        east.setBorder(new LineBorder(outlines[0]));
        west.setBorder(new LineBorder(outlines[0]));

        textArea = new JTextArea();
        textArea.setBackground(null);
        textArea.setForeground(null);

        textArea.setFont(currentFont);

        frame.add(textArea);
        north.setFont(currentFont);
        east.setFont(currentFont);
        south.setFont(currentFont);
        west.setFont(currentFont);
        reset.setFont(currentFont);
        buttonOutlineColors.setFont(currentFont);
        textBackgroundColors.setFont(currentFont);
        fontSizes.setFont(currentFont);
        fontOptions.setFont(currentFont);
        textColors.setFont(currentFont);

        bigPanel.add(buttonPanel);
        bigPanel.add(menuBar);
        frame.add(bigPanel, BorderLayout.NORTH);
        frame.setVisible(true);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == north){
            frame.remove(bigPanel);
            buttonPanel.setLayout(new GridLayout(1,4));
            bigPanel.setLayout(new GridLayout(1,2));
            bigPanel.remove(menuBar);
            bigPanel.add(buttonPanel);
            menuGrid = new GridLayout(1,6);
            menuBar.setLayout(menuGrid);
            menuBar.remove(fontOptions);
            menuBar.remove(fontSizes);
            menuBar.remove(textColors);
            menuBar.remove(buttonOutlineColors);
            menuBar.remove(textBackgroundColors);
            menuBar.remove(reset);
            menuBar.add(fontOptions);
            menuBar.add(fontSizes);
            menuBar.add(textColors);
            menuBar.add(textBackgroundColors);
            menuBar.add(buttonOutlineColors);
            menuBar.add(reset);
            bigPanel.add(menuBar);
            frame.add(bigPanel, BorderLayout.NORTH);
            frame.revalidate();

        }

        if(e.getSource() == south){
            frame.remove(bigPanel);
            buttonPanel.setLayout(new GridLayout(1,4));
            bigPanel.setLayout(new GridLayout(1,2));
            bigPanel.remove(menuBar);
            bigPanel.add(buttonPanel);
            menuGrid = new GridLayout(1,6);
            menuBar.setLayout(menuGrid);
            menuBar.remove(fontOptions);
            menuBar.remove(fontSizes);
            menuBar.remove(textColors);
            menuBar.remove(buttonOutlineColors);
            menuBar.remove(textBackgroundColors);
            menuBar.remove(reset);
            menuBar.add(fontOptions);
            menuBar.add(fontSizes);
            menuBar.add(textColors);
            menuBar.add(textBackgroundColors);
            menuBar.add(buttonOutlineColors);
            menuBar.add(reset);
            bigPanel.add(menuBar, BorderLayout.SOUTH);
            frame.add(bigPanel, BorderLayout.SOUTH);
            frame.revalidate();
        }
        if(e.getSource() == east){
            frame.remove(bigPanel);
            buttonPanel.setLayout(new GridLayout(4,1));
            bigPanel.setLayout(new GridLayout(2,1));
            bigPanel.remove(menuBar);
            bigPanel.add(buttonPanel);
            menuGrid = new GridLayout(1,6);
            menuBar.setLayout(menuGrid);
            menuBar.remove(fontOptions);
            menuBar.remove(fontSizes);
            menuBar.remove(textColors);
            menuBar.remove(textBackgroundColors);
            menuBar.remove(buttonOutlineColors);
            menuBar.remove(reset);
            menuBar.add(fontOptions);
            menuBar.add(fontSizes);
            menuBar.add(textColors);
            menuBar.add(textBackgroundColors);
            menuBar.add(buttonOutlineColors);
            menuBar.add(reset);
            bigPanel.add(menuBar, BorderLayout.EAST);
            frame.add(bigPanel, BorderLayout.EAST);
            frame.revalidate();

        }
        if(e.getSource() == west){
            frame.remove(bigPanel);
            buttonPanel.setLayout(new GridLayout(4,1));
            bigPanel.setLayout(new GridLayout(2,1));
            bigPanel.remove(menuBar);
            bigPanel.add(buttonPanel);
            menuGrid = new GridLayout(1,6);
            menuBar.setLayout(menuGrid);
            menuBar.remove(fontOptions);
            menuBar.remove(fontSizes);
            menuBar.remove(textColors);
            menuBar.remove(textBackgroundColors);
            menuBar.remove(buttonOutlineColors);
            menuBar.remove(reset);
            menuBar.add(fontOptions);
            menuBar.add(fontSizes);
            menuBar.add(textColors);
            menuBar.add(textBackgroundColors);
            menuBar.add(buttonOutlineColors);
            menuBar.add(reset);
            bigPanel.add(menuBar, BorderLayout.WEST);
            frame.add(bigPanel, BorderLayout.WEST);
            frame.revalidate();
        }
        if(e.getSource()==reset){
            currentFont=allFonts[0];
            currentFontSize=fontSizesInt[0];
            textArea.setBackground(null);
            textArea.setForeground(null);
            textArea.setFont(currentFont);
            textArea.setText("");
            north.setBorder(new LineBorder(outlines[0]));
            south.setBorder(new LineBorder(outlines[0]));
            east.setBorder(new LineBorder(outlines[0]));
            west.setBorder(new LineBorder(outlines[0]));
            buttonOutlineColors.setBorder(new LineBorder(outlines[0]));
            textBackgroundColors.setBorder(new LineBorder(outlines[0]));
            textColors.setBorder(new LineBorder(outlines[0]));;
            fontSizes.setBorder(new LineBorder(outlines[0]));
            fontOptions.setBorder(new LineBorder(outlines[0]));
            reset.setBorder(new LineBorder(outlines[0]));
            textArea.setBorder(new LineBorder(outlines[0]));
            north.setBackground(backgrounds[0]);
            south.setBackground(backgrounds[0]);
            east.setBackground(backgrounds[0]);
            west.setBackground(backgrounds[0]);
            fontOptions.setBackground(backgrounds[0]);
            fontSizes.setBackground(backgrounds[0]);
            textColors.setBackground(backgrounds[0]);
            textBackgroundColors.setBackground(backgrounds[0]);
            buttonOutlineColors.setBackground(backgrounds[0]);
            reset.setBackground(backgrounds[0]);
            textArea.setBackground(backgrounds[0]);
            north.setForeground(texts[0]);
            south.setForeground(texts[0]);
            east.setForeground(texts[0]);
            west.setForeground(texts[0]);
            fontOptions.setForeground(texts[0]);
            fontSizes.setForeground(texts[0]);
            textColors.setForeground(texts[0]);
            textBackgroundColors.setForeground(texts[0]);
            buttonOutlineColors.setForeground(texts[0]);
            textArea.setForeground(texts[0]);
            reset.setForeground(texts[0]);
            north.setFont(allFonts[0]);
            south.setFont(allFonts[0]);
            east.setFont(allFonts[0]);
            west.setFont(allFonts[0]);
            fontOptions.setFont(allFonts[0]);
            fontSizes.setFont(allFonts[0]);
            textColors.setFont(allFonts[0]);
            textBackgroundColors.setFont(allFonts[0]);
            buttonOutlineColors.setFont(allFonts[0]);
            reset.setFont(allFonts[0]);
            textArea.setFont(allFonts[0]);
            currentFont=allFonts[0];
            north.setFont(new Font(currentFont.getName(), Font.PLAIN, fontSizesInt[0]));
            south.setFont(new Font(currentFont.getName(), Font.PLAIN, fontSizesInt[0]));
            east.setFont(new Font(currentFont.getName(), Font.PLAIN, fontSizesInt[0]));
            west.setFont(new Font(currentFont.getName(), Font.PLAIN, fontSizesInt[0]));
            fontOptions.setFont(new Font(currentFont.getName(), Font.PLAIN, fontSizesInt[0]));
            fontSizes.setFont(new Font(currentFont.getName(), Font.PLAIN, fontSizesInt[0]));
            textColors.setFont(new Font(currentFont.getName(), Font.PLAIN, fontSizesInt[0]));
            textBackgroundColors.setFont(new Font(currentFont.getName(), Font.PLAIN, fontSizesInt[0]));
            buttonOutlineColors.setFont(new Font(currentFont.getName(), Font.PLAIN, fontSizesInt[0]));
            reset.setFont(new Font(currentFont.getName(), Font.PLAIN, fontSizesInt[0]));
            textArea.setFont(new Font(currentFont.getName(), Font.PLAIN, fontSizesInt[0]));
            currentFontSize=fontSizesInt[0];
            frame.remove(bigPanel);
            buttonPanel.setLayout(new GridLayout(1,4));
            bigPanel.setLayout(new GridLayout(1,2));
            bigPanel.remove(menuBar);
            bigPanel.add(buttonPanel);
            menuGrid = new GridLayout(1,6);
            menuBar.remove(fontOptions);
            menuBar.remove(fontSizes);
            menuBar.remove(textColors);
            menuBar.remove(buttonOutlineColors);
            menuBar.remove(textBackgroundColors);
            menuBar.remove(reset);
            menuBar.add(fontOptions);
            menuBar.add(fontSizes);
            menuBar.add(textColors);
            menuBar.add(textBackgroundColors);
            menuBar.add(buttonOutlineColors);
            menuBar.add(reset);
            bigPanel.add(menuBar);
            frame.add(bigPanel, BorderLayout.NORTH);
            frame.revalidate();

            //reset background
            //reset outline
            //frame.setBackground
        }
        for(int i=0; i<fontOptionsArr.length; i++) {
            if (e.getSource() == fontOptionsArr[i]) {
                north.setFont(allFonts[i]);
                south.setFont(allFonts[i]);
                east.setFont(allFonts[i]);
                west.setFont(allFonts[i]);
                fontOptions.setFont(allFonts[i]);
                fontSizes.setFont(allFonts[i]);
                textColors.setFont(allFonts[i]);
                textBackgroundColors.setFont(allFonts[i]);
                buttonOutlineColors.setFont(allFonts[i]);
                reset.setFont(allFonts[i]);
                textArea.setFont(allFonts[i]);
                currentFont=allFonts[i];

            }
        }

        for(int i=0; i<fontSizesArr.length; i++) {
            if (e.getSource() == fontSizesArr[i]) {
                north.setFont(new Font(currentFont.getName(), Font.PLAIN, fontSizesInt[i]));
                south.setFont(new Font(currentFont.getName(), Font.PLAIN, fontSizesInt[i]));
                east.setFont(new Font(currentFont.getName(), Font.PLAIN, fontSizesInt[i]));
                west.setFont(new Font(currentFont.getName(), Font.PLAIN, fontSizesInt[i]));
                fontOptions.setFont(new Font(currentFont.getName(), Font.PLAIN, fontSizesInt[i]));
                fontSizes.setFont(new Font(currentFont.getName(), Font.PLAIN, fontSizesInt[i]));
                textColors.setFont(new Font(currentFont.getName(), Font.PLAIN, fontSizesInt[i]));
                textBackgroundColors.setFont(new Font(currentFont.getName(), Font.PLAIN, fontSizesInt[i]));
                buttonOutlineColors.setFont(new Font(currentFont.getName(), Font.PLAIN, fontSizesInt[i]));
                reset.setFont(new Font(currentFont.getName(), Font.PLAIN, fontSizesInt[i]));
                textArea.setFont(new Font(currentFont.getName(), Font.PLAIN, fontSizesInt[i]));
                currentFontSize=fontSizesInt[i];



            }
        }

        for(int i=0; i<textColorsArr.length; i++) {
            if (e.getSource() == textColorsArr[i]) {
                north.setForeground(texts[i]);
                south.setForeground(texts[i]);
                east.setForeground(texts[i]);
                west.setForeground(texts[i]);
                fontOptions.setForeground(texts[i]);
                fontSizes.setForeground(texts[i]);
                textColors.setForeground(texts[i]);
                textBackgroundColors.setForeground(texts[i]);
                buttonOutlineColors.setForeground(texts[i]);
                textArea.setForeground(texts[i]);
                reset.setForeground(texts[i]);
            }
        }

        for(int i=0; i<textBackgroundColorsArr.length; i++) {
            if (e.getSource() == textBackgroundColorsArr[i]) {
                north.setBackground(backgrounds[i]);
                south.setBackground(backgrounds[i]);
                east.setBackground(backgrounds[i]);
                west.setBackground(backgrounds[i]);
                fontOptions.setBackground(backgrounds[i]);
                fontSizes.setBackground(backgrounds[i]);
                textColors.setBackground(backgrounds[i]);
                textBackgroundColors.setBackground(backgrounds[i]);
                buttonOutlineColors.setBackground(backgrounds[i]);
                reset.setBackground(backgrounds[i]);
                textArea.setBackground(backgrounds[i]);
            }
        }

        for(int i=0; i<buttonOutlineColorsArr.length; i++) {
            if (e.getSource() == buttonOutlineColorsArr[i]) {
                north.setBorder(new LineBorder(outlines[i]));
                south.setBorder(new LineBorder(outlines[i]));
                east.setBorder(new LineBorder(outlines[i]));
                west.setBorder(new LineBorder(outlines[i]));
                fontOptions.setBorder(new LineBorder(outlines[i]));
                fontSizes.setBorder(new LineBorder(outlines[i]));
                textColors.setBorder(new LineBorder(outlines[i]));
                textBackgroundColors.setBorder(new LineBorder(outlines[i]));
                buttonOutlineColors.setBorder(new LineBorder(outlines[i]));
                reset.setBorder(new LineBorder(outlines[i]));
                textArea.setBorder(new LineBorder(outlines[i]));
            }
        }
    }

    public static void main(String[] args) {
        new GUI();
    }

}


