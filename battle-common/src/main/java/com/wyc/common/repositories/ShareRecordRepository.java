package com.wyc.common.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.wyc.common.wx.domain.ShareRecord;

public interface ShareRecordRepository extends CrudRepository<ShareRecord, String>{

//	@Query(value="select count(*) from com.wyc.common.wx.domain.ShareRecord sr where sr.redPacketId=:redPacketId and sr.drawUserId=:drawUserId")
//	int countByRedPacketIdAndDrawUserId(@Param("redPacketId")String redPacketId, @Param("drawUserId")String drawUserId);

}
