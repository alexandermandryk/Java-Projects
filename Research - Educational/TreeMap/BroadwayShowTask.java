import java.util.ArrayList;
import java.io.*;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Iterator;
import java.text.NumberFormat;
import java.util.Locale;

public class BroadwayShowTask{

	ArrayList<Show> shows;
	ArrayList<Double> average_capacity=new ArrayList<>();
	ArrayList<Long> average_gross=new ArrayList<>();
	HashMap<String, ArrayList<Long>> average_map_gross=new HashMap<>();
	//use the capacity average method and just implement the same things with the gross income methods
	//going to need global variables of average_map_gross and average_gross
	HashMap<String, ArrayList<Double>> average_map=new HashMap<>();

	public BroadwayShowTask(){
		try{
			shows=new ArrayList<>();
			BufferedReader reader=new BufferedReader(new FileReader(new File("Broadway2022.csv")));
			String temp;
			reader.readLine();
			while((temp=reader.readLine())!=null){
					String[] temp_arr=temp.split(",");
					shows.add(new Show(temp_arr[0], temp_arr[1], temp_arr[2], temp_arr[3], Long.parseLong(temp_arr[4]), Integer.parseInt(temp_arr[5]), Double.parseDouble(temp_arr[6])));

			}
		}catch(IOException ioe){
			System.out.println("File not found.");
		}
		grossByMonth(shows);
		System.out.println("\n**************************************************\n");
		grossByType(shows);
		System.out.println("\n**************************************************\n");
		attendanceByType(shows);
		System.out.println("\n**************************************************\n");
		grossByShowPerWeek(shows);
		System.out.println("\n**************************************************\n");
		attendanceByShowPerWeek(shows);
		System.out.println("\n**************************************************\n");
		capacityByShowPerWeek(shows);
		System.out.println("\n**************************************************\n");
		capacityByShowPerWeek(shows);
		System.out.println("\n**************************************************\n");
		capacityAverageByShow();
		System.out.println("\n**************************************************\n");
		grossAverageByShow();
	}

	public void grossAverageByShow(){
		HashMap<String, Long> total_map=new HashMap<>();
		Iterator<String> it=average_map_gross.keySet().iterator();
		while(it.hasNext())
			total_map.put(it.next(), average_gross.remove(0));

		NumberFormat n = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
		it=total_map.keySet().iterator();
		while(it.hasNext()){
			String current_key=it.next();
			System.out.println(current_key+" = "+n.format(total_map.get(current_key)));
		}
	}

	public void capacityAverageByShow(){
		HashMap<String, Double> total_map=new HashMap<>();
		Iterator<String> it=average_map.keySet().iterator();
		while(it.hasNext())
			total_map.put(it.next(), average_capacity.remove(0));

		System.out.println("Average Capacity by Show");

		it=total_map.keySet().iterator();
		while(it.hasNext()){
			String current_key=it.next();
			System.out.println(current_key+": "+(int)(total_map.get(current_key)*100)+"%");
		}
	}

	public void attendanceByShowPerWeek(ArrayList<Show> shows){
		HashMap<String, ArrayList<Integer>> map=new HashMap<>();
		ArrayList<Integer> sum_list=new ArrayList<>();
		String loop_key=shows.get(0).getName();
		ArrayList<Integer> temp_list=new ArrayList<>();
		for(Show show:shows){
			if(!show.getName().equals(loop_key)){
				map.put(loop_key, temp_list);
				temp_list=new ArrayList<>();
				loop_key=show.getName();
			}
			temp_list.add(show.getAttendance());
		}

		System.out.println("Attendance Per Week by Show");
		Iterator<String> it=map.keySet().iterator();
		while(it.hasNext()){
			int attendance_sum=0;
			String key=it.next();
			System.out.println(key);
			ArrayList<Integer> temp=map.get(key);
			for(Integer num:temp){
				System.out.println("\t"+String.format("%,d", num));
				attendance_sum+=num;
			}
			System.out.println("\n\t***\n");
			sum_list.add(attendance_sum);
		}

		HashMap<String, Integer> total_map=new HashMap<>();
		it=map.keySet().iterator();
		while(it.hasNext())
			total_map.put(it.next(), sum_list.remove(0));

		System.out.println("Total Attendance by Show");

		it=total_map.keySet().iterator();
		while(it.hasNext()){
			String current_key=it.next();
			System.out.println(current_key+": "+String.format("%,d",total_map.get(current_key)));
		}

	}

	public void capacityByShowPerWeek(ArrayList<Show> shows){
		HashMap<String, ArrayList<Double>> map=new HashMap<>();
		ArrayList<Double> sum_list=new ArrayList<>();
		String loop_key=shows.get(0).getName();
		ArrayList<Double> temp_list=new ArrayList<>();
		for(Show show:shows){
			if(!show.getName().equals(loop_key)){
				map.put(loop_key, temp_list);
				temp_list=new ArrayList<>();
				loop_key=show.getName();
			}
			temp_list.add(show.getCapacity());
		}

		System.out.println("Capacity Per Week by Show");
		Iterator<String> it=map.keySet().iterator();
		while(it.hasNext()){
			double capacity_sum=0;
			String key=it.next();
			System.out.println(key);
			ArrayList<Double> temp=map.get(key);
			double count=0;
			for(Double num:temp){
				System.out.println("\t"+(int)(num*100)+"%");
				capacity_sum+=num;
				count++;
			}
			System.out.println("\n\t***\n");
			sum_list.add(capacity_sum/count);
		}

		average_map=map;
		average_capacity=sum_list;
	}

	public void grossByShowPerWeek(ArrayList<Show> shows){
		//TreeMap<String, ArrayList<Long>> map=new TreeMap<>();
		//use treemap if you want alphabetical order
		HashMap<String, ArrayList<Long>> map=new HashMap<>();
		ArrayList<Long> sum_list=new ArrayList<>();
		String key=shows.get(0).getName();
		ArrayList<Long> temp_list=new ArrayList<>();
		for(Show show:shows){
			if(!show.getName().equals(key)){
				map.put(key, temp_list);
				temp_list=new ArrayList<>();
				key=show.getName();
			}
			temp_list.add(show.getGross());
		}

		System.out.println("Gross Earnings Per Week by Show");

		NumberFormat n = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
		Iterator<String> it=map.keySet().iterator();
		while(it.hasNext()){
			long gross_sum=0;
			String current_key=it.next();
			System.out.println(current_key);
			ArrayList<Long> temp=map.get(current_key);
			int count=0;
			for(Long num:temp){
				System.out.println("\t"+n.format(num));
				gross_sum+=num;
				count++;
			}
			average_gross.add(gross_sum/(long)count);
			System.out.println("\n\t***\n");
			sum_list.add(gross_sum);
		}

		average_map_gross=map;

		HashMap<String, Long> total_map=new HashMap<>();
		it=map.keySet().iterator();
		while(it.hasNext())
			total_map.put(it.next(), sum_list.remove(0));

		System.out.println("Total Gross Earnings by Show");

		it=total_map.keySet().iterator();
		while(it.hasNext()){
			String current_key=it.next();
			System.out.println(current_key+": "+n.format(total_map.get(current_key)));
		}

	}

	public void attendanceByType(ArrayList<Show> shows){
		HashMap<String, Integer> map=new HashMap<>();
		for(Show show:shows){
			String key=show.getType();
			if(!map.containsKey(key))
				map.put(key, show.getAttendance());
			else map.put(key, map.get(key)+show.getAttendance());
		}

		Iterator<String> it=map.keySet().iterator();
		while(it.hasNext()){
			String key=it.next();
			System.out.println(key+" = "+map.get(key));
		}
	}

	public void grossByType(ArrayList<Show> shows){
		HashMap<String, Long> map=new HashMap<>();
		for(Show show:shows){
			String key=show.getType();
			if(!map.containsKey(key))
				map.put(key, show.getGross());
			else map.put(key, map.get(key)+show.getGross());
		}

		Iterator<String> it=map.keySet().iterator();
		while(it.hasNext()){
			String key=it.next();
			NumberFormat n = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
			System.out.println(key+" = "+n.format(map.get(key)));
		}
	}

	public void grossByMonth(ArrayList<Show> shows){
		System.out.println("Gross Earnings by Month");
		HashMap<Integer, Long> map=new HashMap<>();
		for(Show show:shows){
			int key=show.getMonth();
			if(!map.containsKey(key))
				map.put(key, show.getGross());
			else map.put(key, map.get(key)+show.getGross());
		}

		Iterator<Integer> it=map.keySet().iterator();
		while(it.hasNext()){
			int key=it.next();
			NumberFormat n = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
			System.out.println(key+" = "+n.format(map.get(key)));
		}

	}

	public class Show{

		String date, name, type, theatre;
		long gross;
		int attendance;
		double capacity;

		public Show(String date, String name, String type, String theatre, long gross, int attendance, double capacity){
			this.date=date;
			this.name=name;
			this.type=type;
			this.theatre=theatre;
			this.gross=gross;
			this.attendance=attendance;
			this.capacity=capacity;
		}

		public int getMonth(){return Integer.parseInt( date.substring(0, date.indexOf("/")) );}

		public long getGross(){return gross;}

		public String getType(){return type;}

		public int getAttendance(){return attendance;}

		public String getName(){return name;}

		public double getCapacity(){return capacity;}

	}

	public static void main(String[]args){
		BroadwayShowTask app = new BroadwayShowTask();
	}
}