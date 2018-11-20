import java.util.ArrayList;
/**
 * ParentNode is the base class for Leaf Node and TreeNode classes
 */
public class ParentNode {
	/**
	 * isLeaf define whether the instance of ParentNode is leaf or TreeNode
	 * keyList list of keys
	 * degree degree of the tree
	 * is LeafNode will define whether the insatnce of Parent Node is of Leaf or treeNode
	 */
	protected boolean isLeaf;
	protected ArrayList<Double> keyList;
	protected Integer degree;

	/**
	 * Constructor to create the Node instance with the given degree 
	 * @param  degree assign the degree value of the given tree
	 */
	public ParentNode(Integer degree){
		this.degree=degree;
	}

	/**
	 * [isOverflow checks if the size of the keylist array is more 
	 * than the maximum value allowed for the given degree of the tree.
	 * @return true or false based upon the size of the keysList
	 */

	public boolean isOverflow() {
		return keyList.size() > this.degree-1;
	}

}
