package circularLinkedList;

public class FDListFine extends FDList {

	//public Cursor pointer;
	
	public FDListFine(String Value) {
		super(Value);
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("serial")
	public class AlreadyUnlockedElementException extends Exception {

		public AlreadyUnlockedElementException() {
			
		}
		
	}
		
	
	public CursorFine reader(Element E){
			return new CursorFine(E, this);
		}
	
		//Programmer uses this method for acquiring locks on the elements passed as the parameters to this method.
		//This method can acquire lock on three elements in execution cycle. If any of the elements are not free
		//i.e. if any of the elements are already locked by other threads, then this function will not acquire locks 
		// on any of the elements and will make thread to wait until other threads unlock these elements  i.e. 
		//it will acquire lock when only all the elements are free. The order of the elements should be passed 
		//as element.previous, element and element.next
	
		private boolean lock(Element element1, Element element2, Element element3) throws DeletedItemException, InterruptedException{
			//If any one of the elements passed as the parameter is null, then the programmer intents to perform
			//"Insert After" or "Insert Before" operation. If none of the elements passed as the parameters are pointing
			//to null, then the programmer intents to perform "Delete" operation.
			//System.out.println("In lock method...values:::"+element2.value+" "+element3.value);
			//variables defined for keeping track on the locks acquired on element.
			boolean lock1 = false;
			boolean lock2 = false;
			boolean lock3 = false;
			
			//This operation is to check whether the element2 has been deleted.
			if((element2.next == null) && (element2.previous == null)){
				throw new DeletedItemException();
			}
			
			while((lock1 == false) || (lock2 == false) || (lock3 == false)){
				//This operation is to check whether the element2 has been deleted by any other concurrent thread after the current thread awakes
				
				if(element1 == null){
					element3 = element2.next;
				}else if(element3 == null){
					element1 = element2.previous;
				}else{
					element3 = element2.next;
					element1 = element2.previous;
				}
				
				
				if((element2.next == null) && (element2.previous == null)){
					throw new DeletedItemException();
				}
				
				
				if(element1 == null)
					lock1 = true;
				else{
						synchronized (element1) {
							if(this.isLocked(element1) == false){
								element1.isLocked = true;
							lock1 = true;
						}
						}
				}
				if(this.isLocked(element2) == false){
					synchronized (element2) {
						element2.isLocked = true;
						lock2 = true;
					}
				}
				if(element3 == null)
					lock3 = true;
				else{
						synchronized (element3) {
							if(this.isLocked(element3) == false){
								element3.isLocked = true;
								lock3 = true;
						
						}
						}	
				}
				
				//System.out.println("acessed the locks");
				//System.out.println("printing is locked values::"+lock1+" "+lock2+" "+lock3);
				
				if((lock1 == false) || (lock2 == false) || (lock3 == false)){
					if((element1 != null) && lock1 == true)
						 {
							synchronized (element1) {
								element1.isLocked = false;
							}
							lock1 = false;
						}			
					if((element2 != null) && lock2 == true)
					{
						synchronized (element2) {
							element2.isLocked = false;	
							
						}
						lock2 = false;
						}	
					if((element3 != null) && lock3 == true)
						 {
						synchronized (element3) {
							element3.isLocked = false;
						}
								
							lock3 = false;
						}						
				
					synchronized (element2) {
						wait();
					}
						
				}else{
					//This operation has been done in order to ensure that the elements are still in sequential order.
					//If any new element has been inserted between any of the elements than this will capture the 
					//new updates. If part is for "Insert After" operation and else part is for "Insert After" operation. 
					if(element1 == null){
						if(element3 != element2.next){
							
							synchronized (element2) {
								element2.isLocked = false;	
							}
								
								lock2 = false;
							
							synchronized (element3) {
								element3.isLocked = false;
							}
								
								lock3 = false;
							
							synchronized (element2) {
								wait();
							}
								
							
						}
					}else if(element3 == null){
						if(element1 != element2.previous){
							
							synchronized (element1) {
								element1.isLocked = false;	
							}
								
								lock1 = false;
							
							synchronized (element2) {
								element2.isLocked = false;
							}
								
								lock2 = false;
							
							synchronized (element2) {
								wait();
							}
								
							
						}
					}else{
						if((element1 != element2.previous) || (element3 != element2.next)){
							
								
							synchronized (element1) {
								element1.isLocked = false;	
							}
							
								lock1 = false;
							
							synchronized (element2) {
								element2.isLocked = false;
							}
								
								lock2 = false;
							
								synchronized (element3) {
									element3.isLocked = false;
								}
								
								lock3 = false;
							
							synchronized (element2) {
								wait();
							}
								
							
						
						}
					}	
				}
			}
			return true;
	}
		
	//function for giving the current status for the element locks
	private boolean isLocked(Element element) throws DeletedItemException{
			if(element  == null){
				throw new DeletedItemException();
			}
			return element.isLocked; 
	}
	
	//unlock function is used for releasing the locks on the elements.The function throws AlreadyUnlockedElementException if the 
	//element has been already unlocked. The element will only release locks form all elements if all the elements are locked i.e
	//no element on getting unlocked should not throw AlreadyUnlockedElementException. The order of the elements should be passed 
	//as element.previous, element and element.next
	
	private boolean unlock(Element element1, Element element2, Element element3) throws AlreadyUnlockedElementException{
		//If any one of the elements passed as the parameter is null, then the programmer intents to release locks after performing
		//"Insert After" or "Insert Before" operation. If none of the elements passed as the parameters are pointing
		//to null, then the programmer intents to release locks after performing "Delete" operation.
		
		//variables defined for keeping track of the unlock states of the elements
		boolean unlock1 = false;
		boolean unlock2 = false;
		boolean unlock3 = false;
		
		if(element1 == null)
			unlock1 = true;
		else{
			{
				synchronized (element1) {
					if(element1.isLocked == false)
						unlock1 = false;
					else{
						element1.isLocked = false;
						unlock1 = true;
					}
				}
				
			
		}
	
		if(element2 == null)
			unlock2 = true;
		else{
			synchronized (element2) {
				if(element2.isLocked == false)
					unlock2 = false;
				
				else{
					element2.isLocked = false;
					unlock2 = true;
				}
			
			}
				
		}
		
		if(element3 == null)
			unlock3 = true;
		else{
			
			synchronized (element3) {
				if(element3.isLocked == false)
					unlock3 = false;
				else{
					element3.isLocked = false;
					unlock3 = true;
				}
			}
			
				
			
		}
		
		if((unlock1 == false) || (unlock2 == false) || (unlock3 == false))
			throw new AlreadyUnlockedElementException();
		else{
			synchronized (element2) {
				notifyAll();
			}
		}
		
		}
		return true;
	}
		
		

	public class CursorFine extends Cursor{
		
		// data values
		private WriterFine writerFine;
		private FDListFine parentList;
		
		// constructor
		public CursorFine(Element E, FDListFine P) {
			super(E, P);
			parentList = P;
			writerFine = new WriterFine(this);	
		}
		
		// returns the writer object for this cursor.
		public WriterFine writerFine() {
			return writerFine;
		}
	}
	
	public class WriterFine extends Writer{

		private CursorFine parentCursor;
		public String action = new String();
		
		public WriterFine(CursorFine dad) {
			super(dad);
			parentCursor = dad;
			// TODO Auto-generated constructor stub
		}
		
		//function for inserting after the desired value by putting fine grained locks on elements
		public boolean insertAfter(String Value) throws DeletedItemException{
			try{
				if((parentCursor.curr() == head) && (parentCursor.curr().next == head))
					super.insertAfter(Value);
				else{
					lock(null, parentCursor.curr(), parentCursor.curr().next);
					super.insertAfter(Value);
					unlock(null, parentCursor.curr(), parentCursor.curr().next.next);
				}
				return true;
			}catch(AlreadyUnlockedElementException alreadyUnlockedException) {
				System.out.println("The element accessed by thread::"+Thread.currentThread()+" has been already unlocked");
				return false;
			}
			/*catch(DeletedItemException deletedItemException){
				System.out.println("The element::"+parentCursor.curr().value+" which was being accessed by::"+Thread.currentThread()+" has already been deleted by another thread");
				return false;
			}*/catch(InterruptedException interruptException){
				interruptException.printStackTrace();
				return false;
			}/*catch(Exception exception){
				exception.printStackTrace();
				return false;
			}*/	
		}
		
		//function for inserting before the desired value by putting fine grained locks
		public boolean insertBefore(String Value)  throws DeletedItemException{
			try{
				if((parentCursor.curr() == head) && (parentCursor.curr().next == head))
					super.insertAfter(Value);
				else{
					lock(parentCursor.curr().previous, parentCursor.curr(), null);
					super.insertBefore(Value);	
					unlock(parentCursor.curr().previous.previous, parentCursor.curr(), null);
				}
				return true;
			}catch(AlreadyUnlockedElementException alreadyUnlockedException) {
				System.out.println("The element accessed by thread::"+Thread.currentThread()+" has been already unlocked");
				return false;
			}
			/*catch(DeletedItemException deletedItemException){
				System.out.println("The element::"+parentCursor.curr().value+" which was being accessed by::"+Thread.currentThread()+" has already been deleted by another thread");
				return false;
			}*/catch(InterruptedException interruptException){
				interruptException.printStackTrace();
				return false;
			}/*catch(Exception exception){
				exception.printStackTrace();
				return false;
			}*/	
		}
		
		//function for deleting the desired value by putting fine grained locks
		public boolean delete() throws DeletedItemException, HeadDeleteException{			
			try{
				if (parentCursor.curr() == parentCursor.parentList.head){
					throw new HeadDeleteException();
				}
				else {
					lock(parentCursor.curr().previous, parentCursor.curr(), parentCursor.curr().next);
					Element element = parentCursor.curr().previous;
					super.delete();
					unlock(element, null, element.next);
				return true;
				}
			}/*catch(HeadDeleteException headDeleteException){
				System.out.println("The user cannot delete the head element of the linked list");
				return false;
			}*/
			catch(AlreadyUnlockedElementException alreadyUnlockedException) {
				System.out.println("The element accessed by thread::"+Thread.currentThread()+" has been already unlocked");
				return false;
			}
			/*catch(DeletedItemException deletedItemException){
				System.out.println("The element::"+parentCursor.curr().value+" which was being accessed by::"+Thread.currentThread()+" has already been deleted by another thread");
				return false;
			}*/catch(InterruptedException interruptException){
				interruptException.printStackTrace();
				return false;
			}/*catch(Exception exception){
				exception.printStackTrace();
				return false;
			}*/	
		}
		
	}
}
