package com.battle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattlePeriodStageDao;

@Service
public class BattlePeriodStageService {

	@Autowired
	private BattlePeriodStageDao battlePeriodStageDao;
}
