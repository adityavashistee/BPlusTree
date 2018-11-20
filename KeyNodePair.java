public class KeyNodePair{

	/**
	 * parentNode Node associated with the given key value
	 * key value associated with the given node
	 */
	private ParentNode parentNode;
	private Double key;

	/**
	 * constructor for key node pair which will have a key Node 
	 * as apair associated to it
	 */
	public KeyNodePair(Double key, ParentNode parentNode){
		this.key=key;
		this.parentNode=parentNode;
	}
	/**
	 * [getKey retrieves the key value
	 * @return key value
	 */
	public Double getKey(){
		return this.key;
	}
	/**
	 * [getValue retrive the Node value
	 * @return the Parent node instance
	 */

	public ParentNode getValue(){
		return this.parentNode;
	}
}