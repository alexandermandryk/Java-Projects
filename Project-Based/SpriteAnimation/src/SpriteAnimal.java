/*import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteAnimal {
    BufferedImage sheetImage;
    BufferedImage[] images=new BufferedImage[80];
    public SpriteAnimal(){
        try {
            sheetImage = ImageIO.read(new File("sheet.png"));
            for(int w=0; w<8; w++){
                for(int l=0; l<10; l++){
                    images[(8*w)+l]=sheetImage.getSubimage(w*185, l*215, (w*185)+185, (l*215)+215);
                    images[(8*w)+l].resize()
                }
            }

        }catch(IOException ioe){}
    }

    public BufferedImage resize(BufferedImage image, int width, int height)
    {
        Image temp=image.getScaledInstance(width,height, Image.SCALE_SMOOTH);
        BufferedImage scaledVersion = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2=scaledVersion.createGraphics();
        g2.drawImage(temp,0,0,null);
        g2.dispose();
        return scaledVersion;
    }
}

//start pos: (0,0)
//increment: 185W (8 wide)
//increment: 215L (10 long)


/*

This should be fun.

If it's not, then you're not fun.

Here's how to load in the sprite sheet:

imageSheet = ImageIO.read(new File("Aladdin.png"));
Here are two important lines for chopping the sheet into individual images:
    //x and y are the starting pixel position for the individual image (these
        would be part of a loop where x,y change based on positioning of //each
        individual image.  Width should be the width of each individual image.
        Keep in mind that a good sprite sheet has all of the images //in perfect columns
images[i] = imageSheet.getSubimage(x*width,y,width,height);
images[i] = resize(images  [i], width, height);


In the end, you need to extends JPanel so that you can use paintComponent and you have to implements the Runnable interface so that you can handle time delay (with a thread - like we did in MusicBox).

 */