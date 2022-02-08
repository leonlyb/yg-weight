package com.example.dechuan.dataconfig;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author eden
 * @description:
 * @menu
 * @date 2021/7/22 15:22
 */
@Configuration
@MapperScan(basePackages = "com.example.dechuan.mapper.second",sqlSessionTemplateRef ="secondSqlSessionTemplate")
public class SecondDataSourceConfig {

    @Autowired
    private SecondDataBaseProperties prop;

    //    创建数据源
    @Bean(name = "secondDS")
    public DataSource getSecondDataSource() {
        DataSource build =  DataSourceBuilder.create()
                .driverClassName(prop.driverClassName)
                .url(prop.url)
                .username(prop.username)
                .password(prop.password)
                .build();
        return build;
    }


    // 创建SessionFactory
    @Bean(name = "secondSqlSessionFactory")
    public SqlSessionFactory secondSqlSessionFactory(@Qualifier("secondDS") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean  bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(
                // 设置mybatis的xml所在位置
                new PathMatchingResourcePatternResolver().getResources("classpath*:mybatis/mapper/second/**/*.xml"));
        bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return bean.getObject();
    }

    // 创建事务管理器

    @Bean("secondTransactionManger")
    public DataSourceTransactionManager secondTransactionManger(@Qualifier("secondDS") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    // 创建SqlSessionTemplate

    @Bean(name = "secondSqlSessionTemplate")
    public SqlSessionTemplate secondSqlSessionTemplate(@Qualifier("secondSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    private Class getType(String type) {
        try {
            return Class.forName(type);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
