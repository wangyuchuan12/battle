package com.wyc.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.common.domain.TakeoutAmountEntry;
import com.wyc.common.repositories.TakeoutAmountEntryRepository;

@Service
public class TakeoutAmountEntryService {

	@Autowired
	private TakeoutAmountEntryRepository takeoutAmountEntryRepository;

	public TakeoutAmountEntry findOne(String id) {
		
		return takeoutAmountEntryRepository.findOne(id);
	}

	public List<TakeoutAmountEntry> findAllByIsDisplay(int isDisplay) {
		
		return takeoutAmountEntryRepository.findAllByIsDisplay(isDisplay);
	}
}
