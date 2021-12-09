package bg.mycompany.eventbuddy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cloudinary")
public class CloudinaryConfig {

    private String cloudName;
    private String apiKey;
    private String apiSecret;

    public CloudinaryConfig() {
    }

    public String getCloudName() {
        return cloudName;
    }

    /**
     * This sets the cloud name of the cloudinary account.
     * @param cloudName the cloud name associated with the cloudinary account.
     */

    public void setCloudName(String cloudName) {
        this.cloudName = cloudName;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }
}
