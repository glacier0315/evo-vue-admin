package com.glacier.modules.sys.entity.dto.menu;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

/**
 * 路由显示类
 * @author glacier
 * @version 1.0
 * date 2020-06-06 10:10
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Router implements Serializable {
    private static final long serialVersionUID = -7180881732184069045L;
    /**
     * 路由名字
     */
    private String name;
    /**
     * 路由地址
     */
    private String path;
    /**
     * 是否隐藏路由，当设置 true 的时候该路由不会再侧边栏出现
     */
    private boolean hidden;
    /**
     * 重定向地址，当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
     */
    private String redirect;
    /**
     * 组件地址
     */
    private String component;
    /**
     * 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
     */
    private Boolean alwaysShow;
    /**
     * 元数据
     */
    private Meta meta;
    /**
     * 子路由
     */
    private List<Router> children;

    public Router() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getRedirect() {
        return this.redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getComponent() {
        return this.component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Boolean getAlwaysShow() {
        return this.alwaysShow;
    }

    public void setAlwaysShow(Boolean alwaysShow) {
        this.alwaysShow = alwaysShow;
    }

    public Meta getMeta() {
        return this.meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Router> getChildren() {
        return this.children;
    }

    public void setChildren(List<Router> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Router{" +
                "name='" + this.name + '\'' +
                ", path='" + this.path + '\'' +
                ", hidden=" + this.hidden +
                ", redirect='" + this.redirect + '\'' +
                ", component='" + this.component + '\'' +
                ", alwaysShow=" + this.alwaysShow +
                ", meta=" + this.meta +
                ", children=" + this.children +
                '}';
    }
}
