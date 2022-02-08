package com.example.dechuan.dataconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author eden
 * @description:
 * @menu
 * @date 2021/7/22 15:18
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource.second")
public class SecondDataBaseProperties {
    String url;
    String username;
    String password;
    String driverClassName;
}

