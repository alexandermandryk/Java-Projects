import java.util.EmptyStackException;

public class SuperList<E>{

	ListNode<E> root, end;
	int size;

	public SuperList(){
		clear();
	}

	public void clear(){
		//SuperList(); //if it works

		root=null;
		end=null;
		size=0;
	}

	public String toString(){
		String str="[";
		ListNode<E> temp=root;
		for(int a=0; a<size; a++){
			str+=temp+", ";
			temp=temp.getNext();
		}
		return str+"]";
	}

	public void add(int index, E value){
		ListNode<E> temp=new ListNode<E>(value);
		if(index<0 || index>size)
			throw new IndexOutOfBoundsException();
		if(size==index) //list.add(0, "H") size==0;
			add(value);
		else if(index==0){ //if add to the start
			root.setPrevious(temp);
			temp.setNext(root);
			root=temp;
			size++;
		}else{ //if add to the middle
			ListNode<E> first=root;
			for(int a=0; a<index-1; a++) //has to be -1
				first=first.getNext();
			ListNode<E> second=first.getNext();
			first.setNext(temp);
			second.setPrevious(temp);
			temp.setNext(second);
			temp.setPrevious(first);
			size++;
		}

	}

	/*

	index == 3

	stops at 2

0 1 2 3 4 5 6
    T T

    */

	public E remove(int index){
		if(index<0 || index>size)
			throw new IndexOutOfBoundsException();
		if(size-1==index)
			return remove();
		else if(index==0){
			return poll();
		}else{
			ListNode<E> first=root;
			for(int a=0; a<index-1; a++)
				first=first.getNext();
			ListNode<E> returner=first.getNext();
			ListNode<E> second=returner.getNext();
			first.setNext(second);
			second.setPrevious(first);
			size--;
			return returner.getValue();
		}
	}

    public E remove(){
		if(size==0)
			throw new EmptyStackException();
		ListNode<E> returner=end;
		if(size==1){
			root=null;
			end=null;
		}else{
			end=end.getPrevious();
			end.setNext(null);
		}
		size--;
		return returner.getValue();
	}

	public E pop(){return remove();}

	public E poll(){
		if(size==0)
			return null;
			//throw new IndexOutOfBoundsException();
		ListNode<E> returner=root;
		if(size!=1){
			root=root.getNext();
			root.setPrevious(null);
		}else{
			root=null;
			end=null;
		}
		size--;
		return returner.getValue();

	}

	//list.add(5);
	public void add(E value){
		ListNode<E> temp=new ListNode<E>(value);

		if(size==0 || root==null){
			root=temp;
			temp=root;
		}else{
			end.setNext(temp);
			temp.setPrevious(end);
			//end=temp;
		}
		end=temp;
		size++;
	}

	public void push(E value){
		ListNode<E> temp=new ListNode<E>(value);
		if(size==0){
			root=temp;
			end=temp;
		}else{
			root.setPrevious(temp);
			temp.setNext(root);
			root=temp;
		}
		size++;
	}

	public E set(int index, E value){
		if(index<0 || index>size-1)
			throw new ArrayIndexOutOfBoundsException();
		ListNode<E> returner=null;
		ListNode<E> value_node=new ListNode<E>(value);
		if(size==1){
			returner=root;

			root=value_node;
			end=value_node;
		} else if(index==0){
			returner=root;
			//needs more

			ListNode<E> second=root.getNext();
			root=value_node;
			second.setPrevious(root);
			root.setNext(second);
		}else if(size-1==index){
			returner=end;
			end.getPrevious().setNext(value_node);
			value_node.setPrevious(end.getPrevious());
			end=value_node;
		}else{
			returner=root;
			for(int a=0; a<index; a++)
				returner=returner.getNext();
			returner.getPrevious().setNext(value_node);
			returner.getNext().setPrevious(value_node);
			value_node.setPrevious(returner.getPrevious());
			value_node.setNext(returner.getNext());

		}

		return returner.getValue();
	}

	public E get(int index){
		if(index<0 || index>size)
			throw new IndexOutOfBoundsException();
		if(size-1==index)
			return end.getValue();
		else if(index==0)
			return root.getValue();
		else{
			ListNode<E> first=root;
			for(int a=0; a<index; a++){ //index:1 size:2
				first=first.getNext();
			}
			return first.getValue();
		}
	}

	public E stackPeek()	{return end.getValue();}
	public E queuePeek()	{return root.getValue();}
	public int size()				{return size;}
	public boolean isEmpty()		{return size==0;}

	public boolean contains(E value){
		ListNode<E> temp=root;
		for(int a=0; a<size; a++){
			if(temp.equalsNode(value))
				return true;
		}
		return false;
	}

	public class ListNode<E>{

		ListNode<E> next, previous;
		E value;

		public ListNode(E value){
			this.value=value;
			next=null;
			previous=null;
		}

		public boolean equalsNode(E value){
			return this.value==value;
		}

		public E getValue()								{return value;}
		public ListNode<E> getPrevious()				{return previous;}
		public ListNode<E> getNext()					{return next;}
		public void setNext(ListNode<E> next)			{this.next=next;}
		public void setPrevious(ListNode<E> previous)	{this.previous=previous;}
		public String toString()						{return value+"";}

	}
}