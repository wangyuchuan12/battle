var CustomerFormPanel = Ext.extend(Ext.form.FormPanel,{
	openidTextField:null,
	phonenumberTextField:null,
	defaultAddressTextField:null,
	cityTextField:null,
	countryTextField:null,
	groupIdTextField:null,
	headimgUrlTextField:null,
	languageTextField:null,
	nicknameTextField:null,
	provinceTextField:null,
	sexTextField:null,
	tokenTextField:null,
	invalidDateTextField:null,
	constructor:function(){
		var outThis = this;
		this.openidTextField = new Ext.form.TextField({
			fieldLabel:"openid",
			disabled:true
		});
		this.phonenumberTextField = new Ext.form.TextField({
			fieldLabel:"电话号码",
			disabled:true
		});
		this.defaultAddressTextField = new Ext.form.TextField({
			fieldLabel:"默认地址",
			disabled:true
		});
		this.cityTextField = new Ext.form.TextField({
			fieldLabel:"所在城市",
			disabled:true
		});
		this.countryTextField = new Ext.form.TextField({
			fieldLabel:"所在国家",
			disabled:true
		});
		
		this.languageTextField = new Ext.form.TextField({
			fieldLabel:"语言",
			disabled:true
		});
		this.groupIdTextField = new Ext.form.TextField({
			fieldLabel:"所属组",
			disabled:true
		});
		this.headimgUrlTextField = new Ext.form.TextField({
			fieldLabel:"头像",
			disabled:true
		});
		this.provinceTextField = new Ext.form.TextField({
			fieldLabel:"所在省",
			disabled:true
		});
		this.sexTextField = new Ext.form.TextField({
			fieldLabel:"性别",
			disabled:true
		});
		this.tokenTextField = new Ext.form.TextField({
			fieldLabel:"token",
			disabled:true
		});
		this.invalidDateTextField = new Ext.form.TextField({
			fieldLabel:"token有效期",
			disabled:true
		});
		
		this.nicknameTextField = new Ext.form.TextField({
			fieldLabel:"用户别昵",
			disabled:true
		});
		CustomerFormPanel.superclass.constructor.call(this,{
			items:[outThis.openidTextField,outThis.nicknameTextField,outThis.phonenumberTextField,outThis.defaultAddressTextField,
			       outThis.cityTextField,outThis.countryTextField,outThis.groupIdTextField,
			       outThis.headimgUrlTextField,outThis.provinceTextField,outThis.sexTextField,outThis.languageTextField,
			       outThis.tokenTextField,outThis.invalidDateTextField],
			frame:true,
			buttons:[{text:"确定"}]
		});
	}
});