package com.wyc.common.wx.domain;

public class Button {
    //点击推事件
    public static final String TYPE_CLICK="click";
    //跳转url
    public static final String TYPE_VIEW="view";
    //扫码推事件
    public static final String TYPE_SCANCODE_PUSH="scancode_push";
    //扫码推事件且弹出“消息接收中”提示框
    public static final String TYPE_SCANCODE_WAITMSG="scancode_waitmsg";
    //弹出系统拍照发图
    public static final String TYPE_PIC_SYSPHOTO="pic_sysphoto";
    //弹出拍照或者相册发图
    public static final String TYPE_PIC_PHOTO_OR_ALBUM="pic_photo_or_album";
    //弹出微信相册发图器
    public static final String TYPE_PIC_WEIXIN = "pic_weixin";
    
    //弹出地理位置选择器
    public static final String TYPE_LOCATION_SELECT="location_select";
    
    //下发消息（除文本消息）
    public static final String TYPE_MEDIA_ID="media_id";
    
    public static final String TYPE_VIEW_MIMITED="view_limited";
    private String type;
    private String name;
    private String key;
    private Button[] sub_button;
    private String url;
    private String media_id;
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public Button[] getSub_button() {
        return sub_button;
    }
    public void setSub_button(Button[] sub_button) {
        this.sub_button = sub_button;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getMedia_id() {
        return media_id;
    }
    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }
}
