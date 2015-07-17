import java.util.ArrayList;

// The methods used to test the functionality of the BST implementation of the Relation abstract data type
public class RelationTest {
    private static RelationImplement<String, String> languages;
    private static RelationImplement<Integer, String> classes;

    public static void main (String[] args){
        
        // Create new relation
        languages = new RelationImplement<String,String>();

        // Add non-matching pairs
        addPair("FR", "French");
        addPair("DE", "German");
        addPair("IT", "Italy");
        addPair("BE", "French");
        addPair("BE", "Flemish");
        addPair("NL", "Dutch");
        addPair("UK", "English");
        addPair("IE", "English");
        addPair("IE", "Irish");
        
        // Attempt to add a pair which is already in the relation
        System.out.println(String.format("addPair(\"%s\", \"%s\"): void", "DE", "German"));
        languages.addPair("DE", "German");

        // Check if a pair which has been added to the relation can be found
        Boolean contains = languages.containsPair("UK", "English");
        System.out.println("containsPair(\"UK\", \"English\"): " + contains + "\n");

        // Check if a pair which has not been added to the relation can be found
        contains = languages.containsPair("UK", "Scottish");
        System.out.println("containsPair(\"UK\", \"Scottish\"): " + contains + "\n");
        
        // Display the ArrayList of all of the y values that have IE as their x
        ArrayList<String> yValues = languages.getYValues("IE");
        System.out.println("getY(\"IE\"): " + yValues.toString() + "\n");
        
        // Display the ArrayList of all of the x values that have French as their y
        ArrayList<String> xValues = languages.getXValues("French");
        System.out.println("getX(\"French\"): " + xValues.toString() + "\n");

        // Remove a particular pair
        System.out.println("removePair(\"NL\", \"Dutch\"): void");
        languages.removePair("NL", "Dutch");
        System.out.println("toString():\n" + languages.toString());

        // Remove any pairs that have BE as their x
        System.out.println("removeXPairs(\"BE\"): void");
        languages.removeXPairs("BE");
        System.out.println("toString():\n" + languages.toString());
        
        // Remove any pairs that have English as their y
        System.out.println("removeYPairs(\"English\"): void");
        languages.removeYPairs("English");
        System.out.println("toString():\n" + languages.toString());
        
        // Clear the relation
        System.out.println("clear(): void");
        languages.clear();
        System.out.println("toString():\n" + languages.toString());

        // Create new relation
        classes = new RelationImplement<Integer,String>();

        // Add non-matching pairs
        addPair(4, "Programming");
        addPair(9, "ISD");
        addPair(10, "Big Data");
        addPair(3, "Networks");
        addPair(9, "Algorithms");
        addPair(1, "Security");
        addPair(2, "Internet Tech");
        addPair(8, "Ethical Hacking");
        addPair(4, "Algorithms");
        addPair(8, "Internet Tech");
        
        // Attempt to add a pair which is already in the relation
        System.out.println(String.format("addPair(\"%s\", \"%s\"): void", 9, "ISD"));
        classes.addPair(9, "ISD");

        // Check if a pair which has been added to the relation can be found
        contains = classes.containsPair(3, "Networks");
        System.out.println("containsPair(3, \"Networks\"): " + contains + "\n");

        // Check if a pair which has not been added to the relation can be found
        contains = classes.containsPair(8, "Cryptography");
        System.out.println("containsPair(8, \"Cryptography\"): " + contains + "\n");
        
        // Display the ArrayList of all of the y values that have IE as their x
        ArrayList<String> yClasses = classes.getYValues(9);
        System.out.println("getY(9): " + yClasses.toString() + "\n");
        
        // Display the ArrayList of all of the x values that have French as their y
        ArrayList<Integer> xClasses = classes.getXValues("Algorithms");
        System.out.println("getX(\"Algorithms\"): " + xClasses.toString() + "\n");

        // Remove a particular pair
        System.out.println("removePair(1, \"Security\"): void");
        classes.removePair(1, "Security");
        System.out.println("toString():\n" + classes.toString());

        // Remove any pairs that have BE as their x
        System.out.println("removeXPairs(4): void");
        classes.removeXPairs(4);
        System.out.println("toString():\n" + classes.toString());
        
        // Remove any pairs that have English as their y
        System.out.println("removeYPairs(\"Internet Tech\"): void");
        classes.removeYPairs("Internet Tech");
        System.out.println("toString():\n" + classes.toString());
        
        // Clear the relation
        System.out.println("clear(): void");
        classes.clear();
        System.out.println("toString():\n" + classes.toString());
    }
    
    // Helper method to add new pairs to the relation
    private static void addPair (String country, String language){
        System.out.println(String.format("addPair(\"%s\", \"%s\"): void", country, language));
        languages.addPair(country, language);
        System.out.println("toString():\n" + languages.toString());
    }

    private static void addPair (Integer number, String name){
        System.out.println(String.format("addPair(%d, \"%s\"): void", number, name));
        classes.addPair(number, name);
        System.out.println("toString():\n" + classes.toString());
    }
}