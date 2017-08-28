package com.wyc.common.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.common.domain.Href;
import com.wyc.common.repositories.HrefRepository;

@Service
public class HrefService {

	@Autowired
	private HrefRepository hrefRepository;

	public Href findOneByCode(String code) {
		System.out.println("..............code:"+code);
		return hrefRepository.findOneByCode(code);
	}

	public void add(Href href) {
		href.setId(UUID.randomUUID().toString());
		
		href.setUpdateAt(new DateTime());
		
		href.setCreateAt(new DateTime());
		
		hrefRepository.save(href);
		
	}

	public void update(Href href) {
		href.setUpdateAt(new DateTime());
		
		hrefRepository.save(href);
		
	}
}
