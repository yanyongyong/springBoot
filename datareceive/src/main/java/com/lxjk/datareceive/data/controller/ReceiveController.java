package com.lxjk.datareceive.data.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: ziv
 * @Date: 2018/5/17 10:50
 * @Description:
 */
@RestController
@RequestMapping("/api")
public class ReceiveController {

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String receiveData(@RequestBody String data){
//    public String receiveData(String data){
        return data + "改表了";
    }

    @GetMapping("/protected")
    public @ResponseBody Object hellWorld() {
        return "Hello World! This is a protected api";
    }

}
