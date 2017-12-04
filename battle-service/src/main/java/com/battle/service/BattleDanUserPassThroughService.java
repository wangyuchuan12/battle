package com.battle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.dao.BattleDanUserPassThroughDao;

@Service
public class BattleDanUserPassThroughService {

	@Autowired
	private BattleDanUserPassThroughDao battleDanUserPassThroughDao;
}
