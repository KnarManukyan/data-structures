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
  public static void bubbleSort(SinglyLinkedList<Integer> l) {
        Node<Integer> current = l.head;
        for(int i = 0; i < l.size()-1; i++){
            Node<Integer> current2 = current.getNext();
            for(int j = 0; j < l.size()-i-1; j++){
              if(current.getElement() > current2.getElement()){
                int temp = current.getElement();
                current.setElement(current2.getElement());
                current2.setElement(temp);
              }
              current2 = current2.getNext();
            }
            current = current.getNext();
        }
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
class bubbleSort {
  public static void main(String[] args) {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
    list.addFirst(7);
    list.addLast(4);
    list.addLast(5);
    list.addLast(2);
    list.addLast(5);
    list.addLast(6);
    list.printList();
    SinglyLinkedList.bubbleSort(list);
    list.printList();
  }
}
