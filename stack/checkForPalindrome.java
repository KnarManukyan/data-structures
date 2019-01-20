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
  public boolean helper(Node<Character> node, int size){
    Stack<Character> s= new LinkedStack<>();
    for(int i = 0; i < size/2; i++){
      s.push(node.getElement());
      node = node.getNext();
    }
    for(int i = size/2; i < size; i++){
      if(size%2 != 0 && i==size/2){
        node = node.getNext();
        continue;
      }
      if(node.getElement() == s.top()){
        s.pop();
      } else{
        return false;
      }
      node = node.getNext();
    }
    return true;
  }
  public boolean checkForPalindrome(){
    return helper((Node<Character>) head,size());
  }
  public void printList () {
    Node<E> current = head;
    for(int i = 0; i < size-1; i++){
      System.out.print(current.getElement() + " -> ");
      current = current.getNext();
    }
    System.out.print(current.getElement());
    System.out.println();
  }
}
interface Stack<E> {
  int size();
  boolean isEmpty();
  E top();
  void push(E e);
  E pop();
 }

class LinkedStack<E> implements Stack<E>{
  private SinglyLinkedList<E> list = new SinglyLinkedList<>( );
  public int size() {return list.size();}
  public boolean isEmpty() {return list.isEmpty();}
  public E top() {return list.first();};
  public void push(E e){list.addFirst(e);}
  public E pop(){return list.removeFirst();}
}
class checkForPalindrome {
  public static void main(String[] args) {
    SinglyLinkedList<Character> l1= new SinglyLinkedList<>();
    l1.addFirst('a');
    l1.addLast('b');
    l1.addLast('b');
    l1.addLast('a');
    System.out.println(l1.checkForPalindrome());
    SinglyLinkedList<Character> l2= new SinglyLinkedList<>();
    l2.addFirst('a');
    l2.addLast('b');
    l2.addLast('c');
    l2.addLast('b');
    l2.addLast('a');
    System.out.println(l2.checkForPalindrome());
    SinglyLinkedList<Character> l3= new SinglyLinkedList<>();
    l3.addFirst('a');
    l3.addLast('b');
    l3.addLast('c');
    l3.addLast('c');
    l3.addLast('a');
    System.out.println(l3.checkForPalindrome());
  }
}
/* checkForPalindrome function uses the helper function, which uses stack to check
   if the string is palindrome. the function pushes the half of the string to the stack and then starts to if the net element is equal to the top element, the top element will be poped.
*/ 
