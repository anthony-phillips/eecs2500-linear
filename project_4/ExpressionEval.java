import java.util.EmptyStackException;
import java.util.Scanner;

class ExpressionEval {

   public static void main(String[] args) {
      Scanner stdIn = new Scanner(System.in);
      char[] tokens;
      MyStack<Character> operators = new MyStack<Character>();
      MyStack<Integer>   operands  = new MyStack<Integer>();
      
      System.out.print("Please enter an expression: ");
      tokens = stdIn.nextLine().toCharArray();
      stdIn.close();

      boolean prevTokenWasDigit = false;
      for (int i = 0; i < tokens.length; i++) {
         char token = tokens[i];

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

            if (prevTokenWasDigit)
               currTokenVal += (operands.pop() * 10);

            operands.push(currTokenVal);
            prevTokenWasDigit = true;
         } else {
            while (!operators.empty() && 
                   compareOperator(operators.peek(), token) >= 0) {
               char operator = operators.pop();
               int  val2     = operands.pop();
               int  val1     = operands.pop();

               operands.push(evaluate(val1, operator, val2));
            }

            operators.push(token);

            if (operators.size() > operands.size())
               break;
            
            prevTokenWasDigit = false;
         }
      }

      if      (operators.size() > operands.size() - 1)
         System.out.println("Incorrectly formed expression: Extra operator.");
      else if (operators.size() < operands.size() - 1)
         System.out.println("Incorrectly formed expression: Extra operand.");
      else {
         while (!operators.empty()) {
            char operator = operators.pop();
            int  val2     = operands.pop();
            int  val1     = operands.pop();

            operands.push(evaluate(val1, operator, val2));
         }

         System.out.println(operands.pop());
      }
   }

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

   private static int compareOperator(char operator1, char operator2) {
      int op1Precedence = getOperatorPrecedence(operator1);
      int op2Precedence = getOperatorPrecedence(operator2);
      
      return Integer.compare(op1Precedence, op2Precedence);
   }

   private static int getOperatorPrecedence(char operator) {
      switch (operator) {
         case '*':
            return 1;
         case '+': 
         case '-': 
         default :
            return 0;
      }
   }

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

   private static class MyStack<E> {

      public MyStack() {
         this.array = (E[]) new Object[0];
      }

      private E[] array;

      public int size() { return this.array.length; }

      public boolean empty() { return this.array.length == 0; }

      public void push(E element) {
         E[] newArray = (E[]) new Object[this.array.length + 1];

         newArray[0] = element;

         for (int i = 1; i < newArray.length; i++)
            newArray[i] = array[i-1];

         this.array = newArray;
      }

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

      public E peek()
      throws EmptyStackException{
         if (this.array.length == 0)
            throw new EmptyStackException();

         return this.array[0];
      }
   }
}