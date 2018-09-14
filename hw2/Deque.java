

//import java.util.*;

import java.util.Iterator;

import java.util.NoSuchElementException;

//import java.lang.*;


public class Deque<Item> implements Iterable<Item> {

	private Node first = null;

	private Node last = null;

	private int size = 0;

	private class Node{

		public Item item = null;

		public Node next = null;

		public Node previous = null;

	}



   	//private LinkedList<Item> list = new LinkedList<Item>();

	// construct an empty deque
	public Deque(){}                           
   
	// is the deque empty?	
   	public boolean isEmpty(){
   		return first == null;
   	}                 
   
   	// return the number of items on the deque
   	public int size(){
   		return size;	
   	}

	// add the item to the front
   	public void addFirst(Item item){

   		if(item == null){
   			throw new IllegalArgumentException();
   		}

   		//list.addFirst(item);

   		Node newNode = new Node();

   		newNode.item = item;

   		newNode.next = first;

   		if(first != null)
   			first.previous = newNode;

   		first = newNode;

   		if(last == null){
   			last = newNode;
   		}

   		size++;
   	}          

   	// add the item to the end
   	public void addLast(Item item){

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
   	}           

	// remove and return the item from the front
   	public Item removeFirst(){

   		if(isEmpty()){
   			throw new NoSuchElementException();
   		}

   		//return list.removeFirst();


   		Node toreturn = first;

   		first = first.next;

   		if(first != null){
   			first.previous = null;
   		}else{
   			last = null;
   		}

   		size--;

   		return toreturn.item;
   	}                
   
   // remove and return the item from the end
   	public Item removeLast(){

   		if(isEmpty()){
   			throw new NoSuchElementException();
   		}

   		Node toreturn = last;

   		if(last.previous != null){
   			last.previous.next = null;
   		}else{
   			first = null;
   		}

   		last = last.previous;



  		size--;

   		return toreturn.item;
   		//return list.removeLast();
   	}                 

   	// return an iterator over items in order from front to end
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

			//System.out.println(iter == null);

			return iter != null;

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

   		Deque<Integer> d = new Deque<Integer>();

   		d.addFirst(10);

   		d.addFirst(14);

   		d.addLast(18);

   		Iterator<Integer> i = d.iterator();

   		while(i.hasNext()){

   			System.out.println(i.next());
   		}


   		System.out.println(d.removeFirst());

   		System.out.println(d.removeLast());

   		System.out.println(d.removeLast());





		Deque<Integer> deque = new Deque<Integer>();
         deque.addFirst(0);
         System.out.println(deque.removeLast());   //   ==> 0
         deque.addFirst(2);
         System.out.println(deque.removeLast()); //     ==> 2
         System.out.println(deque.isEmpty()); //    ==> false


         deque = new Deque<Integer>();
         deque.addFirst(0);
         System.out.println(deque.removeLast()); //     ==> 0
         System.out.println(deque.isEmpty());      //   ==> false


         deque = new Deque<Integer>();
         System.out.println(deque.isEmpty());        // ==> true
         System.out.println(deque.isEmpty());        // ==> true
         System.out.println(deque.isEmpty());        // ==> true
         System.out.println(deque.isEmpty());         //==> true
         deque.addFirst(4);
         System.out.println(deque.removeLast());      //==> 4
         deque.addFirst(6);
         System.out.println(deque.removeLast());     // ==> 6
         System.out.println(deque.isEmpty());        // ==> false



   	}   
}