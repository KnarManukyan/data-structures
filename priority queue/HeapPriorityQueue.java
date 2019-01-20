import java.util.*;
interface Entry<K,V> {
  K getKey();
  V getValue();
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
class HeapPriorityQueue<K,V> extends AbstractPriorityQueue<K,V> {
  protected ArrayList<Entry<K,V>> heap = new ArrayList<>();
  public HeapPriorityQueue() { super(); }
  public HeapPriorityQueue(Comparator<K> comp) { super(comp); }
  public HeapPriorityQueue(K[] keys, V[] values) {
    super();
    for (int j=0; j < Math.min(keys.length, values.length); j++)
      heap.add(new PQEntry<>(keys[j], values[j]));
    heapify();
  }
  protected int parent(int j) { return (j-1) / 2; }    
  protected int left(int j) { return 2*j + 1; }
  protected int right(int j) { return 2*j + 2; }
  protected boolean hasLeft(int j) { return left(j) < heap.size(); }
  protected boolean hasRight(int j) { return right(j) < heap.size(); }
  protected void swap(int i, int j) {
    Entry<K,V> temp = heap.get(i);
    heap.set(i, heap.get(j));
    heap.set(j, temp);
  }
  protected void upheap(int j) {
    while (j > 0) {           
      int p = parent(j);
      if (compare(heap.get(j), heap.get(p)) >= 0) break;
      swap(j, p);
      j = p;                               
    }
  }
  protected void downheap(int j) {
    while (hasLeft(j)) {
      int leftIndex = left(j);
      int smallChildIndex = leftIndex;    
      if (hasRight(j)) {
          int rightIndex = right(j);
          if (compare(heap.get(leftIndex), heap.get(rightIndex)) > 0)
            smallChildIndex = rightIndex; 
      }
      if (compare(heap.get(smallChildIndex), heap.get(j)) >= 0)
        break;
      swap(j, smallChildIndex);
      j = smallChildIndex;                
    }
  }
  protected void heapify() {
    int startIndex = parent(size()-1);   
    for (int j=startIndex; j >= 0; j--)  
      downheap(j);
  }
  public int size() { return heap.size(); }
  public Entry<K,V> min() {
    if (heap.isEmpty()) return null;
    return heap.get(0);
  }
  public Entry<K,V> insert(K key, V value) throws IllegalArgumentException {
    checkKey(key);     
    Entry<K,V> newest = new PQEntry<>(key, value);
    heap.add(newest);
    upheap(heap.size() - 1);  
    return newest;
  }
  public Entry<K,V> removeMin() {
    if (heap.isEmpty()) return null;
    Entry<K,V> answer = heap.get(0);
    swap(0, heap.size() - 1);             
    heap.remove(heap.size() - 1);         
    downheap(0);                    
    return answer;
  }
  private void sanityCheck() {
    for (int j=0; j < heap.size(); j++) {
      int left = left(j);
      int right = right(j);
      if (left < heap.size() && compare(heap.get(left), heap.get(j)) < 0)
        System.out.println("Invalid left child relationship");
      if (right < heap.size() && compare(heap.get(right), heap.get(j)) < 0)
        System.out.println("Invalid right child relationship");
    }
  }
  public static void heapSort(List<Integer> al, Comparator<Integer> comp){
    HeapPriorityQueue<Integer, Integer> pq = new HeapPriorityQueue<>(comp);
    int size = al.size();
    for(int i = 0; i<size; i++){
        pq.insert(al.remove(0), null);
    }
    for(int i = 0; i<size; i++){
        al.add(pq.removeMin().getKey());
    }
  }
  public static void main(String[] args) {
    DefaultComparator<Integer> c = new DefaultComparator<Integer>();
    List<Integer> al1 = new ArrayList<>();
    al1.addAll(Arrays.asList(7, 4, 8, 2, 5, 3, 9));
    int size = al1.size();
    heapSort(al1, c);
    System.out.println("heap sort: ");
    for(int i = 0; i<size; i++){
         System.out.printf("%s ", al1.get(i));
    }
    System.out.println();
    System.out.println();
    List<Integer> al2 = new ArrayList<>();
    al2.addAll(Arrays.asList(5, 6, 4, 7, 3, 8, 2, 9, 1, 10));
    size = al2.size();
    heapSort(al2, c);
    for(int i = 0; i<size; i++){
         System.out.printf("%s ", al2.get(i));
    }
  }
}

// phase 1: insertion:
//    
//             tree                                              array
//    
//               5                                              [5]
//               
//               5                                             [5,6]
//             /
//            6
//    
//               4                                             [4,5,6]
//             /   \
//            6     5
//    
//               4                                            [4,5,6,7]
//             /   \
//            6     5
//           /
//          7
//    
//               3                                            [3,4,5,7,6]
//             /   \
//            4     5
//           / \
//          7   6
//    
//               3                                           [3,4,5,7,6,8]
//             /   \
//            4     5
//           / \   /
//          7   6 8
//    
//               2                                           [2,4,3,7,6,8,5]
//             /   \
//            4     3
//           / \   / \
//          7   6 8   5
//    
//               1                                           [1,2,3,4,6,8,5,9]
//             /   \
//            2     3
//           / \   / \
//          4   6 8   5
//         /
//        9  
//    
//               1                                          [1,2,3,4,6,8,5,9,7]
//             /   \
//            2     3
//           / \   / \
//          4   6 8   5
//         /\
//        9  7
//    
//               1                                         [1,2,3,4,6,8,5,9,7,10]
//             /   \
//            2     3
//           / \   / \
//          4   6 8   5
//         /\  /
//        9 7 10
//    
//     phase 2: removal:
//    
//              tree                     array                        result
//    
//               2                [2,4,3,7,6,8,5,9,10]                 [1]
//             /   \
//            4     3
//           / \   / \
//          7   6 8   5
//         /\ 
//        9 10 
//    
//               3                 [3,4,5,7,6,8,10,9]                  [1,2]
//             /   \
//            4     5
//           / \   / \
//          7   6 8   10
//         / 
//        9  
//    
//               4                  [4,6,5,7,9,8,10]                  [1,2,3]
//             /   \
//            6     5
//           / \   / \
//          7   9 8   10
//      
//               5                   [5,6,8,7,9,10]                  [1,2,3,4]
//             /   \
//            6     8
//           / \   /
//          7   9 10 
//      
//               6                    [6,7,8,10,9]                  [1,2,3,4,5]
//             /   \
//            7     8
//           / \
//          10  9 
//         
//               7                     [7,9,8,10]                  [1,2,3,4,5,6]
//             /   \
//            9     8
//           /
//          10  
//        
//               8                      [8,9,10]                  [1,2,3,4,5,6,7]
//             /   \
//            9     10
//        
//               9                       [9,10]                  [1,2,3,4,5,6,7,8]
//             /
//            10
//             
//               10                       [10]                  [1,2,3,4,5,6,7,8,9]
//         
//                                         []                 [1,2,3,4,5,6,7,8,9,10]
//
//
// worst case will run in O(nlogn), best case will be again O(nlogn)
// insertion is O(logn), for n elements it will be O(nlogn)
// removal is also O(nlogn)
// it's O(logn) as we will go only with a path with maximum size of height of the  tree
// In the case  when the input is in increasing order the building of the tree is O(n), 
// but removing is again O(nlogn)