// This program uses a stack to store user provided words
// and print them out in reverse order
// Anthony Phillips
// 10/26/2017
import java.util.Stack;
import java.util.Scanner;

class JavaStack {

   public static void main(String[] args) {
      Stack<String> stack = new Stack<String>();
      
      Scanner stdIn = new Scanner(System.in);

      System.out.println("Enter 'DONE' to terminate.");
      System.out.println("Please enter words:");
      
      // Get the words with "DONE" as a sentinel
      while (true) {
         String word = stdIn.nextLine();

         if (word.compareToIgnoreCase("DONE") == 0)
            break;

         stack.push(word);
      }

      // Print words by popping them from the stack
      System.out.println();
      System.out.println("Entered words:");
      while (!stack.isEmpty())
         System.out.println(stack.pop());

      stdIn.close();
   }

}