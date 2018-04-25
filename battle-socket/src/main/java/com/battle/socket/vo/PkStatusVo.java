package com.battle.socket.vo;

public class PkStatusVo {

	private String battleId;
	
	private String roomId;
	
	private Integer homeStatus;
	
	private String homeImg;
	
	private String homeNickname;
	
	private Integer beatStatus;
	
	private String beatImg;
	
	private String beatNickname;
	
	private Integer roomStatus;

	public String getBattleId() {
		return battleId;
	}

	public void setBattleId(String battleId) {
		this.battleId = battleId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public Integer getHomeStatus() {
		return homeStatus;
	}

	public void setHomeStatus(Integer homeStatus) {
		this.homeStatus = homeStatus;
	}

	public String getHomeImg() {
		return homeImg;
	}

	public void setHomeImg(String homeImg) {
		this.homeImg = homeImg;
	}

	public String getHomeNickname() {
		return homeNickname;
	}

	public void setHomeNickname(String homeNickname) {
		this.homeNickname = homeNickname;
	}

	public Integer getBeatStatus() {
		return beatStatus;
	}

	public void setBeatStatus(Integer beatStatus) {
		this.beatStatus = beatStatus;
	}

	public String getBeatImg() {
		return beatImg;
	}

	public Integer getRoomStatus() {
		return roomStatus;
	}

	public void setRoomStatus(Integer roomStatus) {
		this.roomStatus = roomStatus;
	}

	public void setBeatImg(String beatImg) {
		this.beatImg = beatImg;
	}

	public String getBeatNickname() {
		return beatNickname;
	}

	public void setBeatNickname(String beatNickname) {
		this.beatNickname = beatNickname;
	}
}
