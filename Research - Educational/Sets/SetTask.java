import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

public class SetTask{

	ArrayList<Set<Integer>> list=new ArrayList<>();

	public SetTask(){
		for(int a=0; a<(int)(Math.random()*8)+2; a++){
			Set<Integer> temp=new HashSet<Integer>();
			for(int b=0; b<10; b++)
				temp.add((int)(Math.random()*20));
			System.out.println(a+": "+temp);
			list.add(temp);
		}
		System.out.println("List: "+list);
		Set<Integer> union=new HashSet<Integer>();
		for(Set<Integer> set:list)
			union.addAll(set);
		System.out.println("Union: "+union);

		union=new HashSet<Integer>();
		union.addAll(list.get(0));
		for(Set<Integer> set:list)
			union.retainAll(set);
		System.out.println("Inter: "+union);

		union=list.get(0);
		boolean subset=true;
		for(int a=1; a<list.size(); a++){
			if(union.addAll(list.get(a)))
				subset=false;
		}
		System.out.println("Big Subset: "+subset);
	}
	public static void main(String[]args){
		SetTask app=new SetTask();
	}
}