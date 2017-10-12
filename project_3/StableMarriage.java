// This program applies the Gale-Shapley algorithm to
// solve the stable marriage problem, using the people
// given in a user-supplied text file.
// Anthony Phillips
// 10/12/17

import java.io.File;
import java.nio.file.Files;
// Don't worry, I implemented my own Linked List class
// for the men / women preference lists
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;

class StableMarriage {
   static Person[] men;
   static Person[] women;
   
   public static void main(String[] args) {
      Scanner stdIn = new Scanner(System.in);
      
      // Get the file name
      String fileName = "";
      if (args.length > 0) {
         fileName = args[0];
      } else {
         System.out.print("Please specify an input file: ");
         fileName = stdIn.nextLine();
         System.out.println();
      }

      // Read the file
      String[] fileLines = getFileLines(new File(fileName));

      // Parse the file contents, initializing men / women 
      // and their respective lists of preferences.
      parseFileLines(fileLines);

      // Ask who is proposing
      String matchType = "";
      System.out.println("Who would you like to propose?");
      System.out.print("Please type 'men' or 'women': ");
      matchType = stdIn.nextLine();
      System.out.println();

      System.out.println("Press enter to apply the Gale Shapley algorithm.");
      stdIn.nextLine();

      // Execute the matching algorithm
      if (matchType.equals("men")) {
         stableMatching(men, women);      
         System.out.println("Marriages:");
         for (Person man : men)
            System.out.println("("+man.name+","+man.match.name+")");
      } else if (matchType.equals("women")) {
         stableMatching(women, men);      
         System.out.println("Marriages:");
         for (Person woman : women)
            System.out.println("("+woman.name+","+woman.match.name+")");
      } else {
         System.out.println("Invalid option: " + matchType);
         return;
      }

   }

   // The method that applies the Gale-Shapley algorithm
   private static void stableMatching(Person[] proposers, Person[] proposees) {
      LinkedList<Person> freeProposers = new LinkedList<Person>(Arrays.asList(proposers));

      while (freeProposers.size() > 0) {
         // Grab the first free person and their highest match
         // they have yet to propose to
         Person proposer = freeProposers.pop();
         Person proposee = proposer.getHighestNotProposed();

         // If the proposal is successful
         if (proposer.propose(proposee)) {
            // Free the proposee's former match
            if (proposee.match != null) {
               freeProposers.addLast(proposee.match);
               proposee.match = null;
            }
            // Marry the new couple
            proposer.marry(proposee);
         }
         // If not, put the proposer in the back of the queue
         else
            freeProposers.addLast(proposer);
      }
   }

   // Method to read lines from file
   private static String[] getFileLines(File inputFile) {
      String inputFileContent = "";

      try {
         inputFileContent = new String(Files.readAllBytes(
                                       inputFile.toPath())).replace("\r", "");
      } catch (Exception e) {
         System.out.println("Unable to read file.");
         System.exit(2);
      }

      return inputFileContent.split("\n");
   }

   // Method to parse file lines, initialize variables,
   // and provide feedback to the user on their contents
   private static void parseFileLines(String[] fileLines) {
      int numCouples = 0;
      String[] menNames   = new String[0];
      String[] womenNames = new String[0];

      try {
         numCouples = Integer.parseInt(fileLines[0].trim());
         System.out.println("Number of men / women: " + numCouples);

         System.out.println();
         System.out.println("Men: " + fileLines[1]);
         menNames   = fileLines[1].split(",");

         System.out.println();
         System.out.println("Women: " + fileLines[2]);
         womenNames = fileLines[2].split(",");
      } catch (Exception ex) {
         System.out.println("Invalid input file format.");
         System.exit(1);
      }

      // Initialize our arrays
      men   = new Person[numCouples];
      women = new Person[numCouples];

      for (int i = 0; i < numCouples; i++) {
         men[i]   = new Person(menNames[i]);
         women[i] = new Person(womenNames[i]);

         men[i].preferenceList   = new PreferenceList();
         women[i].preferenceList = new PreferenceList();
      }

      // Men's preferences
      System.out.println();
      System.out.println("Men Preferences:");
      for (int i = 3; i < 3+numCouples; i++) {
         System.out.println(fileLines[i]);
         String[] prefLineSections = fileLines[i].split(":");

         Person man = men[Arrays.asList(men).indexOf(new Person(prefLineSections[0]))];
         String[] preferences = prefLineSections[1].split(",");

         for (String preference : preferences)
            man.preferenceList.add(women[Arrays.asList(women).indexOf(new Person(preference))]);
      }

      // Women's preferences
      System.out.println();
      System.out.println("Women Preferences:");
      for (int i = 3+numCouples; i < fileLines.length; i++) {
         System.out.println(fileLines[i]);
         String[] prefLineSections = fileLines[i].split(":");

         Person woman = women[Arrays.asList(women).indexOf(new Person(prefLineSections[0]))];
         String[] preferences = prefLineSections[1].split(",");

         for (String preference : preferences)
            woman.preferenceList.add(men[Arrays.asList(men).indexOf(new Person(preference))]);
      }
      System.out.println();
   }

   // Class to represent a person male or female
   public static class Person {

      public Person (String name) {
         this.name = name;
      }

      public String name;

      public PreferenceList preferenceList;

      public Person match;

      public List<Person> proposals = new ArrayList<Person>();

      public boolean propose(Person proposee) {
         proposals.add(proposee);

         if (proposee.match == null)
            return true;
         else if (proposee.preferenceList.indexOf(this) < proposee.preferenceList.indexOf(proposee.match))
            return true;
         else
            return false;
      }

      public void marry(Person proposee) {
         this.match    = proposee;
         proposee.match = this;
      }

      public Person getHighestNotProposed() {
         PreferenceListNode currNode = preferenceList.head;

         while (currNode.link != null){
            if (!proposals.contains(currNode.person))
               return currNode.person;
            else
               currNode = currNode.link;
         }
         return currNode.person;
      }

      @Override
      public boolean equals(Object object){
         if (object == null || !(object instanceof Person))
            return false;

         Person person = (Person)object;

         return this.name.equalsIgnoreCase(person.name);
      }
   }

   // Custom linked list class that uses custom node class
   public static class PreferenceList {

      public PreferenceListNode head;

      public int length = 0;

      public void add(Person person) {
         PreferenceListNode newNode = new PreferenceListNode(person);

         if (length == 0) {
            head = newNode;
            length++;
            return;
         }

         PreferenceListNode currNode = head;

         while (currNode.link != null)
            currNode = currNode.link;
            
         currNode.link = newNode;
         length++;
      }

      public int indexOf(Person person) {
         int index = 0;
         PreferenceListNode currNode = head;

         while (index != length){
            if (currNode.person.equals(person))
               return index;
            else {
               index++;
               currNode = currNode.link;
            }
         }
         return -1;
      }

      public Person get(int index) {
         PreferenceListNode currNode = head;

         for (int i = 0; i < index; i++) {
            currNode = currNode.link;
         }

         return currNode.person;
      }

   }

   // Custom linked list node class
   public static class PreferenceListNode {
      public Person person;

      public Boolean proposedTo = false;

      public PreferenceListNode(Person person) { this.person = person; }

      public PreferenceListNode link;
   }   
}