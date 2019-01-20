interface Queue<E> {
 int size();
 boolean isEmpty();
 void enqueue(E e);
 E first();
 E dequeue();
 }
class ArrayQueue<E> implements Queue<E> {
 private E[ ] data;
 private int f = 0; 
 private int size = 0;
 public ArrayQueue() { 
 data = (E[ ]) new Object[1];
 }
 public int size( ) { return size; }
 public boolean isEmpty( ) { return (size == 0); }
 protected void resize() {
  E[ ] temp = (E[ ]) new Object[2*size];
  for (int k=0; k < size; k++)
  temp[k] = data[k];
  data = temp;
}
 public void enqueue(E e) {
 if (size == data.length) resize();
 data[size+f] = e;
 size++;
 }
 public E first( ) {
 if (isEmpty( )) return null;
 return data[f];
 }
 public E dequeue( ) {
 if (isEmpty( )) return null;
 E answer = data[f];
 data[f++] = null;
 size--;
 return answer;
}
 public static void main(String[] args) {
    Queue<Integer> q = new ArrayQueue();
    q.enqueue(6);
    q.enqueue(5);
    q.enqueue(4);
    q.enqueue(3);
    q.enqueue(2);
    q.enqueue(1);
    q.dequeue();
    q.enqueue(6);
    System.out.println(q.dequeue());
    System.out.println(q.dequeue());
    System.out.println(q.dequeue());
    System.out.println(q.dequeue());
    System.out.println(q.dequeue());
    System.out.println(q.dequeue());
  }
}
/* the initial size is 1. So from the first enqueue the size will be doubled.
   the amortized running time for this strategy is O(1), which is more optimal then the incremental strategy(O(n))
*/