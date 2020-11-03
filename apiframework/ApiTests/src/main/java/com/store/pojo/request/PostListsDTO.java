package com.store.pojo.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostListsDTO {

	private String id;
	private String status;
	private String name;
	private List<String> photoUrls;
	private Category category;
	private List<Tags> tags;

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PostListsDTO(String id, String status, String name, List<String> photoUrls, Category category,
			List<Tags> tags) {
		super();
		this.id = id;
		this.status = status;
		this.name = name;
		this.photoUrls = photoUrls;
		this.category = category;
		this.tags = tags;
	}
	public PostListsDTO() {
		
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getPhotoUrls() {
		return photoUrls;
	}

	public void setPhotoUrls(List<String> photoUrls) {
		this.photoUrls = photoUrls;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Tags> getTags() {
		return tags;
	}

	public void setTags(List<Tags> tags) {
		this.tags = tags;
	}
	

	public static PostListsDTO getloadedObject(Map<String, String> dataMap) {
		
		Category category = new Category();
		
		category.setId(dataMap.get("Category_id"));
		category.setName(dataMap.get("Category_Name"));
		
			
        Tags tags = new Tags();
		
        tags.setId(dataMap.get("Tag_id"));
        tags.setName(dataMap.get("Tag_Name"));
		
		List<Tags> tagslist = new ArrayList<Tags>();
		tagslist.add(tags);
		
		List<String> photoUrls= new ArrayList<String>();
		photoUrls.add("String");
		
		
	PostListsDTO postlistdto = new 	PostListsDTO(dataMap.get("id"), dataMap.get("status"), 
									dataMap.get("Name"), photoUrls, category, tagslist);
	
		
		return postlistdto;
		
	}
	

	}
