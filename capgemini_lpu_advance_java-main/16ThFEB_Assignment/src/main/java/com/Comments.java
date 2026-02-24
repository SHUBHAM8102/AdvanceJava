package com;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comments")
public class Comments {
	@Id
	private int id;
	private String content;
	private String created_at;
	
	@ManyToOne
	@JoinColumn(name="post_id")
	private Post post;
	
	@ManyToOne
	@JoinColumn(name="author_id")
	private Users author_id;
	
	
	
	
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public Users getAuthor_id() {
		return author_id;
	}
	public void setAuthor_id(Users author_id) {
		this.author_id = author_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	
	
	public Comments() {}
	public Comments(int id, String content, String created_at) {
		super();
		this.id = id;
		this.content = content;
		this.created_at = created_at;
	}
	
	
	
	
}