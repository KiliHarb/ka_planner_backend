package com.fallingcube.ka_planner.text;

public class Text {

	public static String[] editingArray = new String[10000];

	private int id;
	private String name;
	private String content;
	private String dateOfCreation;
	private String editingDate;

	public Text() {
		super();
	}

	public Text(int id, String name, String content, String dateOfCreation, String editingDate) {
		this.setId(id);
		this.setName(name);
		this.setContent(content);
		this.setDateOfCreation(dateOfCreation);
		this.setEditingDate(editingDate);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEditingDate() {
		return editingDate;
	}

	public void setEditingDate(String editingDate) {
		this.editingDate = editingDate;
	}

	public String getDateOfCreation() {
		return dateOfCreation;
	}

	public void setDateOfCreation(String dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
