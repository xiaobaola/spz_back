package com.spz.complaint.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spz.complaint.entity.Complaint;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 投诉表 服务类
 * </p>
 *
 * @author last
 * @since 2024-11-14
 */
public interface ComplaintService extends IService<Complaint> {

    List<Complaint> listClerk(Page<Complaint> pageParam, String number, Integer status, String begin, String end);
}
