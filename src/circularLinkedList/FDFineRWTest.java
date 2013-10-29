package circularLinkedList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import circularLinkedList.FDList.Cursor;
import circularLinkedList.FDList.DeletedItemException;
import circularLinkedList.FDList.Element;
import circularLinkedList.FDListFineRW.CursorFineRW;

public class FDFineRWTest extends Thread {

	
	static FDListFineRW fd;
	//static Random randomNumber = new Random();
	int counter = 0;
	//int objectCounter = 0;
	static FileWriter fstream;
	static BufferedWriter out;
	static int workLoad;
	
	static int workAmount = 10000;
	static int threadCount = 30;
	
	
	public FDFineRWTest(int workLoad){
		FDFineRWTest.workLoad = workLoad;
	}
	
	
	public FDFineRWTest() {
		// TODO Auto-generated constructor stub
	}

	public void createList(){
		fd = new FDListFineRW("0");
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
		//int random = randomNumber.nextInt(100);
		//workload 1
		if(FDFineRWTest.workLoad == 1){
			try{
				CursorFineRW c = fd.reader(fd.head);
				for(int i = 0; i < (workAmount/threadCount); i++){
					
						String k=c.curr().value;
						c.next();
					
				}
			}catch(Exception exception){
				System.out.println(exception.getMessage());
			}
		}
		
		
		//workload 2 80% reads + 20% writes)
		if(FDFineRWTest.workLoad == 2){
			try{
				CursorFineRW cursor1 = fd.reader(fd.head);
				for(int k = 0; k < (workAmount/threadCount); k++){
				int elementTracker = 0;
				do{
					elementTracker++;
					if(elementTracker == this.counter){
						if(this.counter % 5 == 0){
							if(this.counter % 10 == 0){
									//System.out.println("Inserted After started by thread::"+this.counter);
									cursor1.writerFineRW().insertAfter(Integer.toString(this.counter));
									cursor1.next();
									//System.out.println("Inserted After completed by thread::"+this.counter);
									
							}else if((this.counter % 4 == 0) && (this.counter % 5 != 0)){
									//System.out.println("Inserted before started by thread::"+this.counter);
									cursor1.writerFineRW().insertBefore(Integer.toString(this.counter));
									cursor1.next();
									//System.out.println("Inserted before completed by thread::"+this.counter);
									
							}else{
									//System.out.println("deleted started by thread::"+this.counter);
									Element element1 = cursor1.curr().previous;
									CursorFineRW cursor2 = fd.reader(element1);
									cursor1.writerFineRW().delete();
									cursor1 = cursor2;
									cursor1.next();
									//System.out.println("deleted completed by thread::"+this.counter);
									
							}
						}else{
								//System.out.println("Value Returned::"+cursor1.writerFineRW().readValue()+" by thread::"+this.counter);
								cursor1.next();
						}
					}
				}while(elementTracker != this.counter);		
				}
			}catch(Exception e){
				System.out.println(e.getMessage()+" completed by thread::"+this.counter);
			}
		}
		
		//Workload 3. 50% reads and 50% writes
		if(FDFineRWTest.workLoad == 3){
			try{
				CursorFineRW cursor1 = fd.reader(fd.head);
				for(int k = 0; k < (workAmount/threadCount); k++){
				int elementTracker = 0;
				do{
					elementTracker++;
					if(elementTracker == this.counter){
						if(this.counter % 2 == 0){
							if(this.counter % 5 == 0){
									//System.out.println("Inserted After started by thread::"+this.counter);
									cursor1.writerFineRW().insertAfter(Integer.toString(this.counter));
									cursor1.next();
									//System.out.println("Inserted After completed by thread::"+this.counter);
									
							}else if((this.counter % 4 == 0) && (this.counter % 5 != 0)){
									//System.out.println("Inserted before started by thread::"+this.counter);
									cursor1.writerFineRW().insertBefore(Integer.toString(this.counter));
									cursor1.next();
									//System.out.println("Inserted before completed by thread::"+this.counter);
									
							}else{
									//System.out.println("deleted started by thread::"+this.counter);
									Element element1 = cursor1.curr().previous;
									CursorFineRW cursor2 = fd.reader(element1);
									cursor1.writerFineRW().delete();
									cursor1 = cursor2;
									cursor1.next();
									//System.out.println("deleted completed by thread::"+this.counter);
									
							}
						}else{
								//System.out.println("Value Returned::"+cursor1.writerFineRW().readValue()+" by thread::"+this.counter);
							String p = cursor1.curr().value;
							cursor1.next();
						}
					}
				}while(elementTracker != this.counter);		
				}
			}catch(Exception e){
				System.out.println(e.getMessage()+" completed by thread::"+this.counter);
			}
		}
		
		//Workload 4. 80% writes + 20% read
				if(FDFineRWTest.workLoad == 4){
					try{
						CursorFineRW cursor1 = fd.reader(fd.head);
						for(int k = 0; k < (workAmount/threadCount); k++){
						int elementTracker = 0;
						do{
							elementTracker++;
							if(elementTracker == this.counter){
								if(this.counter % 5 == 0){
									//System.out.println("Value Returned::"+cursor1.writerFineRW().readValue()+" by thread::"+this.counter);
									cursor1.next();
									
								}else{
									if(this.counter % 5 == 0){
										//System.out.println("Inserted After started by thread::"+this.counter);
										cursor1.writerFineRW().insertAfter(Integer.toString(this.counter));
										cursor1.next();
										//System.out.println("Inserted After completed by thread::"+this.counter);
										
								}else if((this.counter % 4 == 0) && (this.counter % 5 != 0)){
										//System.out.println("Inserted before started by thread::"+this.counter);
										cursor1.writerFineRW().insertBefore(Integer.toString(this.counter));
										cursor1.next();
										//System.out.println("Inserted before completed by thread::"+this.counter);
										
								}else{
										//ystem.out.println("deleted started by thread::"+this.counter);
										Element element1 = cursor1.curr().previous;
										CursorFineRW cursor2 = fd.reader(element1);
										cursor1.writerFineRW().delete();
										cursor1 = cursor2;
										cursor1.next();
										//System.out.println("deleted completed by thread::"+this.counter);
										
								}	
								}
							}
						}while(elementTracker != this.counter);		
						}
					}catch(Exception e){
						//System.out.println(e.getMessage()+" completed by thread::"+this.counter);
					}
				}
	
	}
	
	public boolean checkSemantics(){
		try{
			CursorFineRW cursor = FDFineRWTest.fd.reader(fd.head);
			boolean check = true;
			do{
				if((cursor.curr().next.previous != cursor.curr()) || (cursor.curr().previous.next != cursor.curr())){
					check = false;
					break;
				}
				cursor.next();
			}while(cursor.curr() != FDFineRWTest.fd.head);
			return check;
		}catch(Exception exception){
			exception.printStackTrace();
			return false;
		}
	}
	
	
	public void test(){
		try {
			long finalTime = 0;
			long sum = 0;
		for(int k = 0; k < 100; k ++){
			FDFineRWTest []test = new FDFineRWTest[threadCount];
			Thread []testThread = new Thread[threadCount];
			//FDFineRWTest.workLoad = 3;
				for (int i = 0; i < test.length; i++){
					test[i] = new FDFineRWTest();
					//test[i].objectCounter = i;
					test[i].counter = i + 1;
					testThread[i] = new Thread(test[i]); 
				}	
				
				
					FDFineRWTest.out.write("WorkLoad:: "+FDFineRWTest.workLoad+"\n");
				
				//out.write("Workload::"+workLoad);
				long time1 = System.nanoTime();
				
				for(int j=0; j < testThread.length; j++){
					testThread[j].start();
				}
				
				for(int j = 0; j < testThread.length; j++){
					testThread[j].join();
				}
				System.out.println("Is the file logging");
				long time2 = System.nanoTime();
				finalTime = time2 - time1;
				sum = sum + finalTime;
				
			}
			out.write(Long.toString((sum/100))+",");
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
