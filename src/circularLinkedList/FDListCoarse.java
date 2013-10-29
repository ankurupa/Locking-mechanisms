package circularLinkedList;

public class FDListCoarse extends FDList {
	
	public FDListCoarse(String Value) {
		super(Value);				
	}
	
	public CursorCoarse reader(Element E) {
		return new CursorCoarse(E, this);
	}
	

	public class CursorCoarse extends Cursor {	
		
		private WriterCoarse writerCoarse;
		private FDListCoarse parentList;
		
		public CursorCoarse(Element E, FDListCoarse P) {
			super(E, P);
			parentList = P;
			writerCoarse = new WriterCoarse(this);
		}

		public void next() throws DeletedItemException {
			synchronized (head) {
				super.next();
			}
			
		}
		
		public void previous() throws DeletedItemException {
			synchronized (head) {
				super.previous();
			}
		}		
		
		public WriterCoarse writerCoarse() {
			return writerCoarse;
		}
	}
	
	public class WriterCoarse extends Writer {

		private CursorCoarse parentCursor;
		public String action = new String();
		
		public WriterCoarse(CursorCoarse dad) {
			super(dad);
			parentCursor = dad;
			// TODO Auto-generated constructor stub
		}
		
		public boolean delete() throws DeletedItemException, HeadDeleteException {
			synchronized (head) {
				super.delete();
			}
			return true;
		}
		
		public boolean insertBefore(String Value) throws DeletedItemException{
			synchronized(head) {
				super.insertBefore(Value);
			}
			return true;
		}
		public boolean  insertAfter(String Value) throws DeletedItemException{
			System.out.println("in insert after of child");
			synchronized(head) {
				super.insertAfter(Value);
			}
			return true;
			
		}
		
	}
	
}
