package Stats;

import java.util.LinkedList;

public class QuickSort
{
  //--------------v  HELPER METHODS  v--------------
  //swap values at indices x, y in array o
  public static void swap( int x, int y, LinkedList<Integer> o )
  {
    int tmp = o.get(x);
    o.set(x, o.get(y));
    o.set(y, tmp);
  }

  /**
   * void qsort(int[])
   * @param d -- array of ints to be sorted in place
   */
  public static void qsort( LinkedList<Integer> d )
  {
    qSortHelpr(d, 0, d.size()-1);
  }


  /***
   * void qSortHelpr(int[], int, int)
   * @param arr -- array of ints to be sorted in place
   * @param loPos -- lower bound for partition range
   * @param hiPos -- upper bound for partition range
   ***/
  public static void qSortHelpr( LinkedList<Integer> arr, int loPos, int hiPos ) {
    if ( loPos < hiPos ) {
      int pvtPos = partition(arr, loPos, hiPos);
      qSortHelpr(arr, loPos, pvtPos-1);
      qSortHelpr(arr, pvtPos+1, hiPos);
    }
  }


  /**
   * int partition(int[], int, int, int)
   * DESCRIP
   *
   * @param arr    input array of ints
   * @param loPos  leftmost index
   * @param hiPos  rightmost index
   * @return int   position of pivot
   */
  public static int partition( LinkedList<Integer> arr, int loPos, int hiPos )
  {
    //pick random pivot
    int pvtPos = loPos + (int)(Math.random() * (hiPos-loPos));

    int pvtVal = arr.get(pvtPos);

    swap( pvtPos, hiPos, arr);

    int s = loPos;

    for( int i = loPos; i < hiPos; i++ ) {
      if ( arr.get(i) <= pvtVal ) {
        swap( i, s, arr );
        s++;
      }
    }
    swap(s, hiPos, arr);

    return s; //return pos where pvt landed
  }//end partition

  public static void printArr( LinkedList<Integer> a )
  {
    for ( int o : a )
      System.out.print( o + " " );
    System.out.println();
  }
  //main method for testing
  public static void main( String[] args )
  {

    /*~~~~s~l~i~d~e~~~m~e~~~d~o~w~n~~~~~~~~~~~~~~~~~~~~ (C-k, C-k, C-y) 
    //get-it-up-and-running, static test case:
    int [] arr1 = {7,1,5,12,3};
    System.out.println("\narr1 init'd to: " );
    printArr(arr1);
    qsort( arr1 );
    System.out.println("arr1 after qsort: " );
    printArr(arr1);
    // randomly-generated arrays of n distinct vals
    int[] arrN = new int[10];
    for( int i = 0; i < arrN.length; i++ )
    arrN[i] = i;
    System.out.println("\narrN init'd to: " );
    printArr(arrN);
    shuffle(arrN);
    System.out.println("arrN post-shuffle: " );
    printArr(arrN);
    qsort( arrN );
    System.out.println("arrN after sort: " );
    printArr(arrN);
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


    LinkedList<Integer> e = new LinkedList<Integer>();
    e.add(2);
    e.add(29);
    qsort(e);
    printArr(e);
    /*~~~~s~l~i~d~e~~~m~e~~~d~o~w~n~~~~~~~~~~~~~~~~~~~~ (C-k, C-k, C-y)
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

  }//end main

}//end class QuickSort