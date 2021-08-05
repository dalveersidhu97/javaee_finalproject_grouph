package com.banking.beans;

/**
 * 
 * @author Group-H
 * @date 03-07-2021
 * @description Category Option Model for storing info about input fields of a transaction category.
 * 		Such as inputName = amount, inputType = number, title = Enter amount and so on.
 * 
 */

public class CategoryOption {

	private int id;
	private int categoryId;
	private String title;
	private String inputName;
	private String inputType;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getInputName() {
		return inputName;
	}
	public void setInputName(String inputName) {
		this.inputName = inputName;
	}
	public String getInputType() {
		return inputType;
	}
	public void setInputType(String inputType) {
		this.inputType = inputType;
	}
	
	
	
}
