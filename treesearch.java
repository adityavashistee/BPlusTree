import java.util.concurrent.LinkedBlockingQueue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.*;

public class treesearch{
	/**
	 *  tree is the insatnce of the B+ Tree 
	 */

	private static BPlusTree tree;

	/**
	 * [initialise  the instance of BPlustree with the given degree
	 * @param m describes the degree
	 */
	private static void initialise(int m){
		tree = new BPlusTree(m);
	}

	/**
	 * [insert the given key value into the instance of the BPlusTree
	 * @param input string which have the key value pair spearted by comma
	 */
	private static void insert(String input){
		input=input.substring(input.indexOf('(')+1, input.indexOf(')'));
		String[] inputData=input.split(",");
		double key = Double.parseDouble(inputData[0]);
		//int value = Integer.valueOf(inputData[1].substring(inputData[1].indexOf('e') + 1));
		tree.insert(key,inputData[1]);
		//System.out.println("inside insert "+ inputData[0] +"  "+ inputData[1]);
	}
	/**
	 * [search the given value of key into the given instance of the BPlusTree
	 * @param input  key value
	 * @param output Value associated with the given key
	 */
	private static void search(String input, StringBuffer output){
		input=input.substring(input.indexOf('(')+1, input.indexOf(')'));
		if(input.contains(",")) searchRange(input, output);
		else{
			double key = Double.parseDouble(input);
			String y=tree.searchTheKey(key);
			if(y!=null) output.append(y+"\n");
			else output.append("null\n");
			//System.out.println(y);
		}
	}

	/**
	 * [searchRange search all the values associated with the keys lying in 
	 * the range of key1 and key2
	 * @param input  string containg key1 and key2 separated by comma
	 * @param output stores the output into the given String Buffer
	 */

	private static void searchRange(String input, StringBuffer output){
		String[] inputData=input.split(",");
		double key1 = Double.parseDouble(inputData[0]);
		double key2 = Double.parseDouble(inputData[1]);
		//System.out.println(tree.searchRange(key1,key2));
		ArrayList AL =tree.searchRange(key1,key2);
		if(AL!=null && AL.size()>0){
			String toPrint = tree.searchRange(key1,key2).toString();
			output.append(toPrint.substring(1,toPrint.length()-1)+"\n");
		} 
		else output.append("null\n");
		//System.out.println("inside serach range "+ key1 + "  " + key2);
	}

	/**
	 * [main method of the application 
	 * @param args[0] input file name
	 */
	public static void main(String args[]){
		//LinkedList inputData= new LinkedList();
		int orderOfTree=0;
		String fileName=args[0];
		StringBuffer output=new StringBuffer();
		try{
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String txtNum;
			if(br!=null){
				initialise(Integer.valueOf(br.readLine()));
				while((txtNum = br.readLine()) != null){
			    	if(txtNum.charAt(0)=='I' ||txtNum.charAt(0)=='i' )
			    		insert(txtNum);
			    	else if(txtNum.charAt(0)=='S' || txtNum.charAt(0)=='s')
			    		search(txtNum,output);
				    //inputData.add(txtNum);
				}
			}
			//System.out.println(outputTree(tree));
			//printTree();
			//System.out.println("output is");
			//System.out.println(output);
			//create a temporary file
		    File logFile=new File("output_file");

    		BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));
    		writer.write(output.toString());

    		//Close writer
    		writer.close();
		}
		catch (FileNotFoundException e){
	    	e.printStackTrace();
	   	}
	   	catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
}