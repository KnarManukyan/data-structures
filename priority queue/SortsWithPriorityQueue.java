import java.util.*;
interface Entry<K,V> {
  K getKey();
  V getValue();
}
interface Position<E> {
  E getElement() throws IllegalStateException;
}
class LinkedPositionalList<E> implements Iterable{
  private static class Node<E> implements Position<E>{
    private E element;
    private Node<E> prev;
    private Node<E> next;
    public Node(E e, Node<E> p, Node<E> n) {
      element = e;
      prev = p;
      next = n;
    }
    public E getElement() throws IllegalStateException {
      if (next == null)
        throw new IllegalStateException("Position no longer valid");
      return element;
    }
    public Node<E> getPrev() {
      return prev;
    }
    public Node<E> getNext() {
      return next;
    }
    public void setElement(E e) {
      element = e;
    }
    public void setPrev(Node<E> p) {
      prev = p;
    }
    public void setNext(Node<E> n) {
      next = n;
    }
  }
  private Node<E> header;
  private Node<E> trailer;
  private int size = 0;
  public LinkedPositionalList() {
    header = new Node<>(null, null, null);
    trailer = new Node<>(null, header, null);
    header.setNext(trailer);
  }
  private Node<E> validate(Position<E> p) throws IllegalArgumentException {
    if (!(p instanceof Node)) throw new IllegalArgumentException("Invalid p");
    Node<E> node = (Node<E>) p;
    if (node.getNext() == null)
      throw new IllegalArgumentException("p is no longer in the list");
    return node;
  }
  private Position<E> position(Node<E> node) {
    if (node == header || node == trailer)
      return null;
    return node;
  }
  public int size() { return size; }
  public boolean isEmpty() { return size == 0; }
  public Position<E> first() {
    return position(header.getNext());
  }
  public Position<E> last() {
    return position(trailer.getPrev());
  }
  public Position<E> before(Position<E> p) throws IllegalArgumentException {
    Node<E> node = validate(p);
    return position(node.getPrev());
  }
  public Position<E> after(Position<E> p) throws IllegalArgumentException {
    Node<E> node = validate(p);
    return position(node.getNext());
  }
  private Position<E> addBetween(E e, Node<E> pred, Node<E> succ) {
    Node<E> newest = new Node<>(e, pred, succ);
    pred.setNext(newest);
    succ.setPrev(newest);
    size++;
    return newest;
  }
  public Position<E> addFirst(E e) {
    return addBetween(e, header, header.getNext());
  }
  public Position<E> addLast(E e) {
    return addBetween(e, trailer.getPrev(), trailer);
  }
  public Position<E> addBefore(Position<E> p, E e)
                                throws IllegalArgumentException {
    Node<E> node = validate(p);
    return addBetween(e, node.getPrev(), node);
  }
  public Position<E> addAfter(Position<E> p, E e)
                                throws IllegalArgumentException {
    Node<E> node = validate(p);
    return addBetween(e, node, node.getNext());
  }
  public E set(Position<E> p, E e) throws IllegalArgumentException {
    Node<E> node = validate(p);
    E answer = node.getElement();
    node.setElement(e);
    return answer;
  }
  public E remove(Position<E> p) throws IllegalArgumentException {
    Node<E> node = validate(p);
    Node<E> predecessor = node.getPrev();
    Node<E> successor = node.getNext();
    predecessor.setNext(successor);
    successor.setPrev(predecessor);
    size--;
    E answer = node.getElement();
    node.setElement(null);
    node.setNext(null);
    node.setPrev(null);
    return answer;
  }
  private class PositionIterator implements Iterator<Position<E>> {
    private Position<E> cursor = first();
    private Position<E> recent = null;
    public boolean hasNext() { return (cursor != null);  }
    public Position<E> next() throws NoSuchElementException {
      if (cursor == null) throw new NoSuchElementException("nothing left");
      recent = cursor;
      cursor = after(cursor);
      return recent;
    }
    public void remove() throws IllegalStateException {
      if (recent == null) throw new IllegalStateException("nothing to remove");
      LinkedPositionalList.this.remove(recent);
      recent = null;
    }
  }
  private class PositionIterable implements Iterable<Position<E>> {
    public Iterator<Position<E>> iterator() { return new PositionIterator(); }
  }
  public Iterable<Position<E>> positions() {
    return new PositionIterable();
  }
  private class ElementIterator implements Iterator<E> {
    Iterator<Position<E>> posIterator = new PositionIterator();
    public boolean hasNext() { return posIterator.hasNext(); }
    public E next() { return posIterator.next().getElement(); }
    public void remove() { posIterator.remove(); }
  }
  public Iterator<E> iterator() { return new ElementIterator(); }
}
class DefaultComparator<E> implements Comparator<E> {
  public int compare(E a, E b) throws ClassCastException {
    return ((Comparable<E>) a).compareTo(b);
  }
}
interface PriorityQueue<K,V> {
  int size();
  boolean isEmpty();
  Entry<K,V> insert(K key, V value) throws IllegalArgumentException;
  Entry<K,V> min();
  Entry<K,V> removeMin();
}
abstract class AbstractPriorityQueue<K,V> implements PriorityQueue<K,V> {
  protected static class PQEntry<K,V> implements Entry<K,V> {
    private K k;
    private V v;
    public PQEntry(K key, V value) {
      k = key;
      v = value;
    }
    public K getKey() { return k; }
    public V getValue() { return v; }
    protected void setKey(K key) { k = key; }
    protected void setValue(V value) { v = value; }
  }
  private Comparator<K> comp;
  protected AbstractPriorityQueue(Comparator<K> c) { comp = c; }
  protected AbstractPriorityQueue() { this(new DefaultComparator<K>()); }
  protected int compare(Entry<K,V> a, Entry<K,V> b) {
    return comp.compare(a.getKey(), b.getKey());
  }
  protected boolean checkKey(K key) throws IllegalArgumentException {
    try {
      return (comp.compare(key,key) == 0);
    } catch (ClassCastException e) {
      throw new IllegalArgumentException("Incompatible key");
    }
  }
  public boolean isEmpty() { return size() == 0; }
}
class SortedPriorityQueue<K,V> extends AbstractPriorityQueue<K,V> {
  private LinkedPositionalList<Entry<K,V>> list = new LinkedPositionalList<Entry<K,V>>();
  public SortedPriorityQueue() { super(); }
  public SortedPriorityQueue(Comparator<K> comp) { super(comp); }
  public Entry<K,V> insert(K key, V value) throws IllegalArgumentException {
    checkKey(key);    // auxiliary key-checking method (could throw exception)
    Entry<K,V> newest = new PQEntry<>(key, value);
    Position<Entry<K,V>> walk = list.last();
    // walk backward, looking for smaller key
    while (walk != null && compare(newest, walk.getElement()) < 0)
      walk = list.before(walk);
    if (walk == null)
      list.addFirst(newest);                   // new key is smallest
    else
      list.addAfter(walk, newest);             // newest goes after walk
    return newest;
  }
  public Entry<K,V> min() {
    if (list.isEmpty()) return null;
    return list.first().getElement();
  }
  public Entry<K,V> removeMin() {
    if (list.isEmpty()) return null;
    return list.remove(list.first());
  }
  public int size() { return list.size(); }
}
class UnsortedPriorityQueue<K,V> extends AbstractPriorityQueue<K,V> {
  private LinkedPositionalList<Entry<K,V>> list = new LinkedPositionalList<>();
  public UnsortedPriorityQueue() { super(); }
  public UnsortedPriorityQueue(Comparator<K> comp) { super(comp); }
  private Position<Entry<K,V>> findMin() {
    Position<Entry<K,V>> small = list.first();
    for (Position<Entry<K,V>> walk : list.positions())
      if (compare(walk.getElement(), small.getElement()) < 0)
        small = walk;
    return small;
  }
  public Entry<K,V> insert(K key, V value) throws IllegalArgumentException {
    checkKey(key);  
    Entry<K,V> newest = new PQEntry<>(key, value);
    list.addLast(newest);
    return newest;
  }
  public Entry<K,V> min() {
    if (list.isEmpty()) return null;
    return findMin().getElement();
  }
  public Entry<K,V> removeMin() {
    if (list.isEmpty()) return null;
    return list.remove(findMin());
  }
  public int size() { return list.size();}
}
class SortsWithPriorityQueue {
  public static void selectionSortWithPQ(List<Integer> al, Comparator<Integer> comp){
    UnsortedPriorityQueue<Integer, Integer> pq = new UnsortedPriorityQueue<>(comp);
    int size = al.size();
    for(int i = 0; i<size; i++){
         pq.insert(al.remove(0), null);
    }
    for(int i = 0; i<size; i++){
         al.add(pq.removeMin().getKey());
    }
  }
  public static void insertionSortWithPQ(List<Integer> al, Comparator<Integer> comp){
    SortedPriorityQueue<Integer, Integer> pq = new SortedPriorityQueue<>(comp);
    int size = al.size();
    for(int i = 0; i<size; i++){
         pq.insert(al.remove(0), null);
    }
    for(int i = 0; i<size; i++){
         al.add(pq.removeMin().getKey());
    }
  }
   public static void main(String[] args){
    DefaultComparator<Integer> c = new DefaultComparator<Integer>();
    List<Integer> al1 = new ArrayList<>();
    al1.addAll(Arrays.asList(7, 4, 8, 2, 5, 3, 9));
    int size = al1.size();
    selectionSortWithPQ(al1, c);
    System.out.println("insertion sort: ");
    for(int i = 0; i<size; i++){
         System.out.printf("%s ", al1.get(i));
    }
    List<Integer> al2 = new ArrayList<>();
    al2.addAll(Arrays.asList(7, 4, 8, 2, 5, 3, 9));
    System.out.println();
    insertionSortWithPQ(al2, c);
    System.out.println("selection sort: ");
    for(int i = 0; i<size; i++){
         System.out.printf("%s ", al2.get(i));
    }
    System.out.println();
  }
}

// selection best case: O(n^2), worst case O(n^2)
// the insertion in the unsorted priority queue is O(1)
// the removal from the unsorted priority queue is O(n) for an element, so for n
// element it will be O(n^2)
// So total O(n^2) in any case
//
// insetion best case O(n), worst case O(n^2)
// the insertion in the sorted priority queue is O(n) in worst case and O(1) 
// in the best case for an element, so for n elements worst case O(n^2) and best // case O(n)
// the removal from the sorted priority queue is O(1)