package com.wyc.common.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.common.repositories.ShareRepository;
import com.wyc.common.wx.domain.Share;

@Service
public class ShareService {

	@Autowired
	private ShareRepository shareRepository;

	public Share add(Share share) {
		share.setId(UUID.randomUUID().toString());
		share.setCreateAt(new DateTime());
		share.setUpdateAt(new DateTime());
		return shareRepository.save(share);
		
	}
}
