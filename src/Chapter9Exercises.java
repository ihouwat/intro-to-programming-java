// Chapter 9: Linked Data Structures and Recursion
// Includes singly linked lists, recursion, binary trees...

import java.util.ArrayList;
import static java.lang.System.out;

public class Chapter9Exercises {

    public static void main(String[] args) {
        Chapter9Exercises MainClass = new Chapter9Exercises();

        out.println("EXERCISE 1a - calculate factorial recursively");
        out.println("Factorial of 4 is: " + factorial(4));
        out.println();

        out.println("EXERCISE 1a - calculate fibonacci sequence recursively");
        out.println("Fibonacci for input 4 is: " + fibonacci(4  ));
        out.println();

        out.println("EXERCISE 3 - print a reversed linked list");
        out.println();
        Chapter9Exercises.SinglyLinkedList list = MainClass.new SinglyLinkedList();
        list.insertIntoLinkedList(1);
        list.insertIntoLinkedList(2);
        list.insertIntoLinkedList(3);
        list.insertIntoLinkedList(4);
        out.println("Original list");
        list.printList();
        out.println();
        out.println("Reversed list");
        list.printReversedList(list.head);
        out.println();

        out.println("EXERCISE 4 - print the contents of a Binary Tree using a queue data structure");
        Chapter9Exercises.BinaryTree tree = MainClass.new BinaryTree();
        tree.insertIntoTree(3);
        tree.insertIntoTree(4);
        tree.insertIntoTree(15);
        tree.insertIntoTree(0);
        tree.insertIntoTree(20);
        tree.insertIntoTree(1);
        tree.printTree();
    }


    // Exercise 1a: Write a recursive function to compute factorial(N)
    static int factorial(int N) {
        if (N == 1) return 1; // base case
        assert N > 0 : "Input to calculate factorial must be a positive integer";
        return N * factorial(N-1);
    } // end factorial();


    // Exercise 1b: Write a recursive function to compute fibonacci(N)
    static int fibonacci(int N) {
        if(N == 0 || N == 1 || N == 2) return 1; // base case
        return fibonacci(N-1) + fibonacci(N-2);
    } // end fibonacci();

    // Exercise 3: Input is a linked list of integers. Reverse and return the list, without modifying the old list.
    class SinglyLinkedList {
        private ListNode head;
        private int size;

        // Constructor to initialize list
        public SinglyLinkedList() {
            head = null;
            size = 0;
        }

        // Class to create node
        public class ListNode {
            int item; // An item in the list.
            ListNode next; // Pointer to the next node in the list.
        }

        public void insertIntoLinkedList (int n) {
            // Create the linked list node
            ListNode newNode;
            newNode = new ListNode();
            newNode.item = n;

            if (head == null){
                head = newNode;
            }
            else {
                ListNode pointer = head;
                while(pointer.next != null) {
                    pointer = pointer.next;
                }
                pointer.next = newNode;
            }

            size++;
        } // end insertIntoLinkedList();

        public void printList() {
            assert head != null : "You cannot print an empty list";
            ListNode pointer = head;
            while(pointer != null){
                out.println(pointer.item);
                pointer = pointer.next;
            }
        } // end printList();

        public void printReversedList(ListNode head) {
            if(head == null) return; // base case
            else {
                printReversedList(head.next);
                out.println(head.item);
            }
        } // end printReversedList();
    } // end SinglyLinkedList class


    // Exercise 4: print the contents of a Binary Tree using a queue data structure
    class BinaryTree {
        private TreeNode root; // root node

        // class to create node
        public class TreeNode {
            int value; // value of node
            TreeNode right; // right pointer
            TreeNode left; // left pointer
        }

        // Constructor to initialize tree
        public BinaryTree() {
            root = null;
        }

        public void insertIntoTree(int val) {
            TreeNode newNode = new TreeNode();
            newNode.value = val; // add inserted value to new node

            if(root == null) root = newNode; // if there is no root, make it into the new node

            TreeNode current = root; // otherwise create a 'current' variable that points to the root

            while(true) {
                if (val == current.value) return; // if there are duplicate values in tree, don't do anything

                if (val > current.value) { // if val is larger than value of current node
                    if(current.right == null) { // AND if the current node has no right child
                        current.right = newNode; // make the new node the right child
                        return;
                    }
                    current = current.right; // if there is a right child, make it the current node
                }
                else { // if val is smaller than value of current node, do the same as above for the current's left child
                    if(current.left == null) {
                        current.left = newNode;
                        return;
                    }
                    current = current.left;
                }
            }
        } // end insertIntoTree();

        public void printTree() {
            if(root == null) return;

            ArrayList<TreeNode> queue = new ArrayList<>();
            // Add the root node to an empty queue
            queue.add(root);

            // While the queue is not empty
            while(queue.size() != 0) {
                TreeNode node = queue.get(0); // Get a node from the queue
                queue.remove(0);
                out.println(node.value); // Print the item in the node
                if (node.left != null) { // if node.left is not null:
                    queue.add(node.left); // add it to the queue
                }
                if (node.right != null) { // if node.right is not null
                    queue.add(node.right); // add it to the queue
                }
            }
        } // end printTree();
    } // end BinaryTree class;
}