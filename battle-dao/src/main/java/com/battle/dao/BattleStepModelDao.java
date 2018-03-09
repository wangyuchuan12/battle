package com.battle.dao;
import javax.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleStepModel;

public interface BattleStepModelDao extends CrudRepository<BattleStepModel, String>{
	
	@QueryHints({@QueryHint(name ="org.hibernate.cacheable", value ="true") })
	Page<BattleStepModel> findAll(Pageable pageable);

	@QueryHints({@QueryHint(name ="org.hibernate.cacheable", value ="true") })
	BattleStepModel findOneByCode(String code);

}
