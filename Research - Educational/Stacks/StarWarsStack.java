import java.util.ArrayList;
import java.util.Stack;
import java.io.*;

public class StarWarsStack{

	public static class Runner{

		ArrayList<CharacterSW> characters=new ArrayList<CharacterSW>();
		Stack<CharacterSW> males=new Stack<CharacterSW>();
		Stack<CharacterSW> females=new Stack<CharacterSW>();
		Stack<CharacterSW> droids=new Stack<CharacterSW>();
		Stack<CharacterSW> birth=new Stack<CharacterSW>();
		Stack<CharacterSW> STACK_USELESS=new Stack<CharacterSW>();

		public Runner(){
			try{
				BufferedReader reader=new BufferedReader(new FileReader(new File("StarWarsCharacters.csv")));
				String temp;
				boolean first=true;
				while((temp=reader.readLine())!=null){
					if(!first){
						String[] input=temp.split(",");
						int a=(input.length==10)?1:0;
						CharacterSW ind=new CharacterSW(input[0],input[5+a],input[6+a],input[7+a],input[8+a]);
						((ind.getGender().contains("male"))?((ind.getGender().equalsIgnoreCase("female"))?females:males):((ind.getSpecies().equalsIgnoreCase("droid"))?droids:STACK_USELESS)).push(ind);
						((ind.getYear().contains("BBY"))?birth:STACK_USELESS).push(ind);

					}else
						first=false;
				}
				print(males, "Male Characters",false);
				print(females, "Female Characters",false);
				print(droids, "Droids",false);
				print(birth,"Ages",true);
			}catch(IOException ioe){
				System.out.println("cannot find file");
			}
		}

		public void print(Stack<CharacterSW> stack, String header,boolean droid){
			String default_header=String.format(header+"\n%-30s%-20s","Name","Homeworld");
			System.out.print(default_header );
			if(droid)
				System.out.println("Birth Year (BBY)" );
			else System.out.println();

			int count=0;
			while(!stack.empty()){
				CharacterSW temp=stack.pop();
				String bby="";
				if(droid)
					bby=temp.getYear().substring(0,temp.getYear().length()-3);
				String str=String.format("%-30s%-20s%s",temp.getName(),temp.getHome(),bby);
				System.out.println(str);

			}
			System.out.println();
		}

		public class CharacterSW{

			String name, gender, home, species, year;

			public CharacterSW(String name, String year, String gender, String home, String species){
				this.name=name;
				this.year=year;
				this.gender=gender;
				this.home=home;
				this.species=species;
			}

			public String getName(){return name;}
			public String getYear(){return year;}
			public String getGender(){return gender;}
			public String getHome(){return home;}
			public String getSpecies(){return species;}
		}

	}



	public static void main(String[]args){
		Runner runner=new Runner();
	}
}