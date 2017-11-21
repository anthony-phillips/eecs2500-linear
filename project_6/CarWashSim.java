import java.lang.Math;
import java.util.*;

public class CarWashSim {
   private static final int QUEUE_MAX_SIZE     = 8;
   private static final int WASH_MIN_DURATION  = 2;
   private static final int WASH_MAX_DURATION  = 5;
   private static final int ARRIVAL_MIN_OFFSET = 1;
   private static final int ARRIVAL_MAX_OFFSET = 5;

   public static void main(String[] args) {
      WashQueue serviceLine = new WashQueue();

      // Statistics counters
      int servedCount  = 0;
      int idleTime     = 0;
      int totalWait    = 0;
      int longestWait  = 0;
      int bypassCount  = 0;

      // User defined parameters
      int shiftLength  = 0;
      int openCarCount = 0;

      // Read in the number of minutes for the car wash shift
      Scanner stdIn = new Scanner(System.in);
      do {
         System.out.print("Please enter the shift length in minutes: ");
         try {
            shiftLength = Integer.parseUnsignedInt(stdIn.nextLine());
         } catch (Exception ex) {
            System.out.println("Shift length must be an unsigned integer.");
         }
      } while (shiftLength < 0);
      
      // Read in the number of customers waiting when the car wash opens
      do {
         System.out.print("Please enter the opening number of cars:  ");
         try {
            openCarCount = Math.min(Integer.parseUnsignedInt(stdIn.nextLine()),
                                    QUEUE_MAX_SIZE);
         } catch (Exception ex) {
            System.out.println("Open car count must be an unsigned integer.");
         }
      } while (shiftLength < 0);
      stdIn.close();

      // Fill the wash queue with n Car objects, where n = openCarCount
      for (int i = 0; i < openCarCount; i++)
         serviceLine.add(Car.random(0));

      // For each minute of the shift:
      Car currCar = null;
      int nextArrivalCD = (int)(Math.random() * ARRIVAL_MAX_OFFSET - 1)
                          + ARRIVAL_MIN_OFFSET;
      for (int currTime = 0; currTime < shiftLength; currTime++) {
         
         // If a new customer has arrived and there is room for them
         // in the queue, add them to the line. If there is no room,
         // they must bypass the line; increment the bypass counter.
         if (nextArrivalCD == 0) {
            if (!serviceLine.isFull())
               serviceLine.add(Car.random(currTime));
            else
               bypassCount++;
            // Schedule the arrival time for the next customer
            nextArrivalCD = (int)(Math.random() * ARRIVAL_MAX_OFFSET - 1)
                          + ARRIVAL_MIN_OFFSET;
         }

         // If the current car's wash has been completed,
         // clear the current car and increment the served counter.
         if (currCar != null) {
            if (currCar.completionTime == currTime) {
               currCar = null;
               servedCount++;
            }
         }

         // If there is no current car, check for a line. If there
         // is a line, grab the next car and perform necessary 
         // calculations. If no line is present, the car wash is
         // idle; increment the idle counter.
         if (currCar == null) {
            if (!serviceLine.isEmpty()){
               currCar = serviceLine.remove();

               // Check if new car had the longest wait in line
               int waitTime = currTime - currCar.arrivalTime;
               longestWait  = Math.max(waitTime, longestWait);
               totalWait   += waitTime;

               // Calculate the current car's completion time
               currCar.completionTime = currTime + currCar.washDuration;
            } else 
               idleTime++;
         }

         nextArrivalCD--;
      }

      // At the completion of the shift, report the gathered statistics
      System.out.println("Customers served     : "+servedCount);
      System.out.println("Carwash idle time    : "+idleTime);
      System.out.println("Average wait time    : "+totalWait/servedCount);
      System.out.println("Longest wait time    : "+longestWait);
      System.out.println("Carwash bypass count : "+bypassCount);

   }

   // Implementation of a FIFO queue collection, adapted
   // for use with Car objects in this program.
   private static class WashQueue {
      // The internal array for the queue.
      private Car[] queue = new Car[QUEUE_MAX_SIZE];
      private int tailIndex = -1;

      // These methods provide information as to the size of the queue.
      public int size() { return tailIndex + 1; }
      public boolean isEmpty() { return this.size() == 0; }
      public boolean isFull()  { return this.size() >= QUEUE_MAX_SIZE; }

      // Add a car to the tail of the queue.
      // Throws exception if the queue is full.
      public void add(Car car) throws IllegalStateException {
         if (this.size() >= QUEUE_MAX_SIZE)
            throw new IllegalStateException("Wash queue is full: max size = "
                                            + QUEUE_MAX_SIZE);

         queue[++tailIndex] = car;
      }

      // Remove a car from the head of the queue.
      // Throws exception if the queue is empty.
      public Car remove() throws NoSuchElementException {
         if (this.isEmpty())
            throw new NoSuchElementException("Attempt to access empty queue.");

         Car head = queue[0];

         Car[] shiftedQueue = new Car[QUEUE_MAX_SIZE];
         for (int i = 1; i < QUEUE_MAX_SIZE; i++)
            shiftedQueue[i-1] = queue[i];

         queue = shiftedQueue;
         tailIndex--;
         return head;
      }
   }

   // Basic class to represent a car and its properties,
   // as is relevant to this program.
   private static class Car {
      public int arrivalTime;
      public int washDuration;
      public int completionTime;

      public Car (int arrivalTime, int washDuration) {
         this.arrivalTime  = arrivalTime;
         this.washDuration = washDuration;
      }

      // Creates a car with a random wash duration
      // and the specified arrival time.
      public static Car random(int arrivalTime) {
         int randomWashDuration = (int)(Math.random() * WASH_MAX_DURATION - 1)
                                  + WASH_MIN_DURATION;

         return new Car(arrivalTime, randomWashDuration);
      }
   }
}