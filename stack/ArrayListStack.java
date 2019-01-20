class ArrayList<E> {
 public static final int CAPACITY=16;
 private E[ ] data;
 private int size = 0; 
 public ArrayList() { this(CAPACITY); } 
 public ArrayList(int capacity) { 
  data = (E[ ]) new Object[capacity];
 }
 public int size() { return size; }
 public boolean isEmpty() { return size == 0; }
 public E get(int i) throws IndexOutOfBoundsException {
  checkIndex(i, size);
  return data[i];
 }
 protected void resize() {
  E[ ] temp = (E[ ]) new Object[2*size];
  for (int k=0; k < size; k++)
  temp[k] = data[k];
  data = temp;
}
  public void add(int i, E e){
    checkIndex(i, size + 1);
    if (size == data.length) resize();
    for (int k=size-1; k >= i; k--) 
    data[k+1] = data[k];
    data[i] = e;
    size++;
 }
 public E remove(int i) throws IndexOutOfBoundsException {
 checkIndex(i, size);
    E temp = data[i];
    for (int k=i; k < size-1; k++)
    data[k] = data[k+1];
    data[size-1] = null;
    size--;
    return temp;
 }
 protected void checkIndex(int i, int n) throws IndexOutOfBoundsException {
  if (i < 0 || i >= n)
  throw new IndexOutOfBoundsException("Illegal index: " + i);
 }
}

interface Stack<E> {
  int size();
  boolean isEmpty();
  void push(E e);
  E top();
  E pop();
}
class ArrayListStack<E> implements Stack<E> {
 private ArrayList<E> arr = new ArrayList<>();
 public int size() { return arr.size(); }
 public boolean isEmpty() { return arr.isEmpty(); }
 public void push(E element) {arr.add(size(), element); }
 public E top() { return arr.get(arr.size()-1); }
 public E pop() { return arr.remove(arr.size()-1); }
 public static void main(String[] args) {
    Stack<Integer> a = new ArrayListStack<>();
    a.push(5);
    a.push(4);
    a.push(3);
    a.push(2);
    a.push(1);
    int n = a.size();
    for(int i = 0; i < n; i++){
      System.out.println(a.pop());
    }
  }
}
/* the initial size is 1. So from the first push the size will be doubled.
   the amortized running time for this strategy is O(1), which is more optimal then the incremental strategy(O(n))
*/