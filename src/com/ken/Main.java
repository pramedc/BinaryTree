package com.ken;

import java.util.*;

public class Main {

    // Binary tree node
    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    static class Solution {

        /**
         * Adds a new row of nodes with value 'val' at the specified depth.
         * Uses Breadth-First Search (BFS) to locate all nodes at depth - 1.
         */
        public TreeNode addOneRow(TreeNode root, int val, int depth) {

            // Special case: insert a new root above the existing tree
            if (depth == 1) {
                TreeNode newRoot = new TreeNode(val);
                newRoot.left = root;
                return newRoot;
            }

            // Queue used for level-order (BFS) traversal
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);

            // Root starts at depth 1
            int currentDepth = 1;

            // Traverse level by level
            while (!queue.isEmpty()) {

                // Number of nodes in the current level
                int size = queue.size();

                // We've reached the level directly above where the new row
                // should be inserted.
                if (currentDepth == depth - 1) {

                    for (int i = 0; i < size; i++) {

                        TreeNode current = queue.poll();

                        // Save the existing children
                        TreeNode oldLeft = current.left;
                        TreeNode oldRight = current.right;

                        // Insert two new nodes
                        current.left = new TreeNode(val);
                        current.right = new TreeNode(val);

                        // Attach the original subtrees
                        current.left.left = oldLeft;
                        current.right.right = oldRight;
                    }

                    // No need to continue traversing
                    break;
                }

                // Add all children from the current level to the queue
                // so we can process the next level.
                for (int i = 0; i < size; i++) {

                    TreeNode current = queue.poll();

                    if (current.left != null) {
                        queue.offer(current.left);
                    }

                    if (current.right != null) {
                        queue.offer(current.right);
                    }
                }

                currentDepth++;
            }

            return root;
        }
    }

    /**
     * Builds a binary tree from an array.
     *
     * Example:
     * [4,2,6,3,1,5]
     */
    public static TreeNode buildTree(Integer[] arr) {

        if (arr == null || arr.length == 0 || arr[0] == null)
            return null;

        TreeNode root = new TreeNode(arr[0]);

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int i = 1;

        while (!queue.isEmpty() && i < arr.length) {

            TreeNode current = queue.poll();

            // Create left child
            if (i < arr.length && arr[i] != null) {
                current.left = new TreeNode(arr[i]);
                queue.offer(current.left);
            }
            i++;

            // Create right child
            if (i < arr.length && arr[i] != null) {
                current.right = new TreeNode(arr[i]);
                queue.offer(current.right);
            }
            i++;
        }

        return root;
    }

    /**
     * Converts a binary tree back into a list.
     */
    public static List<Integer> treeToList(TreeNode root) {

        List<Integer> result = new ArrayList<>();

        if (root == null)
            return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {

            TreeNode current = queue.poll();

            // Preserve nulls so the tree structure is maintained
            if (current == null) {
                result.add(null);
            } else {

                result.add(current.val);

                queue.offer(current.left);
                queue.offer(current.right);
            }
        }

        // Remove unnecessary nulls at the end.
        while (!result.isEmpty()
                && result.get(result.size() - 1) == null) {
            result.remove(result.size() - 1);
        }

        return result;
    }

    public static void main(String[] args) {

        Solution solution = new Solution();

        // ---------------- Example 1 ----------------
        Integer[] input1 = {4, 2, 6, 3, 1, 5};

        TreeNode root1 = buildTree(input1);

        TreeNode result1 = solution.addOneRow(root1, 1, 2);

        System.out.println("Example 1");
        System.out.println("Input : " + Arrays.toString(input1));
        System.out.println("Output: " + treeToList(result1));

        // ---------------- Example 2 ----------------
        Integer[] input2 = {4, 2, null, 3, 1};

        TreeNode root2 = buildTree(input2);

        TreeNode result2 = solution.addOneRow(root2, 1, 3);

        System.out.println();

        System.out.println("Example 2");
        System.out.println("Input : " + Arrays.toString(input2));
        System.out.println("Output: " + treeToList(result2));
    }
}