package circularLinkedList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import circularLinkedList.FDList.DeletedItemException;
import circularLinkedList.FDList.Element;
import circularLinkedList.FDListFine.CursorFine;
import circularLinkedList.FDListFineRW.CursorFineRW;


public class FDListFineTest extends Thread {
	
	static FDListFine fd;
	static FileWriter fstream;
	static BufferedWriter out;
	static int workLoad;
	int counter = 0;
	
	static int workAmount = 100000;
	static int threadCount = 70;
	
	public void createList(){
		fd = new FDListFine("0");
		CursorFine cursor = fd.reader(fd.head);
		for (int i = 1; i < 1000; i++){
			try {
				cursor.writer().insertAfter(Integer.toString(i));
			} catch (DeletedItemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean checkSemantics(){
		try{
			CursorFine cursor = FDListFineTest.fd.reader(fd.head);
			boolean check = true;
			do{
				if((cursor.curr().next.previous != cursor.curr()) || (cursor.curr().previous.next != cursor.curr())){
					check = false;
					break;
				}
				cursor.next();
			}while(cursor.curr() != FDListFineTest.fd.head);
			return check;
		}catch(Exception exception){
			exception.printStackTrace();
			return false;
		}
	}
	
	public void run(){
		if(FDListFineTest.workLoad == 1){
			try{
				CursorFine c = fd.reader(fd.head);
				for(int i = 0; i < (workAmount/threadCount); i++){
						//System.out.println("Value Returned::"+c.curr().value);
						c.next();
				}
			}catch(Exception exception){
				System.out.println(exception.getMessage());
			}
		}
		
		
		//workload 2 80% reads + 20% writes)
		if(FDListFineTest.workLoad == 2){
			try{
				CursorFine cursor1 = fd.reader(fd.head);
				for(int k = 0; k < (workAmount/threadCount); k++){
				int elementTracker = 0;
				do{
					elementTracker++;
					if(elementTracker == this.counter){
						if(this.counter % 5 == 0){
							if(this.counter % 5 == 0){
							//		System.out.println("Inserted After started by thread::"+this.counter);
									cursor1.writerFine().insertAfter(Integer.toString(this.counter));
									cursor1.next();
							//		System.out.println("Inserted After completed by thread::"+this.counter);
									
							}else if((this.counter % 4 == 0) && (this.counter % 5 != 0)){
							//		System.out.println("Inserted before started by thread::"+this.counter);
									cursor1.writerFine().insertBefore(Integer.toString(this.counter));
									cursor1.next();
						//			System.out.println("Inserted before completed by thread::"+this.counter);
									
							}else{
							//		System.out.println("deleted started by thread::"+this.counter);
									Element element1 = cursor1.curr().previous;
									CursorFine cursor2 = fd.reader(element1);
									cursor1.writerFine().delete();
									cursor1 = cursor2;
									cursor1.next();
							//		System.out.println("deleted completed by thread::"+this.counter);
									
							}
						}else{
								//System.out.println("Value Returned::"+cursor1.curr().value+" by thread::"+this.counter);
								cursor1.next();
						}
					}
				}while(elementTracker != this.counter);		
				}
			}catch(Exception e){
			//	System.out.println(e.getMessage()+" completed by thread::"+this.counter);
			}
		}
		
		//Workload 3. 50% reads and 50% writes
		if(FDListFineTest.workLoad == 3){
			try{
				CursorFine cursor1 = fd.reader(fd.head);
				for(int k = 0; k < (workAmount/threadCount); k++){
				int elementTracker = 0;
				do{
					elementTracker++;
					if(elementTracker == this.counter){
						if(this.counter % 2 == 0){
							if(this.counter % 5 == 0){
				//					System.out.println("Inserted After started by thread::"+this.counter);
									cursor1.writerFine().insertAfter(Integer.toString(this.counter));
									cursor1.next();
				//					System.out.println("Inserted After completed by thread::"+this.counter);
									
							}else if((this.counter % 4 == 0) && (this.counter % 5 != 0)){
					//				System.out.println("Inserted before started by thread::"+this.counter);
									cursor1.writerFine().insertBefore(Integer.toString(this.counter));
									cursor1.next();
						//			System.out.println("Inserted before completed by thread::"+this.counter);
									
							}else{
							//		System.out.println("deleted started by thread::"+this.counter);
									Element element1 = cursor1.curr().previous;
									CursorFine cursor2 = fd.reader(element1);
									cursor1.writerFine().delete();
									cursor1 = cursor2;
									cursor1.next();
								//	System.out.println("deleted completed by thread::"+this.counter);
									
							}
						}else{
							//	System.out.println("Value Returned::"+cursor1.curr().value+" by thread::"+this.counter);
								cursor1.next();
						}
					}
				}while(elementTracker != this.counter);		
				}
			}catch(Exception e){
			//	System.out.println(e.getMessage()+" completed by thread::"+this.counter);
			}
		}
		
		
		//Workload 4. 80% writes + 20% read
		if(FDFineRWTest.workLoad == 4){
			try{
				CursorFine cursor1 = fd.reader(fd.head);
				for(int k = 0; k < (workAmount/threadCount); k++){
				int elementTracker = 0;
				do{
					elementTracker++;
					if(elementTracker == this.counter){
						if(this.counter % 5 == 0){
				//			System.out.println("Value Returned::"+cursor1.curr().value+" by thread::"+this.counter);
							cursor1.next();
							
						}else{
							if(this.counter % 5 == 0){
					//			System.out.println("Inserted After started by thread::"+this.counter);
								cursor1.writerFine().insertAfter(Integer.toString(this.counter));
								cursor1.next();
						//		System.out.println("Inserted After completed by thread::"+this.counter);
								
						}else if((this.counter % 4 == 0) && (this.counter % 5 != 0)){
							//	System.out.println("Inserted before started by thread::"+this.counter);
								cursor1.writerFine().insertBefore(Integer.toString(this.counter));
								cursor1.next();
						//		System.out.println("Inserted before completed by thread::"+this.counter);
								
						}else{
							//	System.out.println("deleted started by thread::"+this.counter);
								Element element1 = cursor1.curr().previous;
								CursorFine cursor2 = fd.reader(element1);
								cursor1.writerFine().delete();
								cursor1 = cursor2;
								cursor1.next();
							//	System.out.println("deleted completed by thread::"+this.counter);
								
						}	
						}
					}
				}while(elementTracker != this.counter);		
				}
			}catch(Exception e){
//				System.out.println(e.getMessage()+" completed by thread::"+this.counter);
			}
		}
		
	}
	
	
	public void test(){
		try {
			long finalTime = 0;
			long sum = 0;
		for(int k = 0; k < 100; k ++){
		FDListFineTest []test = new FDListFineTest[threadCount];
		Thread []testThread = new Thread[threadCount];
		//FDListFineTest.workLoad = 1;
			for (int i = 0; i < test.length; i++){
				test[i] = new FDListFineTest();
				//test[i].objectCounter = i;
				test[i].counter = i + 1;
				testThread[i] = new Thread(test[i]); 
			}	
			//out.write("Workload::"+workLoad);
			long time1 = System.nanoTime();
			
			for(int j=0; j < testThread.length; j++){
				testThread[j].start();
			}
			
			for(int j = 0; j < testThread.length; j++){
				testThread[j].join();
			}
			long time2 = System.nanoTime();
			finalTime = time2 - time1;
			sum = sum + finalTime;
		}
		FDListFineTest.out.write("WorkLoad:: "+FDListFineTest.workLoad+"\n");
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
	
