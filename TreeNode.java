import java.util.ArrayList;
import java.util.List;

public class TreeNode extends ParentNode {

	/**
	 *  childNodes refer to the childern of the iven treenode 
	 */

	protected ArrayList<ParentNode> childNodes;

	/**
	 * Constructor to create an instance of TreeNode with a single key 
	 * value which will have child0 and child1 as the right and left children
	 * @param  key    key value to be saved
	 * @param  child0 left child
	 * @param  child1 right child
	 * @param  degree degree of the tree
	 */

	public TreeNode(Double key, ParentNode child0, ParentNode child1, Integer degree) {
		super(degree);
		isLeaf = false;
		keyList = new ArrayList<Double>();
		keyList.add(key);
		childNodes = new ArrayList<ParentNode>();
		childNodes.add(child0);
		childNodes.add(child1);
	}
	/**
	 * Constructor to create an instance of TreeNode with list of keys and list of children nodes
	 * @param  newKeys     new keys for the new tree node
	 * @param  newChildren new childern for the new treenode
	 * @param  degree      degree of the tree
	 */
	public TreeNode(List<Double> newKeys, List<ParentNode> newChildren, Integer degree) {
		super(degree);
		isLeaf = false;

		keyList = new ArrayList<Double>(newKeys);
		childNodes = new ArrayList<ParentNode>(newChildren);

	}

	/**
	 * insertInSortedOrder Insert the keyNode pair in the given sorted order
	 * @param e     KeyNodePair to be inserted in the sorted order
	 * @param index index where to add key in the keyslist
	 */
	public void insertInSortedOrder(KeyNodePair e, int index) {
		Double key = e.getKey();
		ParentNode child = e.getValue();
		if (index >= keyList.size()) {
			keyList.add(key);
			childNodes.add(child);
		} else {
			keyList.add(index, key);
			childNodes.add(index+1, child);
		}
	}

}
