import java.util.ArrayList;

// The interface representing the abstract data type Relation
public interface Relation<X ,Y> {
    /////////// Accessors ///////////
    public Boolean containsPair (X x, Y y);
    // Return true if the pair (x,y) is contained in the relation
    
    public ArrayList<Y> getYValues (X x);
    // Return an ArrayList of all the values y of type Y such that the pair 
    // (x,y) is contained in the relation
    
    public ArrayList<X> getXValues (Y y);
    // Return an ArrayList of all the values y of type Y such that the pair 
    // (x,y) is contained in the relation
    
    /////////// Transformers ///////////
    public void clear (); 
    // Make the relation empty
    
    public void addPair (X x, Y y);
    // Add the pair (x,y) to the relation
    
    public void removePair (X x, Y y);
    // Remove the pair (x,y) from the relation 
    
    public void removeXPairs (X x);
    // Remove all pairs (x,y) with y of type Y 
    
    public void removeYPairs (Y y);
    // Remove all pairs (x,y) with x of type X
    
    public String toString();
    // Renders the relation's contents as a string
}