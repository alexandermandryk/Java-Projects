import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class CreditCard {
    ArrayList<String> numbers = new ArrayList<>();
    int real=0;
    int fake=0;
    public CreditCard(){

        try {
            BufferedReader input = new BufferedReader(new FileReader(new File("C:\\Users\\alexm\\IdeaProjects\\Competition\\src\\input1.txt")));
            String text;
            while ((text = input.readLine()) != null) {
                String[] infoarray = text.split("");

                numbers.add(text);
            }
        }catch(Exception e){
        }
        for(String number : numbers){
            System.out.println(number);
            if(checkSum(number))
                real++;
            else
                fake++;
        }

        System.out.print("real: "+real+"\t\tfake: "+fake);

    }
    public static void main(String[] args){
        CreditCard app = new CreditCard();
    }
    public boolean checkSum(String str){
        int check=Integer.parseInt(str.substring(str.length()-1));
        String payload=str.substring(0, str.length()-1);
        ArrayList<Integer> nums=new ArrayList<>();
        for(int a=0; a<payload.length(); a++)
            nums.add(Integer.parseInt(String.valueOf(payload.charAt(a))));
        int count=0;
        for(int a=payload.length()-1; a>=0; a--){
            if(count%2==0) {
                nums.set(a, nums.get(a) * 2);
                if(nums.get(a)>9) {
                    int temp = nums.get(a) % 10;
                    nums.set(a, nums.get(a)/10);
                    nums.add(temp);
                }
            }
            count++;
        }
        int sum=0;
        for(int num:nums)
            sum+=num;
        int sum_check=10-(sum%10);
        return sum_check==check;
    }
}