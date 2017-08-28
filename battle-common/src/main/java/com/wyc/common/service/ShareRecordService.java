package com.wyc.common.service;

import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.common.repositories.ShareRecordRepository;
import com.wyc.common.wx.domain.ShareRecord;

@Service
public class ShareRecordService {

	@Autowired
	private ShareRecordRepository shareRecordRepository;

	public int countByRedPacketIdAndDrawUserId(String redPacketId, String drawUserId) {
		
	//	return shareRecordRepository.countByRedPacketIdAndDrawUserId(redPacketId,drawUserId);
		
		return 0;
	}

	public ShareRecord add(ShareRecord shareRecord) {
		
		shareRecord.setId(UUID.randomUUID().toString());
		shareRecord.setUpdateAt(new DateTime());
		return shareRecordRepository.save(shareRecord);
	}
}
