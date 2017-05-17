package edu.assignment.deque;

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by shuyi_chen on 2/1/17.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        n = 0;
        q = (Item[]) new Object[1];
    }

    // is the queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("cannot add null item.");
        }

        if (n == q.length) {
            resize(q.length * 2);
        }
        q[n++] = item;
    }

    /* when deleting random element you can just simply switch it with the last element in array
     * and decrease the array size counter. So deletion will take constant time.
     * throw a java.util.NoSuchElementException if the client attempts to sample or dequeue an item
     * from an empty randomized queue.
    */
    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("queue is empty already.");
        }

        int index = StdRandom.uniform(n);
        Item item = q[index];
        q[index] = q[--n];
        q[n] = null;

        if (n == q.length / 4 && n > 0) {
            resize(q.length / 2);
        }
        return item;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("queue is empty already.");
        }

        int index = StdRandom.uniform(n);
        return q[index];
    }

    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i;
        private int[] indexSequence;

        public RandomizedQueueIterator() {
            i = 0;
            indexSequence = new int[n];
            for (int j = 0; j < n; j++) {
                indexSequence[j] = j;
            }
            StdRandom.shuffle(indexSequence);
        }

        @Override
        public boolean hasNext() {
            return i < n;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("don't has next item.");
            }
            return q[indexSequence[i++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("not support remove.");
        }
    }

    // resize array
    private void resize(int capacity) {
        assert capacity >= n;
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = q[i];
        }
        q = copy;
    }

    // unit testing (optional)
    public static void main(String[] args) { }
}
