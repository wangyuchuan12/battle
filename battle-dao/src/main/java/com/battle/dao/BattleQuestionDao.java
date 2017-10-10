package com.battle.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleQuestion;

public interface BattleQuestionDao extends CrudRepository<BattleQuestion, String>{

	List<BattleQuestion> findAllByBattleIdAndPeriodStageId(String battleId, String periodStageId);

	List<BattleQuestion> findAllByIdIn(List<String> ids);

	List<BattleQuestion> findAllByPeriodStageIdAndBattleSubjectIdAndIsDelOrderBySeqAsc(String stageId, String subjectId,Integer isDel);

	List<BattleQuestion> findAllByPeriodStageIdAndIsDelOrderBySeqAsc(String stageId,Integer isDel);

	List<BattleQuestion> findAllByBattleIdAndPeriodStageIdAndBattleSubjectIdIn(String battleId, String stageId,
			String[] subjectIds);

}
