package tk.artiquno.warehouse.management.authentication.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@Data
@ConfigurationProperties(prefix = "authentication")
@ConfigurationPropertiesScan
public class SecurityProperties {
    private String secret;
    private int daysToExpire;
}
