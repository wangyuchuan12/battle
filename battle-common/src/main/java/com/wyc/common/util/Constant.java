package com.wyc.common.util;



public class Constant {

	
	
	public static final int SEX_FEMALE_TYPE =0; // 女
	public static final int SEX_MALE_TYPE =1; // 男
	public static final int SEX_UNKNOW_TYPE =2; //
	
	
	
	/***********************房间验证类型************************/
	//密码验证
	public static final int PASSWORD_VERIFY_TYPE = 0;
	
	//问题验证
	public static final int QUESTION_VERIFY_TYPE = 1;
	
	//不进行问题验证
	public static final int NO_VERIFY_TYPE = 2;
	
	
	/***********************房间用户状态************************/
	
	//申请中
	public static final int AUDIT_DRAW_ROOM_MEMEBER_STATUS = 0;
	
	//申请通过
	public static final int PASSED_DRAW_ROOM_MEMEBER_STATUS = 1;
	
	//已退出
	public static final int OUT_DRAW_ROOM_MEMEBER_STATUS = 2;
	
	
	/***********************红包类型************************/
	//问答红包
	public static final int QUESTION_TYPE = 0;
	
	
	//竞答红包
	public static final int VIE_TYPE = 1;
	
	
	/**********************问题类型**************************/
	
	public static final int CHOICE_QUESTION_TYPE=0;
	
	public static final int FILLBLANK_QUESTION_TYPE=1;
	
	public static final int FILLTERMS_QUESTION_TYPE=2;
	
	
	/***********************支付类型************************/
	//余额支付
	public static final int ACCOUNT_PAY_TYPE=0;
	
	//微信支付
	public static final int WX_PAY_TYPE=1;
	
	//豆子支付
	public static final int BEAN_PAY_TYPE=2;
	
	//砖石支付
	public static final int MASONRY_PAY_TYPE=3;
	
	
	
	
	/********************订单类型*************************/
	//商品订单类型
	public static final int GOOD_ORDER_TYPE = 0;
	
	/************************账号交易类型********************/
	//微信平台-本平台
	public static final int ACCOUNT_RECORD_WP_TYPE=0;
	
	//微信平台-微信账户
	public static final int ACCOUNT_RECORD_WO_TYPE=1;
	
	//本平台-微信账户
	public static final int ACCOUNT_RECORD_PO_TYPE=2;
	
	
	/************************付款方式********************/
	//人民币
	public static final int PAY_MODEL_RMB=0;
	
	
	/*************************账号支出类型***************/
	//入账
	public static final int ACCOUNT_RECORD_IN=0;
	
	//出账
	public static final int ACCOUNT_RECORD_OUT=1;
	
	
	/***********************申请单状态************************/
	//申请单申请中状态
	public static final int APPLY_FORM_STATUS_IN = 0;
	
	//申请单申请成功状态
	public static final int APPLY_FORM_STATUS_SUCCESS = 1;
	
	//申请单申请失败状态
	public static final int APPLY_FORM_STATUS_FAILURE = 2;
	
	
	//手动处理申请单
	public static final int APPLY_FORM_STATUS_MANUAL = 3;
	
	
	/***********************申请单类型************************/
	//申请单提现申请
	public static final int APPLY_FORM_TYPE_TAKE_OUT = 0;
	
	//申请单充值申请
	public static final int APPLY_FORM_TYPE_TAKE_IN = 1;
	
	
	
	/***********************红包列表筛选类型************************/
	//全部
	public static final int RED_PACKET_TYPE_ALL = 0;
	
	//我发起的
	public static final int RED_PACKET_TYPE_HAND=1;
	
	//我参与的
	public static final int RED_PACKET_TYPE_TAKEPART=2;
	
	
	/***********************分享的类型************************/
	//分享给好友
	public static final int SHARE_APP_MSG_SHARE_TYPE = 0;
	
	//分享到朋友圈
	public static final int SHARE_TIME_LINE_SHARE_TYPE = 1;
	
	
	/***********************分享的类别************************/
	
	public static final int SHARE_RED_PACKET_TYPE = 0;
	
	
	
	/***********************支付之后回调类型********************/
	//发起红包回调
	public static final int PAY_SUCCESS_CALLBACK_ADDREDPACK=0;
	
	//充值金额回调
	public static final int PAY_SUCCESS_CALLBACK_RECHARGE=1;
	
	
	/**********************支付的类别************************/
	//普通问答红包支付回调
	public static final int PAY_QA_TYPE=0;
	//竞答红包支付回调
	public static final int PAY_VIE_TYPE=1;
	
	//竞答红包参赛
	public static final int PAY_VIE_TAKEPART_TYPE=2;
	
	/**********************支付回调************************/
	//普通问答红包支付回调地址
	public static final String PAY_QA_TYPE_NONCE_URL="/api/pay/wx/pa_pay_success";
	//竞答红包支付回调地址
	public static final String PAY_VIE_TYPE_NONCE_URL="/api/pay/wx/vie_pay_success";
	
	//竞答红包参赛支付回调地址
	public static final String PAY_VIE_TAKEPART_TYPE_NONCE_URL="/api/pay/wx/vie_takepart_pay_success";
	
	public static final String PAY_ORDER_NONCE_URL="/api/pay/wx/order_pay_success";

	
	
	/***********************竞答红包参与状态********************/
	//未参与
	public static final int NOT_INVOLVED_TAKEPART_STATUS=0;
	
	//正在进行
	public static final int UNDERWAY_TAKEPART_STATUS=1;
	
	//参与结数，问答红包表示金额已经领取
	public static final int COMPLETE_TAKEPART_STATUS=2;
	
	//中途退出
	public static final int RENEGINT_TAKEPART_STATUS=3;
	
	
	//领取失败
	public static final int FAIL_TAKEPART_STATUS=4;
	
	
	/***********************挑战者参与状态********************/
	//未参与
	public static final int DEKORN_NOT_INVOLVED_TAKEPART_STATUS=0;
	
	//正在进行
	public static final int DEKORN_UNDERWAY_TAKEPART_STATUS=1;
	
	//参与结数
	public static final int DEKORN_COMPLETE_TAKEPART_STATUS=2;
	
	//中途退出
	public static final int DEKORN_RENEGINT_TAKEPART_STATUS=3;
	
	
	//领取失败
	public static final int DEKORN_FAIL_TAKEPART_STATUS=4;
	
	
	/*********************问答参与者状态************************/
	//正在进行
	public static final int PAPER_ANSWER_UNDERWAY_TAKEPART_STATUS=1;
		
	//参与结数
	public static final int PAPER_ANSWER_COMPLETE_TAKEPART_STATUS=2;
		
	//中途退出
	public static final int PAPER_ANSWER_RENEGINT_TAKEPART_STATUS=3;
	
	
	
	
	/************************ 竞答红包分配状态 *********************/
	//未分配
	public static final int NOT_DISTRIBUTION_STATUS = 0;
	
	//已分配
	public static final int ALEADY_RECEIVE_STATUS = 1;
	
	
	
	
	/**************************挑战本身状态******************************/
	
	//游离状态
	public static final int DEKORN_FREE_STATUS = 0;
	
	//进行中状态
	public static final int DEKORN_PROGRESS_STATUS=1;
	
	//结束状态
	public static final int DEKORN_END_STATUS=2;
	
	//拒绝状态
	public static final int DEKORN_PROGRESS_REJECT=3;
	
	
	/**************************prop状态******************************/
	//冷却中状态
	public static final int PROP_COOLING_STATUS = 0;
	
	//完成状态
	public static final int PROP_COMPLETE_STATUS=1;
	
	
	/***************************battleToMember状态********************/
	//游离状态
	public static final int BM_STATUS_FREE = 0;
	
	//进行中
	public static final int BM_STATUS_IN = 1;
	
	//结束
	public static final int BM_STATUS_END = 2;
	
	//异常退出状态
	public static final int BM_STATUS_ERROR = 3;
	
	
	
	/**************************试卷答题类型*****************************/
	//闯关类型
	public static final int PAPER_ANSWER_TYPE_BATTLE=0;
	
	
	/**************************BattleRank状态**************************/
	
	//正在组队
	public static final int BATTLE_RANK_IN=1;
	
	//组队完成
	public static final int BATTLE_RANK_COMPLETE=2;
	
	//结束
	public static final int BATTLE_RANK_END=3;
	
	/*************************BattleRankMember状态************************/
	
	//游离状态
	public static final int BATTLE_RANK_MEMBER_STATUS_FREE=0;
	
	//进行中状态
	public static final int BATTLE_RANK_MEMBER_STATUS_IN=1;
	
	//结束状态
	public static final int BATTLE_RANK_MEMBER_STATUS_END=2;
	
	
	/*************************BattleModelToDraw************************/
	
	
	//游离状态
	public static final int BATTLE_MODEL_TO_DRAWUSER_FREE=0;
	
	//进行中状态
	public static final int BATTLE_MODEL_TO_DRAWUSER_IN=1;
	
	//结束状态
	public static final int BATTLE_MODEL_TO_DRAWUSER_END=2;
	
	
	/*************************Battle状态************************/
	
	//游离状态
	public static final int BATTLE_STATUS_FREE = 0;
	
	//进行中
	public static final int BATTLE_STATUS_IN = 1;
	
	//超时
	public static final int BATTLE_STATUS_TIMEOUT = 2;
	
	//完成
	public static final int BATTLE_STATUS_SUCCESS = 3;
	
	
	/*************************BattleReward状态************************/
	
	//未分配
	public static final int BATTLE_REWARD_STATUS_FRESS=0;
	
	//已分配，未领取
	public static final int BATTLE_REWARD_STATUS_IN=1;
	
	//已领取
	public static final int BATTLE_REWARD_STATUS_SUCCESS=2;
	
	
	
	
	/************************支付错误代码**********************************/
	
	//砖石不足
	public static final int MASONRY_SHORTAGE_ERROR_CODE = 0;
	
	//支付砖石数量错误
	public static final int MASONRY_NUM_ERROR_CODE = 1;
	
	
	/************************红包来源**********************************/
	
	//来自搜索
	public static final int RED_PACKET_SEARCH_SOURCE = 0;
	
	//来自新增
	public static final int RED_PACKET_HAND_SOURCE = 1;
	
	//来自比赛
	public static final int RED_PACKET_BATTLE_SOURCE = 2;
	
}
