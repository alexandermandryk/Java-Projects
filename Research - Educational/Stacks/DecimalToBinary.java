import java.util.Stack;

public class DecimalToBinary{
	public static void main(String[]args){
		Stack<Integer> stack=new Stack<Integer>();
		int a=23;
		while(a>0){
			stack.push(a%2);
			a/=2;
		}
		while(!stack.empty())
			System.out.print(stack.pop());
		System.out.println();
	}
}