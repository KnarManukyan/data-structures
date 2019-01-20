class SinglyLinkedList<E> {
  private class Node<E> {
    private E element;
    private Node<E> next;
    public Node(E e, Node<E> n){
      element = e;
      next = n;
    }
    private E getElement(){return element;}
    private Node<E> getNext(){return next;}
    private void setNext(Node<E> n){next = n;}
  }
  private Node<E> head = null;
  private Node<E> tail = null;
  int size = 0;
  public int size(){return size;}
  public boolean isEmpty(){return size == 0;}
  public E first() {
    if (size == 0){
      return null;
    } 
    return head.getElement( );
  }
  public E last() { 
    if (size == 0){
      return null;
    } 
    return tail.getElement( );
  }
  public void addFirst(E e){ 
    Node<E> newHead = new Node<>(e, head);
    head = newHead;
    if(size == 0){
      tail = head;
    }
    size++;
  }
  public void addLast(E e){ 
    Node<E> newNode = new Node<>(e, null);
    if(size != 0){
      tail.setNext(newNode);
    } else {
      head = newNode;
    }
    tail = newNode;
    size++;
  }
  public E removeFirst(){
    if(size == 0){
      return null;
    }
    E e = head.getElement();
    head = head.getNext();
    size--;
    if(head == null){
      tail = null;
    }
    return e;
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
class Sort {
  public static void sort(Stack<Integer> input){
    Stack<Integer> helper=new LinkedStack<Integer>();
    while(!input.isEmpty()){
      int current=input.pop(); 
      while(!helper.isEmpty() && current>helper.top()){ 
            input.push(helper.pop());  
        }
      helper.push(current); 
    }
    while(!helper.isEmpty()){ 
          input.push(helper.pop());
    }
  }
  public static void main(String[] args) {
    LinkedStack<Integer> l= new LinkedStack();
    l.push(2);
    l.push(1);
    l.push(3);
    l.push(4);
    l.push(5);
    l.push(0);
    sort(l);
    System.out.println("top" + l.top());
    while(!l.isEmpty()){
      System.out.println(l.pop());
    }
  }
}
