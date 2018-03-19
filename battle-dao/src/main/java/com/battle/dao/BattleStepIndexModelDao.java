package com.battle.dao;
import java.util.List;
import javax.persistence.QueryHint; 
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import com.battle.domain.BattleStepIndexModel;

public interface BattleStepIndexModelDao extends CrudRepository<BattleStepIndexModel, String>{

	//@QueryHints({@QueryHint(name ="org.hibernate.cacheable", value ="true") })
	List<BattleStepIndexModel> findAllByIsDel(Pageable pageable,Integer isDel);

	//@QueryHints({@QueryHint(name ="org.hibernate.cacheable", value ="true") })
	List<BattleStepIndexModel> findAllByModelIdAndIsDel(String modelId,Integer isDel);

}
