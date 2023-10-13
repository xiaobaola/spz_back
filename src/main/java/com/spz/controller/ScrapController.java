package com.spz.controller;

import com.spz.common.Res;
import com.spz.entity.scrap.Scrap;
import com.spz.service.ScrapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("spz/scrap")
@Slf4j
public class ScrapController {
    @Autowired
    private ScrapService scrapService;

    @GetMapping("/{id}/list")
    public Res<ArrayList<Scrap>> list(@PathVariable Integer id) {
        ArrayList<Scrap> list = scrapService.listByTypeId(id);
        return Res.success(list);
    }
}
