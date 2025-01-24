public class BusStop {
    public static void main(String[]args){
        String[] stops=new String[]{"682", "256","256","256","256", "528", "529"};
/*
        String str="";
        int pos=-1;
        while(str.equals("") && pos<stops.length-1)
            str=(stops[stops.length-1].equals(stops[pos=pos+1]))?(pos+1)+"":"";
        System.out.println((str.equals(""))?"1000":str);*/

        int pos=-1;
        while(pos<stops.length-1)
            System.out.print(((pos=pos+1)!=stops.length-1)?((stops[pos].equals(stops[stops.length-1]))?((stops.length)-(((pos*-1)+(pos=stops.length))))+"\n":""):"1000\n");







        /*for(int a=0; a<stops.length-1; a++){
            if(stops[a].equals(stops[stops.length-1]))
                str=(a+1)+"";
        }
        System.out.println(str);*/

        //OR

        //System.out.println(recurv(stops, 0));

    }
/*
    public static String recurv(String[] stops, int pos){
        if(pos==stops.length-1)
            return "1000";
        if(stops[pos].equals(stops[stops.length-1]))
            return (pos+1)+"";
        return recurv(stops, pos+1);
    }

 */
}
