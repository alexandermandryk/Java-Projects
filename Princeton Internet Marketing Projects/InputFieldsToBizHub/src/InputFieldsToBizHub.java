import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

public class InputFieldsToBizHub {

    Queue<String> var_queue=new LinkedList<>();
    public InputFieldsToBizHub(){
        try {
            String complete="";
            BufferedReader script = new BufferedReader(new FileReader(new File("C:\\Users\\Owner\\repos\\Laptop-IdeaProjects\\InputFieldsToBizHub\\src\\basic_js.txt")));
            BufferedReader variables = new BufferedReader(new FileReader(new File("C:\\\\Users\\\\Owner\\\\repos\\\\Laptop-IdeaProjects\\\\InputFieldsToBizHub\\\\src\\\\variables.txt")));
            String var;
            while((var=variables.readLine())!=null){
                if(!var.equals(""))
                    var_queue.add(var);
            }
            String text;
            while ((text=script.readLine())!=null) {
                if(text.contains("INSERT"))
                    complete+=text.substring(0,text.indexOf('"')+1)+var_queue.poll()+(text.substring(text.indexOf('"')+7));
                else
                    complete+=text;
                complete+="\n";
            }
            FileWriter format=new FileWriter("C:\\\\Users\\\\Owner\\\\repos\\\\Laptop-IdeaProjects\\\\InputFieldsToBizHub\\\\src\\\\js.txt", false);
            format.append(complete);
            format.flush();
            format.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void main(String[]args){
        InputFieldsToBizHub app=new InputFieldsToBizHub();
    }
}
