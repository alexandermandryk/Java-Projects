import java.io.*;
import java.util.Scanner;

//currently up to date with "MI - Grand Rapids"
public class Appender_CSV {
    FileWriter csv_file;
    String full_text="";
    Scanner reader;
    public Appender_CSV(){
        reader=new Scanner(System.in);
        System.out.print("Welcome! This code is used to prepare a CSV file from a txt file of emails.\n\nWill you be adding an email before creating a CSV?: ");
        String dec=reader.nextLine();
        if(dec.contains("y") || dec.contains("Y") || dec.equalsIgnoreCase("1")) {
            System.out.print("Make sure that you have pasted the email into PrepForEmailTxtInput.txt, press enter when completed.");
            reader.nextLine();
            PrepForEmailTxt app = new PrepForEmailTxt();
        }
        try {
            csv_file = new FileWriter("C:\\Users\\alexm\\IdeaProjects\\CSV_MANAGE\\src\\testers.csv", false);
            csv_file.append("\"Legal Business Name\";\"Full Franchise Location Name (Friendly Name)\";\"Enter the exact legal business name, as registered with the EIN. Ex: Twilio Inc. rather than Twilio. (If the name you've entered abides by this note, check this box).\";\"Legal Business Street Address\";\"City\";\"State\";\"Zipcode\";\"Business Type\";\"Business Industry\";\"Business Registration ID (EIN)\";\"Main Microsite URL\";\"Authorized Representative First Name for Twilio Verification\";\"Authorized Representative Last Name for Twilio Verification\";\"Authorized Representative Email For Twilio Verification\";\"Authorized Representative Phone for Twilio Verification\";\"Business Title\";\"Position At Company\";\"Owner Cell Phone\";\"Twilio SMS Number (Inbound + Outbound # displayed to customers, if you have one)\";\"Twilio will contact your authorized representatives to verify your identities. Please ensure that they are contactable via email and phone. (If the rep information above abides by this note, check this box).\";\"Primary Business Phone Number\";\"Primary Business Email Address\";\"Primary Business Password\";\"Signature Name: This signature will also be utilized in all external emails.\";\"Signature Email Address\";\"Signature Phone Number\";\"BizHub+ Communication Email: This newly created email address is tied into outbound/inbound communication through your BizHub+ CRM. What name do you want as the beginning of this email? (ex: NAME@UNITS-Location.com)\";\"Twilio Forwarding Number\";\"BizHub+ Primary User First and Last Name\";\"Primary User Email\";\"Primary User Cell Phone\";\"BizHub+ Second User First and Last Name\";\"Second User Email\";\"Second User Cell Phone\";\"Third User Name\";\"Third User Email\";\"Third User Cell Phone\";\"BizHub+ Fourth User First and Last Name\";\"Fourth User Email\";\"Fourth User Cell Phone\";\"Promotion: If a web lead is not responsive within the first four days, are you comfortable with offering a $25 coupon as part of your drip email campaign?\";\"Promotion: What other promotions are you willing to offer potential customers who haven't responded for longer than a week?\";\"Container Insurance: How does your Container Insurance work? Please include cost, interval, insured amount and price/month.\";\"If you have a one-time Container Damage Waiver, enter that here (leave blank if it doesn't apply).\";\"Contents Insurance: How does your Contents Insurance work? List all your different price points, and information for each. Please include cost, interval, insured amount and price/month for each.\";\"Product\";\"Product Description\";\"For Rent?\";\"Rental Cost\";\"For Purchase?\";\"Purchase Cost\";\"Product\";\"Product Description\";\"For Rent?\";\"Rental Cost\";\"For Purchase?\";\"Purchase Cost\";\"Product\";\"Product Description\";\"For Rent?\";\"Rental Cost\";\"For Purchase?\";\"Purchase Cost\";\"Product\";\"Product Description\";\"For Rent?\";\"Rental Cost\";\"For Purchase?\";\"Purchase Cost\";\"Moving Bundles: If you rent or sell a Moving Bundle, fill the text box with this format: (# of blankets, # of straps, rent/sell, bundle cost/month)\";\"Google My Business (GMB) Review Link\";\"Google My Business Access (Has GMB ownership access been granted to unitsmovingandportablestorage@gmail.com? We need this access in order to tie this into your account dashboard and reporting).\";\"Google Analytics Access (We should already have access to your Google Analytics, in the rare instance that we don't - please indicate so below).\";\"Google Paid Search Access (Has PPC read-only access been granted to unitsmovingandportablestorage@gmail.com? We need this access in order to tie this into your account dashboard and reporting).\";\"Facebook Access (Has Facebook admin access been granted to pimnj.mcc@gmail.com? We need this access in order to tie this into your account dashboard and reporting).\";\"Submit\";\"IP\";\"URL\";\"Form ID\";\"Date Created\";\"Do we have permission to port your Twilio phone numbers from Bizhub Classic to BH+ to use them in new system?\";\"Warehouse Business Address\"\n");
            BufferedReader input = new BufferedReader(new FileReader(new File("C:\\Users\\alexm\\IdeaProjects\\CSV_MANAGE\\src\\email.txt")));
            String text;
            while ((text = input.readLine()) != null) {
                if(text.equals("blank")){
                    for(int a=0; a<81; a++)
                        appendToFullText("");
                }if (text.equals("new_email")) {
                    csv_file.append(full_text.substring(0, full_text.length()) + "\n");
                    full_text = "";
                }else if(text.contains("Main Microsite URL")){
                    System.out.println("Test String: "+text);
                    text=text.substring(text.indexOf(":")+2);
                    if(text.contains("http")){
                        text=text.substring(text.indexOf("/")+2);
                    }
                    text=(text.contains("www."))?text:("www."+text);
                    text=(text.charAt(text.length()-1)=='/')?text.substring(0, text.length()-1):text;
                    appendToFullText(" "+text);
                }else if(!text.contains("Do we have permission to port your Twilio phone numbers from Bizhub Classic to BH+ to use them in new system?:")){
                    while(text.contains(":"))
                        text=text.substring(text.indexOf(":")+1);
                    appendToFullText(text);
                }
            }
            csv_file.flush();
            csv_file.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void appendToFullText(String text){
        full_text+="\""+((text.length()>0)?text.substring(1):text)+"\";";
    }

    public static void main(String[]args){
        Appender_CSV app = new Appender_CSV();
    }
}

/*

SoCal
GA - Augusta
NC - Hickory
TN - Memphis

 */
