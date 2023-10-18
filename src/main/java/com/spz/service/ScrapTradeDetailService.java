package com.spz.service;

import com.spz.entity.scrap.ScrapTradeDetail;

import java.util.ArrayList;
import java.util.List;

public interface ScrapTradeDetailService {
    void insertList(List<ScrapTradeDetail> scrapTradeDetails, String number);
}
