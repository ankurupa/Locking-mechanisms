package circularLinkedList;

import java.util.concurrent.atomic.AtomicInteger;

public class FDListCoarseRW extends FDList{
	
	//fields for coarse level read write locks
		volatile private AtomicInteger coarseReaderRequest = new AtomicInteger(0);				
		volatile private AtomicInteger coarseReaders  = new AtomicInteger(0);
		volatile private AtomicInteger coarseWriters = new AtomicInteger(0);
		volatile private AtomicInteger coarseWriteRequests = new AtomicInteger(0);

	public FDListCoarseRW(String Value) {
		super(Value);
		// TODO Auto-generated constructor stub
	}
	
	//defining reader function which will accept element as parameter and return corresponding CursorFineRW object
		public CursorCoarseRW reader(Element E){
			return new CursorCoarseRW(E, this);
		}
	
	
	//locking for Coarse grained read operation
	//The thread will lock the whole linked list for performing the read operation.
	//However, the thread will allow other concurrent threads to acquire the read locks
	private void lockCoarseRead(){
		try{
			this.coarseReaderRequest.compareAndSet(0, 1);
			if((this.coarseWriteRequests.get() == 1) || (this.coarseWriters.get() > 1)){
				synchronized (this) {
					wait();
				}					
			}		
			this.coarseReaders.incrementAndGet();
			this.coarseReaderRequest.compareAndSet(1, 0);
		}catch(Exception exception){
			exception.printStackTrace();
		}
		
	}
	
	//unlocking the coarse grained read lock
	private synchronized void unlockCoarseRead(){
		this.coarseReaders.decrementAndGet();
		synchronized (this) {
			notifyAll();
		}
	}
	
	//locking for Coarse grained write operation. The thread will wait for current operations to end
	//Once it acquire the write lock, it will not allow any other concurrent threads to acquire read or write locks.
	private void lockCoarseWrite(){
		try{
			this.coarseWriteRequests.compareAndSet(0, 1);
			if((this.coarseReaders.get() > 0) || (this.coarseWriters.get() > 0)){
				synchronized (this) {
					wait();
				}
			}
			this.coarseWriteRequests.decrementAndGet();
			this.coarseWriters.incrementAndGet();
		}catch(Exception exception){
			exception.printStackTrace();
		}	
	}
	
	//unlocking the coarse grained write lock
	private void unlockCoarseWrite(){
		this.coarseWriters.decrementAndGet();
		synchronized (this) {
			notifyAll();
		}
	}
	
	public class CursorCoarseRW extends Cursor{
		
		// data values
		private WriterCoarseRW writerCoarseRW;
		private FDListCoarseRW parentList;
		
		// constructor
		public CursorCoarseRW(Element E, FDListCoarseRW P) {
			super(E, P);
			parentList = P;
			writerCoarseRW = new WriterCoarseRW(this);	
		}
		
		// returns the writer object for this cursor.
		public WriterCoarseRW writerCoarseRW() {
			return writerCoarseRW;
		}	

	}
	
	
	public class WriterCoarseRW extends Writer{
		
		private CursorCoarseRW parentCursor;
		public String action = new String();
		
		public WriterCoarseRW(CursorCoarseRW dad) {
			super(dad);
			parentCursor = dad;
			// TODO Auto-generated constructor stub
		}
		
		
		//function for reading the value at the desired position by putting coarse grained RW lock
		public String readValue(){
			try{
				//System.out.println("In fine grained RW version of readValue and the value is::"+parentCursor.curr().value);
				lockCoarseRead();
				String value = parentCursor.curr().value;
				unlockCoarseRead();
				return value;
			}catch(Exception exception){
				exception.printStackTrace();
				return null;
			}
		}
		
		//function for inserting after desired value by putting coarse grained RW lock
		public boolean insertAfter(String Value) throws DeletedItemException{
			//try{
				//System.out.println("In fine grained RW version of insert after");
				lockCoarseWrite();
				super.insertAfter(Value);
				unlockCoarseWrite();		
				return true;
			//}catch(Exception exception){
			//	exception.printStackTrace();
			//	return false;
			//}	
		}
		
		//function for inserting before the desired value by putting coarse grained RW lock
		public boolean insertBefore(String Value) throws DeletedItemException{
			//try{
				//System.out.println("In fine grained RW version of insert before");
				lockCoarseWrite();
				super.insertBefore(Value);		
				unlockCoarseWrite();
				return true;
			//}/*catch(Exception exception){
			//	exception.printStackTrace();
			//	return false;
			//}*/	
		}
		
		//function for deleting the desired value by putting coarse grained RW lock
		public boolean delete() throws DeletedItemException, HeadDeleteException{			
		//	try{
				//System.out.println("In fine grained RW version of delete");
				if (parentCursor.curr() == parentCursor.parentList.head){
					throw new HeadDeleteException();
				}
				else {
					lockCoarseWrite();			
					super.delete();
					unlockCoarseWrite();
				return true;
				}
		//	}catch(HeadDeleteException headDeleteException){
		//		System.out.println("The user cannot delete the head element of the linked list");
		//		return false;
	//		}/*catch(Exception exception){
	//			exception.printStackTrace();
	//			return false;
	//		}*/	
		}
		
	}
}
