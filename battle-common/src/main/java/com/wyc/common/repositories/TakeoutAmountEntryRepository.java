package com.wyc.common.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.wyc.common.domain.TakeoutAmountEntry;

public interface TakeoutAmountEntryRepository extends CrudRepository<TakeoutAmountEntry, String>{

	List<TakeoutAmountEntry> findAllByIsDisplay(int isDisplay);

}
