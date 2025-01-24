import java.util.stream.Stream;

public class Vaccines {

    public static void main(String[]args_){
        int[][] data=new int[][]{
                {1, 5, 3, 2, 4, 4, 2, 2, 3, 5},
                {2, 1, 1, 2, 0, 1, 1, 1, 0, 0},
                {30, 22, 23, 25, -1, 30, 18, 25, -1, -1}
        };

        int[][] output=new int[1][data[0].length];
        for(int index: zeroes(data))
            output[0][index] = 0;

    }

    public static int[] priorities(int[][] data){
        int[] output=new int[data[0].length-zeroes(data).length];
        for(int person=0; person<data[0].length; person++){
            int list=0;
            for(int phase=1; phase<=maxPhase(data[0]); phase++){
                if(data[1][person]==phase)
                    list=list*10+person;
            }
        }
        return output;
    }

    public static int maxPhase(int[] phases){
        int phase=0;
        for(int num : phases)
            phase=(num>phase)?num:phase;
        return phase;
    }

    public static int[] zeroes(int[][] data){
        String output="";

        for(int person=0; person<data[0].length; person++){
            if(data[1][person]==2)
                output+=person+",";
            else if((data[2][person]>=0) && (data[2][person]<=21))
                output+=person+",";
        }
        String[] string_arr=output.split(",");
        int[] returner=new int[string_arr.length];
        for(int x=0; x<returner.length; x++)
            returner[x]=Integer.parseInt(string_arr[x]);
        return returner;
        //if you are allowed to this is quicker
        //return Stream.of(output.split(",")).mapToInt(Integer::parseInt).toArray();
    }
}
