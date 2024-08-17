package com.spz.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spz.common.Res;
import com.spz.entity.page.PageBean;
import com.spz.entity.scrap.Scrap;
import com.spz.service.ScrapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("spz/scrap")
@Slf4j
public class ScrapController {
    @Autowired
    private ScrapService scrapService;

    /**
     * @param @param id
     * @return @return {@link Res }<{@link ArrayList }<{@link Scrap }>>
     * @author last
     * @describe
     */
    @GetMapping("/{id}/list")
    public Res<ArrayList<Scrap>> list(@PathVariable Integer id) {
        log.info("查询: id:{}", id);
        ArrayList<Scrap> list = scrapService.listByTypeId(id);
        return Res.success(list);
    }

    /**
     * @param @param   page
     * @param pageSize
     * @param name
     * @param begin
     * @param end
     * @return @return {@link Res }<{@link PageBean }>
     * @author last
     * @describe 分页查询
     */
    @GetMapping("/page")
    public Res<PageBean> page(@RequestParam(defaultValue = "1")Integer page,
                                 @RequestParam(defaultValue = "10")Integer pageSize,
                                 String name,
                                 @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                 @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("分页查询中，第{}页，{}条,其他参数：{},{},{},{}", page, pageSize, name, begin, end);
        PageBean pageBean = scrapService.page(page, pageSize, name, begin, end);
        return Res.success(pageBean);
    }

    @GetMapping("/{id}")
    public Res<Scrap> getById(@PathVariable Integer id) {
        log.info("查询: id: {}", id);
        Scrap scrap = scrapService.getById(id);
        return Res.success(scrap);
    }

    @PutMapping
    public Res<String> updateById(@RequestBody Scrap scrap) {
        log.info("更新: 回收品{}", scrap);
        scrapService.updateById(scrap);
        return Res.success("修改回收品成功");
    }

    @DeleteMapping
    public Res<String> deleteByIds(@RequestParam List<Integer> ids) {
        log.info("删除: 回收品id:{}", ids);
        scrapService.deleteByIds(ids);
        return Res.success("删除回收品成功");
    }

    @PostMapping
    public Res<String> insert(@RequestBody Scrap scrap) {
        log.info("插入: {}", scrap);
        scrapService.insert(scrap);
        return Res.success("新增回收品成功");
    }
}