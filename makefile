JCC=javac
JFLAGS=-g

default:ParentNode.class TreeNode.class Leaf.class BPlusTree.class treesearch.class KeyNodePair.class

KeyNodePair.class:KeyNodePair.java
	$(JCC) $(JFLAGS) KeyNodePair.java

ParentNode.class:ParentNode.java
	$(JCC) $(JFLAGS) ParentNode.java

TreeNode.class:TreeNode.java
	$(JCC) $(JFLAGS) TreeNode.java

Leaf.class:Leaf.java
	$(JCC) $(JFLAGS) Leaf.java

BPlusTree.class:BPlusTree.java
	$(JCC) $(JFLAGS) BPlusTree.java

treesearch.class:treesearch.java
	$(JCC) $(JFLAGS) treesearch.java

clean:
	${RM} *.class
