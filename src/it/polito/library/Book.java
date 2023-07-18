package it.polito.library;

public class Book implements Comparable<Book>{
	
	private String title;
	private int id;
	private int count=1;
	public void setCount(int count) {
		this.count = count;
	}







	private int rcount=0;
	private int available =1;

	
	
	public Book(String title, int id) {
		
		this.title = title;
		this.id = id;
	}
	
	
	
	
	
	
	
	public void icount() {count++;}
	public void ircount() {rcount++;}
	
	
	
	
	
	
	
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getId() {
		return id;}
	
	public int getCount() {
			return count;
	}
	public void setId(int id) {
		this.id = id;
	}







	@Override
	public int compareTo(Book o) {
		
		return  title.compareTo(o.title);
	}







	public int getAvailable() {
		return available;
	}







	public void setAvailable(int available) {
		this.available = available;
	}







	public int getRcount() {
		return rcount;
	}

}
