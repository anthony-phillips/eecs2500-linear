// This class is some sort of wrapper for an array?
// This kinda works somehow.
// Anthony Phillips
// 09/21/2017

public class Bag {

   private int[] data;
   private int manyItems;

   public Bag() {
      data = new int[1000];
      manyItems = 0;
   }

   // This is probably the second-worst function I've ever written.
   public void add(int newEntry) {
      if (data.length == manyItems) {
         int[] newArray = new int[manyItems + 1];
         
         for (int i = 0; i < manyItems; i++)
            newArray[i] = data[i];

         data = newArray;
      }

      data[manyItems++] = newEntry; 
   }

   // This is literally the worst function I've ever written.
   public boolean remove(int target) {
      int[] newArray = new int[manyItems-1];
      int indexOfTarget = 0;

      for (int i = 0; i <= manyItems; i++) {
         if (i == manyItems)
            return false;

         if (data[i] == target) {
            indexOfTarget = i;
            break;
         } else
            newArray[i] = data[i];
      }

      for (int j = indexOfTarget, i = ++indexOfTarget; i < manyItems; i++)
         newArray[j] = data[i];

      manyItems--;
      data = newArray;
      return true;
   }

   public int size() { return manyItems; }

   public int countOccurrences(int target) {
      int countOccurrences = 0;
      for (int i : data) {
         if (i == target)
            countOccurrences++;
      }
      return countOccurrences;
   }
}