package com.practice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="student_info")
public class Student {//@Entity is use to create table and make columns

		@Id 
		@Column(name="student_id")
		private int id;//@Id is used to make it primery key
		@Column(name="student_name")
		private String name;
		@Column(name="student_percentage")
		private double percentage;
		
		@Override
		public String toString() {
			return "Student [id=" + id + ", name=" + name + ", percentage=" + percentage + "]";
		}
		public Student(){
			
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public double getPercentage() {
			return percentage;
		}
		public void setPercentage(double percentage) {
			this.percentage = percentage;
		}
		public Student(int id, String name, double percentage) {
			
			this.id = id;
			this.name = name;
			this.percentage = percentage;
		}
		

	

}
