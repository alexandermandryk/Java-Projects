import java.io.*;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.LinkedList;

public class WordTask{

	Queue<Word> queue=new LinkedList<Word>();
	PriorityQueue<Word> pq=new PriorityQueue<Word>();

	public WordTask(){
		try{
			BufferedReader reader=new BufferedReader(new FileReader(new File("random_words.txt")));
			String temp;
			while((temp=reader.readLine())!=null){
				String[] temp_arr=temp.split(" ");
				for(String str : temp_arr){
					if(!Character.isLetter(str.charAt(0)) && !Character.isDigit(str.charAt(0)))
						str=str.substring(1);
					if(!Character.isLetter(str.charAt(str.length()-1)) && !Character.isDigit(str.charAt(str.length()-1)))
						str=str.substring(0,str.length()-1);
					queue.add(new Word(str));
					pq.add(new Word(str));
				}
			}

			while(!queue.isEmpty()){
				//System.out.println(queue.remove() +"\t\t"+ pq.remove());
				System.out.print(String.format("\n%-30s%-30s", queue.remove(), pq.remove()));
			}
		}catch(IOException ioe){
			System.out.println("File not found");
		}
	}

	public class Word implements Comparable<Word>{

		String str="";

		public Word(String str){
			this.str=str;
		}

		public int compareTo(Word word){
			if(str.charAt(0)<word.getWord().charAt(0))
				return 1;
			if(str.charAt(0)>word.getWord().charAt(0))
				return -1;
			return 0;
		}

		public String getWord(){return str;}
		public String toString(){
			return str;
		}
	}
}