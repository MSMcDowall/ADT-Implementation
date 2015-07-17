import java.util.ArrayList;

// The implementation of the abstract data type Relation using a binary search tree
public class RelationImplement<X extends Comparable<X>,Y extends Comparable<Y>> implements Relation<X,Y>{
    // Each RelationImplement object is the root node of a binary search tree
    private Pair<X,Y> root;
    
    public RelationImplement() {
        // Construct an empty SLL
        this.root = null;
    }
    
    /////////// Inner Class ///////////
    private static class Pair<X extends Comparable<X>, Y extends Comparable<Y>> {
        // Each Pair object is a node of a binary search tree which contains the x and y elements
        protected X x;
        protected Y y;
        protected Pair<X,Y> left, right;
        
        protected Pair (X x, Y y){
            this.x = x;
            this.y = y;
            left = null;
            right = null;
        }

        // Compares the x element to a given x 
        // The -1 is used to compensate for the altered compare method so that when the given x < the element x 
        // it goes to the left subtree
        protected int compareToX (X x) {
            return -1*(this.x.compareTo(x));
        }

        // Compares the y element to a given y 
        // The -1 is used to compensate for the altered compare method so that when the given y < the element y 
        // it goes to the left subtree
        protected int compareToY (Y y) {
            return -1*(this.y.compareTo(y));
        }

        // Removes the topmost element in a tree and replaces it with one of its chldren
        // If it has two children then the leftmost Pair of the right subtree is chosen
        protected Pair<X,Y> deleteTopmost () {
            if (this.left == null)
                return this.right;
            else if (this.right == null)
                return this.left;
            else { // The pair has two children
                Pair<X,Y> leftmostPair = this.right.getLeftmost();
                this.x = leftmostPair.x;
                this.y = leftmostPair.y;
                this.right = this.right.deleteLeftmost();
                return this;
            }
        }
        
        // Retrieves the leftmost Pair in a given subtree
        private Pair<X,Y> getLeftmost() {
            Pair<X,Y> curr = this;
            while (curr.left != null)
                curr = curr.left;
            return curr;
        }
        
        // Removes the leftmost Pair once it has been used to replace the topmost node that was to be deleted
        private Pair<X,Y> deleteLeftmost() {
            if (this.left == null)
                return this.right;
            else {
                Pair<X,Y> parent = this;
                Pair<X,Y> curr = this.left;
                while (curr.left != null) {
                    parent = curr;
                    curr = curr.left;
                }
                parent.left = curr.right;
                return this;
            }
        }
    }
    /////////// End of Inner Class ///////////

    // Returns true is the given elements exist as a pair within the relation
    // This uses the standard BST search algorithm first on the x value, and then on the y value
    // Time Complexity: best = O(log n), worst = O(n)
    public Boolean containsPair (X x, Y y) {
        int xDirection = 0;
        int yDirection = 0;
        Boolean found = false;
        Pair<X,Y> curr = this.root;
        while (curr != null){
            xDirection = curr.compareToX(x);
            if (xDirection == 0) {
                yDirection = curr.compareToY(y);
                if (yDirection == 0){
                    found = true;
                    break;
                }
                else if (yDirection < 0)
                    curr = curr.left;
                else
                    curr = curr.right;
            }
            if (xDirection < 0) 
                curr = curr.left;
            else if (xDirection > 0)
                curr = curr.right;
        }
        return found;
    }
    
    // This method retrieves all the values of y which are in a pair with the given x
    // This uses the BST search algorithm until a matching x is found and then 
    // recursively calls the search algorithm on both the left and the right subtree
    // Time Complexity: best = O(log n), worst = O(n)
    public ArrayList<Y> getYValues (X x) {
        ArrayList<Y> yValues = new ArrayList<Y>();
        Pair<X,Y> curr = this.root;
        yValues = getYValues(x, curr, yValues);
        return yValues;
    }
    
    // The recursive method to retrieve the values of y associated with a given x
    private ArrayList<Y> getYValues (X x, Pair<X,Y> curr, ArrayList<Y> yValues){
        int xDirection = 0;
        while (curr!= null) {
            xDirection = curr.compareToX(x);
            if (xDirection == 0){
                yValues.add(curr.y);
                if (curr.left != null)
                    yValues = getYValues(x, curr.left, yValues);
                if (curr.right != null)
                    yValues = getYValues(x, curr.right, yValues);
                return yValues;
            }
            else if (xDirection < 0)
                curr = curr.left;
            else
                curr = curr.right;
        }
        return yValues;
    }
    
    // This method retrieves all the values of x which are in a pair with the given x
    // As the relation is sorted by the x values, each Pair must be visited and tested
    // This uses the in-order traversal algorithm
    // Time Complexity: O(n)
    public ArrayList<X> getXValues (Y y) {
        ArrayList<X> xValues = new ArrayList<X>();
        Pair<X,Y> curr = this.root;
        xValues = getXValues(y, curr, xValues);
        return xValues;
    }
    
    // The recursive method to retrieve the values of x associated with a given ys
    private ArrayList<X> getXValues (Y y, Pair<X,Y> curr, ArrayList<X> xValues){
        int yDirection = curr.compareToY(y);
        if (curr.left != null)
            getXValues(y, curr.left, xValues);
        if (yDirection == 0)
            xValues.add((X) curr.x);
        if (curr.right != null)
            getXValues(y, curr.right, xValues);
        return xValues;
    }

    // Clears the entire relation
    // Time Complexity: O(1)
    public void clear () {
        this.root = null;
    }
    
    // Adds two given elements into a pair in the relation using the BST insertion algorithm
    // Inserts the pair into the relation choosing the location by first a comparison with other x values
    // Once matching x values are found it will be placed in order of the y values
    // Time Complexity: best = O(log n), worst = O(n)
    public void addPair (X x, Y y) {
        int xDirection = 0;
        int yDirection = 0;
        Pair<X,Y> parent = null;
        Pair<X,Y> curr = this.root;

        while (true){
            if (curr == null) {
                Pair<X,Y> newPair = new Pair<X,Y>(x,y);
                if (root == null)
                    root = newPair;
                else if (xDirection == 0){
                    yDirection = parent.compareToY(y);
                    if (yDirection < 0)
                        parent.left = newPair;
                    else if (yDirection > 0)
                        parent.right = newPair;
                }
                else if (xDirection < 0)
                    parent.left = newPair;
                else if (xDirection > 0)
                    parent.right = newPair;
                return;
            }
            xDirection = curr.compareToX(x);
            parent = curr;
            if (xDirection == 0){
                yDirection = curr.compareToY(y);
                if (yDirection == 0){
                    // Ensure that duplicates of a Pair can not be added
                    System.out.println("Pair " + String.format("(%s, %s)", 
                    x.toString(), y.toString()) + " already exists. \n");
                    break;
                }                
                else if (yDirection < 0){
                    curr = curr.left;
                }
                else 
                    curr = curr.right;
            }
            else if (xDirection < 0)
                curr = curr.left;
            else
                curr = curr.right;
        }
    }
    
    // Removes the pair matching the given values using the BST deletion algorithm
    // It searches first by the x values and then by the y values
    public void removePair (X x, Y y) {
        int xDirection = 0;
        int yDirection = 0;
        Pair<X,Y> parent = null;
        Pair<X,Y> curr = this.root;
        
        while(curr != null){
            xDirection = curr.compareToX(x);
            if (xDirection == 0){
                yDirection = curr.compareToY(y);
                if (yDirection == 0){
                    remove(curr, parent);
                }                     
                parent = curr;
                if (yDirection < 0)
                    curr = curr.left;
                else
                    curr = curr.right;
            }
            else {
                parent = curr;
                if (xDirection < 0)
                    curr = curr.left;
                else
                    curr = curr.right;
            }
        }
    }
    
    // Remove all Pairs which contain the given x
    // This uses the BST search algorithm until the first matching x is found
    // It will then traverse each of the subtrees using the pre-order traversal algorithm
    // If a match is found then the BST deletion algorithm is used to remove it
    // Time Complexity: best = O(log n) (where the subtrees are empty) worst = O(n) (topmost node needs to be deleted)
    public void removeXPairs (X x) {
        Pair<X,Y> curr = this.root;
        Pair<X,Y> parent = null;
        int xDirection = 0;        
        while(curr != null){
            xDirection = curr.compareToX(x);
            if (xDirection == 0){
                break;
            }
            else {
                parent = curr;
                if (xDirection < 0)
                    curr = curr.left;
                else
                    curr = curr.right;
            }
        }
        removeXPairs(x, curr, parent);
    }
    
    // Recursive element to traverse through the subtrees of a given element to find the Pairs to delete
    private void removeXPairs (X x, Pair<X,Y> curr, Pair<X,Y> parent){
        int xDirection = curr.compareToX(x);
        if (xDirection == 0){
            remove(curr, parent);  
            if (curr.left != null){
                removeXPairs(x, curr.left, parent);
            }
            if (curr.right != null){
                removeXPairs(x, curr.right, parent);
            } 
        }
        if (curr.left != null){
            parent = curr;
            removeXPairs(x, curr.left, parent);
        }
        if (curr.right != null){
            parent = curr;
            removeXPairs(x, curr.right, parent);
        }
    }


    // Remove all Pairs which contain the given y element
    // This uses the BST in-order traversal algotithm and then the BST deletion algorithm 
    // Time Complexity: O(n)
    public void removeYPairs (Y y) {
        Pair<X,Y> curr = this.root;
        Pair<X,Y> parent = null;
        removeYPairs(y, curr, parent);       
    }
    
    // The recursive method which traverses through all the elements and deletes any that match the y
    private void removeYPairs (Y y, Pair<X,Y> curr, Pair<X,Y> parent){
        int yDirection = curr.compareToY(y);
        if (curr.left != null){
            parent = curr;
            removeYPairs(y, curr.left, parent);
        }
        if (yDirection == 0)
            remove(curr, parent);
        if (curr.right != null){
            parent = curr;
            removeYPairs(y, curr.right, parent);
        }
    }

    // Helper method to remove a given pair from the relation
    private void remove (Pair<X,Y> curr, Pair<X,Y> parent){
        Pair<X,Y> delete = curr.deleteTopmost();
        if (curr == root)
            root = delete;
        else if (curr == parent.left)
            parent.left = delete;
        else if (curr == parent.right)
            parent.right = delete;
        return;
    }
    
    // Returns the relation as a String ready to be displayed
    // Uses the BST in-order traversal algorithm. 
    // Time Complexity: O(n)
    public String toString () {
        StringBuilder displayBuilder = new StringBuilder();
        String displayString = printInOrder(root, displayBuilder);
        return displayString;
    } 

    // Recursively turns the elements of each Pair into a string
    private String printInOrder (Pair<X,Y> top, StringBuilder displayBuilder) {
        if (top != null){
            printInOrder(top.left, displayBuilder);

            String x = top.x.toString();
            String y = top.y.toString();
            String displayPair = String.format("(%s, %s)\n", x, y);
            displayBuilder.append(displayPair);

            // Recursively call the printPreOrder through the whole tree
            printInOrder(top.right, displayBuilder);
        }
        String displayString = displayBuilder.toString();
        return displayString;
    }
}
