package com.spz.complaint.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.spz.common.Res;
import com.spz.complaint.entity.Complaint;
import com.spz.complaint.entity.wrapper.ComplaintWrapper;
import com.spz.complaint.service.ComplaintService;
import com.spz.entity.page.PageBean;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * 投诉表 前端控制器
 * </p>
 *
 * @author last
 * @since 2024-11-14
 */
@RestController
@RequestMapping("spz/complaint")
@Tag(name = "投诉模块")
@Slf4j
@RequiredArgsConstructor
public class ComplaintController {
    private final ComplaintService complaintService;
    @PostMapping("/complainant")
    public Res<String> insertComplainant(@RequestBody Complaint complaint) {
        // complaint中创建number UUID
        complaint.setNumber(IdWorker.getIdStr());
        // 设置status为1 状态 1:未处理 2:商家处理中 3:等待客服最终反馈 4:投诉已处理
        complaint.setStatus(1);
        complaintService.save(complaint);
        return Res.success("投诉成功");
    }
    @PostMapping("/respondent")
    public Res<String> insertRespondent(@RequestBody Complaint complaint) {
        complaintService.save(complaint);
        return Res.success("完成反馈");
    }
    @PostMapping("/respondent/clerk")
    public Res<String> updateRespondent(@RequestBody Complaint complaint) {
        // 更新沟通信息
        complaintService.save(complaint);
        return Res.success("完成反馈");
    }
    @PostMapping("/clerk/complainant")
    public Res<String> insertClerk(@RequestBody Complaint complaint) {
        complaintService.saveOrUpdate(complaint);
        return Res.success("处理完成");
    }

    // 客服跟踪这个投诉
    @PutMapping("/clerk/follow")
    public Res<String> updateClerkFollow(@RequestBody Complaint complaint) {
        // 根据complaint.id更新状态
        complaint.setStatus(1);
        // 在原来的基础上跟新 status,updateTime,clerk
        complaintService.updateById(complaint);
        return Res.success("处理完成");
    }

    // 使用mybatisPlus的page
    @GetMapping("/clerk/list")
    public Res<Page<Complaint>> listClerk(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "1") String number,
            @RequestParam(defaultValue = "-1", required = false) Integer status,
            @RequestParam(defaultValue = "2024-11-11") String begin,
            @RequestParam(defaultValue = "2024-11-11") String end) {

        log.info("分页查询中，第{}页，{}条,其他参数：订单编号:{}, 状态:{}, 开始:{}, 结束:{}", page, pageSize, number, status, begin, end);

        // MyBatis Plus 分页查询
        Page<Complaint> pageParam = new Page<>(page, pageSize); // 初始化分页参数
        List<Complaint> clerkList = complaintService.listClerk(pageParam, number, status, begin, end); // 调用 service 层进行查询

        // 设置查询结果到分页对象
        pageParam.setRecords(clerkList);

        return Res.success(pageParam); // 返回分页结果
    }
}
