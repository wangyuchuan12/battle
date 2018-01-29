package com.battle.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wyc.annotation.IdAnnotation;
import com.wyc.annotation.ParamAnnotation;
import com.wyc.annotation.ParamEntityAnnotation;

@ParamEntityAnnotation
@Entity
@Table(name="battle_member_question_answer",indexes={@Index(columnList="battle_member_paper_answer_id",name="battleMemberQuestionAnswerIndex")})
public class BattleMemberQuestionAnswer  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@IdAnnotation
	private String id;
	
	@ParamAnnotation
	@Column(name="question_id")
	private String questionId;
	
	@ParamAnnotation
	@Column(name="battle_member_paper_answer_id")
	private String battleMemberPaperAnswerId;
	
	
	//0选择题 1填空题 2填词题
	@Column
	@ParamAnnotation
	private Integer type;
	
	//图片地址
	@Column
	@ParamAnnotation
	private String imgUrl;
	
	@Column
	@ParamAnnotation
	private String question;
	
	
	//答案（把可见部分的答案提取出来放进去）
	@Column
	@ParamAnnotation
	private String answer;
	
	//正确答案
	@Column(name="right_answer")
	@ParamAnnotation
	private String rightAnswer;
	
	//选项
	@Column
	@ParamAnnotation
	private String options;
	
	@ParamAnnotation
	@Column(name = "create_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime createAt;
	
	@ParamAnnotation
    @Column(name = "update_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime updateAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getBattleMemberPaperAnswerId() {
		return battleMemberPaperAnswerId;
	}

	public void setBattleMemberPaperAnswerId(String battleMemberPaperAnswerId) {
		this.battleMemberPaperAnswerId = battleMemberPaperAnswerId;
	}
	
	

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}

	public DateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(DateTime createAt) {
		this.createAt = createAt;
	}

	public DateTime getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(DateTime updateAt) {
		this.updateAt = updateAt;
	}
}
