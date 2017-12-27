package com.battle.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.battle.domain.BattleQuestion;
import com.battle.vo.StageSubjectQuestionNum;

public interface BattleQuestionDao extends CrudRepository<BattleQuestion, String>{

	@Query("from com.battle.domain.BattleQuestion bq where bq.battleId=:battleId and bq.periodStageId=:periodStageId order by rand()")
	List<BattleQuestion> findAllByBattleIdAndPeriodStageIdRandom(@Param("battleId")String battleId,@Param("periodStageId")String periodStageId,Pageable pageable);

	List<BattleQuestion> findAllByIdIn(List<String> ids);

	List<BattleQuestion> findAllByPeriodStageIdAndBattleSubjectIdAndIsDelOrderBySeqAsc(String stageId, String subjectId,Integer isDel);

	List<BattleQuestion> findAllByPeriodStageIdAndIsDelOrderBySeqAsc(String stageId,Integer isDel);

	List<BattleQuestion> findAllByBattleIdAndPeriodStageIdAndBattleSubjectIdIn(String battleId, String stageId,
			String[] subjectIds);

	List<BattleQuestion> findAllByBattleIdAndBattlePeriodIdAndIsDel(String battleId, String periodId, int isDel);

	
	@Query("select count(bq) as num,bq.periodStageId as stageId,bq.battleSubjectId as subjectId "
			+ "from com.battle.domain.BattleQuestion bq where  "
			+ "bq.periodStageId in(:stageIds) and bq.battleSubjectId in(:subjectIds) group by bq.periodStageId,bq.battleSubjectId")
	List<Object[]> getQuestionNumByStageIdsAndSubjectIds(@Param("stageIds") List<String> stageIds, @Param("subjectIds") List<String> subjectIds);

}
