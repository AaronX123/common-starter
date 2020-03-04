package aaron.common.data.common;

/**
 * DTO基类，token属性便于切面提取公共字段
 * @author xiaoyouming
 * @version 1.0
 * @since 2019-08-23
 */
public class BaseDto extends CommonField {
    /**
     * 当前前端传来的DTO里的版本信息，在更新和删除时需要和数据库中version进行比较
     */
    private Long oldVersion;
    public BaseDto() {
    }

    public Long getOldVersion() {
        return oldVersion;
    }

    public void setOldVersion(Long oldVersion) {
        this.oldVersion = oldVersion;
    }

    @Override
    public String toString() {
        return "BaseDto{" +
                "oldVersion=" + oldVersion +
                ", id=" + id +
                ", orgId=" + orgId +
                ", companyId=" + companyId +
                ", createdBy=" + createdBy +
                ", createdTime=" + createdTime +
                ", updatedBy=" + updatedBy +
                ", updatedTime=" + updatedTime +
                ", version=" + version +
                '}';
    }
}
