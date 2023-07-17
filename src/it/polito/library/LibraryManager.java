package it.polito.library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;


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
    	
    	
    	Optional<Integer> B = books.stream().filter(b->b.getTitle().equals(bookTitle))
    			.map(Book :: getId).sorted().findFirst();
    	
    	if (B==null) {return "Not available";}
    	else {return B+"";}
    			
    			
    	
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
	
	for(Rental r : rentals) {
		if((r.getBook()+"").equals(bookID) && r.getReader()+"").equals(bookID))
	}
	
	
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
        return null;
    }
    
    
    // R3: Book Donations
    
    /**
	* Collects books donated to the library.
	* 
	* @param donatedTitles It takes in input book titles in the format "First title,Second title"
	*/
    public void receiveDonation(String donatedTitles) {
    }
    
    // R4: Archive Management

    /**
	* Retrieves all the active rentals.
	* 
	* @return the map linking reader IDs with their active rentals

	*/
    public Map<String, String> getOngoingRentals() {
        return null;
    }
    
    /**
	* Removes from the archives all book copies, independently of the title, that were never rented.
	* 
	*/
    public void removeBooks() {
    }
    	
    // R5: Stats
    
    /**
	* Finds the reader with the highest number of rentals
	* and returns their unique ID.
	* 
	* @return the uniqueID of the reader with the highest number of rentals
	*/
    public String findBookWorm() {
        return null;
    }
    
    /**
	* Returns the total number of rentals by title. 
	* 
	* @return the map linking a title with the number of rentals
	*/
    public Map<String,Integer> rentalCounts() {
        return null;
    }

}
