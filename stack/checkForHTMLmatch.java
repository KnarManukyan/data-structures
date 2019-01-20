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
    public int size( ) { return size; }
    public boolean isEmpty( ) { return size == 0; }
  public E first( ) {
    if (isEmpty( )) return null;
    return head.getElement( );
  }
  public void addFirst(E e) { 
    head = new Node<>(e, head); 
    if (size == 0)
    tail = head; 
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
 }
interface Stack<E> {
  int size( );
  boolean isEmpty( );
  void push(E e);
  E top( );
  E pop( );
 }
 
class LinkedStack<E> implements Stack<E> {
 private SinglyLinkedList<E> list = new SinglyLinkedList<>( );
 public LinkedStack( ) { }
 public int size( ) { return list.size( ); }
 public boolean isEmpty( ) { return list.isEmpty( ); }
 public void push(E element) { list.addFirst(element); }
 public E top( ) { return list.first( ); }
 public E pop( ) { return list.removeFirst( ); }
 public static <E> Stack<E> reverse(Stack<E> s){
   Stack<E> helper = new LinkedStack<>();
   int n = s.size();
   for(int i = 0; i<n; i++){
     helper.push(s.top());
     s.pop();
   }
   return helper;
 }
 }
class checkForHTMLmatch {
  public static boolean check(String input){
    Stack<Character> stack = new LinkedStack<>();
    for(int i = 0; i < input.length(); i++){
      if(input.charAt(i) == '<' && input.charAt(i+1) == '/'){
        if(stack.top() == '>'){
          stack.push('/');
        } else{
          return false;
        }
      } else if(input.charAt(i) == '<'){
        stack.push('<');
      } else if(input.charAt(i) == '>'){
        if(stack.top() == '<'){
          stack.push('>');
        } else if(stack.top() == '/'){
          stack.pop();
          stack.pop();
          stack.pop();
        } else{
          return false;
        }
      } 
    }
    return stack.isEmpty();
  }
  public static void main(String[] args) {
    String input = "<body><center><h1> The Little Boat </h1></center><p> The storm tossed the little boat like a cheap sneaker in an old washing machine. The three drunken fishermen were used to such treatment, of course, but not the tree salesman, who even as a stowaway now felt that he had overpaid for the voyage. </p> <ol> <li> Will the salesman die? </li> <li> What color is the boat? </li> <li> And what about Naomi? </li> </ol> </body>";
    System.out.println(check(input));
  }
}
