package com.spz.complaint.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spz.complaint.entity.Complaint;
import com.spz.complaint.mapper.ComplaintMapper;
import com.spz.complaint.service.ComplaintService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 投诉表 服务实现类
 * </p>
 *
 * @author last
 * @since 2024-11-14
 */
@RequiredArgsConstructor
@Service
public class ComplaintServiceImpl extends ServiceImpl<ComplaintMapper, Complaint> implements ComplaintService {
    private final ComplaintMapper complaintMapper; // 假设有一个 ComplaintMapper 用于操作数据库

    public List<Complaint> listClerk(Page<Complaint> page, String number, Integer status, String begin, String end) {
        // 使用 LambdaQueryWrapper 来构造查询条件
        LambdaQueryWrapper<Complaint> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(number != null, Complaint::getNumber, number);
        queryWrapper.eq(status != null, Complaint::getStatus, status);
        queryWrapper.between(begin != null && end != null, Complaint::getCreateTime, begin, end);
        return complaintMapper.selectPage(page, queryWrapper).getRecords();
    }
}