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
  public static void insertionSort(SinglyLinkedList<Integer> l) {
        Node<Integer> current = l.head.getNext();
        Node<Integer> prev = l.head;
        for(int i = 1; i < l.size(); i++){
          Node<Integer> current2 = l.head;
          Node<Integer> prev2 = null;
            for(int j = 0; j < i; j++){
              if(current.getElement() < current2.getElement()){
                Node<Integer> next = current2.getNext();
                current2.setNext(current.getNext());
                if(next != current){
                  current.setNext(next);
                } else {
                  current.setNext(current2);
                }
                if(prev != current2){
                  prev.setNext(current2);
                }
                if(prev2!=null){
                  prev2.setNext(current);
                }
                break;
              }
              prev2 = current2;
              current2 = current2.getNext();
            }
            prev = current;
            current = current.getNext();
        }
    }
  public static void printList (SinglyLinkedList<Integer> l) {
    Node<Integer> current = l.head;
    for(int i = 0; i < l.size-1; i++){
      System.out.print(current.getElement() + " -> ");
      current = current.getNext();
    }
    System.out.print(current.getElement());
    System.out.println();
  }
}
class InsertionSort{
  public static void main(String[] args) {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
    list.addFirst(1);
    list.addLast(4);
    list.addLast(5);
    list.addLast(2);
    list.addLast(5);
    list.addLast(6);
    SinglyLinkedList.insertionSort(list);
    SinglyLinkedList.printList(list);
  }
}
