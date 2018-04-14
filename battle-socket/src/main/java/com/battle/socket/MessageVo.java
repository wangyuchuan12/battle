package com.battle.socket;

import java.util.List;

public class MessageVo {

	public final static int ALL_ONLINE_TYPE = 0;
	public final static int ROOM_TYPE = 1;
	public final static int USERS_TYPE = 2;
	
	
	public final static String PROGRESS_CODE = "progressCode";
	//调用编号
	private String code;
	
	//调用类型 0表示 全范围 1表示某个房间 2表示userId结合
	private Integer type;
	
	//调用数据
	private Object data;
	
	//房间名称
	private String roomId;
	
	//用户数据
	private List<String> userIds;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public List<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}
}
