
import java.lang.Math;
import java.util.Arrays;


public class Board {
    
    private final int[][] blocks;




    private final int MAX_NEIGHBORS = 4;



    public Board(int[][] blocks){

        int[][] similar = blocks.clone();


        for(int b=0; b<similar.length; b++){
            similar[b] = blocks[b].clone();
        }



        this.blocks = similar;




    }           // construct a board from an n-by-n array of blocks
                                           // (where blocks[i][j] = block in row i, column j)


    public int dimension(){
    	return blocks.length;
    }                 // board dimension n
    

    public int hamming(){

        int incorrect = 0;

        int expectedVal = 1;

        for(int i=0; i<blocks.length; i++){

            for(int j=0; j<blocks[0].length; j++){



                if(blocks[i][j] == 0){
                    expectedVal++;
                    continue;
                }

                if(blocks[i][j] != expectedVal){
                    incorrect++;
                }

                expectedVal++;
            }

        }

        return incorrect;
    }                   // number of blocks out of place




    public int manhattan(){

        int[][] correctBoard;

        correctBoard = new int[blocks.length][blocks[0].length];


        for(int i=0; i<correctBoard.length; i++) {

            for (int j = 0; j < correctBoard[0].length; j++) {

                correctBoard[i][j] = i * correctBoard.length + j + 1;



            }
        }

        correctBoard[correctBoard.length-1][correctBoard[0].length-1] = 0;

        //


        int totalDiff = 0;

        for(int i=0; i<blocks.length; i++) {

            for (int j = 0; j < blocks[0].length; j++) {


                if(blocks[i][j] == 0){
                    continue;
                }

                if(  blocks[i][j] == correctBoard[i][j] ){
                    continue;
                }



                for(int i2=0; i2<correctBoard.length; i2++) {

                    for (int j2 = 0; j2 < correctBoard[0].length; j2++) {


                        if(  blocks[i][j] == correctBoard[i2][j2] ){


                            totalDiff += Math.abs(i - i2) + Math.abs(j - j2);

                            // skip remaining checks
                            i2 = correctBoard.length;
                            j2  = correctBoard[0].length;


                        }


                    }

                    //if(i2 == correctBoard.length-1){
                       // System.out.println("error with manhattan");
                   // }


                }

            }
        }


        return totalDiff;


    }                 // sum of Manhattan distances between blocks and goal


    public boolean isGoal()  {

        int incorrect = 0;

        int expectedVal = 1;

        for(int i=0; i<blocks.length; i++){

            for(int j=0; j<blocks[0].length; j++){



                if(blocks[i][j] == 0){
                    expectedVal++;
                    continue;
                }

                if(blocks[i][j] != expectedVal){
                    incorrect++;
                }

                expectedVal++;
            }

        }

        return incorrect == 0;

    }               // is this board the goal board?



    public Board twin()   {

        int[][] similar = blocks.clone();


        for(int b=0; b<similar.length; b++){
            similar[b] = blocks[b].clone();
        }


        if(similar[0][0] != 0 && similar[0][1] != 0) {

            int temp = similar[0][0];

            similar[0][0] = similar[0][1];

            similar[0][1] = temp;


        }else{


            int temp = similar[1][0];

            similar[1][0] = similar[1][1];

            similar[1][1] = temp;




        }

        return new Board(similar);

    }                  // a board that is obtained by exchanging any pair of blocks




    public boolean equals(Object y){


        if(!(y instanceof Board)){
            return false;
        }

        int[][] input = ((Board) y).blocks;



        if(input.length != blocks.length){
            return false;
        }

        if(input[0].length != blocks[0].length){
            return false;
        }


        for(int i=0; i<blocks.length; i++) {

            for (int j = 0; j < blocks[0].length; j++) {

                if(input[i][j] != blocks[i][j]){

                    return false;
                }
            }
        }

        return true;

    }         // does this board equal y?




    public Iterable<Board> neighbors(){

        Board[] neighs;

        neighs = new Board[MAX_NEIGHBORS];

        int i0 = -1;

        int j0 = -1;

        for(int i=0; i<blocks.length; i++) {

            for (int j = 0; j < blocks[0].length; j++) {

                if(blocks[i][j] == 0){
                    i0 = i;
                    j0 = j;
                }

            }
        }


        int neighsCount = 0;

        for(int i=-1; i<=1; i++) {

            for (int j = -1; j <= 1; j++) {

                if((i == 0 && j == 0) || (i!=0 && j!=0)){
                    continue;
                }

                int iNeigh = i0 + i;

                int jNeigh = j0 + j;

                if( iNeigh >= 0 && iNeigh < blocks.length){

                    if( jNeigh >= 0 && jNeigh < blocks[0].length){


                        int[][] neighborArr = blocks.clone();

                        for(int b=0; b<neighborArr[0].length; b++){
                            neighborArr[b] = blocks[b].clone();
                        }

                        neighborArr[i0][j0] = neighborArr[iNeigh][jNeigh];

                        neighborArr[iNeigh][jNeigh] = 0;

                        neighs[neighsCount] = new Board(neighborArr);

                        neighsCount++;


                    }

                }

            }

        }



        int count = 0;
        for(int x=0; x<neighs.length; x++){

            if(neighs[x] != null){
                count++;
            }
        }


        Board[] nei = new Board[count];


        for(int x=0; x<count; x++){

            nei[x] = neighs[x];
        }


        return Arrays.asList(nei);

    }      // all neighboring boards




    public String toString(){

        StringBuilder out = new StringBuilder("");

        out.append(dimension());

        out.append("\n");


        for(int i=0; i< blocks.length; i++){

            for(int j=0; j<blocks[0].length; j++){

                    out.append(" ");

                out.append(blocks[i][j]);

            }

            out.append("\n");

        }

        return out.toString();
    }              // string representation of this board (in the output format specified below)




    public static void main(String[] args){

    }



    // unit tests (not graded)








}






