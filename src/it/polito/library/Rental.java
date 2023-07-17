package it.polito.library;

import java.util.ArrayList;
import java.util.Date;

public class Rental {
	private int book;
	private int reader;
	private String date;
	private String[] dmy = new String[3];
	
	
	
	
	
	
	
	
	public Rental(int book, int reader, String date) {
		
		this.book = book;
		this.reader = reader;
		this.date = date;
		dmy = date.split("-");
		
	}
	public int getBook() {
		return book;
	}
	public void setBook(int book) {
		this.book = book;
	}
	public int getReader() {
		return reader;
	}
	public void setReader(int reader) {
		this.reader = reader;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	

}
