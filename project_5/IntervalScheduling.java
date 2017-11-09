// Computes the optimum combination of given intervals.
// Anthony Phillips
// 11/09/2017
import java.io.File;
import java.nio.file.Files;
import java.util.*;

class IntervalScheduling {
   
   private static Integer[] memOptimal;

   public static void main(String[] args) {
      File inputFile;
      ArrayList<Interval> intervals = new ArrayList<Interval>();
      
      if (args.length > 0)
         inputFile = new File(args[0]);
      else {
         Scanner stdIn = new Scanner(System.in);
         System.out.print("Please specify an input file: ");
         inputFile = new File(stdIn.nextLine());
         stdIn.close();
      }

      if (!Files.exists(inputFile.toPath())) {
         System.out.println("Specified file does not exist.");
         return;
      }

      try {
         intervals = parseIntervalFile(inputFile);
      } catch (Exception ex) {
         System.out.println("Unable to read file.");
         return;
      }

      if (intervals.size() == 0) {
         System.out.println("Empty interval set.");
         return;
      }

      memOptimal = new Integer[intervals.size()];

      for (int i = intervals.size()-1; i >= 0; i--) {
         Interval interval = intervals.get(i);
         for (int j = i; j >= 0; j--) {
            if (intervals.get(j).end <= interval.start)
               interval.compatible = intervals.get(j);
         }
      }

      int optimumProfit = computeOptimum(intervals.size()-1, intervals);
      System.out.println("Optimum profit: " + optimumProfit);
      
      System.out.print("Using jobs: ");
      findSolution(intervals.size()-1, intervals);
   }

   private static ArrayList<Interval> parseIntervalFile(File inputFile) 
      throws Exception{
      List<String>   fileLines = new ArrayList<String>();
      ArrayList<Interval> intervals = new ArrayList<Interval>();

      try{
         fileLines = Files.readAllLines(inputFile.toPath());
      } catch (Exception ex) {
         throw ex;
      } 
      
      intervals = new ArrayList<Interval>(fileLines.size());
      
      for (int i = 0; i < fileLines.size(); i++) {
         String[] intervalArgs = fileLines.get(i).split(" ");

         int start = Integer.parseInt(intervalArgs[0]);
         int end   = Integer.parseInt(intervalArgs[1]);
         int weight    = Integer.parseInt(intervalArgs[2]);

         Interval interval = new Interval(i, start, end, weight);

         intervals.add(interval);
      }

      return intervals;
   }

   private static int computeOptimum (int j, ArrayList<Interval> intervals) {
      if (memOptimal[j] == null) {
         Interval interval = intervals.get(j);

         memOptimal[j] = Integer.max(            
            interval.weight + (interval.compatible == null ? 
               0 : computeOptimum(interval.compatible.number, intervals)),
         
            (j ==0 ? 0 : computeOptimum(j-1, intervals))
         );
      }      
      
      return memOptimal[j];
   }

   private static void findSolution (int j, ArrayList<Interval> intervals) {
      if (j == -1)
         return;

      Interval interval = intervals.get(j);

      if (interval.weight + (interval.compatible == null ? 0 : memOptimal[interval.compatible.number]) > 
         (j == 0 ? 0 : memOptimal[j-1])) {
         System.out.print(interval.number+1 + " ");
         if (interval.compatible == null)
            return;
         findSolution(interval.compatible.number, intervals);
      } else
         findSolution(j-1, intervals);
   }

   private static class Interval {
      public int number;
      public int start;
      public int end;
      public int weight;
      
      public Interval compatible;

      public Interval(int number, int start, int end, int weight) {
         this.number = number;
         this.start  = start;
         this.end    = end;
         this.weight = weight;
      }      
   }

}