import java.util.ArrayList;

/**
 * BPlusTree class which handles insert, search and search range operations 
 */

public class BPlusTree {

	public ParentNode root;
	public Integer maxSize;
	public Integer minSize;
	private Integer degree;

	/**
	 * initialises the BPlusTree instance and set the value of root, maxSize and minSize parameters
	 * @param degree to initialise the BPlusTree insatnce with the degree from input file
	 */
	public BPlusTree(int degree){
		this.degree=degree;
		this.maxSize=degree-1;
		this.minSize=maxSize/2;
	}

	/**
	 * return the string with the value corresponding to the given key.
	 * @param input is a key value whose corresponding value is required for searching
	 * @return String eg Value19, Value10 as an output for the given Search Opeartion
	 */

	public String searchTheKey(Double key) {
		// Return if empty tree or key
		if(key == null || root == null) {
			return null;
		}
		// Look for leaf parentNode that key is pointing to
		Leaf leaf = (Leaf)fetchTreeNode(root, key);
					
		// Look for value in the leaf
		for(int i=0; i<leaf.keyList.size(); i++) {
			if(key.compareTo(leaf.keyList.get(i)) == 0) {
				return leaf.values.get(i);
			}
		}
					
		return null;
	}
	/**
	 * search the given instance of B+ tree for the range of key values ranging from key1 to key2.
	 * Returns the list of key value pairs which fall in the given range.
	 * @param key1 lower key value
	 * @param key2 upper key value
	 * @return List of values whose key lie between these two keyList
	 */
	public ArrayList<String> searchRange(Double key1, Double key2) {

		ArrayList<String> AL = new ArrayList<String>();
		// Return if empty tree or key
		if(key1 == null || key2 == null || root == null) {
			return AL;
		}
		Leaf leaf = (Leaf)fetchTreeNode(root,key1);

		while(key1.compareTo(leaf.keyList.get(leaf.keyList.size()-1)) >=0 && leaf.leafRight!=null)
			leaf=leaf.leafRight;
					
		// Look for value in the leaf
		for(int i=0; i<leaf.keyList.size(); i++) {
			if(key1.compareTo(leaf.keyList.get(i)) <= 0) {
				while(i<leaf.keyList.size() && key2.compareTo(leaf.keyList.get(i)) >= 0){
					if(!leaf.values.get(i).contains(",")){
						String keyValue="("+leaf.keyList.get(i).toString()+ ","+(String)leaf.values.get(i)+")";
						AL.add(keyValue);
					}
					else{
						String[] vals = leaf.values.get(i).split(",");
						for(int j=0; j<vals.length; j++){
							String keyValue="("+leaf.keyList.get(i).toString()+ ","+vals[j]+")";
							AL.add(keyValue);
						}
					}
					i++;
					if(i==leaf.keyList.size() && key2.compareTo(leaf.keyList.get(i-1)) >= 0 && leaf.leafRight!=null){
						i=0;
						leaf=leaf.leafRight;
					}
				}
				
			}
		}
		return AL;
	}

	/**
	 * return the Parent Node with the given key as one of the keys in the range of 
	 * the given nodes. Input parameters are tree node as root node and key to look for. 
	 * @param  parentNode root node in whose sub tree we will be looking for the given key 
	 * @param  key        key value we are looking for in the given B+ Tree
	 * @return            returns the leaf node in whose range the given key value falls
	 */
	private ParentNode fetchTreeNode(ParentNode parentNode, Double key) {
		if(parentNode.isLeaf) {
			return parentNode;
		} 
		// The parentNode is treeNode node
		else {
			TreeNode treeNode = (TreeNode)parentNode;
			
			// Key < Key1, return fetchTreeNode(P0, Key)
			if(key.compareTo(treeNode.keyList.get(0)) < 0) {
				return fetchTreeNode((ParentNode)treeNode.childNodes.get(0), key);
			}
			// Key >= Keym, return fetchTreeNode(Pm, Key), m = #entries
			else if(key.compareTo(treeNode.keyList.get(parentNode.keyList.size()-1)) >= 0) {
				return fetchTreeNode((ParentNode)treeNode.childNodes.get(treeNode.childNodes.size()-1), key);
			}
			// look for i such that Keyi <= Key < Key(i+1), return fetchTreeNode(Pi,Key)
			else {
				// search linearly
				for(int i=0; i<treeNode.keyList.size()-1; i++) {
					if(key.compareTo(treeNode.keyList.get(i)) >= 0 && key.compareTo(treeNode.keyList.get(i+1)) < 0) {
						return fetchTreeNode((ParentNode)treeNode.childNodes.get(i+1), key);
					}
				}
 			}
			return null;
		}
	} 
	
	/**
	 * Insert the key value pair into the given instance of B+tree.
	 * @param key   Double Key value that need to be inserted
	 * @param value value corresponding to the given key to be inserted into the tree
	 */

	public void insert(Double key, String value) {
		Leaf newLeaf = new Leaf(key, value,degree);
		KeyNodePair entry = new KeyNodePair(key, newLeaf);
		
		if(root!= null && root.keyList.size() != 0) {
			Leaf leaf = (Leaf)fetchTreeNode(root, key);
			if(leaf!=null) {
				for(int i=0; i<leaf.keyList.size(); i++) {
					if(key.compareTo(leaf.keyList.get(i)) == 0) {
						String valueAti=leaf.values.get(i);
						leaf.values.set(i,(String)(valueAti + "," + value));
						return;
					}
				}
			}
		}

		// Insert entry into subtree with root node pointer
		if(root == null || root.keyList.size() == 0) {
			root = entry.getValue();
		}
		// keyNodePair null initially, and null on return unless child is split
		KeyNodePair keyNodePair = getTheChildEntry(root, entry, null);
		
		if(keyNodePair == null) {
			return;
		} else {
			TreeNode newRoot = new TreeNode(keyNodePair.getKey(), root, 
					keyNodePair.getValue(),degree);
			root = newRoot;
			return;
		}
	}

	/**
	 * This method is called in insert function will return null if no split is required or will 
	 * return the keyNodePair where the split happens.
	 * @param  parentNode  
	 * @param  entry       [description]
	 * @param  keyNodePair is null when passed initially 
	 * @return             will return null if no split is reuired and will return the KeyvaluePair 
	 *                      if split is required
	 */
	
	private KeyNodePair getTheChildEntry(ParentNode parentNode, KeyNodePair entry, KeyNodePair keyNodePair) {
		if(!parentNode.isLeaf) {
			// Choose subtree, find i such that Ki <= entry's key value < J(i+1)
			TreeNode treeNode = (TreeNode) parentNode;
			int i = 0;
			while(i < treeNode.keyList.size()) {
				if(entry.getKey().compareTo(treeNode.keyList.get(i)) < 0) {
					break;
				}
				i++;
			}
			// Recursively, insert entry
			keyNodePair = getTheChildEntry((ParentNode) treeNode.childNodes.get(i), entry, keyNodePair);
			
			// Usual case, didn't split child
			if(keyNodePair == null) {
				return null;
			} 
			// Split child case, must insert keyNodePair in node
			else {
				int j = 0;
				while (j < treeNode.keyList.size()) {
					if(keyNodePair.getKey().compareTo(treeNode.keyList.get(j)) < 0) {
						break;
					}
					j++;
				}
				
				treeNode.insertInSortedOrder(keyNodePair, j);
				
				// Usual case, put keyNodePair on it, set keyNodePair to null, return
				if(!treeNode.isOverflow()) {
					return null;
				} 
				else{
					keyNodePair = splitTheIndexNode(treeNode);
					
					// Root was just split
					if(treeNode == root) {
						// Create new node and make tree's root-node pointer point to newRoot
						TreeNode newRoot = new TreeNode(keyNodePair.getKey(), root, 
								keyNodePair.getValue(),degree);
						root = newRoot;
						return null;
					}
					return keyNodePair;
				}
			}
		}
		// Node pointer is a leaf node
		else {
			Leaf leaf = (Leaf)parentNode;
			Leaf newLeaf = (Leaf)entry.getValue();
			
			leaf.insertInSortedOrder(entry.getKey(), newLeaf.values.get(0));
			
			// Usual case: leaf has space, put entry and set keyNodePair to null and return
			if(!leaf.isOverflow()) {
				return null;
			}
			// Once in a while, the leaf is full
			else {
				keyNodePair = splitTheLeafNode(leaf);
				if(leaf == root) {
					TreeNode newRoot = new TreeNode(keyNodePair.getKey(), leaf, 
							keyNodePair.getValue(),degree);
					root = newRoot;
					return null;
				}
				return keyNodePair;
			}
		}
	}
	/**
	 * [splitTheLeafNode Splits the leaf Node whenever the overflow 
	 * happens in the leaf Node
	 * @param  leaf - input leaf node which is oveflowed and is 
	 * require to be splitted in half
	 * @return      return the key Node pair as an output with new
	 * splitKey and right node as the node
	 */

	public KeyNodePair splitTheLeafNode(Leaf leaf) {
		
		ArrayList<Double> newKeys = new ArrayList<Double>();
		ArrayList<String> newValues = new ArrayList<String>();
		
		// The rest D entries move to brand new node
		for(int i=minSize; i<=maxSize; i++) {
			newKeys.add(leaf.keyList.get(i));
			newValues.add(leaf.values.get(i));
		}
		
		// First D entries stay
		for(int i=minSize; i<=maxSize; i++) {
			leaf.keyList.remove(leaf.keyList.size()-1);
			leaf.values.remove(leaf.values.size()-1);
		}
		
		Double splitKey = newKeys.get(0);
		Leaf rightNode = new Leaf(newKeys, newValues,degree);
		
		// Set sibling pointers
		Leaf tmp = leaf.leafRight;
		leaf.leafRight = rightNode;
		leaf.leafRight.leafLeft = rightNode;
		rightNode.leafLeft = leaf;
		rightNode.leafRight = tmp;
        
		KeyNodePair keyNodePair = new KeyNodePair(splitKey, rightNode);
		
		return keyNodePair;
	}

	/**
	 * splitTheIndexNode splits the Index node when it overflows
	 * @param  treeNode node which is required to be splited
	 * @return  New key node Pair with key as SplitKey and node as Rigt Node 
	 */

	public KeyNodePair splitTheIndexNode(TreeNode treeNode) {

		ArrayList<Double> newKeys = new ArrayList<Double>();
		ArrayList<ParentNode> newChildren = new ArrayList<ParentNode>();
		
		// Note difference with splitting leaf page, maxSize+1 key values and MaxSize+2 parentNode pointers
		Double splitKey = treeNode.keyList.get(minSize);
		int minValue=minSize;
		//System.out.println(minSize + " " + min);
		treeNode.keyList.remove(minValue);
		
		// First minsize key values and minsize+1 parentNode pointers stay
		// Last minSize keyList and minSize+1 pointers move to new parentNode
		newChildren.add(treeNode.childNodes.get(minSize+1));
		treeNode.childNodes.remove(minSize+1);
		
		while(treeNode.keyList.size() > minSize) {
			newKeys.add(treeNode.keyList.get(minSize));
			treeNode.keyList.remove(minValue);
			newChildren.add(treeNode.childNodes.get(minSize+1));
			treeNode.childNodes.remove(minSize+1);
		}

		TreeNode rightNode = new TreeNode(newKeys, newChildren,degree);
		KeyNodePair keyNodePair = new KeyNodePair(splitKey, rightNode);

		return keyNodePair;
	}
}