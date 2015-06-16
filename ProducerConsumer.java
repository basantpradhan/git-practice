package com.basant.anonymous;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;


class ProducerConsumerImpl{

	private Object lock = new Object();
	
	
	private final  List<Integer> list = new LinkedList<Integer>();
	private final Integer LIMIT = 50;
	
	private boolean producerRunning = true;
	private boolean consumerRunning = true ;
	
	Random random = new Random();
	
	public void producer() throws InterruptedException{
		
		while(producerRunning){
			
			synchronized (lock) {
				if(list.size()==LIMIT)
					lock.wait();
				else{
					list.add(random.nextInt(100000000));
					lock.notify();
				}
			}// synchronized block
		}//while loop
	}//method end
	
	
	public void consumer () throws InterruptedException{
		
		while(consumerRunning){
			 
			synchronized (lock) {
			 
				 if(list.size()==0)
					 lock.wait();
				 else {
					    System.out.print("(old) list.size():"+list.size()+"-->");
					    int removed = list.remove(0);
				        System.out.println("(new)List.size():"+list.size()+" removed value:"+removed);
				        lock.notify();
				       
				 }
				 
			}
			
		}//while
	}
}

public class ProducerConsumer {

   
	public static void main(String args []) throws InterruptedException{
		
		
		final ProducerConsumerImpl obj = new ProducerConsumerImpl();
		
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
			   try {
				      obj.producer();
			   } catch (InterruptedException e) {
				e.printStackTrace();
			  }
			}
		});
		
		
		
		
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
			   try {
				      obj.consumer();
			   } catch (InterruptedException e) {
				e.printStackTrace();
			  }
			}
		});
		
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		
		
		
	}
}
