public class RunnerSuperList{

	SuperList<Integer> super1, stack_super, queue_super;
	SuperList<String> strList;

	public RunnerSuperList(){
		super1=new SuperList<Integer>();

		//first bullet
		//System.out.println("\n\nBullet 1:");
		for(int a=0; a<30; a++)
			super1.add((int)(Math.random()*1000)+1);

		//second bullet
		System.out.println("Bullet 2:");
		System.out.println(super1);

		//third bullet
		System.out.println("\n\nBullet 3:");
		System.out.println(super1.size());

		//fourth bullet
		//System.out.println("\n\nBullet 4:");
		stack_super=new SuperList<Integer>();
		int size=super1.size();
		System.out.println("\n\nsize of super1: "+super1.size()+"\n");
		for(int a=0; a<size; a++)
			stack_super.push(super1.remove());

		//fifth bullet
		System.out.println("\n\nBullet 5: Display the \"Stack\" version");
		queue_super=new SuperList<Integer>();
		System.out.println(stack_super);
		System.out.println("\n\nsize of super1: "+super1.size()+"\n");
		for(int a=0; a<size; a++)
			queue_super.add(stack_super.pop());

		//sixth bullet
		System.out.println("\n\nBullet 6: Display the \"Queue\" version");
		System.out.println(queue_super);
		System.out.println("\n\nsize of super1: "+super1.size()+"\n");
		for(int a=0; a<size; a++)
			super1.add((int)(Math.random()*super1.size()), queue_super.poll());

		int sumE=0;
		int sumO=0;
		System.out.println("\n\nsize of super1: "+super1.size()+"\n");
		for(int a=0; a<size; a++){
			if(a%2==0)
				sumE+=super1.get(a);
			else
				sumO+=super1.get(a);
		}
		System.out.println("\n\nsize of super1: "+super1.size()+"\n");
		System.out.println("\n\nBullet 7: Display the sum");
		System.out.println(sumE+sumO);
		System.out.println("\n\nBullet 8: Display the sum");
		System.out.println(sumE);
		System.out.println("\n\nBullet 9: Display the sum");
		System.out.println(sumO);
		System.out.println("\n\nsize of super1: "+super1.size()+"\n");

		for(int a=0; a<size; a+=2)
			super1.add(super1.get(a));
		size=super1.size();
		System.out.println("\n\nsize of super1: "+super1.size()+"\n");

		for(int a=size-1; a>-1; a--){
			if(super1.get(a)%3==0)
				super1.remove(a);
		}

		super1.add(3, 55555);
		size=super1.size();
		System.out.println("\n\nsize of super1: "+super1.size()+"\n");
		System.out.println(size);
		System.out.println(super1);

		for(int a=1; a<size; a++){
			int b=a;
			while(b>0 && super1.get(b)>super1.get(b-1)){
				int temp=super1.get(b);
				System.out.println(b);
				super1.set(b, super1.get(b-1));
				super1.set(b-1, temp);
				b--;
			}
		}
		System.out.println("\n\nBullet 12: Descending");
		System.out.println(size);
		System.out.println("\n\nsize of super1: "+super1.size()+"\n");
		System.out.println(super1);


		if(size%2==0)
			System.out.println( "Median: "+ ( ((double)super1.get(size/2-1)+super1.get(size/2))/2 ) );
		else
			System.out.println("Median: "+super1.get(size/2));

		System.out.print("Before: ");
		for(int a=0; a<size/2; a++)
			System.out.print(super1.get(a)+", ");
		System.out.print("\nAfter: ");
		for(int a=size-1; a>size/2; a--)
			System.out.print(super1.get(a)+", ");

		strList=new SuperList<String>();
		String[] words=(new String("Longer words will stay in the list therefore they won't be removed.")).split(" ");
		for(String word:words){
			if(!word.equals(" "))
				strList.add((word.contains("."))?word.substring(0, word.length()-1):word);
		}
		System.out.println("\n\nentire list: "+strList);

		size=strList.size();
		for(int a=size-1; a>-1; a--){
			if(strList.get(a).length()<4)
				strList.remove(a);
		}
		size=strList.size();
		for(int a=1; a<size; a++){
			int b=a;
			while(b>0 && strList.get(b).compareTo(strList.get(b-1))<0){
				String temp=strList.get(b);
				System.out.println(b);
				strList.set(b, strList.get(b-1));
				strList.set(b-1, temp);
				b--;
			}
		}
		System.out.println("\n\nascending: "+strList);

		sumE=0;
		for(int a=0; a<size; a++)
			sumE+=strList.get(a).length();
		System.out.println("Average word length: "+((double)sumE/size));
	}
}