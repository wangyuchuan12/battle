package com.wyc.common.repositories;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import com.wyc.common.domain.Href;

public interface HrefRepository extends CrudRepository<Href, String>{

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Href findOneByCode(String code);

}
