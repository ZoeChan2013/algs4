package edu.assignment.deque;

import java.io.IOException;

/**
 * Created by shuyi_chen on 3/17/17.
 */
public class Solution {

    static int friendCircles (String[] friends) {
        int friendNumber = (int)(Math.sqrt(friends.length));
        Integer[] friendRoot = new Integer[friendNumber];
        int circleNumber = 1;

        for (int i = 0; i < friends.length; i++) {
            int row = i / friendNumber;
            int column = i % friendNumber;
            if(friends[i].equals("Y")) {
                if (row < column) {
                    if (friendRoot[row] == null) {
                        friendRoot[row] = row;
                    }
                    if (friendRoot[column] == null) {
                        friendRoot[column] = row;
                    }
                    if (friendRoot[row] != null) {
                        friendRoot[column] = friendRoot[row];
                    }
                }
            }
        }

        for (int j = 1; j < friendRoot.length; j++) {
            int flag = 0;
            if (friendRoot[j] == null) {
                circleNumber++;
            }else if (flag < friendRoot[j]) {
                flag = friendRoot[j];
                circleNumber++;
            }
        }

        return circleNumber;
    }

    public static void main (String[] args) throws IOException {
        String[] friends = {"Y","N", "N", "Y"};
        int result = friendCircles(friends);
        System.out.print(result);

        //{"Y","Y", "N", "Y", "Y", "Y", "N", "Y","Y"}
        //"Y","Y", "N", "N", "Y", "Y", "Y", "N", "N", "Y", "Y", "N", "N","N","N","Y"
        //"Y","N","N","N","N","N","Y","N","N","N","N","N","Y","N","N","N","N","N","Y","N","N","N","N","N","Y"
    }
}
