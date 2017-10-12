import java.io.File;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.ArrayList;

public class StableMarriage {
    public static void main(String[] args) {
        Scanner stdIn = new Scanner(System.in);
        String filePath;

        System.out.print("Please enter the path to an input file: ");

        File inputFile = new File(stdIn.nextLine());

        Boy  boy1  = new Boy ("1");
        Boy  boy2  = new Boy ("2");
        Boy  boy3  = new Boy ("3");
        Boy  boy4  = new Boy ("4");
        Boy  boy5  = new Boy ("5");
        
        Girl girlA = new Girl("A");
        Girl girlB = new Girl("B");
        Girl girlC = new Girl("C");
        Girl girlD = new Girl("D");
        Girl girlE = new Girl("E", girlA, girlB, girlC, girlD);

    }

    public static abstract class Human<E> {
        public String name;
        public LinkedList<E> preferences = new LinkedList<E>();
        public E partner;

        public Human(String name) {
            this.name = name;
        }
        
        public Human(String name, E ...preferences) {
            this.name = name;
            addPreferences(preferences);
        }

        public void addPreferences(E ...preferences) {
            for (E preference : preferences)
                preferences.add(preference);
        }

        
    }

    public static class Boy extends Human<Girl> {
        public Boy(String name) {
            super(name);
        }
        public Boy(String name, Girl ...preferences) {
            super(name, preferences);
        }
    }

    public static class Girl extends Human<Boy> {
        public Girl(String name) {
            super(name);
        }
        public Girl(String name, Boy ...preferences) {
            super(name, preferences);
        }
    }
}