package com.hc.architecture.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RestController;

import com.hc.architecture.client.tag.service.IBaseTagService;

@RestController("/tag")
public class TagController {

    @Resource
    private IBaseTagService baseTagService;

}
