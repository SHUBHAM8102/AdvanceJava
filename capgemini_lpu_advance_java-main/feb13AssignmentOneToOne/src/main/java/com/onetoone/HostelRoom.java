package com.onetoone;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "hostel_room")
public class HostelRoom {
	
	@Id
	private int roomId;
	private int roomNumber;
	private String blockName;
	private int floorNumber;
	public HostelRoom(int roomId, int roomNumber, String blockName, int floorNumber) {
		super();
		this.roomId = roomId;
		this.roomNumber = roomNumber;
		this.blockName = blockName;
		this.floorNumber = floorNumber;
	}
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public int getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	public String getBlockName() {
		return blockName;
	}
	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}
	public int getFloorNumber() {
		return floorNumber;
	}
	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}
	
	public HostelRoom() {}
	
}
