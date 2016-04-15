package com.tsingda.simple.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

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
}
