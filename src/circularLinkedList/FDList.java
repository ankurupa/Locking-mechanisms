package circularLinkedList;

import java.util.concurrent.atomic.AtomicInteger;

public class FDList {
	
	
	
			// since this is a circular list it dosen't matter
			// what the first item is, but we need to keep
			// a pointer to some element in the list.
			public Element head;
			
			
			// Constructor
			// requires the first item to be added to the list
			public FDList(String Value) {		
				
				head = new Element(Value);
				head.next = head;
				head.previous = head;
							
			}
			
			public Element head(){
				return this.head;
			}
			
			@SuppressWarnings("serial")
			public class DeletedItemException extends Exception {

				public DeletedItemException() {
					
				}
				
			}
			@SuppressWarnings("serial")
			public class HeadDeleteException extends Exception {

				public HeadDeleteException() {
					
				}
				
			}
			
			// print the current list
			// it's not part of the interface but its a useful debugging tool.
			public void printList() throws DeletedItemException {
				
				Cursor pointer = this.reader(this.head);
				int count = 0;
				while ((pointer.curr() != this.head) || (count==0)){
					System.out.println(pointer.curr().value);
					pointer.next();
					count++;
				}
				
			}
			
			// get a cursor pointing at element E.
			public Cursor reader(Element E){
						
				return new Cursor(E, this);
			}
							
			// the basic building blocks of the list. Each element
			// contains a value(a string for now) and a pointer
			// to the next and previous elements in the list
			public class  Element {
				
				//fields for fine grained read write locks
				volatile public AtomicInteger readerRequest = new AtomicInteger(0);				
				volatile public AtomicInteger readers  = new AtomicInteger(0);
				volatile public AtomicInteger writers = new AtomicInteger(0);
				volatile public AtomicInteger writeRequests = new AtomicInteger(0);
				
				//field for fine grained locks
				volatile public boolean isLocked = false;
				
				
				
				//constructor
				public Element(String input){
					value = input;
				}
				
				// values
				public String value;
				public Element next;
				public Element previous;
				
				public void destroy() {
					value = null;
				}
				
			}
			
			// a pointer to an element in the list that can be used to
			// iterate through the list with the next() and previous()
			// commands
			public class Cursor {
				
				// data values
				private Writer writer;
				private Element current;
				private FDList parentList;
				
				// constructor
				public Cursor(Element E, FDList P) {
					current  = E;
					parentList = P;
					writer = new Writer(this);	
				}
				
				// advances the cursor to the next element in the list.
				public void next() throws DeletedItemException {
					/*if(current.value == null) {
						throw new DeletedItemException();
					}*/
					
					if((current.next == null) && (current.previous == null)){
						throw new DeletedItemException();
					}
					current = current.next;	
				}
				
				//returns the current cursor
				public Element curr(){
					return this.current;
				}
				
				//moves the cursor back one element
				public void previous() throws DeletedItemException{
					if((current.next == null) && (current.previous == null)){
						throw new DeletedItemException();
					}	
					current = current.previous;	
				}
				
				// returns the writer object for this cursor.
				public Writer writer() {
				
					return writer;
				}
			
				
			}
			
			// the writer class allows cursors to insert and delete items
			// in the list.
			public class Writer {
				
				private Cursor parentCursor;
				public String action = new String();
				
				// constructor
				public Writer(Cursor dad) {
					parentCursor = dad;
				}
				
				// delete an element. If it's the last element
				// in the list the operation fails.
				public boolean delete() throws DeletedItemException, HeadDeleteException{
					// according to questions on Piazza we re allowed to consider
					// the head element constant and non-delete-able.
					
					if (parentCursor.curr() == parentCursor.parentList.head){
						throw new HeadDeleteException();
					}
					else {
						if ((parentCursor.curr().next == null) && (parentCursor.curr().previous == null)) {
							throw new DeletedItemException();
						}
						// remove the element to be deleted from the list
						parentCursor.current.next.previous = parentCursor.current.previous;
						parentCursor.current.previous.next = parentCursor.current.next;

						//This operation ensures that the element deleted is visible in other threads.
						parentCursor.current.next = null;
						parentCursor.current.previous = null;
					return true;
					}
				}
				// inserts an element into the list before the current element
				public boolean insertBefore(String Value) throws DeletedItemException{
										
					if ((parentCursor.curr().next == null) && (parentCursor.curr().previous == null)) {
						throw new DeletedItemException();
					}
					Element newElement  = new Element(Value);
					newElement.next = parentCursor.current;
					newElement.previous = parentCursor.current.previous;
					
					parentCursor.current.previous = newElement;
					newElement.previous.next = newElement;
					
					return true;
					
				}
				// inserts an element into the list after the current element
				public boolean  insertAfter(String Value) throws DeletedItemException{
					if ((parentCursor.curr().next == null) && (parentCursor.curr().previous == null)) {
						throw new DeletedItemException();
					}
					
					Element newElement = new Element(Value);
					newElement.next  = parentCursor.current.next;
					newElement.previous = parentCursor.current;
					
					newElement.next.previous = newElement;
					parentCursor.current.next = newElement;
					
					return true;
				}
				
			}

}
