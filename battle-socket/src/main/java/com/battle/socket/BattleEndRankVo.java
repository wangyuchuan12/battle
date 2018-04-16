package com.battle.socket;

public class BattleEndRankVo {

	private Integer rank;
	
	private String memberId;
	
	private String roomId;
	
	private String nickname;
	
	private String headImg;
	
	private Integer score;
	
	private Integer process;

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getProcess() {
		return process;
	}

	public void setProcess(Integer process) {
		this.process = process;
	}
}
