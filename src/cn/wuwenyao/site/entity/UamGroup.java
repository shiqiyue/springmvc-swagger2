package cn.wuwenyao.site.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 群组
 */
@ApiModel(description = "群组")
public class UamGroup {
    /**
     * 编号
     */
    @ApiModelProperty(required = true, value = "群组的Id")
    private String groupId;
    /**
     * 名称
     */
    @ApiModelProperty(required = true, value = "群组的名称")
    private String name;
    /**
     * 群组图标
     */
    @ApiModelProperty(required = true, value = "群组的头像")
    private String icon;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}