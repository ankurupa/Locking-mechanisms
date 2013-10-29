package circularLinkedList;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import circularLinkedList.FDList.Cursor;
import circularLinkedList.FDList.DeletedItemException;

public class FDListTest extends Thread {
	
	static FDList fd;
	static FileWriter fstream;
	static BufferedWriter out;
	static int workLoad;
	int counter = 0;
	
	public void createList(){
		fd = new FDListFine("0");
		Cursor cursor = fd.reader(fd.head);
		for (int i = 1; i < 100; i++){
			try {
				cursor.writer().insertAfter(Integer.toString(i));
			} catch (DeletedItemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public void run(){
		if(FDListTest.workLoad == 1){
			try{
				Cursor c = fd.reader(fd.head);
				for(int i = 0; i < 100; i++){
					if(this.counter % 2 == 0){
						System.out.println("Value Returned::"+c.curr().value);
						c.next();
					}
					else{
						System.out.println("Value Returned::"+c.curr().value);
						c.previous();
					}
				}
			}catch(Exception exception){
				System.out.println(exception.getMessage());
			}
		}
		
		
		//workload 2 80% reads + 20% writes)
		if(FDListTest.workLoad == 2){
			try{
				Cursor c = fd.reader(fd.head);
				for(int i = 0; i < 100; i++){
					if(this.counter % 2 == 0){
						if(i % 5 == 0){
							if(i%10 == 0){
								c.writer().delete();
								//c.next();
							}if(i % 15 == 0){
								c.writer().insertAfter(Integer.toString(this.counter));
								c.next();
							}
							if(i % 20 == 0){
								c.writer().insertBefore(Integer.toString(this.counter));
								c.next();
							}
						}else{
							System.out.println("Value Returned::"+c.curr().value);
							c.next();
						}
					}else{
						if(i%5 ==0){
							if(i % 10 ==0){
								c.writer().delete();
								//c.previous();
							}if(i % 15 == 0){
								c.writer().insertAfter(Integer.toString(this.counter));
								c.previous();
							}
							if(i % 20 == 0){
								c.writer().insertBefore(Integer.toString(this.counter));
								c.previous();
							}
						}else{
							System.out.println("Value Returned::"+c.curr().value);
							c.previous();
						}
					}
				}
			}catch(Exception e){
				e.getMessage();	
			}
		}
		
		//Workload 3. 50% reads and 50% writes
		if(FDListTest.workLoad == 3){
			try{
				Cursor c = fd.reader(fd.head);
				for(int i = 0; i < 100; i++){
					if(this.counter % 2 == 0){
						if(i % 2 == 0){
							if(i % 4 == 0){
								c.writer().delete();
								//c.next();
							}else{
								c.writer().insertBefore(Integer.toString(this.counter));
							}
						}else{
							System.out.println("Value Returned::"+c.curr().value);
							c.next();
						}
					}else{
						if(i%2 ==0){
							if(i % 4 == 0){
								c.writer().delete();
								//c.previous();
							}else{
								c.writer().insertAfter(Integer.toString(this.counter));
								c.previous();
							}
						}else{
							System.out.println("Value Returned::"+c.curr().value);
							c.previous();
						}
					}
				}
			}catch(Exception e){
				e.getMessage();	
			}
		}
		
	}
	
	
	public void test(){
		FDListTest []test = new FDListTest[50];
		Thread []testThread = new Thread[50];
		//FDListTest.workLoad = 1;
			for (int i = 0; i < test.length; i++){
				test[i] = new FDListTest();
				//test[i].objectCounter = i;
				test[i].counter = i + 1;
				testThread[i] = new Thread(test[i]); 
			}	
			
			try {
				FDListTest.out.write("WorkLoad:: "+FDListTest.workLoad+"\n");
			
			out.write("Workload::"+workLoad);
			long time1 = System.nanoTime();
			
			for(int j=0; j < testThread.length; j++){
				testThread[j].start();
			}
			
			for(int j = 0; j < testThread.length; j++){
				testThread[j].join();
			}
			long time2 = System.nanoTime();
			long finalTime = time2 - time1;
			out.write(Long.toString(finalTime)+",");
			out.write("\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

}
