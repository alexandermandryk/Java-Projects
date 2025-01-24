import java.io.*;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class CustomFieldFinder {
    Map<String, String> map=new HashMap<String, String>();
    FileWriter output;
    Scanner reader;
    BufferedReader input;
    public CustomFieldFinder(){
        try {
            output=new FileWriter("C:\\Users\\alexm\\IdeaProjects\\Custom_Fields_Finder\\src\\output.txt", false);
            input = new BufferedReader(new FileReader(new File("C:\\Users\\alexm\\IdeaProjects\\Custom_Fields_Finder\\src\\body.txt")));
            String text;
            String indicator="cursor-pointer text-gray-700\"></label></div></div></div><div data-v-ad385588=\"\" class=\"capitalize\" style=\"width: 100%;\">";

            //old indicator --> String indicator=" title=\"\" class=\"font-medium cursor-pointer text-gray-700\"></label></div></div></div><div data-v-442883d2=\"\" class=\"capitalize\" style=\"width: 100%;\">";

            while ((text = input.readLine()) != null) {
                if(text.contains(indicator) && isContact()){
                    String name=sourceName(text, indicator);
                    String value=sourceValue(text, indicator);
                    System.out.println(name+" "+value);
                    map.put(name.toLowerCase(), value);
                }
            }
            output.append(map.get("service type")+"\n");
            output.append(map.get("storage location")+"\n");
            output.append(map.get("origin address")+"\n");
            output.append(map.get("origin city")+"\n");
            output.append(map.get("origin state")+"\n");
            output.append(map.get("origin zip code")+"\n");
            output.append(map.get("destination city")+"\n");
            output.append(map.get("destination state")+"\n");
            output.append(map.get("destination zip code")+"\n");
            output.append(map.get("qty months")+"\n");
            output.append(map.get("qty 12")+"\n");
            output.append(map.get("qty 16")+"\n");
            output.append(map.get("qty deliver empty")+"\n");
            output.append(map.get("qty curb to curb")+"\n");
            output.append(map.get("qty redeliver from warehouse")+"\n");
            output.append(map.get("qty return empty")+"\n");
            output.append(map.get("12 foot unit price")+"\n");
            output.append(map.get("16 foot unit price")+"\n");
            output.append(map.get("deliver empty price")+"\n");
            output.append(map.get("curb to curb total")+"\n");
            output.append(map.get("redeliver from warehouse price")+"\n");
            output.append(map.get("return empty price")+"\n");
            output.append(map.get("total 12 foot rental")+"\n");
            output.append(map.get("total 16 foot rental")+"\n");
            output.append(map.get("total return empty")+"\n");
            output.append(map.get("total deliver empty")+"\n");
            output.append(map.get("subtotal cost")+"\n");
            output.append(map.get("total cost")+"\n");
            output.append(map.get("miles from origin")+"\n");
            output.append(map.get("miles from destination")+"\n");
            output.append(map.get("time from origin")+"\n");
            output.append(map.get("time from destination")+"\n");
            output.append(map.get("date requested")+"\n");
            output.append(map.get("12 foot unit price warehouse")+"\n");
            output.append(map.get("16 foot unit price warehouse")+"\n");
            output.append(map.get("destination door position")+"\n");
            output.append(map.get("wp_post_id")+"\n");
            output.append(map.get("de_rate")+"\n");
            output.append(map.get("re_rate")+"\n");
            output.append(map.get("quote_id")+"\n");
            output.append(map.get("return full to warehouse qty")+"\n");
            output.append(map.get("return full to warehouse unit price")+"\n");
            output.append(map.get("total return full to warehouse")+"\n");
            output.append(map.get("redeliver from warehouse unit cost")+"\n");
            output.append(map.get("redeliver from warehouse total")+"\n");
            output.append(map.get("promocode")+"\n");
            output.append(map.get("promo code discount")+"\n");
            output.append(map.get("promo code description")+"\n");
            output.append(map.get("curb to curb rate")+"\n");
            output.append(map.get("pricing rule description")+"\n\n");
            output.append(map.get("product list with quantity")+"\n");
            output.append(map.get("accessories list with quantity")+"\n\n");
            output.append(map.get("damage waiver details")+"\n");
            output.append(map.get("contents coverage details"));
            output.flush();
            output.close();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isContact(){
        try {
            String line = input.readLine();
            return (line.contains("contact."))?true:((line.contains("opportunity."))?false:isContact());
        }catch(IOException ioe){
            ioe.printStackTrace();
            throw new RuntimeException(ioe);
        }
    }

    public String sourceValue(String text, String indicator){
        String temp=text.substring(text.indexOf("value"));
        return temp.substring(temp.indexOf("\"")+1, temp.indexOf(">")-1);
    }

    public String sourceName(String text, String indicator){
        String name=text.substring(text.indexOf(indicator)+indicator.length());
        name=name.substring(0, name.indexOf("<"));
        return name;
    }

    public static void main(String[]args){
        CustomFieldFinder app=new CustomFieldFinder();
    }
}

/*
TX - Dallas/Lewisville --- all good (no change needed)
AZ - Phoenix --- all good (no change needed)
FL - Fort Lauderdale --- fixed (5 wrong)
FL - Tampa --- fixed (5 wrong)
FL - Orlando --- all good (no change needed)
VA - Chesapeake --- fixed (5 wrong)
 */



