

//import java.lang.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;


public class RandomizedQueue<Item> implements Iterable<Item> {

	//private LinkedList<Item> list = new LinkedList<Item>();
	private Node first = null;

	private Node last = null;

	private boolean forward = true;

	private Node random = null;

	private int size = 0;

	private class Node{

		public Item item = null;

		public Node next = null;

		public Node previous = null;

	}



	private void moveRandom(){

		if(random == null){

			random = first;

			return;
		}


		if(forward){
			if(random.next != null){
				random = random.next;
			}else{
				forward = false;
				random = random.previous;
			}
		}else{
			if(random.previous != null){
				random = random.previous;
			}else{
				forward = true;
				random = random.next;
			}
		}


		if(random == null){
			random = first;
		}

		if( StdRandom.uniform() > .7){
			moveRandom();
		}

	}

	// construct an empty randomized queue
   public RandomizedQueue(){
   }                 

   // is the randomized queue empty?
   public boolean isEmpty(){
   		return size == 0;
   }

   // return the number of items on the randomized queue
   public int size(){
   		return size;	
   }

   // add the item
   public void enqueue(Item item){

   		if(item == null){
   			throw new IllegalArgumentException();
   		}

   		//list.addLast(item);

   		Node newNode = new Node();

   		newNode.item = item;

   		if(last != null){
   			last.next = newNode;
   		}

   		newNode.previous = last;

   		last = newNode;

   		if(first == null){
   			first = newNode;
   		}

   		size++;

   		moveRandom();
   }           



   // remove and return a random item
   public Item dequeue(){


      if(size == 0){
         random= null;
                     throw new NoSuchElementException();

      }


   		if(first == null){
   			random = null;
   			throw new NoSuchElementException();
   		}


		Node rand = random;

		moveRandom();

		Node before = rand.previous;

		Node after = rand.next;

		if(before != null){
			before.next = after;
		}

		if(after != null){
			after.previous = before;
		}

      if(first == rand){
         first = first.next;
      }

      if(last == rand){
         last = last.previous;
      }

		size--;

      if(random == rand){
         random = null;
         moveRandom();
      }



		return rand.item;
   }

   // return a random item (but do not remove it)
   public Item sample(){

   		if(first == null){
   			random = null;
   			throw new NoSuchElementException();
   		}

		Node rand = random;

		moveRandom();

		return rand.item;
   }         

   // return an independent iterator over items in random order
   public Iterator<Item> iterator(){
   		return new ListIterator();
   }

   	private class ListIterator implements Iterator<Item>
   	{

   		private Node iter = null;

   		public ListIterator(){
   			iter = first;
   		}

   		public boolean hasNext(){

   			if(first == null){
   				return false;
   			}
			
			if(iter == null){
				return false;
			}   			

			return iter == null;

   		}

   		public void remove(){
   			throw new UnsupportedOperationException();
   		}

   		public Item next(){

   			if(iter == null){
   				throw new NoSuchElementException();
   			}

   			Node toReturn = iter;

   			iter = iter.next;

   			return  toReturn.item;
   		}

   	}


   // unit testing (optional)
   public static void main(String[] args){



RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        System.out.println( rq.size());        //==> 0
        rq.enqueue(11);
         rq.enqueue(19);
        System.out.println( rq.dequeue()) ;    //==> 19
        System.out.println( rq.dequeue());     //==> 11
         rq.enqueue(0);
       System.out.println(  rq.dequeue());     //==> 11



   }

}



