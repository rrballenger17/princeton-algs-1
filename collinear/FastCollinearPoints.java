


import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

import java.util.Comparator;
import java.util.Iterator;

public class FastCollinearPoints {

    private Stack<LineSegment> segmentsStack = new Stack<>();

    private Stack<Point> segFirst = new Stack<>();

    private Stack<Point> segSecond = new Stack<>();

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


    private void addLineSegment(Point leftMost, Point rightMost){

        //System.out.println("adding segment");

        Iterator<Point> itFirst = segFirst.iterator();

        Iterator<Point> itSecond = segSecond.iterator();



        while(itFirst.hasNext()){


            Point g = itFirst.next();

            Point h = itSecond.next();

            if(floatingPointEqual(leftMost.slopeTo(g),rightMost.slopeTo(g)) || floatingPointEqual(leftMost.slopeTo(g),Double.NEGATIVE_INFINITY) || floatingPointEqual(rightMost.slopeTo(g),Double.NEGATIVE_INFINITY)){


                if(floatingPointEqual(leftMost.slopeTo(h),rightMost.slopeTo(h)) || floatingPointEqual(leftMost.slopeTo(h),Double.NEGATIVE_INFINITY) || floatingPointEqual(rightMost.slopeTo(h),Double.NEGATIVE_INFINITY)){


                    return;

                }
            }




        }


        LineSegment seg = new LineSegment(leftMost, rightMost);

        segmentsStack.push(seg);

        segFirst.push(leftMost);

        segSecond.push(rightMost);


        numSegments++;



    }


    public FastCollinearPoints(Point[] points){


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

                if (prev == null || !floatingPointEqual(compSlope,prev)) {


                    //System.out.println("not equal:" + prev + " " + compSlope);

                    prev = compSlope;
                    count = 2;
                    continue;
                }

                //System.out.println("equal:" + prev + " " + compSlope);


                count++;


                if(j < otherPoints.length - 1){

                    if( floatingPointEqual(one.slopeTo(otherPoints[j+1]),prev )){

                        continue;
                    }

                }

                if (count >= 4) {

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

                    addLineSegment(leftMost, rightMost);


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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            System.out.println(segment);
            //segment.draw();
        }
        //StdDraw.show();
    }

}





