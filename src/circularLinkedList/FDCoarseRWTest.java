package circularLinkedList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import circularLinkedList.FDList.Cursor;
import circularLinkedList.FDList.DeletedItemException;
import circularLinkedList.FDList.Element;
import circularLinkedList.FDListCoarseRW.CursorCoarseRW;
import circularLinkedList.FDListFineRW.CursorFineRW;


public class FDCoarseRWTest extends Thread {

	
	static FDListCoarseRW fd;
	//static Random randomNumber = new Random();
	int counter = 0;
	//int objectCounter = 0;
	static FileWriter fstream;
	static BufferedWriter out;
	static int workLoad;
	
	public FDCoarseRWTest(int workLoad){
		FDCoarseRWTest.workLoad = workLoad;
	}
	
	
	public FDCoarseRWTest() {
		// TODO Auto-generated constructor stub
	}


	public void createList(){
		fd = new FDListCoarseRW("0");
		Cursor cursor = fd.reader(fd.head);
		for (int i = 1; i < 50; i++){
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
		if(FDCoarseRWTest.workLoad == 1){
			try{
				CursorCoarseRW c = fd.reader(fd.head);
				for(int i = 0; i < 1000; i++){
					if(this.counter % 2 == 0){
						System.out.println("Value Returned::"+c.curr().value);
						c.next();
					}
				}
			}catch(Exception exception){
				System.out.println(exception.getMessage());
			}
		}
		
		
		//workload 2 80% reads + 20% writes)
		if(FDCoarseRWTest.workLoad == 2){
			try{
				CursorCoarseRW cursor1 = fd.reader(fd.head);
				for(int k = 0; k < 1000; k++){
				int elementTracker = 0;
				do{
					elementTracker++;
					if(elementTracker == this.counter){
						if(this.counter % 5 == 0){
							if(this.counter % 10 == 0){
									System.out.println("Inserted After started by thread::"+this.counter);
									cursor1.writerCoarseRW().insertAfter(Integer.toString(this.counter));
									cursor1.next();
									System.out.println("Inserted After completed by thread::"+this.counter);
									
							}else if((this.counter % 4 == 0) && (this.counter % 5 != 0)){
									System.out.println("Inserted before started by thread::"+this.counter);
									cursor1.writerCoarseRW().insertBefore(Integer.toString(this.counter));
									cursor1.next();
									System.out.println("Inserted before completed by thread::"+this.counter);
									
							}else{
									System.out.println("deleted started by thread::"+this.counter);
									Element element1 = cursor1.curr().previous;
									CursorCoarseRW cursor2 = fd.reader(element1);
									cursor1.writerCoarseRW().delete();
									cursor1 = cursor2;
									cursor1.next();
									System.out.println("deleted completed by thread::"+this.counter);
									
							}
						}else{
								System.out.println("Value Returned::"+cursor1.curr().value+" by thread::"+this.counter);
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
		if(FDCoarseRWTest.workLoad == 3){
			try{
				CursorCoarseRW cursor1 = fd.reader(fd.head);
				for(int k = 0; k < 1000; k++){
				int elementTracker = 0;
				do{
					elementTracker++;
					if(elementTracker == this.counter){
						if(this.counter % 2 == 0){
							if(this.counter % 5 == 0){
									System.out.println("Inserted After started by thread::"+this.counter);
									cursor1.writerCoarseRW().insertAfter(Integer.toString(this.counter));
									cursor1.next();
									System.out.println("Inserted After completed by thread::"+this.counter);
									
							}else if((this.counter % 4 == 0) && (this.counter % 5 != 0)){
									System.out.println("Inserted before started by thread::"+this.counter);
									cursor1.writerCoarseRW().insertBefore(Integer.toString(this.counter));
									cursor1.next();
									System.out.println("Inserted before completed by thread::"+this.counter);
									
							}else{
									System.out.println("deleted started by thread::"+this.counter);
									Element element1 = cursor1.curr().previous;
									CursorCoarseRW cursor2 = fd.reader(element1);
									cursor1.writerCoarseRW().delete();
									cursor1 = cursor2;
									cursor1.next();
									System.out.println("deleted completed by thread::"+this.counter);
									
							}
						}else{
								System.out.println("Value Returned::"+cursor1.curr().value+" by thread::"+this.counter);
								cursor1.next();
						}
					}
				}while(elementTracker != this.counter);		
				}
			}catch(Exception e){
				System.out.println(e.getMessage()+" completed by thread::"+this.counter);
			}
		}
		
		//Workload 4. 100% Writes
			/*	if(FDCoarseRWTest.workLoad == 4){
					try{
						CursorCoarseRW c = fd.reader(fd.head);
						for(int i = 0; i < 100; i++){
							if(this.counter % 2 == 0){
										Element element1 = c.curr().previous;
										CursorCoarseRW cursor1 = fd.reader(element1);
										c.writerCoarseRW().delete();
										c = cursor1;
										c.next();
							}else{
								
										Element element1 = c.curr().previous;
										CursorCoarseRW cursor1 = fd.reader(element1);
										c.writerCoarseRW().delete();
										c = cursor1;
							}
							
							
						}
					}catch(Exception e){
						e.getMessage();	
					}
				}*/
		
	}
	
	public boolean checkSemantics(){
		try{
			Cursor cursor = FDCoarseRWTest.fd.reader(fd.head);
			boolean check = true;
			do{
				if((cursor.curr().next.previous != cursor.curr()) || (cursor.curr().previous.next != cursor.curr())){
					check = false;
					break;
				}
				cursor.next();
			}while(cursor.curr() != FDCoarseRWTest.fd.head);
			return check;
		}catch(Exception exception){
			exception.printStackTrace();
			return false;
		}
	}
	
	
	public void test(){
		FDCoarseRWTest []test = new FDCoarseRWTest[64];
		Thread []testThread = new Thread[64];
		//FDCoarseRWTest.workLoad = 1;
			for (int i = 0; i < test.length; i++){
				test[i] = new FDCoarseRWTest();
				//test[i].objectCounter = i;
				test[i].counter = i + 1;
				testThread[i] = new Thread(test[i]); 
			}	
			
			try {
				FDCoarseRWTest.out.write("WorkLoad: "+FDCoarseRWTest.workLoad+"\n");
			
			//out.write("Workload::"+workLoad);
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
