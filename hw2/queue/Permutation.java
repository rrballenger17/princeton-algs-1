

import edu.princeton.cs.algs4.StdIn;

import java.util.NoSuchElementException;



public class Permutation {
   


	private static RandomizedQueue<String> queue = new RandomizedQueue<String>();

   public static void main(String[] args){

   		int num = Integer.parseInt(args[0]);


   		while(true){



   			String input;


   			try{


   				input = StdIn.readString();
   			}catch(NoSuchElementException e){

   				break;
   			}


   			for(String i: input.split(" ")){

   				queue.enqueue(i);

   			}

   		}


   		while(num > 0){

   			System.out.println( queue.dequeue() );

   			num--;

   		}



   }



}


