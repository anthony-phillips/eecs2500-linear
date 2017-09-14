// This program has a user guess a randomly generated secret number.
// Anthony Phillips
// 09/14/2017

import java.util.Scanner;

class SecretNumber {

   public static void main(String[] args) {
      int secret = (int)(Math.random()*99) + 1;
      Scanner stdIn = new Scanner(System.in);

      System.out.println("Guess the secret number. Enter 0 to exit.");
      while (true) {
         int guess = 0;

         System.out.print("Your guess: ");

         try {
            guess = stdIn.nextInt();
         } catch (Exception ex) {
            System.out.println("The secret number is an integer. Try again.");
            stdIn.nextLine();
            continue;
         }

         if (guess == 0) {
            System.out.println("User quit. Secret was " + secret);
            break;
         }

         if (guess < 1 || guess > 100){
            System.out.println("The secret number is within the range of 1-100."
               + " Try again.");
            continue;
         }

         if (guess != secret) {
            boolean isLow = secret - guess > 0 ? true : false;
            int difference = Math.abs(secret - guess);
            String intensifier;

            if (difference > 30)
               intensifier = "way too";
            else if (difference > 10)
               intensifier = "too";
            else
               intensifier = "a little";

            System.out.println("Your guess was " + intensifier
               + (isLow ? " low." : " high."));
         }

         if (guess == secret) {
            System.out.println("You guessed the secret number!");
            break;
         }
      }
   }
}
