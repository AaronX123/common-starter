import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xiaoyouming
 * @version 1.0
 * @since 2020-03-03
 */
@ConfigurationProperties(prefix = "aaron")
public class PropertiesReader {
    private String dataCenterId;
    private String machineId;

    public String getDataCenterId() {
        return dataCenterId;
    }

    public void setDataCenterId(String dataCenterId) {
        this.dataCenterId = dataCenterId;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }
}
