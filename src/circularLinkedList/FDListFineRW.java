package circularLinkedList;
public class FDListFineRW extends FDList{

	//constructor
	public FDListFineRW(String Value) {
		super(Value);
		// TODO Auto-generated constructor stub
	}
	
	//defining reader function which will accept element as parameter and return corresponding CursorFineRW object
	public CursorFineRW reader(Element E){
		return new CursorFineRW(E, this);
	}

	//method for acquiring read lock
	private void lockRead(Element element) throws DeletedItemException, InterruptedException{
		
		//setting the readerRequest parameter to 1. This parameter indicates that there are threads who are looking
		//for read lock on an element
			element.readerRequest.compareAndSet(0, 1);
			while((element.writeRequests.get() == 1) || (element.writers.get() > 0)){
				synchronized (this) {
					wait();
				}
			}
			if((element.next == null) && (element.previous == null)){
				throw new DeletedItemException();
			}
			//After the thread wakes up, it again checks if the element has been deleted by some other concurrent thread.
		/*	if((element.next == null) || (element.previous == null)){
				throw new DeletedItemException();
			}*/
			//increment the count of readers on the element
			element.readers.incrementAndGet();
			element.readerRequest.compareAndSet(1, 0);
			
		
			
			//the thread will wait if there are any current writer requests or any writers performing their
			//respective operations on element. The thread will wait till all the writers finish with their operations
		
			
				
			
			
			//sets the readerRequest to 0.
			
	}
	
	//method for unlocking read lock
	private void unlockRead(Element element){
		//decrement the count of readers on an element
		
	//	synchronized (element) {
		
			element.readers.decrementAndGet();
	
		
			synchronized (this){
				notifyAll();
		
			
		}
			
	}
	
	//method for acquiring write lock. The advantage of passing three elements to the lockWrite function is the increase in parallelism of the system.
	//The function checks all elements and if any of the element is not free, the thread does not put locks on any of the element.
	//If we pass one element at a time, the thread may lock the first element but wait for acquiring lock on second element. In such a case no other
	//thread will be able to acquire the lock on first element and thus low parallelism.
	private void lockWrite(Element element1, Element element2, Element element3) throws DeletedItemException, InterruptedException{
		//variables for recording the readers and writers on elements.
		//This is needed since for insert after and insert before operations one element passed as a parameter will be null
		//and using element.readers.get() will throw a null-pointer exception
		
		//System.out.println("In lock write method::"+element1+" "+element2+" "+element3);
		
		int e1R = 0;
		int e1W = 0;
		int e2R = 0;
		int e2W = 0;
		int e3R = 0;
		int e3W = 0;
		
		//Checks if the element has been deleted by some other concurrent thread.
		if((element2.next == null) || (element2.previous == null))
				throw new DeletedItemException();
		
		//sets the variable values.
		if(element1 != null){
			
				element1.writeRequests.compareAndSet(0, 1);
				e1R = element1.readers.get();
				e1W = element1.writers.get();
		}
		if(element2 != null){
			
				element2.writeRequests.compareAndSet(0, 1);
				e2R = element2.readers.get();
				e2W = element2.writers.get();
		}	
		if(element3 != null){
			
				element3.writeRequests.compareAndSet(0, 1);
				e3R = element3.readers.get();
				e3W = element3.writers.get();
		}
			
				
			//The thread waits if any of the element is not free.
			while((e1R > 0) || (e1W > 0) || (e2R > 0) || (e2W > 0) || (e3R > 0) || (e3W > 0)){
				synchronized (this) {
					wait();
				}
				
				if((element2.next == null) || (element2.previous == null))
					throw new DeletedItemException();
				
				if(element1 == null){
					element3 = element2.next;
				}else if(element3 == null){
					element1 = element2.previous;
				}else{
					element3 = element2.next;
					element1 = element2.previous;
				}
				
			}
			//Once the threads awakes, it again checks for the element if it has been deleted by some other concurrent thread.
			
			
			//sets the writerRequests to 0 and increment the count of writers on elements
			if(element1 != null){
				
					element1.writeRequests.decrementAndGet();
					element1.writers.incrementAndGet();
			}
			if(element2 != null){
				
					element2.writeRequests.decrementAndGet();
					element2.writers.incrementAndGet();
			}
			if(element3 != null){
				
					element3.writeRequests.decrementAndGet();
					element3.writers.incrementAndGet();
			}
			
			
	}
	
	//method for unlocking write lock
	private void unlockWrite(Element element1, Element element2, Element element3){
		//decrement the count of writers on elements
		if(element1 != null){
			
				element1.writers.decrementAndGet();
		}
		if(element2 != null){
			
				element2.writers.decrementAndGet();
	
		}
		if(element3 != null){
			
				element3.writers.decrementAndGet();
		}
		
		synchronized (this) {
			notifyAll();
		}
	}
	
	public class CursorFineRW extends Cursor{
		
		// data values
		private WriterFineRW writerFineRW;
		private FDListFineRW parentList;
		
		// constructor
		public CursorFineRW(Element E, FDListFineRW P) {
			super(E, P);
			parentList = P;
			writerFineRW = new WriterFineRW(this);	
		}
		
		public void next() throws DeletedItemException{
			//try {
				//lockRead(this.curr());
				super.next();
				//unlockRead(this.curr().previous);
		//	} /*catch (InterruptedException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			//}
		}
		
		public void previous() throws DeletedItemException{
			try {
				lockRead(this.curr().previous);
				super.previous();
				unlockRead(this.curr().next);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// returns the writer object for this cursor.
		public WriterFineRW writerFineRW() {
			return writerFineRW;
		}	

	}
	
	public class WriterFineRW extends Writer{
		
		private CursorFineRW parentCursor;
		public String action = new String();
		
		public WriterFineRW(CursorFineRW dad) {
			super(dad);
			parentCursor = dad;
			// TODO Auto-generated constructor stub
		}
		
		
		//function for reading the value at the desired position
	/*	public String readValue(){
			try{
				//System.out.println("In fine grained RW version of readValue");
				lockRead(parentCursor.curr());
				String value = parentCursor.curr().value;
				unlockRead(parentCursor.curr());
				return value;
			}catch(DeletedItemException deletedItemException){
				System.out.println("The element::"+parentCursor.curr().value+" which was being accessed by::"+Thread.currentThread()+" has already been deleted by another thread");
				return null;
			}catch(InterruptedException interruptxception){
				interruptxception.printStackTrace();
				return null;
			}catch(Exception exception){
				exception.printStackTrace();
				return null;
			}
		}*/
		
		//function for inserting after desired value
		public boolean insertAfter(String Value) throws DeletedItemException{
			try{
				//System.out.println("In fine grained RW version of insert after");
				lockWrite(null, parentCursor.curr(), parentCursor.curr().next);
				super.insertAfter(Value);
				/*Element newElement = new Element(Value);
				
				parentCursor.curr().next.previous = newElement;
				newElement.next = parentCursor.curr().next;
				
				parentCursor.curr().next = newElement;
				newElement.previous = parentCursor.curr();
				*/
				unlockWrite(null, parentCursor.curr(), parentCursor.curr().next.next);		
				return true;
			}catch(InterruptedException interruptException){
				interruptException.printStackTrace();
				return false;
			}
		}
		
		//function for inserting before the desired value
		public boolean insertBefore(String Value) throws DeletedItemException{
			try{
				//System.out.println("In fine grained RW version of insert before");
				lockWrite(parentCursor.curr().previous, parentCursor.curr(), null);			
				super.insertBefore(Value);
				/*Element newElement = new Element(Value);
				
				parentCursor.curr().previous.next = newElement;
				newElement.previous = parentCursor.curr().previous;
				
				parentCursor.curr().previous = newElement;
				newElement.next = parentCursor.curr();
				*/	
				unlockWrite(parentCursor.curr().previous.previous, parentCursor.curr(), null);
				return true;
			}catch(InterruptedException interruptException){
				interruptException.printStackTrace();
				return false;
			}	
		}
		
		//function for deleting the desired value
		public boolean delete() throws DeletedItemException, HeadDeleteException{			
			try{
				//System.out.println("In fine grained RW version of delete");
				if (parentCursor.curr() == parentCursor.parentList.head){
					throw new HeadDeleteException();
				}
				else {
					lockWrite(parentCursor.curr().previous, parentCursor.curr(), parentCursor.curr().next);			
					Element element = parentCursor.curr().previous;
					// remove the element to be deleted from the list
					super.delete();
					/*parentCursor.curr().previous.next = parentCursor.curr().next;
					parentCursor.curr().next.previous = parentCursor.curr().previous;
					parentCursor.curr().next = null;
					parentCursor.curr().previous = null;
					*/
					unlockWrite(element, null, element.next);
				return true;
				}
			}catch(InterruptedException interruptException){
				interruptException.printStackTrace();
				return false;
			}
		}
		
	}
	
}
