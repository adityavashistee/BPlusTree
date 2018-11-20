import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Leaf extends ParentNode {
	/**
	 * 	values list of Values associated with the given leaf node.
	 * 	leafRight It is a pointer to the right leaf node of the tree.
	 * 	leafLeft It is a pointer to the left leaf node of the tree.
	 */
	protected ArrayList<String> values;
	protected Leaf leafRight;
	protected Leaf leafLeft;

	/**
	 * Constructor to create an instance of leaf node when there is only one key 
	 * and one value to populate.
	 * @param  firstKey   single value of key to be inserted in a new instance of the leaf node
	 * @param  firstValue value corresponding to the given key t be inseterd into the leaf node
	 * @param  degree     degree of the tree
	 */
	public Leaf(Double firstKey, String firstValue, Integer degree) {
		super(degree);
		isLeaf = true;
		keyList = new ArrayList<Double>();
		values = new ArrayList<String>();
		keyList.add(firstKey);
		values.add(firstValue);
	}

	/**
	 * Constructor to create an instance of new leaf node with the given list of keys and corresponding list of values.
	 * @param  newKeys   list of keys to be inserted in the new instance of the leaf node
	 * @param  newValues list of values corresponding to thelist of keys to be inserted in the new instance of the leaf node
	 * @param  degree    degree of the tree
	 */
	public Leaf(List<Double> newKeys, List<String> newValues, Integer degree) {
		super(degree);
		isLeaf = true;
		keyList = new ArrayList<Double>(newKeys);
		values = new ArrayList<String>(newValues);
	}

	/**
	 * Method to insert new key value pair into the given leaf node in sorted order.
	 * @param key   key to be inserted in the sorted order
	 * @param value value corresponing that key
	 */
	public void insertInSortedOrder(Double key, String value) {
		if (key.compareTo(keyList.get(0)) < 0) {
			keyList.add(0, key);
			values.add(0, value);
		} else if (key.compareTo(keyList.get(keyList.size() - 1)) > 0) {
			keyList.add(key);
			values.add(value);
		} else {
			ListIterator<Double> iterator = keyList.listIterator();
			while (iterator.hasNext()) {
				if (iterator.next().compareTo(key) > 0) {
					int position = iterator.previousIndex();
					keyList.add(position, key);
					values.add(position, value);
					break;
				}
			}

		}
	}

}
