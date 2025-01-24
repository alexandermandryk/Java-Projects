import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Checker {
    Scanner person=new Scanner(System.in);
    Queue<String> var_queue=new LinkedList<>();
    String control="";
    String test="";
    public Checker(){
        try {
            FileWriter format=new FileWriter("C:\\Users\\Owner\\repos\\Laptop-IdeaProjects\\CHECK_IDs\\src\\variables.txt", true);
            format.append("\ndone");
            format.flush();
            format.close();
            BufferedReader input = new BufferedReader(new FileReader(new File("C:\\\\Users\\\\Owner\\\\repos\\\\Laptop-IdeaProjects\\\\CHECK_IDs\\\\src\\\\variables.txt")));
            String text;
            while (!(text = input.readLine()).equals("done")) {
                if (!text.equals(""))
                    control += text + "\n";
            }
            input = new BufferedReader(new FileReader(new File("C:\\\\Users\\\\Owner\\\\repos\\\\Laptop-IdeaProjects\\\\CHECK_IDs\\\\src\\\\units_page.txt")));
            int count = 0;
            while ((text = input.readLine()) != null){
                if(!text.equals("Merchandise") && !text.equals("Insurance")) {
                    if (count % 2 == 1)
                        test += text + "\n";
                    count++;
                }
            }
            if(control.equals(test))
                System.out.println("✔ ID's MATCH ✔");
            else {
                findError();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void findError(){
        Scanner base = new Scanner(control);
        Scanner variable = new Scanner(test);
        int counter=0;
        while (base.hasNextLine()) {
            counter++;
            System.out.print((base.nextLine().equals(variable.nextLine())) ? "" : "incorrect field on line: " + counter);
        }
    }

    public static void main(String[]args){
        Checker checker=new Checker();
    }
}
