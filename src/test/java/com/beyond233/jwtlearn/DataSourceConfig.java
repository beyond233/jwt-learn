package com.beyond233.jwtlearn;

import com.beyond233.jwtlearn.config.AuthEnableCondition;
import com.beyond233.jwtlearn.config.AuthEnableConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

/**
 * 描述:
 *
 * @author beyond233
 * @since 2020/11/17 21:00
 */
@Configuration
public class DataSourceConfig {

    /**
     * 构建一个嵌入式数据库Hypersonic的数据源
     * */
    @Bean
    @Conditional(AuthEnableCondition.class)
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .addScript("classpath:schema.sql")
                .addScript("classpath:test-data.sql")
                .build();
    }

}
