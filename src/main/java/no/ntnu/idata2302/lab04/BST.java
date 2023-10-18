package no.ntnu.idata2302.lab04;

/**
 * The Real BTS.
 */
public class BST {

    public static BST fromValues(int... values) {
        if (values.length < 1) {
            throw new IllegalArgumentException(
                  "A binary search tree must have at least one value");
        }
        if (values.length == 1) {
            return new BST(values[0]);
        }
        var tree = new BST(values[0]);
        for (int i = 1; i < values.length; i++) {
            tree.insert(values[i]);
        }
        return tree;
    }

    private int value;
    private BST left;
    private BST right;

    public BST(int value) {
        this.value = value;
    }

    public BST insert(int givenValue) {
        if (givenValue < value) {
            if (hasLeft()) {
                left.insert(givenValue);
            } else {
                left = new BST(givenValue);
            }
        } else if (givenValue > value) {
            if (hasRight()) {
                right.insert(givenValue);
            } else {
                right = new BST(givenValue);
            }
        } else {
            throw new IllegalArgumentException("Value already exists in tree");
        }
        return this;
    }

    /**
     * Returns the number of items in a Binary Search Tree.
     *
     * @return the number of items in this tree.
     */
    public int size() {
        int size = 1; //counts the current node.
        if (hasLeft()) {
            size += left.size(); //recursive call to itself on left child.
        }
        if (hasRight()) {
            size += right.size(); //recursive call to itself on right child.
        }
        return size;
    }

    /**
     * Checks to see if a left child exists.
     *
     * @return true of exists, false if not.
     */
    private boolean hasLeft() {
        return left != null;
    }

    /**
     * Checks to see if a right child exists.
     *
     * @return true of exists, false if not.
     */
    private boolean hasRight() {
        return right != null;
    }

    public int minimum() {
        BST currentMin = this;
        while (currentMin.hasLeft()) {
            currentMin = currentMin.left;
        }
        return currentMin.value;
    }

    public int maximum() {

        BST currentMax = this;
        while (currentMax.hasRight()) {
            currentMax = currentMax.right;
        }
        return currentMax.value;

    }

    boolean contains(int givenValue) {
        boolean henrik;
        if (value < givenValue) {
            if (hasRight()) {
                henrik = right.contains(givenValue);
            } else {
                henrik = false;
            }

        } else if (value > givenValue) {
            if (hasLeft()) {
                henrik = left.contains(givenValue);
            } else {
                henrik = false;
            }
            //henrik = hasLeft() && left.contains(givenValue);

        } else {
            henrik = true;

        }
        return henrik;
    }

    int successor(int givenValue) {
        if (value < givenValue) {
            if (hasRight()) {
                return right.successor(givenValue);

            }
            throw new NoSuchValue(givenValue);

        } else if (value > givenValue) {
            if (hasLeft()) {
                try {
                    return left.successor(givenValue);

                } catch (SuccessorNotFound error) {
                    return value;

                } catch (NoSuchValue error) {
                    return value;

                }
            }
            throw new NoSuchValue(givenValue);

        } else {
            if (hasRight()) {
                return right.minimum();

            }
            throw new SuccessorNotFound();
        }
    }

    BST delete(int givenValue) {
        if (givenValue < value) {
            if (hasLeft()) {
                left = left.delete(givenValue);
                return this;
            }
            throw new NoSuchValue(givenValue);
        } else if (givenValue > value) {
            if (hasRight()) {
                right = right.delete(givenValue);
                return this;
            }
            throw new NoSuchValue(givenValue);
        } else {
            if (hasLeft() && !hasRight()) {
                return left;
            }
            if (!hasLeft() && hasRight()) {
                return right;
            }
            if (hasLeft() && hasRight()) {
                try {
                    value = successor(value);

                } catch (SuccessorNotFound error) {
                    return null;

                }
                right.delete(value);
                return this;
            }
            return null;
        }
    }

    /**
     * Initializes the StringBuilder,
     * calls helper method with current node and StringBuilder object.
     * If values are added to the StringBuilder, it removes the trailing comma.
     *
     * @return String representation of the tree.
     */
    public String format() {
        StringBuilder result = new StringBuilder();
        formatStringBuilder(this, result);
        if (result.length() > 0) {
            result.setLength(result.length() - 2);
        }
        return result.toString();
    }

    /**
     * Helper method for format().
     * Recursively traverses the tree and adds values to the StringBuilder.
     * Both left and right subtree are traversed.
     *
     * Appends value of current node to the StringBuilder, with a comma and space (", ").
     * Left first, current then right subtree.
     * Results in all items in tree listed in ascending order, while also being separated by comma.
     *
     * @param node the current node being processed.
     *
     * @param builder the result of the builder.
     */
    private void formatStringBuilder(BST node, StringBuilder builder) {
        if (node == null) {
            return;
        }
        // Traverse left subtree
        formatStringBuilder(node.left, builder);
        // Visit the current node
        builder.append(node.value).append(", ");
        // Traverse right subtree
        formatStringBuilder(node.right, builder);
    }

    private static class NoSuchValue extends RuntimeException {

        private int value;

        public NoSuchValue(int givenValue) {
            super();
            this.value = givenValue;
        }
    }

    public String formatUnreadable() {
        if (this == null) {
            return "";
        }

        //result = (condition) ? value_if_true : value_if_false;

        //If right is not null, assign value of right.format() to rightStr,
        //otherwise assign an empty string ("") to rightStr.
        String leftStr = (left != null) ? left.format() : "";
        String rightStr = (right != null) ? right.format() : "";

        if (!leftStr.isEmpty()) {
            leftStr += ", ";
        }
        if (!rightStr.isEmpty()) {
            rightStr = ", " + rightStr;
        }

        return leftStr + value + rightStr;
    }

    private static class SuccessorNotFound extends RuntimeException {
    }

    private static class PredecessorNotFound extends RuntimeException {
    }

}
