package com.wyc.common.wx.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wyc.common.util.Request;
import com.wyc.common.util.RequestFactory;
import com.wyc.common.util.Response;
import com.wyc.common.wx.domain.Result;

@Service
public class MenuService {
    @Autowired
    private RequestFactory requestFactory;
    public Result createMenu(String json , String accessToken)throws Exception{
        Request request = requestFactory.menuCreateRequest(accessToken);
        Response response = request.post(json);
        Result result = response.readObject(Result.class);
        return result;
    }
}
