package com.glacier.modules.sys.entity.dto.user;

/**
 * @author glacier
 * @version 1.0
 * date 2020-05-24 09:32
 */
public class UserDto extends AbstractUserDto {
    private static final long serialVersionUID = 3725415789818769982L;
    /**
     * 主键
     */
    private String id;
    /**
     * 状态  1 正常  0 锁定
     */
    private String status;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id='" + this.id + '\'' +
                ", status='" + this.status + '\'' +
                ", username='" + this.username + '\'' +
                ", nickname='" + this.nickname + '\'' +
                ", idCard='" + this.idCard + '\'' +
                ", birthday=" + this.birthday +
                ", sex=" + this.sex +
                ", email='" + this.email + '\'' +
                ", mobile='" + this.mobile + '\'' +
                ", postId='" + this.postId + '\'' +
                ", deptId='" + this.deptId + '\'' +
                ", deptName='" + this.deptName + '\'' +
                ", introduction='" + this.introduction + '\'' +
                ", roleIds=" + this.roleIds +
                '}';
    }
}
