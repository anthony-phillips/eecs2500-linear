// This program evaluates a provided "simple infix expression"
// Anthony Phillips
// 10/26/2017

import java.util.EmptyStackException;
import java.util.Scanner;

class ExpressionEval {

   public static void main(String[] args) {
      Scanner stdIn = new Scanner(System.in);
      char[] tokens;
      MyStack<Character> operators = new MyStack<Character>();
      MyStack<Integer>   operands  = new MyStack<Integer>();
      
      // Grab expression
      System.out.print("Please enter an expression: ");
      tokens = stdIn.nextLine().toCharArray();
      stdIn.close();

      boolean prevTokenWasDigit = false;
      for (int i = 0; i < tokens.length; i++) {
         char token = tokens[i];

         // If an invalid token is found, quit
         if (!isValidToken(token)) {
            System.out.println("Invalid token: " + token);
            return;
         }

         if (token == ' ') {
            prevTokenWasDigit = false;
            continue;
         }

         if (Character.isDigit(token)) {
            int currTokenVal = Character.getNumericValue(token);

            // If the previous token was also a digit, add the current
            // token to it to complete the number
            if (prevTokenWasDigit)
               currTokenVal += (operands.pop() * 10);

            // Push the value of the current token to the operands stack
            operands.push(currTokenVal);
            prevTokenWasDigit = true;
         } else {
            // Evaluate as we go
            while (!operators.empty() && 
                   compareOperator(operators.peek(), token) >= 0) {
               char operator = operators.pop();
               int  val2     = operands.pop();
               int  val1     = operands.pop();

               operands.push(evaluate(val1, operator, val2));
            }

            // Push the operator to the operators stack
            operators.push(token);

            // If there are more operators than operands,
            // there is a problem; break now
            if (operators.size() > operands.size())
               break;
            
            prevTokenWasDigit = false;
         }
      }

      // If the expression was incorrectly formed,
      // determine how and alert the user
      if      (operators.size() > operands.size() - 1)
         System.out.println("Incorrectly formed expression: Extra operator.");
      else if (operators.size() < operands.size() - 1)
         System.out.println("Incorrectly formed expression: Extra operand.");
      else {
         // Finish our evaluation
         while (!operators.empty()) {
            char operator = operators.pop();
            int  val2     = operands.pop();
            int  val1     = operands.pop();

            operands.push(evaluate(val1, operator, val2));
         }

         // Print the answer to the evaluated expression
         System.out.println(operands.pop());
      }
   }

   // Evaluates an expression
   private static int evaluate(int operand1, char operator, int operand2) {
      switch (operator) {
         case '*':
            return operand1 * operand2;
         case '+':
            return operand1 + operand2;
         case '-':
            return operand1 - operand2;
         default:
            return 0;
      }
   }

   // Compares two given operators via their precedences
   private static int compareOperator(char operator1, char operator2) {
      int op1Precedence = getOperatorPrecedence(operator1);
      int op2Precedence = getOperatorPrecedence(operator2);
      
      return Integer.compare(op1Precedence, op2Precedence);
   }

   // Gives the precedence of an operator as an int either 1 or 0
   // If an unknown operator is submitted, returns -1
   private static int getOperatorPrecedence(char operator) {
      switch (operator) {
         case '*':
            return 1;
         case '+': 
         case '-':
            return 0;
         default :
            return -1;
      }
   }

   // Returns true if a token is a valid number / operator or false otherwise
   private static boolean isValidToken(char token) {
      if (Character.isDigit(token))
         return true;
      
      switch (token) {
         case '*':
         case '+':
         case '-':
         case ' ':
            return true;
         default : 
            return false;
      }
   }


   // Java's stack class is an extension of class Vector
   // and includes five operations for manipulating the stack
   // This class uses an array and implements four of the
   // five operations that java's stack class does
   // https://docs.oracle.com/javase/7/docs/api/java/util/Stack.html
   private static class MyStack<E> {

      public MyStack() { this.array = (E[]) new Object[0]; }

      // Internal array for storing stack objects
      private E[] array;

      // Returns the size of the stack
      public int size() { return this.array.length; }

      // Returns true if stack is empty
      public boolean empty() { return this.array.length == 0; }

      // Adds item to the stack
      public void push(E element) {
         E[] newArray = (E[]) new Object[this.array.length + 1];

         newArray[0] = element;

         for (int i = 1; i < newArray.length; i++)
            newArray[i] = array[i-1];

         this.array = newArray;
      }

      // Removes and returns item from top of stack
      public E pop()
      throws EmptyStackException {
         if (this.array.length == 0)
            throw new EmptyStackException();

         E element = this.array[0];

         E[] newArray = (E[]) new Object[this.array.length - 1];

         for (int i = 0; i < newArray.length; i++)
            newArray[i] = this.array[i+1];

         this.array = newArray;

         return element;
      }

      // Returns item from top of stack
      public E peek()
      throws EmptyStackException{
         if (this.array.length == 0)
            throw new EmptyStackException();

         return this.array[0];
      }
   }
}