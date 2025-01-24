import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
public class Juggler {
    public static void main(String[]args){
        Juggler app=new Juggler();
    }
    public Juggler(){
        double num = 23189412938328356.0;
        int count=0;
        while(num>76704) {
            num *= 0.9338281915883807;
            count++;
        }
        System.out.println(count*2-1);
    }
}
