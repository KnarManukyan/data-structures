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
    public int size( ) { return size; }
    public boolean isEmpty( ) { return size == 0; }
  public E first( ) {
    if (isEmpty( )) return null;
    return head.getElement( );
  }
  public void addFirst(E e) { 
    head = new Node<>(e, head); 
    if (size == 0)
    tail = head; 
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
interface Stack<E> {
  int size( );
  boolean isEmpty( );
  void push(E e);
  E top( );
  E pop( );
}
class ArrayStack<E> implements Stack<E> {
 public static final int CAPACITY=1000; 
 private E[ ] data; 
 private int t = -1; 
 public ArrayStack( ) { this(CAPACITY); } 
 public ArrayStack(int capacity) { 
 data = (E[ ]) new Object[capacity]; 
 }
 public int size( ) { return (t + 1); }
 public boolean isEmpty( ) { return (t == -1); }
 public void push(E e) throws IllegalStateException {
  if (size( ) == data.length) throw new IllegalStateException("Stack is full");
  data[++t] = e;
 }
 public E top( ) {
  if (isEmpty( )) return null;
  return data[t];
 }
 public E pop( ) {
  if (isEmpty( )) return null;
  E answer = data[t];
  data[t] = null;
  t--;
  return answer;
 }
 public static <E> Stack<E> reverse(Stack<E> s){
   Stack<E> helper = new ArrayStack<>();
   int n = s.size();
   for(int i = 0; i<n; i++){
     helper.push(s.top());
     s.pop();
   }
   return helper;
 }
}
class LinkedStack<E> implements Stack<E> {
 private SinglyLinkedList<E> list = new SinglyLinkedList<>( );
 public LinkedStack( ) { }
 public int size( ) { return list.size( ); }
 public boolean isEmpty( ) { return list.isEmpty( ); }
 public void push(E element) { list.addFirst(element); }
 public E top( ) { return list.first( ); }
 public E pop( ) { return list.removeFirst( ); }
 public static <E> Stack<E> reverse(Stack<E> s){
   Stack<E> helper = new LinkedStack<>();
   int n = s.size();
   for(int i = 0; i<n; i++){
     helper.push(s.top());
     s.pop();
   }
   return helper;
 }
}
class reverseStack {
  public static void main(String[] args) {
    Stack<Integer> S = new LinkedStack<>();
    S.push(5);
    S.push(3);
    S.push(7);
    Stack<Integer> reversed = LinkedStack.reverse(S);
    System.out.println(reversed.pop());
    System.out.println(reversed.pop());
    System.out.println(reversed.top());
  }
}
