package com.battle.dao;
import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import com.battle.domain.DataView;

public interface DataViewDao extends CrudRepository<DataView, String>{

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	DataView findOneByCode(String code);

}
