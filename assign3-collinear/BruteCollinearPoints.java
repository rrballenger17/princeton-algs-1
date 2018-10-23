import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

import java.util.Comparator;

public class BruteCollinearPoints {

    private Stack<LineSegment> segmentsStack = new Stack<>();

    private LineSegment[] segmentsArr= null;

    private int numSegments = 0;

    private boolean floatingPointEqual(Double one, Double two){


        if(one.equals(Double.POSITIVE_INFINITY) && two.equals(Double.POSITIVE_INFINITY) ){
            return true;
        }

        if(one.equals(Double.NEGATIVE_INFINITY) && two.equals(Double.NEGATIVE_INFINITY) ){
            return true;
        }

        if(Math.abs(one - two) < .000001){
            return true;
        }

        return false;

    }


    public BruteCollinearPoints(Point[] points){


        if(points == null){
            throw new java.lang.IllegalArgumentException();
        }

        for(Point p : points){
            if(p == null){
                throw new IllegalArgumentException();
            }
        }





        for(int i=0; i< points.length; i++) {

            Point[] otherPoints = new Point[points.length - i - 1];

            Point one = points[i];

            Comparator<Point> comparator = one.slopeOrder();


            int oCount = 0;
            for (int j = i + 1; j < points.length; j++) {

                otherPoints[oCount] = points[j];
                oCount++;
            }



            Arrays.sort(otherPoints, comparator);




            Double prev = null;
            int count = 0;
            for (int j = 0; j < otherPoints.length; j++) {

                Double compSlope = one.slopeTo(otherPoints[j]);

                if(compSlope.equals(Double.NEGATIVE_INFINITY)){
                    throw new IllegalArgumentException();
                }

                //System.out.println("slope to: " + compSlope);

                if (prev == null || !floatingPointEqual(compSlope,prev) ){

                    prev = compSlope;
                    count = 2;
                    continue;
                }
                count++;

                if (count == 4) {


                    //System.out.println("start group");

                    //System.out.println(one);
                    Point leftMost = one;
                    Point rightMost = one;


                    for (int x = j - 2; x <= j; x++) {

                        Point dos = otherPoints[x];

                        //System.out.println(dos);

                        if (dos.compareTo(leftMost) < 0) {
                            leftMost = dos;

                        }

                        if (dos.compareTo(rightMost) > 0) {
                            rightMost = dos;
                        }
                    }

                    LineSegment seg = new LineSegment(leftMost, rightMost);

                    segmentsStack.push(seg);

                    numSegments = numSegments + 1;


                    prev = null;
                    count = 0;
                }

            }

        }

    }

    public int numberOfSegments(){


        return numSegments;
    }

    public LineSegment[] segments(){

        if(segmentsArr == null){

            segmentsArr = new LineSegment[numSegments];


            for(int i=0; i<numSegments; i++){

                segmentsArr[i] = segmentsStack.pop();

            }

        }



        segmentsStack = null;

        return segmentsArr.clone();

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

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            System.out.println(segment);
            //segment.draw();
        }
        //StdDraw.show();
    }

}


