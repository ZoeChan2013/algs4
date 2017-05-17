package edu.assignment.deque;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by shuyi_chen on 2/2/17.
 */
public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        int count = 0;
        RandomizedQueue<String> inputs = new RandomizedQueue<>();

        while (StdIn.hasNextLine() && !StdIn.isEmpty()) {
            if (inputs.size() == k) {
                if (StdRandom.uniform(count + 1) >= (count + 1 - k)) {
                    inputs.dequeue();
                    inputs.enqueue(StdIn.readString());
                } else {
                    StdIn.readString();
                }
            } else {
                inputs.enqueue(StdIn.readString());
            }
            count++;
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(inputs.dequeue());
        }
    }
}
