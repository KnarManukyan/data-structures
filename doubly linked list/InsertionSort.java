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
    header.getNext().setPrev(null); 
    size--;
    if (size == 0)
    trailer.prev = null;
    return answer;
  }
  public static void insertionSort(DoublyLinkedList<Integer> l) {
        Node<Integer> current = l.header.getNext().getNext();
        int currentElement = current.getElement();
        for(int i = 1; i < l.size(); i++){
          Node<Integer> current2 = current.getPrev();
            int j = i-1;
            while(j>=0 && currentElement < current2.getElement()){
              current2.getNext().setElement(current2.getElement());
              current2 = current2.getPrev();
              j--;
            }
            Node<Integer> helper;
            if(current2 == null){
              helper = l.header.getNext();
            } else {
              helper = current2.getNext();
            }
            helper.setElement(currentElement);
            if(i != l.size()-1){
              current = current.getNext();
              currentElement = current.getElement();
            }
        }
    }
  public void printList () {
    Node<E> current = header.getNext();
    for(int i = 0; i < size-1; i++){
      System.out.print(current.getElement() + " -> ");
      current = current.getNext();
    }
    System.out.print(current.getElement());
    System.out.println();
  }
  public void printListBackwards () {
    Node<E> current = trailer.getPrev();
    for(int i = size-1; i > 0; i--){
      System.out.print(current.getElement() + " <- ");
      current = current.getPrev();
    }
    System.out.print(current.getElement());
    System.out.println();
  }
}
class InsertionSort {
  public static void main(String[] args) {
    DoublyLinkedList<Integer> list = new DoublyLinkedList<Integer>();
    list.addFirst(7);
    list.addLast(4);
    list.addLast(5);
    list.addLast(2);
    list.addLast(5);
    list.addLast(6);
    list.addLast(8);
    list.addLast(154);
    list.addLast(5649);
    list.addLast(26);
    list.addLast(16);
    list.addLast(9);
    list.addLast(15);
    list.printList();
    list.printListBackwards();
    DoublyLinkedList.insertionSort(list);
    list.printList();
  }
}
