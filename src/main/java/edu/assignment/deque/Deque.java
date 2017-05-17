package edu.assignment.deque;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by shuyi_chen on 1/31/17.
 */
public class Deque<Item> implements Iterable<Item> {
    private int n; // size of deque
    private Node first;
    private Node last;

    private class Node {
        private Item item;
        private Node prev;
        private Node next;
    }

    // construct an empty deque
    public Deque() {
        n = 0;
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("Cannot add null item into deque!");
        }

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.prev = null;
        first.next = oldFirst;
        if (isEmpty()) {
            last = first;
        } else {
            oldFirst.prev = first;
        }
        n++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("Cannot add null item into deque!");
        }

        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove item from empty deque!");
        }

        Item item = first.item; // save item tp return
        first = first.next;
        if (first == null) {
            last = null;
        } else {
            first.prev = null;
        }
        n--;
        return item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove item from empty deque!");
        }

        Item item = last.item;
        last = last.prev;
        if (last != null) {
            last.next = null;

        } else {
            first = null;
        }
        n--;
        return item;
    }

    // return an iterator over items in order from front to end
    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Don't support remove function!");
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("The deque is empty");
            }

            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (optional)
    public static void main(String[] args) { }
}

