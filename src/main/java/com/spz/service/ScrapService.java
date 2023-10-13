package com.spz.service;

import com.spz.entity.scrap.Scrap;

import java.util.ArrayList;

public interface ScrapService {
    ArrayList<Scrap> listByTypeId(Integer id);
}
