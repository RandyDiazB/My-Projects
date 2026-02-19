/**
 * This program switches the locations of two different elements contained in a generic array.
 * 
 * @author Randy Diaz
 * @version 1 6/14/2025
 */

 public class PlaceSwap 
 {
     /**
      * Swaps the locations of two elements in a generic array.
      *
      * @param array  The array where the elements to be switched 
      * @param pointA The index position of the first element
      * @param pointB The index position of the second element
      */
     public static <S> void placeSwap(S[] array, int pointA, int pointB)
     {
         S temporaryPlace = array[pointA];
         array[pointA] = array[pointB];
         array[pointB] = temporaryPlace;
     }
 }
 