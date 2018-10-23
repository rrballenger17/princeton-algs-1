import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Iterator;

public class Solver {


    private Step curr;


    private class Step implements Comparable<Step>{

        private final Board board;

        private final Step previous;

        private final int numSteps;




        public Step(Board board, Step previous, int numSteps ){

            this.board = board;

            this.previous = previous;

            this.numSteps = numSteps;


        }

        @Override
        public int compareTo(Step o) {


            if(!(o instanceof Step)){
                System.out.println("compare to non-step");

            }


            Step input = o;

            return (this.numSteps + this.board.manhattan()) - (input.numSteps + input.board.manhattan());
        }
    }




    public Solver(Board initial){


        Step currTwin;



        MinPQ<Step> queue = new MinPQ<Step>();
        MinPQ<Step> queueTwin = new MinPQ<Step>();


        // initial board step
        Step current = new Step(initial, null, 0);
        Step currentTwin = new Step(initial.twin(), null, 0);

        queue.insert(current);
        queueTwin.insert(currentTwin);

        curr = queue.min();
        currTwin = queueTwin.min();


        while(curr.board.manhattan() != 0 && currTwin.board.manhattan() != 0){

            //System.out.println("enter loop " + curr.board.manhattan());

            curr = queue.min();
            currTwin = queueTwin.min();

            //System.out.println(curr.board.isGoal());


            queue.delMin();
            queueTwin.delMin();

            Iterable<Board> neighbors = curr.board.neighbors();
            Iterable<Board> neighborsTwin = currTwin.board.neighbors();


            Iterator<Board> nIter = neighbors.iterator();
            Iterator<Board> nIterTwin = neighborsTwin.iterator();


            //System.out.println("enter loop " + curr.board.manhattan());


            while(nIter.hasNext()){

                Board n = nIter.next();

                if(curr.previous == null || !curr.previous.board.equals(n)){

                    Step t = new Step(n, curr, curr.numSteps + 1);

                    queue.insert(t);
                }
            }


            while(nIterTwin.hasNext()){

                Board n = nIterTwin.next();

                if(currTwin.previous == null || !currTwin.previous.board.equals(n)){

                    Step t = new Step(n, currTwin, currTwin.numSteps + 1);

                    queueTwin.insert(t);
                }
            }

            //System.out.println("enter loop " + curr.board.manhattan());

            //System.out.println("finish adding neighbors");
        }

        //System.out.println("solved " + curr.board.manhattan());

    }           // find a solution to the initial board (using the A* algorithm)





    public boolean isSolvable() {


        return curr.board.manhattan() == 0;


    }           // is the initial board solvable?




    public int moves() {

        if(!isSolvable()){
            return -1;
        }

        return curr.numSteps;


    }                   // min number of moves to solve initial board; -1 if unsolvable





    public Iterable<Board> solution(){


        if(!isSolvable()){
            return null;
        }

        int count = 0;

        Step s = curr;


        while(s.previous != null){


            count++;

            s = s.previous;
        }

        count++;


        Board[] sol = new Board[count];

        s = curr;

        int index = count -1;
        while(s.previous != null){

            sol[index] = s.board;

            s = s.previous;

            index--;
        }

        sol[index] = s.board;


        return Arrays.asList(sol);

    }      // sequence of boards in a shortest solution; null if unsolvable




    public static void main(String[] args){

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    } // solve a slider puzzle (given below)
}


