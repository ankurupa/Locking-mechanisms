

package circularLinkedList;

import java.io.BufferedWriter;
import java.io.FileWriter;

import circularLinkedList.FDListFineRW.CursorFineRW;

public class mainTest{

	/**
	 * @param args
	 */
	static FileWriter fstream;
	//static BufferedWriter out;
	int counter = 0;
	static int workload;
	
	FDCoarseRWTest fdCoarseRW;
	FDFineRWTest fdFineRW;
	FDListFineTest fdFine;
	FDListCoarseTest fdCoarse;
	
	
	

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	//	mainTest maintest = new mainTest();
		try{
			mainTest maintest = new mainTest();
			maintest.fdCoarseRW = new FDCoarseRWTest();
			maintest.fdFineRW = new FDFineRWTest();
			maintest.fdFine = new FDListFineTest();
			maintest.fdCoarse = new FDListCoarseTest();
			
			/*FDCoarseRWTest.fstream = new FileWriter("FDCoarseRW-Output.txt");
			FDCoarseRWTest.out = new BufferedWriter(FDCoarseRWTest.fstream);
			
			FDFineRWTest.fstream = new FileWriter("FDFineRW-Output.txt");
			FDFineRWTest.out = new BufferedWriter(FDFineRWTest.fstream);*/
			
			
			FDListFineTest.fstream = new FileWriter("FDListFine-Output.txt");
			FDListFineTest.out = new BufferedWriter(FDListFineTest.fstream);
			
			//FDListCoarseTest.fstream = new FileWriter("FDListCoarse-Output.txt");
			//FDListCoarseTest.out = new BufferedWriter(FDListCoarseTest.fstream);
			
			
		
			
			//	maintest.checkFDCoarseRWTest();
		//		System.out.println("FD coarse RW completed");
				
		//		System.out.println("FD fine RW completed");
				maintest.checkFDFineTest();
				System.out.println("Fine test completed");
			
		//		maintest.checkFDFineRWTest();
			//	System.out.println("Fine test RW completed");
		//		System.out.println("FD fine completed");
		//		maintest.checkFDCoarseTest();
		//		System.out.println("FD coarse completed");
				
			/*FDCoarseRWTest.out.flush();
			FDCoarseRWTest.out.close();
			FDFineRWTest.out.flush();
			FDFineRWTest.out.close();*/
			FDListFineTest.out.flush();
			FDListFineTest.out.close();
			//FDListCoarseTest.out.flush();
			//FDListCoarseTest.out.close();
			
			System.out.println("completed operation");
		}catch(Exception e){
			e.getMessage();
		}
	}
	
	/*public void checkFDCoarseRWTest(){
		try{
			
			
			fdCoarseRW.createList();
			FDCoarseRWTest.workLoad = 1;
			fdCoarseRW.test();
			FDCoarseRWTest.workLoad = 2;
			fdCoarseRW.test();
			FDCoarseRWTest.workLoad = 3;
			fdCoarseRW.test();
			FDCoarseRWTest.workLoad = 4;
			fdCoarseRW.test();
			
			System.out.println("operation completed");
		}catch(Exception exception){
			exception.printStackTrace();
		}
		
		
	}

	public void checkFDFineRWTest(){
		try{
			
			
			fdFineRW.createList();
			FDFineRWTest.workLoad = 1;
			
			fdFineRW.test();
			System.out.println("Workload 1 completed");
			fdFineRW.createList();
			FDFineRWTest.workLoad = 2;
			fdFineRW.test();
			System.out.println("Workload 2 completed");
			fdFineRW.createList();
			FDFineRWTest.workLoad = 3;
			fdFineRW.test();
			System.out.println("Workload 3 completed");
			fdFineRW.createList();
			FDFineRWTest.workLoad = 4;
			fdFineRW.test();
			System.out.println("Workload 4 completed");
			
			System.out.println("operation completed");
		}catch(Exception exception){
			exception.printStackTrace();
		}
		
		
	}*/
	
	
	public void checkFDFineTest(){
		try{
			
			
			fdFine.createList();
			FDListFineTest.workLoad = 1;
			fdFine.test();
			FDListFineTest.workLoad = 2;
			fdFine.test();
			FDListFineTest.workLoad = 3;
			fdFine.test();
			FDListFineTest.workLoad = 4;
			fdFine.test();
			System.out.println("Workload 4 completed");
			
			System.out.println("operation completed");
		}catch(Exception exception){
			exception.printStackTrace();
		}
		
		
	}
	
/*	public void checkFDCoarseTest(){
		try{
			
			
			fdCoarse.createList();
			FDListCoarseTest.workLoad = 1;
			fdCoarse.test();
			FDListCoarseTest.workLoad = 2;
			fdCoarse.test();
			FDListCoarseTest.workLoad = 3;
			fdCoarse.test();
			FDListCoarseTest.workLoad = 4;
			fdCoarse.test();
			
			System.out.println("operation completed");
		}catch(Exception exception){
			exception.printStackTrace();
		}
		
		
	}*/
	
	
	

}
