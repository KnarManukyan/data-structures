interface Deque<E> {
  int size();
  boolean isEmpty();
  E first();
  E last();
  void addFirst(E e);
  void addLast(E e);
  E removeFirst();
  E removeLast();
}
class DoublyLinkedList<E> { 
    private static class Node<E> {
    private E element; 
    private Node<E> next; 
    private Node<E> prev;
    public Node(E e, Node<E> p, Node<E> n) {
      element = e;
      next = n;
      prev = p;
    }
    public E getElement( ) { return element; }
    public void setElement(E e) { element = e; }
    public Node<E> getNext( ) { return next; }
    public Node<E> getPrev( ) { return prev; }
    public void setNext(Node<E> n) { next = n; }
    public void setPrev(Node<E> p) { prev = p; }
    }
    private Node<E> header;
    private Node<E> trailer;
    private int size = 0; 
    public DoublyLinkedList( ) {
      header = new Node<>(null, null, null);
      trailer = new Node<>(null, header, null);
      header.setNext(trailer);
     } 
    public int size( ) { return size; }
    public boolean isEmpty( ) { return size == 0; }
  public E first( ) {
    if (isEmpty( )) return null;
    return header.getNext( ).getElement( );
  }
  public E last( ) {
    if (isEmpty( )) return null;
    return trailer.getPrev( ).getElement( );
  }
  public void addFirst(E e) { 
    header.next = new Node<>(e, header, header.getNext()); 
    if (size == 0)
    trailer.prev = header.getNext(); 
    size++;
  }
  public void addLast(E e) { 
    Node<E> newest = new Node<>(e, trailer.getPrev(), null); 
    if (isEmpty( ))
    header.next = newest;
    else
    trailer.getPrev().setNext(newest);
    trailer.prev = newest;
    size++;
  }
  public E removeFirst( ) {
    if (isEmpty( )) return null; 
    E answer = header.getNext().getElement( );
    header.next = header.getNext().getNext();
    size--;
    if (size == 0)
    trailer.prev = null;
    else 
    header.getNext().setPrev(null); 
    return answer;
  }
  public E removeLast() {
    if (isEmpty()) return null; 
    E answer = trailer.getPrev().getElement( );
    trailer.prev = trailer.getPrev().getPrev();
    size--;
    if (size == 0)
    header.next = null;
    else
    trailer.getPrev().setNext(null);
    return answer;
  }
}
class LinkedDeque<E> implements Deque<E>{
  DoublyLinkedList<E> s = new DoublyLinkedList<>();
  public int size(){ return size();}
  public boolean isEmpty() { return isEmpty();}
  public E first() { return s.first();}
  public E last() { return s.last();}
  public void addFirst(E e) { s.addFirst(e);}
  public void addLast(E e) { s.addLast(e);}
  public E removeFirst() { return s.removeFirst();}
  public E removeLast() { return s.removeLast();}
   public static void main(String[] args) {
    System.out.println("The queue implemented with doubly linked list");
    Deque<Integer> d = new LinkedDeque<>();
    d.addFirst(5);
    d.addLast(3);
    System.out.println(d.removeLast());
    System.out.println(d.removeFirst());
    System.out.println(d.first());
    System.out.println(d.last());
  }
}

// All dequeue functions are runnign at O(1) running time.
// The space usage of doubly linked list deque is O(n).
// n elements -> n nodes (if the node will be deleted the garbage collector will delete that node from memory)
// for array deque uses an array with the size 0 to 3n
// (as the array can be resized from the start and the end) 
