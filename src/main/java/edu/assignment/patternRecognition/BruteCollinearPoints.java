package edu.assignment.patternRecognition;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by shuyi_chen on 2/11/17.
 */
public class BruteCollinearPoints {
    private int count = 0;
    private ArrayList<LineSegment> foundSegments = new ArrayList<>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.NullPointerException();
        }

        checkDuplicatedEntries(points);

        int n = points.length;
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);

        for (int p = 0; p < n-3; p++) {
            if (pointsCopy[p] == null) {
                throw new java.lang.NullPointerException();
            }
            for (int q = p+1; q < n-2; q++) {
                for (int r = q+1; r < n-1; r++) {
                    if (Double.compare(pointsCopy[p].slopeTo(pointsCopy[q]), pointsCopy[q].slopeTo(pointsCopy[r])) != 0) {
                        continue;
                    }
                    for (int s = r+1; s < n; s++) {
                        if (Double.compare(pointsCopy[p].slopeTo(pointsCopy[q]), pointsCopy[q].slopeTo(pointsCopy[r])) == 0
                                && Double.compare(pointsCopy[q].slopeTo(pointsCopy[r]), pointsCopy[r].slopeTo(pointsCopy[s])) == 0) {
                            foundSegments.add(new LineSegment(pointsCopy[p], pointsCopy[s]));
                            count++;
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return count;
    }

    // the line segments
    public LineSegment[] segments() {
        return foundSegments.toArray(new LineSegment[foundSegments.size()]);
    }

    private void checkDuplicatedEntries(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Duplicated entries in given points.");
                }
            }
        }
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
