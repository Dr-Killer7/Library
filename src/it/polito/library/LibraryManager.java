package it.polito.library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class LibraryManager {
	int cid=1000;
	int rid=1000;
	ArrayList<Book>books=new ArrayList<>();
	ArrayList<Reader>readers=new ArrayList<>();
	ArrayList<Rental>rentals=new ArrayList<>();    
    // R1: Readers and Books 
    
    /**
	 * adds a book to the library archive
	 * The method can be invoked multiple times.
	 * If a book with the same title is already present,
	 * it increases the number of copies available for the book
	 * 
	 * @param title the title of the added book
	 * @return the ID of the book added 
	 */
    public String addBook(String title) {
    	Book c= new Book(title,cid); 
        for (Book b : books) {
        	if (b.getTitle().equals(title)     ) b.icount();
        	c.setCount(b.getCount());
        }
        books.add(c);
        String s = cid+"" ;
        cid++;
            return s ;
    }
    
    
    
    
    /**
	 * Returns the book titles available in the library
	 * sorted alphabetically, each one linked to the
	 * number of copies available for that title.
	 * 
	 * @return a map of the titles liked to the number of available copies
	 */
    public SortedMap<String, Integer> getTitles() {
    	ArrayList<Book> a = books ;
    	Collections.sort(a);
    	
    	HashSet <String> h= new HashSet<>();
    	for (Book  b : books )h.add(b.getTitle());
    	
    	
    	TreeMap<String,Integer> t = new TreeMap<>();
    	for (String s :h) {
    		for (Book b : books) {
    			if (b.getTitle().equals(s)) {t.put(s, b.getCount());}
    		}
    	}
    	
        return t;
    }
    
    /**
	 * Returns the books available in the library
	 * 
	 * @return a set of the titles liked to the number of available copies
	 */
    public Set<String> getBooks() { 
    	HashSet<String> h = new HashSet<>();
    	for (Book b : books) {
    		h.add(b.getId()+"");
    	}
    	
        return h;
    }
    
    /**
	 * Adds a new reader
	 * 
	 * @param name first name of the reader
	 * @param surname last name of the reader
	 */
    public void addReader(String name, String surname) {
    	Reader r = new Reader(name,surname,rid);
    	readers.add(r);
    	
    	rid++;
    	
    }
    
    
    /**
	 * Returns the reader name associated to a unique reader ID
	 * 
	 * @param readerID the unique reader ID
	 * @return the reader name
	 * @throws LibException if the readerID is not present in the archive
	 */
    public String getReaderName(String readerID) throws LibException {
    	int i = Integer.parseInt(readerID);
    	
    	for (Reader b : readers ) {if (b.getId()==i) 
    	{ return b.getName()+" "+b.getSurname();}	
    	}
    	
        throw new LibException("reader does not exist");
    }    
    
    
    // R2: Rentals Management
    
    
    /**
	 * Retrieves the bookID of a copy of a book if available
	 * 
	 * @param bookTitle the title of the book
	 * @return the unique book ID of a copy of the book or the message "Not available"
	 * @throws LibException  an exception if the book is not present in the archive
	 */
    public String getAvailableBook(String bookTitle) throws LibException {
    	int tfound =0;
    	for (Book b :books) {
    		if(b.getTitle().equals(bookTitle)) {tfound=1;}
    	}
    	if(tfound==0) {throw new LibException("title not found");}
    	
    	
    	int B = books.stream().filter(b->b.getTitle().equals(bookTitle)).filter(b->b.getAvailable()==1)
    			.map(Book :: getId).sorted().findFirst().orElse(0);
 
    	if (B==0) {return "Not available";}
    	else{return B+"";}
    			
    			
    	
    }   

    /**
	 * Starts a rental of a specific book copy for a specific reader
	 * 
	 * @param bookID the unique book ID of the book copy
	 * @param readerID the unique reader ID of the reader
	 * @param startingDate the starting date of the rental
	 * @throws LibException  an exception if the book copy or the reader are not present in the archive,
	 * if the reader is already renting a book, or if the book copy is already rented
	 */
	public void startRental(String bookID, String readerID, String startingDate) throws LibException {
    int rfound=0;
    int bfound=0;
    
    
    
	for (Book b :books) {
		if( (b.getId()+"").equals(bookID)) {bfound=1;}
	}
	
	if(bfound==0) {throw new LibException("title not found");}
	
	for (Reader r :readers) {
		if((r.getId()+"").equals(readerID)) {rfound=1;}
	}
	
	if(rfound==0) {throw new LibException("title not found"); }
	Book book = books.stream().filter(e->e.getId()==Integer.parseInt(bookID)).findFirst().orElse(null);
	Reader reader  = readers.stream().filter(e->e.getId()==Integer.parseInt(readerID)).findFirst().orElse(null);
	
	for(Rental r : rentals) {
		if((r.getBook()+"").equals(bookID) ) {
			if((r.getReader()+"").equals(readerID)){r=new Rental( Integer.parseInt(bookID),  Integer.parseInt(readerID),  startingDate);reader.setRents(1);return;}
		}
	}
	if(book.getAvailable()==0||reader.getRents()==1)throw new LibException("abc");
	for(Book b :books) {if((b.getId()+"").equals(bookID)) {
		
	if(b.getAvailable()==1) {for(Reader R :readers) {
		if(R.getRents()==0&&(R.getId()+"").equals(readerID)) {
		Rental r = new Rental(Integer.parseInt(bookID),  Integer.parseInt(readerID),  startingDate);b.setAvailable(0);R.setRents(1);b.ircount();R.icount(); rentals.add(r);
	}}}}};
	
	
	}
    
	/**
	 * Ends a rental of a specific book copy for a specific reader
	 * 
	 * @param bookID the unique book ID of the book copy
	 * @param readerID the unique reader ID of the reader
	 * @param endingDate the ending date of the rental
	 * @throws LibException  an exception if the book copy or the reader are not present in the archive,
	 * if the reader is not renting a book, or if the book copy is not rented
	 */
    public void endRental(String bookID, String readerID, String endingDate) throws LibException {
    	int bid=Integer.parseInt(bookID),rid=Integer.parseInt(readerID);
    	if(bid!=books.stream().filter(e->e.getId()==bid).map(e->e.getId()).findFirst().orElse(0))throw new LibException("abc");
    	if(rid!=readers.stream().filter(e->e.getId()==rid).map(e->e.getId()).findFirst().orElse(0))throw new LibException("abc");
    	int B=Integer.parseInt(bookID); int R =Integer.parseInt(readerID);
    	Book b = books.stream().filter(e->e.getId()==B).findFirst().orElse(null);
    	Reader r  = readers.stream().filter(e->e.getId()==R).findFirst().orElse(null);
    	if(r==null)throw new LibException("abc");
    	if(r.getRents()==0)throw new LibException("abc");
    	Rental rental = rentals.stream().filter(e->e.getBook()==B).filter(e->e.getReader()==R).findFirst().orElse(null);
    	if(rental==null||r.getRents()==0) {throw new LibException("error");}
    	rental.setEndDate(endingDate);b.setAvailable(1);r.setRents(0);
    	
    }
    
    
   /**
	* Retrieves the list of readers that rented a specific book.
	* It takes a unique book ID as input, and returns the readers' reader IDs and the starting and ending dates of each rental
	* 
	* @param bookID the unique book ID of the book copy
	* @return the map linking reader IDs with rentals starting and ending dates
	* @throws LibException  an exception if the book copy or the reader are not present in the archive,
	* if the reader is not renting a book, or if the book copy is not rented
	*/
    public SortedMap<String, String> getRentals(String bookID) throws LibException {
    	int bid=Integer.parseInt(bookID);
    	if(bid!=books.stream().filter(e->e.getId()==bid).map(e->e.getId()).findFirst().orElse(0))throw new LibException("abc");
    	ArrayList<Rental> a = new ArrayList<>();
    	a=(ArrayList<Rental>) rentals.stream().filter(e->e.getBook()==Integer.parseInt(bookID)).collect(Collectors.toList());
    	SortedMap<String, String> s =  new TreeMap<String, String>();
    	for(Rental r :a) {s.put(r.getReader()+"", r.getDate()+" "+r.getEndDate());}
        return s;
    }
    
    
    // R3: Book Donations
    
    /**
	* Collects books donated to the library.
	* 
	* @param donatedTitles It takes in input book titles in the format "First title,Second title"
	*/
    public void receiveDonation(String donatedTitles) {
    	String[] dbooks = donatedTitles.split(","); for(String s :dbooks) {addBook(s);}
    }
    
    // R4: Archive Management

    /**
	* Retrieves all the active rentals.
	* 
	* @return the map linking reader IDs with their active rentals

	*/
    public Map<String, String> getOngoingRentals() {
    	TreeMap<String, String> m =new TreeMap<>();
    	List<Rental> l =rentals.stream().filter(r->r.getEndDate().equals("ONGOING")).collect(Collectors.toList());
    	for (Rental r : l)m.put(r.getReader()+"", r.getBook()+"");
        return m;
    }
    
    /**
	* Removes from the archives all book copies, independently of the title, that were never rented.
	* 
	*/
    public void removeBooks() {
    	List<Book> weli= books.stream().filter(b->b.getRcount()==0).collect(Collectors.toList());
    	for(Book b:weli)books.remove(b);
    }
    	
    
    // R5: Stats
    
    /**
	* Finds the reader with the highest number of rentals
	* and returns their unique ID.
	* 
	* @return the uniqueID of the reader with the highest number of rentals
	*/
    public String findBookWorm() {
    	int r = readers.stream().max(Comparator.comparingInt(reader -> reader.getCount())).map(Reader::getId).orElse(null);
        return r+"";
    }
    
    /**
	* Returns the total number of rentals by title. 
	* 
	* @return the map linking a title with the number of rentals
	*/
    public Map<String,Integer> rentalCounts() {
    	TreeMap<String,Integer> t = new TreeMap<>();
    	for (Book b:books) {
    		if(t.containsKey(b.getTitle())){
    			t.replace(b.getTitle(), t.get(b.getTitle())+b.getRcount()); }
    		else t.put(b.getTitle(), b.getRcount());
    	}
        return t;
    }

}
