public class TreeSet<E extends Comparable<E>>{
    private TreeNode<E> root;
    private int size;
    private String st;
    private boolean found;
    public TreeSet() {
        root = null;
        size = 0;
    }

    public void add(E value){
        if(root==null){
            root=new TreeNode<E>(value);
            size++;
            return;
        }
        add(root,value);
    }

    public void add(TreeNode<E> node, E value){ //could become boolean
        if(node.equalsNode(value))
            return;
        TreeNode<E> curr=new TreeNode<>(value);
        if(value.compareTo(node.getValue())<0){ //IF LESS
            if(node.getLeft()==null)
                node.setLeft(curr);
            else
                add(node.getLeft(), value);
        }else if(value.compareTo(node.getValue())>0) { //IF MORE
            if(node.getRight()==null)
                node.setRight(curr);
            else
                add(node.getRight(), value);
        }
    }

    boolean first=true;
    public String preOrder(){return order(1);}
    public String inOrder() {return order(2);}
    public String postOrder(){return order(3);}

    public String order(int type){
        global_sendoff = "";
        first = true;
        order(root, type);
        return "[" + global_sendoff + "]";
    }

    public void rotateRight(TreeNode<E> rotate_root){

        //root's left child becomes root
        //old roots left child is left child's right child
        TreeNode<E> prev_root=root;
        

        /*if(rotate_root.getLeft()!=null){
            TreeNode<E> b=rotate_root.getLeft();
            if(b.getRight()!=null)
                rotate_root.setLeft(b.getRight());
            b.setRight(rotate_root);
            rotateRight(b);
        }*/
    }

    String global_sendoff="";

    public void order(TreeNode<E> node, int type){
        if(node!=null){
            if(type==1){
                global_sendoff+=((first)?"":", ")+node.getValue();
                first=false;
            }
            order(node.getLeft(), type);
            if(type==2){
                global_sendoff+=((first)?"":", ")+node.getValue();
                first=false;
            }
            order(node.getRight(), type);
            if(type==3){
                global_sendoff+=((first)?"":", ")+node.getValue();
                first=false;
            }
        }
    }
    public void inOrder(TreeNode<E> node){
        if(node!=null) {
            global_sendoff+=((first)?"":", ")+node.getValue();
            inOrder(node.getLeft());

            first=false;
            inOrder(node.getRight());
        }
    }

    //      if >
    //          node.setRight(remove(node.setRight()));

    public TreeNode<E> remove(TreeNode<E> node, E value){ //place, key to remove
        if(node==null)
            return node;

        if(value.compareTo(node.getValue())<0)
            node.setLeft(remove(node.getLeft(), value));
        else if(value.compareTo(node.getValue())>0)
            node.setRight(remove(node.getRight(), value));
        else{
            if(node.getLeft()==null)
                return node.getRight();
            else if(node.getRight()==null)
                return node.getLeft();
            node.setValue(findMinimum(node.getRight()));
            node.setRight(remove(node.getRight(), node.getValue()));
        }
        return node;
    }

    public void remove(E value){
        root=remove(root, value);
    }


    public E findMinimum(TreeNode<E> temp){
        E tempMin=temp.getValue();
        while(temp.getLeft()!=null) {
            tempMin = temp.getLeft().getValue();
            temp=temp.getLeft();
        }
        return tempMin;
    }


    public int size(){return size;}
    public TreeNode<E> getRoot(){return root;}

    public class TreeNode<E extends Comparable<E>>{

        TreeNode<E> left, right;
        E value;
        public TreeNode(E value){
            this.value=value;
            left=null;
            right=null;
        }

        public boolean equalsNode(E value){
            return this.value==value;
        }
        public E getValue(){return value;}
        public TreeNode<E> getLeft(){return left;}
        public TreeNode<E> getRight(){return right;}
        public void setLeft(TreeNode<E> left){this.left=left;}
        public void setRight(TreeNode<E> right){this.right=right;}
        public void setValue(E value){this.value=value;}
        public String toString(){return value+"";}
    }
}
