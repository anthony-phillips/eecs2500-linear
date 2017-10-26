import java.util.Stack;
import java.util.Scanner;

class JavaStack {

   public static void main(String[] args) {
      Stack<String> stack = new Stack<String>();
      
      Scanner stdIn = new Scanner(System.in);

      System.out.println("Enter 'DONE' to terminate.");
      System.out.println("Please enter words:");
      
      while (true) {
         String word = stdIn.nextLine();

         if (word.compareToIgnoreCase("DONE") == 0)
            break;

         stack.push(word);
      }

      System.out.println();
      System.out.println("Entered words:");
      while (!stack.isEmpty())
         System.out.println(stack.pop());

      stdIn.close();
   }

}