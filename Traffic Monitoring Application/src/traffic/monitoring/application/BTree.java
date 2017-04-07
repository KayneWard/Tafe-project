//Source:  http://www.newthinktank.com/2013/03/binary-tree-in-java/
// New Think Tank
package traffic.monitoring.application;

import javax.swing.JTextArea;

public class BTree {

	BTNode root;

	public void addBTNode(int key, String name) {

		// Create a new BTNode and initialize it

		BTNode newBTNode = new BTNode(key, name);

		// If there is no root this becomes root

		if (root == null) {

			root = newBTNode;

		} else {

			// Set root as the BTNode we will start
			// with as we traverse the tree

			BTNode focusBTNode = root;

			// Future parent for our new BTNode

			BTNode parent;

			while (true) {

				// root is the top parent so we start
				// there

				parent = focusBTNode;

				// Check if the new BTNode should go on
				// the left side of the parent BTNode

				if (key < focusBTNode.key) {

					// Switch focus to the left child

					focusBTNode = focusBTNode.leftChild;

					// If the left child has no children

					if (focusBTNode == null) {

						// then place the new BTNode on the left of it

						parent.leftChild = newBTNode;
						return; // All Done

					}

				} else { // If we get here put the BTNode on the right

					focusBTNode = focusBTNode.rightChild;

					// If the right child has no children

					if (focusBTNode == null) {

						// then place the new BTNode on the right of it

						parent.rightChild = newBTNode;
						return; // All Done

					}

				}

			}
		}

	}

	// All BTNodes are visited in ascending order
	// Recursion is used to go to one BTNode and
	// then go to its child BTNodes and so forth

	public void inOrderTraverseTree(BTNode focusBTNode, JTextArea txtaBinTree ) {

		if (focusBTNode != null) {

			// Traverse the left BTNode

			inOrderTraverseTree(focusBTNode.leftChild, txtaBinTree);

			// Visit the currently focused on BTNode

			txtaBinTree.append(focusBTNode.toString()+ ",  ");

			// Traverse the right BTNode

			inOrderTraverseTree(focusBTNode.rightChild, txtaBinTree);

		}

	}

	public void preorderTraverseTree(BTNode focusBTNode, JTextArea txtaBinTree) {

		if (focusBTNode != null) {

			txtaBinTree.append(focusBTNode.toString() + ",  ");

			preorderTraverseTree(focusBTNode.leftChild, txtaBinTree);
			preorderTraverseTree(focusBTNode.rightChild, txtaBinTree);

		}

	}

	public void postOrderTraverseTree(BTNode focusBTNode, JTextArea txtaBinTree) {

		if (focusBTNode != null) {

			postOrderTraverseTree(focusBTNode.leftChild, txtaBinTree);
			postOrderTraverseTree(focusBTNode.rightChild, txtaBinTree);

                                                        txtaBinTree.append(focusBTNode.toString()+ ",  ");

		}

	}

	public BTNode findBTNode(int key) {

		// Start at the top of the tree

		BTNode focusBTNode = root;

		// While we haven't found the BTNode
		// keep looking

		while (focusBTNode.key != key) {

			// If we should search to the left

			if (key < focusBTNode.key) {

				// Shift the focus BTNode to the left child

				focusBTNode = focusBTNode.leftChild;

			} else {

				// Shift the focus BTNode to the right child

				focusBTNode = focusBTNode.rightChild;

			}

			// The BTNode wasn't found

			if (focusBTNode == null)
				return null;

		}

		return focusBTNode;

	}

public static void main(String[] args) {

		BTree theTree = new BTree();

		theTree.addBTNode(50, "Boss");

		theTree.addBTNode(25, "Vice President");

		theTree.addBTNode(15, "Office Manager");

		theTree.addBTNode(30, "Secretary");

		theTree.addBTNode(75, "Sales Manager");

		theTree.addBTNode(85, "Salesman 1");

		// Different ways to traverse binary trees

		// theTree.inOrderTraverseTree(theTree.root);

		// theTree.preorderTraverseTree(theTree.root);

		// theTree.postOrderTraverseTree(theTree.root);

		// Find the BTNode with key 75

		System.out.println("\nBTNode with the key 75");

		System.out.println(theTree.findBTNode(75));

}
}

class BTNode {

	int key;
	String name;

	BTNode leftChild;
	BTNode rightChild;

	BTNode(int key, String name) {

		this.key = key;
		this.name = name;

	}

	public String toString() {

		return name + " has the key " + key;

		/*
		 * return name + " has the key " + key + "\nLeft Child: " + leftChild +
		 * "\nRight Child: " + rightChild + "\n";
		 */

	}

}
