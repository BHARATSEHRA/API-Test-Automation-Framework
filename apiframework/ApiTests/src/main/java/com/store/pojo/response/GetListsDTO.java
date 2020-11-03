package com.store.pojo.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetListsDTO {

	private String id;
	private String status;
	private String name;
	private List<String> photoUrls;
	private Category category;
	private List<Tags> tags;
	private String code;
	private String message;

	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public GetListsDTO(String id, String status, String name, List<String> photoUrls, Category category,
			List<Tags> tags) {
		super();
		this.id = id;
		this.status = status;
		this.name = name;
		this.photoUrls = photoUrls;
		this.category = category;
		this.tags = tags;
	}

	public GetListsDTO() {
		
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
	

	

	}
