package com.battle.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleQuestionDao;
import com.battle.domain.BattleQuestion;
import com.battle.vo.StageSubjectQuestionNum;

@Service
public class BattleQuestionService {

	@Autowired
	private BattleQuestionDao battleQuestionDao;

	public List<BattleQuestion> findAllByBattleIdAndPeriodStageIdRandom(String battleId, String periodStageId,Pageable pageable) {
		return battleQuestionDao.findAllByBattleIdAndPeriodStageIdRandom(battleId,periodStageId,pageable);
	}

	public List<BattleQuestion> findAllByIdIn(List<String> ids) {
		
		return battleQuestionDao.findAllByIdIn(ids);
	}

	public List<BattleQuestion> findAllByPeriodStageIdAndBattleSubjectIdAndIsDelOrderBySeqAsc(String stageId,
			String subjectId,Integer isDel) {
		return battleQuestionDao.findAllByPeriodStageIdAndBattleSubjectIdAndIsDelOrderBySeqAsc(stageId,subjectId,isDel);
	}

	public List<BattleQuestion> findAllByPeriodStageIdAndIsDelOrderBySeqAsc(String stageId,Integer isDel) {
		return battleQuestionDao.findAllByPeriodStageIdAndIsDelOrderBySeqAsc(stageId,isDel);
	}

	public void add(BattleQuestion battleQuestion) {
		
		battleQuestion.setId(UUID.randomUUID().toString());
		battleQuestion.setCreateAt(new DateTime());
		battleQuestion.setUpdateAt(new DateTime());
		battleQuestionDao.save(battleQuestion);
	}

	public void update(BattleQuestion battleQuestion) {
		
		battleQuestion.setUpdateAt(new DateTime());
		battleQuestionDao.save(battleQuestion);
		
	}

	public BattleQuestion findOne(String id) {
		return battleQuestionDao.findOne(id);
	}

	public List<BattleQuestion> findAllByBattleIdAndPeriodStageIdAndBattleSubjectIdInAndIsDel(String battleId, String stageId,
			String[] subjectIds,Integer isDel) {
		return battleQuestionDao.findAllByBattleIdAndPeriodStageIdAndBattleSubjectIdInAndIsDel(battleId,stageId,subjectIds,isDel);
	}

	public List<BattleQuestion> findAllByBattleIdAndBattlePeriodIdAndIsDel(String battleId, String periodId, int isDel) {
		return battleQuestionDao.findAllByBattleIdAndBattlePeriodIdAndIsDel(battleId,periodId,isDel);
	}

	public List<Object[]> getQuestionNumByStageIdsAndSubjectIds(List<String> stageIds,
			List<String> subjectIds) {
		
		return battleQuestionDao.getQuestionNumByStageIdsAndSubjectIds(stageIds,subjectIds);
	}
}
