package com.spz.api.weixin;

import com.alibaba.fastjson.JSONObject;
import com.spz.common.HttpClientUtils;
import com.spz.common.WeChatProperties;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "微信登录模块")
@Component
public class WechatApi {
    @Autowired
    private WeChatProperties wechatProperties;

    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";
    public JSONObject getJSONObjectByCode(String code) {
        Map<String,String> map = new HashMap<>();
        map.put("appid",wechatProperties.getAppid());
        map.put("secret",wechatProperties.getSecret());
        map.put("js_code",code);
        map.put("grant_type","authorization_code");
        JSONObject jsonObject = HttpClientUtils.httpGet(WX_LOGIN, map);
        return jsonObject;
    }
    public String getOpenIdByCode(String code) {
        Map<String,String> map = new HashMap<>();
        map.put("appid",wechatProperties.getAppid());
        map.put("secret",wechatProperties.getSecret());
        map.put("js_code",code);
        map.put("grant_type","authorization_code");
        JSONObject jsonObject = HttpClientUtils.httpGet(WX_LOGIN, map);
        return jsonObject.getString("openid");
    }
}
