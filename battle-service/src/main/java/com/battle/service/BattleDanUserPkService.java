package com.battle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleDanUserPkDao;

@Service
public class BattleDanUserPkService {

	@Autowired
	private BattleDanUserPkDao battleDanUserPkDao;
}
