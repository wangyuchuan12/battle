package com.battle.service.other;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.domain.BattleRoom;
import com.battle.service.BattleRoomService;
import com.wyc.common.domain.MyResource;
import com.wyc.common.smart.service.UploadToQNService;
import com.wyc.common.util.CommonUtil;
import com.wyc.common.wx.service.QrcodeService;

@Service
public class ImgService {
	
	@Autowired
	private UploadToQNService uploadToQNService;
	
	@Autowired
	private QrcodeService qrcodeService;
	
	@Autowired
	private BattleRoomService battleRoomService;
	public MyResource createRoomImg(String roomId)throws Exception{
		BufferedImage bufferedImage = new BufferedImage(500, 800, BufferedImage.TYPE_INT_RGB);
    
		BattleRoom battleRoom = battleRoomService.findOne(roomId);
		if(!CommonUtil.isEmpty(battleRoom.getRoomShareImg())){
			MyResource myResource = new MyResource();
			
			myResource.setUrl(battleRoom.getRoomShareImg());
			
			myResource.setId(UUID.randomUUID().toString());
			
			return myResource;
		}
		
		
		com.wyc.common.util.JavaImgUtil.Pic tt = new com.wyc.common.util.JavaImgUtil.Pic();  
    	bufferedImage.setRGB(0, 0, 40);
    	
    	String qrPath = "pages/battleTakepart/battleTakepart?roomId="+roomId+"&battleId="+battleRoom.getBattleId();
		MyResource myResource = qrcodeService.createWxaqrcode(qrPath);
    	
    	BufferedImage d = tt.loadImageUrl(myResource.getUrl());
    	
    	
    	
	    BufferedImage b = tt.loadImageUrl(battleRoom.getImgUrl());
      
        
        try { 
        	
        	Graphics2D g = bufferedImage.createGraphics();
            
            g.fillRect(0, 0, 500, 800);
            
            g.setBackground(Color.WHITE);
            
            g.drawImage(b, 0, 0, 500, 300, null);
            
            g.setColor(Color.BLACK);
            
            Font font = new Font("Times New Roman",Font.ITALIC,20);
            g.setFont(font);
            g.drawString(battleRoom.getName(), 20, 290);
            
           
            
            g.drawImage(d, 0, 300, 500, 500, null);
            g.dispose();  
            
            String path = "/root/battle/temp";
            
            File file = new File(path);
            file.mkdirs();
            tt.writeImageLocal(path, bufferedImage);
            
            MyResource roomImgResource = new MyResource();
            roomImgResource.setId(UUID.randomUUID().toString());
            roomImgResource.setSystemUrl(path);
            
            uploadToQNService.syncResource(roomImgResource);
            
            file.deleteOnExit();
            
            battleRoom.setRoomShareImg(roomImgResource.getUrl());
            
            battleRoomService.update(battleRoom);
            return roomImgResource;
        } catch (Exception e) {  
            System.out.println(e.getMessage());  
        }  
  
        return null;  
	}
}
