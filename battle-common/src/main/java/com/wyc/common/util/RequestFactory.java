package com.wyc.common.util;

import java.io.IOException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;
@Service
public class RequestFactory {
    @Autowired
    private AutowireCapableBeanFactory autowireFactory;
	private Request getRequestByConnection(URL url) throws IOException {

		Request request = new Request(url);
		autowireFactory.autowireBean(request);
		return request;
	}
	
	public Request accessTokenRequest(String appid, String secret)
			throws IOException {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
						+ appid + "&secret=" + secret);
		return getRequestByConnection(url);

	}
	
	//通过code获取access_token
	public Request oauth2AccessTokenRequest(String appid,String secret,String code , String grantType)throws Exception{
	    URL url = new URL(
	            "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+secret+"&code="+code+"&grant_type="+grantType);
	    return getRequestByConnection(url);
	}
	
	//刷新access_token
	public Request oauth2RefreshToken(String appid , String refreshToken)throws Exception{
	    URL url = new URL(
                    "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="+appid+"&grant_type=refresh_token&refresh_token="+refreshToken);
            return getRequestByConnection(url);
	}
	
	public Request serveripsRequest(String accessToken) throws Exception {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}

	public Request messageCustomRequest(String accessToken) throws Exception {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}

	// 上传图文消息素材【订阅号与服务号认证后均可用】
	public Request uploadnewsRequest(String accessToken) throws Exception {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}

	// 根据分组进行群发【订阅号与服务号认证后均可用】
	public Request massSendallRequest(String accessToken) throws Exception {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}

	// 根据视频群发消息
	public Request uploadVideoRequest(String accessToken) throws Exception {
		URL url = new URL(
				"https://file.api.weixin.qq.com/cgi-bin/media/uploadvideo?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}

	// 根据OpenID列表群发【订阅号不可用，服务号认证后可用】（包括：图文消息，文本，语音，图片）
	public Request massSendRequest(String accessToken) throws Exception {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}

	// 删除群发接口
	public Request massDeleteRequest(String accessToken) throws Exception {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/message/mass/delete?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}

	// 对指定用户发送消息
	public Request massPreviewRequest(String accessToken) throws Exception {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}

	// 获取消息内容
	public Request massGetRequest(String accessToken) throws Exception {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/message/mass/get?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}

	// 模板消息接口
	public Request templateSendRequest(String accessToken) throws Exception {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}

	// 创建分组
	public Request groupsCreateRequest(String accessToken) throws Exception {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/groups/create?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}

	// 查询所有分组
	public Request groupsGetRequest(String accessToken) throws Exception {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/groups/get?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}

	// 查询用户所在分组
	public Request groupsGetidRequest(String accessToken) throws Exception {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/groups/getid?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}

	// 修改分组名称
	public Request groupsUpdateRequest(String accessToken) throws Exception {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/groups/update?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}

	// 移动用户分组
	public Request groupsMembersUpdateRequest(String accessToken)
			throws Exception {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}

	// 批量移动用户分组
	public Request groupsMembersBatchUpdateRequest(String accessToken)
			throws Exception {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/groups/members/batchupdate?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}

	// 删除分组
	public Request groupsDeleteeRequest(String accessToken) throws Exception {
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/groups/delete?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}
	
	//设置用户备注名
	public Request userInfoUpdateRemarkRequest(String accessToken)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token="
						+ accessToken);
		return getRequestByConnection(url);
	}
	
	//获取用户几本信息
	public Request userInfoRequest(String accessToken,String openid,String lang)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/user/info?access_token="+accessToken+"&openid="+openid+"&lang="
						+ lang);
		return getRequestByConnection(url);
	}
	
	//网页用户获取用户信息，通过code获取的opid
	public Request snsUserInfoRequest(String accessToken,String openid,String lang)throws Exception{
            URL url = new URL("https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken+"&openid="+openid+"&lang="+lang);
            return getRequestByConnection(url);
        }
	
	//获取用户列表 next_openid第一个拉取的openid，不填默认从开头开始拉取
	public Request userGetRequest(String accessToken,String next_openid)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/user/get?access_token="+accessToken+"&next_openid="
						+ next_openid);
		return getRequestByConnection(url);
	}
	
	//自定义菜单创建接口
	public Request menuCreateRequest(String accessToken)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+accessToken);
		return getRequestByConnection(url);
	}
	
	//自定义菜单查询
	public Request menuGetRequest(String accessToken)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/menu/get?access_token="+accessToken);
		return getRequestByConnection(url);
	}
	
	//自定义菜单删除
	public Request menuDeleteRequest(String accessToken)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="+accessToken);
		return getRequestByConnection(url);
	}
	
	//自定义菜单配置接口
	public Request menuInfoRequest(String accessToken)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token="+accessToken);
		return getRequestByConnection(url);
	}
	
	//生成带参数的二维码
	public Request qrcodeCreateRequest(String accessToken)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+accessToken);
		return getRequestByConnection(url);
	}
	
	//通过ticket换取二维码
	public Request showQrcodeRequest(String ticket)throws Exception{
		URL url = new URL(
				"https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+ticket);
		return getRequestByConnection(url);
	}
	
	public Request jsapiTicketRequest(String accessToken , String type)throws Exception{
	    URL url = new URL(
                    "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type="+type);
	    return getRequestByConnection(url);
	}
	
	//长链接转短链接接口
	public Request shortUrlRequest(String accessToken)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/cgi-bin/shorturl?access_token="+accessToken);
		return getRequestByConnection(url);
	}
	
	//获取用户增减数据
	public Request getusersummaryRequest(String accessToken)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/datacube/getusersummary?access_token="+accessToken);
		return getRequestByConnection(url);
	}
	
	//获取累计用户数据
	public Request getusercumulateRequest(String accessToken)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/datacube/getusercumulate?access_token="+accessToken);
		return getRequestByConnection(url);
	}
	
	//获取图文群发每日数据
	public Request getarticlesummaryRequest(String accessToken)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/datacube/getarticlesummary?access_token="+accessToken);
		return getRequestByConnection(url);
	}
	
	//获取图文群发总数据
	public Request getarticletotalRequest(String accessToken)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/datacube/getarticletotal?access_token="+accessToken);
		return getRequestByConnection(url);
	}
	
	//获取图文统计数据
	public Request getuserreadRequest(String accessToken)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/datacube/getuserread?access_token="+accessToken);
		return getRequestByConnection(url);
	}
	
	//获取图文统计分时数据
	public Request getuserreadhourRequest(String accessToken)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/datacube/getuserreadhour?access_token="+accessToken);
		return getRequestByConnection(url);
	}
	
	//获取图文分享转发数据
	public Request getusershareRequest(String accessToken)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/datacube/getusershare?access_token="+accessToken);
		return getRequestByConnection(url);
	}
	
	//获取图文分享转发分时数据
	public Request getusersharehourRequest(String accessToken)throws Exception{
		URL url = new URL(
				"https://api.weixin.qq.com/datacube/getusersharehour?access_token="+accessToken);
		return getRequestByConnection(url);
	}
	
	//获取统一支付接口
	public Request payUnifiedorder()throws Exception{
	    URL url = new URL("https://api.mch.weixin.qq.com/pay/unifiedorder");
	    return getRequestByConnection(url);
	}
	
	//获取统一支付接口
	public Request transfers()throws Exception{
	    URL url = new URL("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers");
	    return getRequestByConnection(url);
	}
	
	//获取退款接口
	public Request getRefundRequest()throws Exception{
	    URL url = new URL("https://api.mch.weixin.qq.com/secapi/pay/refund");
            return getRequestByConnection(url);
	}
}
