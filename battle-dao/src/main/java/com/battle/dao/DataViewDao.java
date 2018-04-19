package com.battle.dao;
import org.springframework.data.repository.CrudRepository;

import com.battle.domain.DataView;

public interface DataViewDao extends CrudRepository<DataView, String>{

	
	DataView findOneByCode(String code);

}
