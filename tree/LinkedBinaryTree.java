import java.util.*;
interface Position<E> {
  E getElement() throws IllegalStateException;
}
class SinglyLinkedList<E> { 
    private static class Node<E> {
    private E element; 
    private Node<E> next; 
    public Node(E e, Node<E> n) {
      element = e;
      next = n;
    }
    public E getElement( ) { return element; }
    public void setElement(E e) { element = e; }
    public Node<E> getNext( ) { return next; }
    public void setNext(Node<E> n) { next = n; }
    }
    private Node<E> head = null; 
    private Node<E> tail = null; 
    private int size = 0; 
    public SinglyLinkedList( ) { } 
    public int size( ) { return size; }
    public boolean isEmpty( ) { return size == 0; }
  public E first( ) {
    if (isEmpty( )) return null;
    return head.getElement( );
  }
  public E last( ) { 
    if (isEmpty( )) return null;
    return tail.getElement( );
  }
  public void addFirst(E e) { 
    head = new Node<>(e, head); 
    if (size == 0)
    tail = head; 
    size++;
  }
  public void addLast(E e) { 
    Node<E> newest = new Node<>(e, null); 
    if (isEmpty( ))
    head = newest;
    else
    tail.setNext(newest);
    tail = newest;
    size++;
  }
  public E removeFirst( ) {
    if (isEmpty( )) return null; 
    E answer = head.getElement( );
    head = head.getNext( ); 
    size--;
    if (size == 0)
    tail = null;
    return answer;
  }
}
interface Queue<E> {
  int size();
  boolean isEmpty();
  void enqueue(E element);
  E first();
  E dequeue();
}
class LinkedQueue<E> implements Queue<E> {
  private SinglyLinkedList<E> list = new SinglyLinkedList<>();
  public int size() { return list.size(); }
  public boolean isEmpty() { return list.isEmpty(); }
  public void enqueue(E element) { list.addLast(element); }
  public E first() { return list.first(); }
  public E dequeue() { return list.removeFirst(); }
}
interface Tree<E> extends Iterable<E> {
  Position<E> root();
  Position<E> parent(Position<E> p) throws IllegalArgumentException;
  Iterable<Position<E>> children(Position<E> p) throws IllegalArgumentException;
  int numChildren(Position<E> p) throws IllegalArgumentException;
  boolean isInternal(Position<E> p) throws IllegalArgumentException;
  boolean isExternal(Position<E> p) throws IllegalArgumentException;
  boolean isRoot(Position<E> p) throws IllegalArgumentException;
  nt size();
  boolean isEmpty();
  Iterator<E> iterator();
  Iterable<Position<E>> positions();
}
abstract class AbstractTree<E> implements Tree<E> {
  public boolean isInternal(Position<E> p) { return numChildren(p) > 0; }
  public boolean isExternal(Position<E> p) { return numChildren(p) == 0; }
  public boolean isRoot(Position<E> p) { return p == root(); }
  public int numChildren(Position<E> p) {
    int count=0;
    for (Position child : children(p)) count++;
    return count;
  }
  public int size() {
    int count=0;
    for (Position p : positions()) count++;
    return count;
  }
  public boolean isEmpty() { return size() == 0; }
  public int depth(Position<E> p) throws IllegalArgumentException {
    if (isRoot(p))
      return 0;
    else
      return 1 + depth(parent(p));
  }
  public int height(Position<E> p) throws IllegalArgumentException {
    int h = 0;
    for (Position<E> c : children(p))
      h = Math.max(h, 1 + height(c));
    return h;
  }
  public int heightofTree() throws IllegalArgumentException {
    int h = 0;
    for (Position<E> c : children(root()))
      h = Math.max(h, 1 + height(c));
    return h;
  }
  private class ElementIterator implements Iterator<E> {
    Iterator<Position<E>> posIterator = positions().iterator();
    public boolean hasNext() { return posIterator.hasNext(); }
    public E next() { return posIterator.next().getElement(); }
    public void remove() { posIterator.remove(); }
  }
  public Iterator<E> iterator() { return new ElementIterator(); }
}
interface BinaryTree<E> extends Tree<E> {
  Position<E> left(Position<E> p) throws IllegalArgumentException;
  Position<E> right(Position<E> p) throws IllegalArgumentException;
  Position<E> sibling(Position<E> p) throws IllegalArgumentException;
}
abstract class AbstractBinaryTree<E> extends AbstractTree<E> implements BinaryTree<E> {
  public Position<E> sibling(Position<E> p) {
    Position<E> parent = parent(p);
    if (parent == null) return null;                 
    if (p == left(parent))                           
      return right(parent);                          
    else                                             
      return left(parent);                           
  }
  public int numChildren(Position<E> p) {
    int count=0;
    if (left(p) != null)
      count++;
    if (right(p) != null)
      count++;
    return count;
  }
  public Iterable<Position<E>> children(Position<E> p) {
    List<Position<E>> snapshot = new ArrayList<>(2); 
    if (left(p) != null)
      snapshot.add(left(p));
    if (right(p) != null)
      snapshot.add(right(p));
    return snapshot;
  }
  private void inorderSubtree(Position<E> p, List<Position<E>> snapshot) {
    if (left(p) != null)
      inorderSubtree(left(p), snapshot);
    snapshot.add(p);
    if (right(p) != null)
      inorderSubtree(right(p), snapshot);
  }
  public Iterable<Position<E>> inorder() {
    List<Position<E>> snapshot = new ArrayList<>();
    if (!isEmpty())
      inorderSubtree(root(), snapshot);
    return snapshot;
  }
  public Iterable<Position<E>> positions() {
    return inorder();
  }
}
class LinkedBinaryTree<E> extends AbstractBinaryTree<E>{
  protected static class Node<E> implements Position<E> {
    private E element;       
    private Node<E> parent;  
    private Node<E> left;    
    private Node<E> right;   
    public Node(E e, Node<E> above, Node<E> leftChild, Node<E> rightChild) {
      element = e;
      parent = above;
      left = leftChild;
      right = rightChild;
    }
    public E getElement() { return element; }
    public Node<E> getParent() { return parent; }
    public Node<E> getLeft() { return left; }
    public Node<E> getRight() { return right; }
    public void setElement(E e) { element = e; }
    public void setParent(Node<E> parentNode) { parent = parentNode; }
    public void setLeft(Node<E> leftChild) { left = leftChild; }
    public void setRight(Node<E> rightChild) { right = rightChild; }
  }
  private boolean hasLeft(Position<E> p){
    Node<E> n = validate(p);
    return n.getLeft()!=null;
  }
  private boolean hasRight(Position<E> p){
    Node<E> n = validate(p);
    return n.getRight()!=null;
  }
  protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
    return new Node<E>(e, parent, left, right);
  }
  protected Node<E> root = null;
  private int size = 0;
  protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
    if (!(p instanceof Node))
      throw new IllegalArgumentException("Not valid position type");
    Node<E> node = (Node<E>) p;  
    if (node.getParent() == node)
      throw new IllegalArgumentException("p is no longer in the tree");
    return node;
  }
  public int size() { return size;}
  public Position<E> root() { return root;}
  public Position<E> parent(Position<E> p) throws IllegalArgumentException {
    Node<E> node = validate(p);
    return node.getParent();
  }
  public Position<E> left(Position<E> p) throws IllegalArgumentException {
    Node<E> node = validate(p);
    return node.getLeft();
  }
  public Position<E> right(Position<E> p) throws IllegalArgumentException {
    Node<E> node = validate(p);
    return node.getRight();
  }
  public Position<E> addRoot(E e) throws IllegalStateException {
    if (!isEmpty()) throw new IllegalStateException("Tree is not empty");
    root = createNode(e, null, null, null);
    size = 1;
    return root;
  }
  public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
    Node<E> parent = validate(p);
    if (parent.getLeft() != null)
      throw new IllegalArgumentException("p already has a left child");
    Node<E> child = createNode(e, parent, null, null);
    parent.setLeft(child);
    size++;
    return child;
  }
  public Position<E> addRight(Position<E> p, E e)
                          throws IllegalArgumentException {
    Node<E> parent = validate(p);
    if (parent.getRight() != null)
      throw new IllegalArgumentException("p already has a right child");
    Node<E> child = createNode(e, parent, null, null);
    parent.setRight(child);
    size++;
    return child;
  }
  public E set(Position<E> p, E e) throws IllegalArgumentException {
    Node<E> node = validate(p);
    E temp = node.getElement();
    node.setElement(e);
    return temp;
  }
  public void attach(Position<E> p, LinkedBinaryTree<E> t1,
                    LinkedBinaryTree<E> t2) throws IllegalArgumentException {
    Node<E> node = validate(p);
    if (isInternal(p)) throw new IllegalArgumentException("p must be a leaf");
    size += t1.size() + t2.size();
    if (!t1.isEmpty()) {                
      t1.root.setParent(node);
      node.setLeft(t1.root);
      t1.root = null;
      t1.size = 0;
    }
    if (!t2.isEmpty()) {                
      t2.root.setParent(node);
      node.setRight(t2.root);
      t2.root = null;
      t2.size = 0;
    }
  }
  public E remove(Position<E> p) throws IllegalArgumentException {
    Node<E> node = validate(p);
    if (numChildren(p) == 2)
      throw new IllegalArgumentException("p has two children");
    Node<E> child = (node.getLeft() != null ? node.getLeft() : node.getRight() );
    if (child != null)
      child.setParent(node.getParent());
    if (node == root)
      root = child;                     
    else {
      Node<E> parent = node.getParent();
      if (node == parent.getLeft())
        parent.setLeft(child);
      else
        parent.setRight(child);
    }
    size--;
    E temp = node.getElement();
    node.setElement(null);
    node.setLeft(null);
    node.setRight(null);
    node.setParent(node);
    return temp;
  }
  public Iterable<Position<E>> preorder() {
    List<Position<E>> snapshot = new ArrayList<>();
    Stack<Position<E>> s = new Stack<Position<E>>();
    Position<E> p;
    s.push(root());
    while(!s.isEmpty()){
      p = s.peek();
      snapshot.add(s.pop());
      if(hasRight(p)) s.push(right(p));
      if(hasLeft(p)) s.push(left(p));
    }
    System.out.println("Preorder: ");
    for (Position<E> c : snapshot)
      System.out.print(c.getElement() + " ");
    System.out.println();
    return snapshot;
  }
  public Iterable<Position<E>> postorder() {
    List<Position<E>> snapshot = new ArrayList<>();
    Stack<Position<E>> s1 = new Stack<>();
    Stack<Position<E>> s2 = new Stack<>();
    if (root() == null) return snapshot;
    s1.push(root);
    while (!s1.isEmpty()) {
        Position<E> p = s1.pop();
        s2.push(p);
        if (hasLeft(p)) s1.push(left(p));
        if (hasRight(p)) s1.push(right(p));
    }
    while (!s2.isEmpty()) {
      Position<E> temp = s2.pop();
      snapshot.add(temp);
    }
    System.out.println("Postorder: ");
    for (Position<E> c : snapshot)
      System.out.print(c.getElement() + " ");
    System.out.println();
    return snapshot;
  }
  public Iterable<Position<E>> breadthfirst() {
    List<Position<E>> snapshot = new ArrayList<>();
    if (!isEmpty()) {
      Queue<Position<E>> fringe = new LinkedQueue<>();
      fringe.enqueue(root());
      while (!fringe.isEmpty()) {
        Position<E> p = fringe.dequeue();
        snapshot.add(p);
        for (Position<E> c : children(p))
          fringe.enqueue(c);
      }
    }
    System.out.println("BreadthFirst order: ");
    for (Position<E> c : snapshot)
      System.out.print(c.getElement() + " ");
    System.out.println();
    return snapshot;
  }
  public Iterable<Position<E>> inorder() {
    List<Position<E>> snapshot = new ArrayList<>();
    Stack<Position<E>> s = new Stack<Position<E>>();
    Position<E> p = root();
    s.push(root());
    while(!s.isEmpty()) {
      if(hasLeft(p)){
        p = left(p);
        s.push(p);
      } else {
        p = s.pop();
        snapshot.add(p);
        if(hasRight(p)){
          p = right(p);
          s.push(p);
        }
      }
    }
    System.out.println("Ineorder: ");
    for (Position<E> c : snapshot)
      System.out.print(c.getElement() + " ");
    System.out.println();
    return snapshot;
  }
  public static void main(String[] arg){
    LinkedBinaryTree<Integer> lbt = new LinkedBinaryTree<Integer>();
    lbt.addRoot(5);
    /*Position<Integer> root = lbt.root();
    lbt.addLeft(root, 4);
    Position<Integer> left = lbt.left(root);
    lbt.addRight(root, 3);
    Position<Integer> right = lbt.right(root);
    lbt.addLeft(left, 2);
    lbt.addRight(left, 1);
    lbt.addLeft(right, 8);
    lbt.addRight(right, 9);*/
    System.out.println("The height of the tree is " + lbt.heightofTree());
    /*System.out.println("The height of a leaf is " + lbt.height(lbt.left(left)));
    System.out.println("the depth of a leaf is " + lbt.depth(lbt.left(left)));*/
    lbt.preorder();
    lbt.postorder();
    lbt.breadthfirst();
    lbt.inorder();
    System.out.println();
    LinkedBinaryTree<Integer> lbt1 = new LinkedBinaryTree<Integer>();
    lbt1.addRoot(1);
    Position<Integer> root1 = lbt1.root();
    lbt1.addLeft(root1, 2);
    Position<Integer> left1 = lbt1.left(root1);
    lbt1.addRight(root1, 3);
    Position<Integer> right1 = lbt1.right(root1);
    lbt1.addLeft(left1, 4);
    lbt1.addRight(left1, 5);
    lbt1.addLeft(right1, 6);
    lbt1.addRight(right1, 7);
    System.out.println("The height of the tree is " + lbt1.heightofTree());
    System.out.println("The height of a leaf is " + lbt1.height(lbt1.left(left1)));
    //the running time of culculation heght is O(n), as the algorithms visits every node only once and calculates the height using the height of it's children,
    System.out.println("the depth of a leaf is " + lbt1.depth(lbt1.left(left1)));
    //the depth has running time O(p), where p is the number of parents
    lbt1.preorder();
    //visits the node
    //traverses to the left subtree, then to right subtree
    lbt1.postorder();
    //Traverses the left subtree, then the right subtree
    //Visits the node
    lbt1.breadthfirst();
    //traverses the nodes by levels
    lbt1.inorder();
    //Traverses the left subtree
    //visits the node
    //Traverses the right subtree
  }
}