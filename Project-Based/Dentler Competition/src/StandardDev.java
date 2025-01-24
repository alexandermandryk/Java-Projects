import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class StandardDev {
    ArrayList<Double> devs=new ArrayList<>();
    public StandardDev(){
        try {
            BufferedReader input = new BufferedReader(new FileReader(new File("C:\\Users\\alexm\\IdeaProjects\\Competition\\src\\input3.txt")));
            String text;
            while ((text = input.readLine()) != null) {
                String[] infoarray = text.split(", ");
                int sum=0;
                int[] nums=new int[infoarray.length];
                for(int a=0; a<nums.length; a++)
                    nums[a]= Integer.parseInt(infoarray[a]);
                for(int num : nums)
                    sum+=num;
                devs.add(st_dev(sum, nums));
            }
        }catch(Exception e){
        }

        double min=Integer.MAX_VALUE;
        double max=0;
        for(double dev : devs){
            if(dev<min)
                min=dev;
            if(dev>max)
                max=dev;
        }
        System.out.println(max-min);
    }

    public double st_dev(int sum, int[] nums){
        double new_sum=0;
        for(int num : nums)
            new_sum+=(num-sum)*(num-sum);
        new_sum/=nums.length;
        return Math.sqrt(new_sum);
    }
    public static void main(String[]args){
        StandardDev app=new StandardDev();
    }
}
