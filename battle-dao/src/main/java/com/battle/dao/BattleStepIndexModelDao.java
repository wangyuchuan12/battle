package com.battle.dao;
import java.util.List;
import javax.persistence.QueryHint; 
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleStepIndexModel;

public interface BattleStepIndexModelDao extends CrudRepository<BattleStepIndexModel, String>{

	@QueryHints({@QueryHint(name ="org.hibernate.cacheable", value ="true") })
	List<BattleStepIndexModel> findAll(Pageable pageable);

	@QueryHints({@QueryHint(name ="org.hibernate.cacheable", value ="true") })
	List<BattleStepIndexModel> findAllByModelId(String modelId);

}
