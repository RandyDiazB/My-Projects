/**
 * This program implements a generic Binary Search Tree (BST) that extends
 * a Binary Tree. It supports adding, removing, and retrieving elements
 * while maintaining BST properties. It also provides a method to check
 * if the tree is height-balanced.
 * 
 * The isBalanced method was implemented by Randy Diaz.
 * 
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @author Randy Diaz
 * @version 2025-07-21
 */
public class BinarySearchTree<T extends Comparable<? super T>>
        extends BinaryTree<T> implements SearchTreeInterface<T> {

    public BinarySearchTree() {
        super();
    }

    public BinarySearchTree(T rootEntry) {
        super();
        setRootNode(new BinaryNode<T>(rootEntry));
    }

    /**
     * Prevents user from manually setting subtrees and violating BST rules.
     * @param rootData The data for the root node.
     * @param leftTree The left subtree.
     * @param rightTree The right subtree.
     * @throws UnsupportedOperationException always thrown to prevent manual subtree setting.
     */
    public void setTree(T rootData, BinaryTreeInterface<T> leftTree,
                        BinaryTreeInterface<T> rightTree) {
        throw new UnsupportedOperationException();
    }

    /**
     * Searches for an entry in the BST.
     * Prints result of search.
     * @param anEntry The value to search for.
     * @return The found entry, or null if not found.
     */
    public T getEntry(T anEntry) {
        T result = findEntry(getRootNode(), anEntry);
        if (result != null) {
            System.out.println("Found " + result);
        } else {
            System.out.println(anEntry + " not found");
        }
        return result;
    }

    /**
     * Recursively searches the BST for an entry.
     * @param rootNode The root node of the subtree.
     * @param anEntry The entry to find.
     * @return The found entry, or null if not found.
     */
    private T findEntry(BinaryNode<T> rootNode, T anEntry) {
        T result = null;

        if (rootNode != null) {
            T rootEntry = rootNode.getData();

            if (anEntry.equals(rootEntry)) {
                result = rootEntry;
            } else if (anEntry.compareTo(rootEntry) < 0) {
                result = findEntry(rootNode.getLeftChild(), anEntry);
            } else {
                result = findEntry(rootNode.getRightChild(), anEntry);
            }
        }

        return result;
    }

    /**
     * Checks if the BST contains the given entry.
     * Prints whether the entry exists in the tree.
     * @param entry The entry to check for.
     * @return true if found, false otherwise.
     */
    public boolean contains(T entry) {
        boolean found = getEntry(entry) != null;
        System.out.println(entry + (found ? " exists in the tree" : " does not exist in the tree"));
        return found;
    }

    /**
     * Adds a new entry to the BST.
     * Prints whether an entry was added or replaced.
     * @param newEntry The entry to add.
     * @return The old entry if replaced, or null if added.
     */
    public T add(T newEntry) {
        T result = null;

        if (isEmpty()) {
            setRootNode(new BinaryNode<>(newEntry));
            System.out.println("Added " + newEntry);
        } else {
            result = addEntry(getRootNode(), newEntry);
            if (result == null) {
                System.out.println("Added " + newEntry);
            } else {
                System.out.println("Replaced " + result + " with " + newEntry);
            }
        }

        return result;
    }

    /**
     * Recursively adds an entry to the BST.
     * @param rootNode The root node of the subtree.
     * @param anEntry The entry to add.
     * @return The replaced entry, or null if added.
     */
    private T addEntry(BinaryNode<T> rootNode, T anEntry) {
        T result = null;
        int comparison = anEntry.compareTo(rootNode.getData());

        if (comparison == 0) {
            result = rootNode.getData();
            rootNode.setData(anEntry);
        } else if (comparison < 0) {
            if (rootNode.hasLeftChild()) {
                result = addEntry(rootNode.getLeftChild(), anEntry);
            } else {
                rootNode.setLeftChild(new BinaryNode<>(anEntry));
            }
        } else {
            if (rootNode.hasRightChild()) {
                result = addEntry(rootNode.getRightChild(), anEntry);
            } else {
                rootNode.setRightChild(new BinaryNode<>(anEntry));
            }
        }

        return result;
    }

    /**
     * Removes an entry from the BST.
     * Prints whether an entry was removed or not found.
     * @param anEntry The entry to remove.
     * @return The removed entry, or null if not found.
     */
    public T remove(T anEntry) {
        ReturnObject oldEntry = new ReturnObject(null);
        BinaryNode<T> newRoot = removeEntry(getRootNode(), anEntry, oldEntry);
        setRootNode(newRoot);

        if (oldEntry.get() != null) {
            System.out.println("Removed " + oldEntry.get());
        } else {
            System.out.println(anEntry + " not found");
        }

        return oldEntry.get();
    }

    /**
     * Recursively removes an entry from the BST.
     * @param rootNode The root node of the subtree.
     * @param anEntry The entry to remove.
     * @param oldEntry A ReturnObject to hold the removed entry.
     * @return The new root of the subtree after removal.
     */
    private BinaryNode<T> removeEntry(BinaryNode<T> rootNode, T anEntry,
                                      ReturnObject oldEntry) {
        if (rootNode != null) {
            T rootData = rootNode.getData();
            int comparison = anEntry.compareTo(rootData);

            if (comparison == 0) {
                oldEntry.set(rootData);
                rootNode = removeFromRoot(rootNode);
            } else if (comparison < 0) {
                BinaryNode<T> leftChild = rootNode.getLeftChild();
                BinaryNode<T> subtreeRoot = removeEntry(leftChild, anEntry, oldEntry);
                rootNode.setLeftChild(subtreeRoot);
            } else {
                BinaryNode<T> rightChild = rootNode.getRightChild();
                rootNode.setRightChild(removeEntry(rightChild, anEntry, oldEntry));
            }
        }

        return rootNode;
    }

    /**
     * Removes the root node of the subtree.
     * @param rootNode The root node to remove.
     * @return The new root node after removal.
     */
    private BinaryNode<T> removeFromRoot(BinaryNode<T> rootNode) {
        if (rootNode.hasLeftChild() && rootNode.hasRightChild()) {
            BinaryNode<T> leftSubtreeRoot = rootNode.getLeftChild();
            BinaryNode<T> largestNode = findLargest(leftSubtreeRoot);
            rootNode.setData(largestNode.getData());
            rootNode.setLeftChild(removeLargest(leftSubtreeRoot));
        } else if (rootNode.hasRightChild()) {
            rootNode = rootNode.getRightChild();
        } else {
            rootNode = rootNode.getLeftChild();
        }

        return rootNode;
    }

    /**
     * Finds the largest node in the given subtree.
     * @param rootNode The root node of the subtree.
     * @return The node containing the largest value.
     */
    private BinaryNode<T> findLargest(BinaryNode<T> rootNode) {
        if (rootNode.hasRightChild()) {
            rootNode = findLargest(rootNode.getRightChild());
        }

        return rootNode;
    }

    /**
     * Removes the largest node in the given subtree.
     * @param rootNode The root node of the subtree.
     * @return The subtree root after removal of the largest node.
     */
    private BinaryNode<T> removeLargest(BinaryNode<T> rootNode) {
        if (rootNode.hasRightChild()) {
            BinaryNode<T> rightChild = rootNode.getRightChild();
            rightChild = removeLargest(rightChild);
            rootNode.setRightChild(rightChild);
        } else {
            rootNode = rootNode.getLeftChild();
        }

        return rootNode;
    }

    /**
     * Helper class used to hold and return an entry during removal operations.
     */
    private class ReturnObject {
        private T item;

        private ReturnObject(T entry) {
            item = entry;
        }

        /**
         * Returns the stored entry.
         * @return The stored entry.
         */
        public T get() {
            return item;
        }

        /**
         * Sets the stored entry.
         * @param entry The entry to store.
         */
        public void set(T entry) {
            item = entry;
        }
    }

    /**
     * Determines whether the BST is height-balanced.
     * Prints whether the tree is balanced.
     * A tree is height-balanced if the difference in height
     * between left and right subtrees for every node is at most 1.
     *
     * @author Randy Diaz
     * @version 2025-07-22
     * @return true if the tree is balanced, false otherwise
     */
    public Boolean isBalanced() {
        boolean balanced = isBalanced(getRootNode()) != -1;
        System.out.println("Tree is " + (balanced ? "balanced" : "not balanced"));
        return balanced;
    }

    /**
     * Helper method that recursively checks balance of subtrees.
     * Returns subtree height if balanced, or -1 if unbalanced.
     *
     * @author Randy Diaz
     * @param node The root node of the subtree.
     * @return Height if balanced, -1 if not balanced.
     */
    private int isBalanced(BinaryNode<T> node) {
        if (node == null) return 0;

        int leftHeight = isBalanced(node.getLeftChild());
        if (leftHeight == -1) return -1;

        int rightHeight = isBalanced(node.getRightChild());
        if (rightHeight == -1) return -1;

        if (Math.abs(leftHeight - rightHeight) > 1) return -1;

        return 1 + Math.max(leftHeight, rightHeight);
    }
}
