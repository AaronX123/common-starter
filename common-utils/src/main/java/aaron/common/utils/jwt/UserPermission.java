package aaron.common.utils.jwt;

/**
 * @author Cjf
 */
public class UserPermission {
    private Long id;
    private String userName;
    private Long orgId;
    private Long companyId;
    private Long userOnlineId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "UserPermission{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", orgId=" + orgId +
                ", companyId=" + companyId +
                ", userOnlineId=" + userOnlineId +
                '}';
    }

    public Long getUserOnlineId() {
        return userOnlineId;
    }

    public void setUserOnlineId(Long userOnlineId) {
        this.userOnlineId = userOnlineId;
    }
}
