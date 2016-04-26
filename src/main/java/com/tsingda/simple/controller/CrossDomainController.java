package com.tsingda.simple.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping(value = "cross")
public class CrossDomainController {

    @RequestMapping(value = { "form" })
    public String form() {
        return "form";
    }

    @RequestMapping(value = "upload")
    public String upload(MultipartFile file, String id, String redirectUrl) {
        String fileName = "please upload a file";
        if(file != null && !file.isEmpty()){
            fileName = file.getOriginalFilename();
        }
        return String.format("redirect:%s?data=%s&id=%s", redirectUrl, fileName, id);
    }
    
    @RequestMapping(value = "jqupload")
    public String jqUpload(MultipartFile file, String id, String redirectUrl) {
        String fileName = "please upload a file";
        if(file != null && !file.isEmpty()){
            fileName = file.getOriginalFilename();
        }
        return String.format("redirect:%s?data=%s&id=%s", redirectUrl, fileName, id);
    }
    
    
    @RequestMapping(value = { "data" }, method = RequestMethod.GET)
    public @ResponseBody Map<String, String[]> data(HttpServletRequest request) throws JsonProcessingException {
        
        return request.getParameterMap();
    }

}
