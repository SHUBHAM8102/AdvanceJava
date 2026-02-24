package com;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysql");
	EntityManager em = emf.createEntityManager();
	EntityTransaction et = em.getTransaction();
	
	public void registerUser(Users user) {
		et.begin();
		em.persist(user);
		et.commit();
	}
	
	public void createApostForUser(int userId, Post post) {

		Users u = em.find(Users.class, userId);
		
		if(u != null) {
			post.setAuthor(u);
			et.begin();
			em.persist(post);
			et.commit();
		}
		
	}
	
	public void updatePost(int postId, String newContent) {
		Post p = em.find(Post.class, postId);
		
		if(p != null) {
			et.begin();
			p.setContent(newContent);
			et.commit();
		}
		
	}
	
	public void deletePost(int postId) {
		et.begin();
		Post p = em.find(Post.class, postId);
		
		if(p != null) {
			em.remove(p);
		}
		et.commit();
	}
	
	public void addCommentToPost(int userId, int postId, Comments comment) {
		Users u = em.find(Users.class, userId);
		Post p = em.find(Post.class, postId);
		
		if(u != null && p != null) {
			
			comment.setAuthor_id(u);
			comment.setPost(p);
			
			et.begin();
			em.persist(comment);
			et.commit();
		}
		
		
	}
	
	public List<Post> fetchAllPostsByUser(int userId) {
		Users u = em.find(Users.class, userId);
		
		List<Post> list = u.getPost();
		if(u != null)
			return list;
		else
			return new ArrayList<>();
	}
	
	public List<Comments> fetchAllCommentsfromPost(int postId){
		Post p = em.find(Post.class, postId);
		
		if(p != null)
			return p.getComments();
		else
			return new ArrayList<>();
	}
	
	public List<Comments> fetchAllCommentsByUser(int userId){
		Users u = em.find(Users.class, userId);
		
		if(u != null)
			return u.getComments();
		else
			return new ArrayList<>();
	}
	
	public static void main(String[] args) {

	    Main m = new Main();

	    Users u1 = new Users(1,"Puneeth","123","puneeth@gmail.com");
	    m.registerUser(u1);


	    Post p1 = new Post(101,"First Post","Hello world","today","today");
	    Post p2 = new Post(102,"Second Post","JPA is easy","today","today");

	    m.createApostForUser(1, p1);
	    m.createApostForUser(1, p2);


	    Comments c1 = new Comments(201,"Nice post","today");
	    Comments c2 = new Comments(202,"Very helpful","today");

	    m.addCommentToPost(1,101,c1);
	    m.addCommentToPost(1,101,c2);


	    m.updatePost(101,"Updated content here");


	    System.out.println("POSTS BY USER:");

	    List<Post> posts = m.fetchAllPostsByUser(1);

	    for(Post p : posts){
	        System.out.println(p.getTitle());
	    }


	    System.out.println("COMMENTS OF POST:");

	    List<Comments> comments = m.fetchAllCommentsfromPost(101);

	    for(Comments c : comments){
	        System.out.println(c.getContent());
	    }


	    System.out.println("COMMENTS BY USER:");

	    List<Comments> userComments = m.fetchAllCommentsByUser(1);

	    for(Comments c : userComments){
	        System.out.println(c.getContent());
	    }

	}

}