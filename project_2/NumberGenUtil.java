import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.nio.file.StandardOpenOption;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.sun.javafx.css.CssError.InlineStyleParsingError;

public class NumberGenUtil {
   
      public static void main(String[] args) {
         int startNum;
         int endNum;
         int orderType;
         String fileName;

         if (args.length != 4){
            System.out.println("Invalid number of arguments.");
            printUsage();
            return;
         }
         
         try {
            startNum  = Integer.parseInt(args[0]);
            endNum    = Integer.parseInt(args[1]);
            orderType = Integer.parseInt(args[2]);
         } catch (Exception e) {
            System.out.println("Invalid arguments.");
            printUsage();
            return;
         }

         fileName = args[3];

         ArrayList<String> data = new ArrayList<String>();
         switch (orderType) {
            case 0:
               for (int i = startNum; i <= endNum; i++)
                  data.add(Integer.toString(i));
               break;
            case 1:
               for (int i = endNum; i >= startNum; i--)
                  data.add(Integer.toString(i));
               break;
            case 2:
               for (int i = startNum; i <= endNum; i++) {
                  data.add(Integer.toString((int)(Math.random()
                           *(endNum - startNum) + startNum)));
               }
               break;
            default:
               for (int i = startNum; i <= endNum; i++)
                  data.add(Integer.toString(i));
         }
         
         try {
            Files.write(Paths.get(fileName), data, StandardCharsets.UTF_8, 
                        StandardOpenOption.CREATE_NEW);
         } catch (Exception e) {
            System.out.println("Failed to create file or write to file.");
            printUsage();
         }
      }

      private static void printUsage(){
         System.out.println("Usage: java NumberGenUtil start_number end_number order_type file_name");
         System.out.println("Order Types are as follows:");
         System.out.println("0  = ascending");
         System.out.println("1  = descending");
         System.out.println("2  = random");
         System.out.println("File creation will fail if the file exists.");
      }
}