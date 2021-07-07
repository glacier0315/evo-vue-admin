package com.glacier.modules.sys.service;

import com.glacier.common.core.entity.page.PageRequest;
import com.glacier.common.core.entity.page.PageResponse;
import com.glacier.modules.sys.entity.dto.post.PostDto;
import com.glacier.modules.sys.entity.dto.post.PostQuery;

import java.util.List;

/**
 * 岗位业务层
 * @author glacier
 * @version 1.0
 * date 2020-09-05 21:46
 */
public interface PostService {
    /**
     * 分页查询
     *
     * @param pageRequest 请求封装实体类
     * @return 分页响应
     */
    PageResponse<PostDto> findPage(PageRequest<PostQuery> pageRequest);

    /**
     * 查询所有
     *
     * @return 所有岗位
     */
    List<PostDto> findAllList();

    /**
     * 校验
     *
     * @param postDto 岗位封装实体类
     * @return 校验结果
     */
    boolean checkCode(PostDto postDto);

    /**
     * 保存操作
     *
     * @param postDto 岗位封装实体类
     * @return 保存记录数
     */
    int save(PostDto postDto);

    /**
     * 根据Id删除
     *
     * @param id 主键
     * @return 删除记录数
     */
    int delete(String id);
}
