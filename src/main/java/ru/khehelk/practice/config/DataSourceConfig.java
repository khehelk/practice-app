package ru.khehelk.practice.config;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.jooq.ConnectionProvider;
import org.jooq.impl.DataSourceConnectionProvider;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.postgres")
    public DataSourceProperties postgresProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource postgresDataSource() {
        return postgresProperties().initializeDataSourceBuilder()
            .type(HikariDataSource.class)
            .build();
    }


    @Bean
    @ConfigurationProperties("spring.datasource.clickhouse")
    public DataSourceProperties clickhouseProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource clickhouseDataSource() {
        return clickhouseProperties().initializeDataSourceBuilder()
            .type(HikariDataSource.class)
            .build();
    }

    @Bean
    public SqlSessionFactory clickhouseSqlSessionFactory(
        @Qualifier("clickhouseDataSource") DataSource clickhouseDataSource
    ) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(clickhouseDataSource);

        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate clickhouseSqlSessionTemplate(SqlSessionFactory clickhouseSqlSessionFactory) {
        return new SqlSessionTemplate(clickhouseSqlSessionFactory);
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean(ConnectionProvider.class)
    DataSourceConnectionProvider dataSourceConnectionProvider(DataSource postgresDataSource) {
        return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(postgresDataSource));
    }

}