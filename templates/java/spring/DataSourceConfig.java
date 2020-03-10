package package g-alot{basePackage}.conf;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/*
export JDBC_DATABASE_URL=jdbc:h2:./database/MyDataBase
export JDBC_DATABASE_USERNAME=test
export JDBC_DATABASE_PASSWORD=test

export JDBC_DATABASE_DRIVER=org.h2.Driver
*/

@Configuration
public class DataSourceConfig {

    private static final Logger LOG = LoggerFactory.getLogger(DataSourceConfig.class);

    @Bean
    public DataSource userDataSource() {
        String dbUrl = System.getenv("JDBC_DATABASE_URL") != null ? System.getenv("JDBC_DATABASE_URL") : "jdbc:h2:./database/to-do-service-persistence";
        String username = System.getenv("JDBC_DATABASE_USERNAME") != null ? System.getenv("JDBC_DATABASE_USERNAME") : "test";
        String password = System.getenv("JDBC_DATABASE_PASSWORD") != null ? System.getenv("JDBC_DATABASE_PASSWORD") : "test";
        String driverClassName = System.getenv("JDBC_DATABASE_DRIVER");

        if (driverClassName == null) {
            if (dbUrl.startsWith("jdbc:h2:")) {
                driverClassName = "org.h2.Driver";
            } else if(dbUrl.startsWith("jdbc:postgresql:")) {
                driverClassName = "org.postgresql.Driver";
            } else {
                throw new IllegalArgumentException("please define JDBC_DATABASE_DRIVER");
            }
        }
/*
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);*/

        LOG.info("JDBC_DATABASE_URL: " + dbUrl.replaceAll("(?i)(password).+", "***************"));
        LOG.info("JDBC_DATABASE_DRIVER: " + driverClassName);
        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDefaultAutoCommit(false);

        return dataSource;
    }
}
