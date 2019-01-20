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
class ArrayDeque<E> implements Deque<E> {
 private E[] data;
 private int f = 0; //index of first element 
 private int size = 0;
 public ArrayDeque() { 
  data = (E[]) new Object[1]; //this implementation in the constructor creates an array with initial length 1 
 }
 public int size() { return size; }
 public boolean isEmpty() { return (size == 0); }
 protected void resizeFromStart() {
  E[ ] temp = (E[ ]) new Object[2*data.length];
  //copying elements in the second half of the array
  for (int k=data.length; k < 2*data.length; k++){ 
    temp[k] = data[k-data.length];
  }
  f=data.length; // the index of the first element will be equal to the size of initial array
  data = temp;
 }
 protected void resizeFromEnd() {
  //copying elements in the beginning of the array
  E[ ] temp = (E[ ]) new Object[2*data.length];
  for (int k=0; k < data.length; k++){
    temp[k] = data[k];
  }
  data = temp;
 }
 public E first() {
  if (isEmpty()) return null;
  return data[f];
 }
 public E last() {
  if (isEmpty()) return null;
  return data[size+f-1];
 }
 public void addFirst(E e) {
  if (size == data.length || f == 0) resizeFromStart();
  f--; 
  data[f] = e;
  size++;
 }
 public void addLast(E e) {
  if (data[data.length-1] != null) resizeFromEnd();
  data[size+f] = e;
  size++;
 }
 public E removeFirst() {
  if (isEmpty()) return null;
  E answer = data[f];
  data[f] = null;
  ++f;
  size--;
  return answer;
 }
 public E removeLast() {
  if (isEmpty()) return null;
  E answer = data[size+f-1];
  data[size+f-1] = null;
  size--;
  return answer;
 }
 public void print() {
   for(int i = 0; i<data.length; i++){
     System.out.println(data[i]);
   }
 }
   public static void main(String[] args) {
    System.out.println("The queue implemented with an unlimited array");
    System.out.println();
    ArrayDeque<Integer> a = new ArrayDeque<>();
    a.addFirst(8);
    a.addFirst(6);
    a.addFirst(5);
    a.addFirst(4);
    a.addLast(7);
    a.addLast(10);
    a.addLast(15);
    a.addLast(3);
    a.addLast(2);
    a.addLast(1);
    a.addLast(0);
    a.removeFirst();
    a.removeLast();
    a.print();
  }
}

// All dequeue functions are runnign at O(1) running time.
// The space usage of doubly linked list deque is O(n).
// n elements -> n nodes (if the node will be deleted the garbage collector will delete that node from memory)
// for array deque uses an array with the size 0 to 3n
// (as the array can be resized from the start and the end) 
