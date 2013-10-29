package circularLinkedList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import circularLinkedList.FDList.DeletedItemException;
import circularLinkedList.FDList.Element;
import circularLinkedList.FDListCoarse.CursorCoarse;


public class FDListCoarseTest extends Thread {
	
	static FDListCoarse fd;
	static FileWriter fstream;
	static BufferedWriter out;
	static int workLoad;
	int counter = 0;
	
	public void createList(){
		fd = new FDListCoarse("0");
		CursorCoarse cursor = fd.reader(fd.head);
		for (int i = 0; i < 200; i++){
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
			CursorCoarse cursor = FDListCoarseTest.fd.reader(fd.head);
			boolean check = true;
			do{
				if((cursor.curr().next.previous != cursor.curr()) || (cursor.curr().previous.next != cursor.curr())){
					check = false;
					break;
				}
				cursor.next();
			}while(cursor.curr() != FDListCoarseTest.fd.head);
			return check;
		}catch(Exception exception){
			exception.printStackTrace();
			return false;
		}
	}
	
	public void run(){
		if(FDListCoarseTest.workLoad == 1){
			try{
				CursorCoarse c = fd.reader(fd.head);
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
		if(FDListCoarseTest.workLoad == 2){
			try{
				CursorCoarse c = fd.reader(fd.head);
				for(int i = 0; i < 100; i++){
					if(this.counter % 2 == 0){
						if(i % 5 == 0){
							//if(i%10 == 0){
							if((i%5 == 0)){
								Element element1 = c.curr().previous;
								CursorCoarse cursor1 = fd.reader(element1);
								c.writerCoarse().delete();
								c = cursor1;
								c.next();
							}
							if((i%5 == 0) && (i%10 == 0)){
								c.writerCoarse().insertAfter(Integer.toString(this.counter));
								c.next();
							}
							if((i%5 == 0) && (i%10 == 0) && (i%20 == 0)){
								c.writerCoarse().insertBefore(Integer.toString(this.counter));
								c.next();
							}
						}else{
							System.out.println("Value Returned::"+c.curr().value);
							c.next();
						}
					}else{
						if(i % 5 == 0){
							//if(i%10 == 0){
							if((i%5 == 0)){
								Element element1 = c.curr().previous;
								CursorCoarse cursor1 = fd.reader(element1);
								c.writerCoarse().delete();
								c = cursor1;
								c.previous();
							}
							if((i%5 == 0) && (i%10 == 0)){
								c.writerCoarse().insertAfter(Integer.toString(this.counter));
								c.previous();
							}
							if((i%5 == 0) && (i%10 == 0) && (i%20 == 0)){
								c.writerCoarse().insertBefore(Integer.toString(this.counter));
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
		
		//Workload 4. 100% writes
		if(FDListCoarseTest.workLoad ==4){
			try{
				CursorCoarse c = fd.reader(fd.head);
				for(int i = 0; i < 100; i++){
					if(this.counter % 2 == 0){
						
								Element element1 = c.curr().previous;
								CursorCoarse cursor1 = fd.reader(element1);
								c.writerCoarse().delete();
								c = cursor1;
								c.next();
							
					}else{
						
								Element element1 = c.curr().previous;
								CursorCoarse cursor1 = fd.reader(element1);
								c.writerCoarse().delete();
								c = cursor1;
								c.previous();
					}
				}
			}catch(Exception e){
				e.getMessage();	
			}
		}
		
		//Workload 3. 50% reads and 50% writes
				if(FDListCoarseTest.workLoad == 3){
					try{
						CursorCoarse c = fd.reader(fd.head);
						for(int i = 0; i < 100; i++){
							if(this.counter % 2 == 0){
								if(i % 2 == 0){
									//if(i%10 == 0){
									if((i%2 == 0)){
										Element element1 = c.curr().previous;
										CursorCoarse cursor1 = fd.reader(element1);
										c.writerCoarse().delete();
										c = cursor1;
										c.next();
									}
									if((i%2 == 0) && (i%4 == 0)){
										c.writerCoarse().insertAfter(Integer.toString(this.counter));
										c.next();
									}
									if((i%2 == 0) && (i%4 == 0) && (i%6 == 0)){
										c.writerCoarse().insertBefore(Integer.toString(this.counter));
										c.next();
									}
								}else{
									System.out.println("Value Returned::"+c.curr().value);
									c.next();
								}
							}else{
								if(i % 2 == 0){
									//if(i%10 == 0){
									if((i%2 == 0)){
										Element element1 = c.curr().previous;
										CursorCoarse cursor1 = fd.reader(element1);
										c.writerCoarse().delete();
										c = cursor1;
										c.previous();
									}
									if((i%2 == 0) && (i%4 == 0)){
										c.writerCoarse().insertAfter(Integer.toString(this.counter));
										c.previous();
									}
									if((i%2 == 0) && (i%4 == 0) && (i%6 == 0)){
										c.writerCoarse().insertBefore(Integer.toString(this.counter));
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
		FDListCoarseTest []test = new FDListCoarseTest[60];
		Thread []testThread = new Thread[60];
		//FDListCoarseTest.workLoad = 1;
			for (int i = 0; i < test.length; i++){
				test[i] = new FDListCoarseTest();
				//test[i].objectCounter = i;
				test[i].counter = i + 1;
				testThread[i] = new Thread(test[i]); 
			}	
			
			try {
				FDListCoarseTest.out.write("WorkLoad: "+FDListCoarseTest.workLoad+" \n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			long time1 = System.nanoTime();
			
			for(int j=0; j < testThread.length; j++){
				testThread[j].start();
			}	
			
			for(int j=0; j < testThread.length; j++){
				try {
					testThread[j].join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			try {
				long time2 = System.nanoTime();
				long finalTime = time2 - time1;
				FDListCoarseTest.out.write(Long.toString(finalTime)+",");
				FDListCoarseTest.out.write("\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
}
	
