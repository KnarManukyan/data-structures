import java.util.*;
class ReverseComparator<E> implements Comparator<E> {
  public int compare(E a, E b) throws ClassCastException {
    return ((Comparable<E>) b).compareTo(a);
  }
}
class InPlaceHeapSort{
  public static void swap(List<Integer> al, int i, int j){
    int temp1 = al.get(i);
    al.set(i, al.get(j));
    al.set(j, temp1);
  }
  public static void heapify(List<Integer> al, Comparator<Integer> comp, int i){
    while(i>=0){
      if(comp.compare(al.get(i), al.get((i-1)/2))<0){
        swap(al, i, (i-1)/2);
      } else break;
      i = (i-1)/2;
    }
  }
  public static void removeMin(List<Integer> al, Comparator<Integer> comp, int i){
    int max = al.remove(0);
    al.add(0, al.remove(al.size()-i-1));
    al.add(al.size()-i,max);
    int k = al.size()-i;
    i = 0;
    while(i<(k-2)/2){
      if(comp.compare(al.get(2*i+1), al.get(2*i+2))<0){
        if(comp.compare(al.get(2*i+1), al.get(i))<0){
          swap(al, i, 2*i+1);
        }
      } else if(comp.compare(al.get(2*i+1), al.get(2*i+2))>0){
        if(comp.compare(al.get(2*i+2), al.get(i))<0){
          swap(al, i, 2*i+2);
        }
      }
      else break;
      i = 2*i+1;
    }
  }
  public static void heapSort(List<Integer> al, Comparator<Integer> comp){
    int size = al.size();
    for(int i = 0; i<size; i++){
        heapify(al,comp,i);
    }
    for(int i = 0; i<size-1; i++){
        removeMin(al, comp, i);
    }
  }
  public static void main(String[] args) {
    ReverseComparator<Integer> c = new ReverseComparator<Integer>();
    List<Integer> al1 = new ArrayList<>();
    al1.addAll(Arrays.asList(7, 4, 8, 2, 5, 3, 9));
    int size = al1.size();
    heapSort(al1, c);
    System.out.println("heap sort: ");
    for(int i = 0; i<size; i++){
         System.out.printf("%s ", al1.get(i));
    }
    List<Integer> al2 = new ArrayList<>();
    al2.addAll(Arrays.asList(5, 6, 4, 7, 3, 8, 2, 9, 1, 10));
    size = al2.size();
    heapSort(al2, c);
    System.out.println();
    System.out.println();
    for(int i = 0; i<size; i++){
         System.out.printf("%s ", al2.get(i));
    }
    System.out.println();
  }
}

// running time is the same, as this implemantation uses the same logic to insert and remove element from heap. So it is O(nlogn)
//
// space requirment with heapSort O(n), as we use memory to create a new heap of n elements
// space requirment with in-place heapSort O(1), as the heap is created inside the list