package com.gugumin.halo.config;

import com.dtflys.forest.Forest;
import com.gugumin.pojo.MetaType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * The type Halo config.
 *
 * @author minmin
 * @date 2023 /03/08
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties("halo-config")
public class HaloConfig {
    private static final String END_WITH_CHAR = "/";
    private String url;
    private String username;
    private String password;
    private String metaType;

    @PostConstruct
    private void initForestVar() {
        String url = this.url;
        if (url.endsWith(END_WITH_CHAR)) {
            url = url.substring(0, url.length() - 1);
        }
        Forest.config().setVariableValue("url", url);
    }

    public MetaType getMetaType() {
        for (MetaType type : MetaType.values()) {
            if (type.getValue().equalsIgnoreCase(metaType)) {
                return type;
            }
        }
        return MetaType.YAML;
    }
}
