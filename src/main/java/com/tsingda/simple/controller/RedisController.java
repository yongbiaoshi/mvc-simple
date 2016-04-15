package com.tsingda.simple.controller;

import java.util.Locale;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("redis")
public class RedisController {

    private static final Logger logger = LoggerFactory.getLogger(RedisController.class);
    
    @Resource(name = "redisTemplate")
    private RedisTemplate<String, String> redisTemplate;
    
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public @ResponseBody Long home(Locale locale, Model model, String data) {
        logger.debug("/redis");
        Long l = redisTemplate.opsForSet().add("TestSetKey", data);
        return l;
    }
    
    @RequestMapping(value = "str", method = RequestMethod.GET)
    public @ResponseBody String str(String data) {
        stringRedisTemplate.opsForHash().put("device_global_settings", "app_download_url", "http://115.28.61.118/haoban/M00/00/6E/cxw9dlcHU7yAOvUHAH-uehCv4IA142.apk");
        return stringRedisTemplate.opsForHash().get("device_global_settings", "app_download_url").toString();
    }

}
