/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */


package Project;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class BinarySearchTree implements Serializable {
  class Node implements Serializable{
    String key,num;
    Node left, right;

    public Node(String name, String num) {
      key = name;
      this.num = num;
      left = right = null;
    }
  }

  Node root;

  BinarySearchTree() {
    root = null;
  }

  void insert(String key, String num) {
    root = insertKey(root, key, num);
  }

  // Insert key in the tree
  Node insertKey(Node root, String key, String num) {
    // Return a new node if the tree is empty
    if (root == null) {
      root = new Node(key, num);
      return root;
    }

    // Traverse to the right place and insert the node
    if (key.compareTo(root.key) < 0)
      root.left = insertKey(root.left, key, num);
    else if (key.compareTo(root.key) > 0)
      root.right = insertKey(root.right, key, num);

    return root;
  }

  void display() {
      System.out.println("******Contact List******");
    displayContacts(root);
  }

  // Inorder Traversal
  void displayContacts(Node root) {
    if (root != null) {
      displayContacts(root.left);
      System.out.print(root.key + " -> "+ root.num);
      displayContacts(root.right);
    }
  }

  void delete(String key) {
    root = deleteContact(root, key);
  }

  Node deleteContact(Node root, String key) {
    // Return if the tree is empty
    if (root == null)
      return root;

    // Find the node to be deleted
    if (key.compareTo(root.key )< 0)
      root.left = deleteContact(root.left, key);
    else if (key.compareTo(root.key) > 0)
      root.right = deleteContact(root.right, key);
    else {
      // If the node is with only one child or no child
      if (root.left == null)
        return root.right;
      else if (root.right == null)
        return root.left;

      // If the node has two children
      // Place the inorder successor in position of the node to be deleted
      root.key = minValue(root.right);

      // Delete the inorder successor
      root.right = deleteContact(root.right, root.key);
    }

    return root;
  }
  void saveContacts(BinarySearchTree bst) throws Exception{
       FileOutputStream fos = new FileOutputStream("C:\\Users\\HP\\Desktop\\Contacts.txt");
       ObjectOutputStream oos = new ObjectOutputStream(fos);
       oos.writeObject(bst);
       oos.close();
   }
   BinarySearchTree receiveContacts() throws Exception{
       FileInputStream fis = new FileInputStream("C:\\Users\\HP\\Desktop\\Contacts.txt");
       ObjectInputStream ois = new ObjectInputStream(fis);
       BinarySearchTree object = (BinarySearchTree)ois.readObject();
       ois.close();
       return object;
   }
  // Find the inorder successor
  String minValue(Node root) {
    String minv = root.key;
    while (root.left != null) {
      minv = root.left.key;
      root = root.left;
    }
    return minv;
  }
  boolean search(String name){
      if (root != null) {
          
      displayContacts(root.left);
      displayContacts(root.right);
      if(name.indexOf(root.key) <= 0){
      return true;
      }
    
    }
    return false;
  }
  // Driver Program to test above functions
  public static void main(String[] args) throws Exception{
    BinarySearchTree tree = new BinarySearchTree();
    Scanner input = new Scanner(System.in);
    int choice = -1;
    while(choice != 0){
    System.out.println("\nPress 1 to add a new contact.");
    System.out.println("Press 2 to delete a contact.");
    System.out.println("Press 3 to display the contact list.");
    System.out.println("Press 4 to receive contact.");
    System.out.println("Press 0 to exit.");
    choice = input.nextInt();
    
        if(choice == 1){
        System.out.println("Enter contact name; ");
        input.nextLine();
        String key = input.nextLine();
        System.out.println("Enter number: ");
        String num = input.nextLine();
        tree.insert(key, num);
        tree.saveContacts(tree);
        System.out.println("Contact saved!");
    }
    if(choice == 2){
        tree.display();
        System.out.println("\nEnter the contact name you want to delete: ");
        input.nextLine();
        String name = input.nextLine();
        if(tree.search(name)){
            tree.delete(name);
            tree.saveContacts(tree);
            System.out.println("Contact deleted!");
            tree.display();
        }
    }
    if(choice == 3){
        tree.display();
    }
    if(choice == 4){
        tree = tree.receiveContacts();
        System.out.println("Data received!");
        tree.display();
    }
  }
      System.out.println("Exited.");
  }
}


