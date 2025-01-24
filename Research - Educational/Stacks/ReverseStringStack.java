import java.util.Scanner;
import java.util.Stack;

public class ReverseStringStack{
	public static void main(String[]args){
		Scanner reader=new Scanner(System.in);
		Stack<Character> stack=new Stack<Character>();
		String[] word=reader.nextLine().split("");;
		for(String letter : word)
			stack.push(letter.charAt(0));
		while(!stack.empty()){
			System.out.print(stack.pop());
		}
		System.out.println();
	}
}