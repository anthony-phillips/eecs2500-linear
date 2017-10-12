// This program generates a pair of dice, rolls them, and displays the faces.
// Anthony Phillips
// 09/14/2017
public class Classes {

   public static void main(String[] args) {
      Die[] dice = new Die[2];

      // Generate / roll dice
      for (int i = 0; i < dice.length; i++) {
         dice[i] = new Die();
         dice[i].roll();
      }

      // Display dice
      for (Die die : dice)
         die.printFace();

   }

   public static class Die {
      private int value;

      public Die() { this.value = -1; }

      // "Rolls" the die, setting the value to a random number
      public void roll() {
         int randomInt = (int)(Math.random() * 5) + 1;
         this.value = randomInt;
      }

      // Prints the current value of the die in ASCII art
      public void printFace() {
         System.out.println();
         switch (this.value) {
            case 1:  System.out.println("     \n  *  \n     "); break;
            case 2:  System.out.println("*    \n     \n    *"); break;
            case 3:  System.out.println("*    \n  *  \n    *"); break;
            case 4:  System.out.println("*   *\n     \n*   *"); break;
            case 5:  System.out.println("*   *\n  *  \n*   *"); break;
            case 6:  System.out.println("* * *\n     \n* * *"); break;
            default: System.out.println("No value.");
         }
         System.out.println();
      }
   }
}
