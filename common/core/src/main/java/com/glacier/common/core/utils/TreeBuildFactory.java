package com.glacier.common.core.utils;

import com.glacier.common.core.entity.TreeData;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 菜单构建工厂
 *
 * @author glacier
 * @version 1.0
 * date 2020-06-16 21:08
 */
public class TreeBuildFactory {
    
    private TreeBuildFactory() {
    }
    
    /**
     * 组装树
     *
     * @param list 树元素集合
     * @param <T>  树元素类型
     * @return 树
     */
    public static <T extends TreeData<T>> List<T> buildTree(final List<T> list) {
        return buildTree(list, t -> StringUtils.isBlank(t.getParentId()));
    }
    
    /**
     * 组装树
     *
     * @param list      树元素集合
     * @param predicate 根节点断言
     * @param <T>       树元素类型
     * @return 树
     */
    public static <T extends TreeData<T>> List<T> buildTree(final List<T> list, Predicate<T> predicate) {
        // 分离出所有一级
        List<T> topList = list.stream()
                .filter(predicate)
                .peek(t -> t.setGrade(1))
                .sorted(Comparator.comparingInt(T::getOrderNum))
                .collect(Collectors.toList());
        // 组装子类菜单
        buildChildren(topList, list);
        return topList;
    }
    
    /**
     * 递归组装子节点
     *
     * @param topList 当前父级元素
     * @param list    待查询树元素集合
     */
    private static <T extends TreeData<T>> void buildChildren(final List<T> topList, final List<T> list) {
        topList.forEach(t -> {
            List<T> subList = list.stream()
                    .filter(e -> t.getId().equals(e.getParentId()))
                    .peek(e -> e.setGrade(t.getGrade() + 1))
                    .sorted(Comparator.comparingInt(T::getOrderNum))
                    .collect(Collectors.toList());
            t.setChildren(subList);
            buildChildren(subList, list);
        });
    }
}
