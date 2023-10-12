package trees;

public class BTree<E> {

	public static class Node<F> {
		// data fields
		private F data;
		private Node<F> left;
		private Node<F> right;
		public Node(F data, Node<F> left, Node<F> right) {
			super();
			this.data = data;
			this.left = left;
			this.right = right;
		}
		public Node(F data) {
			super();
			this.data = data;
		}
		
		
	}
	
	// data fields
	private Node<E> root;
	private int size;
	
	BTree() {
		root=null;
		size=0;
	}
	
	BTree(E data) {
		root = new Node<>(data);
		size=1;
	}
	
	BTree(E data, BTree<E> left, BTree<E> right) {
		root = new Node<>(data,left.root,right.root);
		size = 1 + left.size + right.size;
		
	}
	
	/**
	 * Gets the maximum height of a subtree
	 * @param current Root node
	 * @return the local height
	 */
	private int height(Node<E> current) {
		if(current == null)
			return 0;
		return 1 + Math.max(height(current.left), height(current.right));
	}

	/**
	 * Gets the height of this tree
	 * @param current Root node
	 * @return the height
	 */
	public int height() {
		return height(root);
	}

	/**
	 * Get the level of a data value below node
	 * @param node The root node to search under
	 * @param data The data value to search for
	 * @param level The current level
	 * @return 0 if it wasn't found, the level otherwise
	 */
	private int getLevel(Node node, E data, int level){
		if(node == null) {
			return 0; // Node was not found
		}
		if(data == node.data) {
			return level;
		}
		int downlevel = getLevel(node.left, data, level+1);
		if(downlevel != 0)
			return downlevel;
		downlevel = getLevel(node.right, data, level+1);
		return downlevel;
	}

	/**
	 * Get the level of a specific data value
	 * @param data The data value to search for
	 * @return 0 if it wasn't found, the level otherwise
	 */
	public int getLevel(E data){
		return getLevel(root, data, 1);
	}
	
	private String toString(Node<E> current, int level) {
		StringBuilder s = new StringBuilder();
		for (int i=0; i<level;i++) {
			s.append("-");
		}
		if (current==null) {
			s.append("null\n");
		} else {
			s.append(current.data.toString()+"\n");
			s.append(toString(current.left, level+1));
			s.append(toString(current.right,level+1));
		}
		return s.toString();
		
	}
	public String toString() {
		return toString(root,0);
	}
	
	public static void main(String[] args) {
		BTree<Integer> t1 = new BTree<>(7,new BTree<>(), new BTree<>());
		BTree<Integer> t2 = new BTree<>(33,new BTree<>(27,new BTree<>(), new BTree<>()),new BTree<>());
		BTree<Integer> t3 = new BTree<>(23,t1,t2);
		
		System.out.println(t3);
		System.out.println(t3.height());
	}
}
