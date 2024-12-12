package it.gov.pagopa.pu.worker.config;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
public class DataSourceConfiguration {

  @Bean
  @ConfigurationProperties("spring.data.mypay")
  public DataSource myPayDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean myPayEntityManagerFactory(
      DataSource dataSource,
      EntityManagerFactoryBuilder builder) {

      Map<String, Object> props = new HashMap<>();
      props.put("hibernate.physical_naming_strategy", CamelCaseToUnderscoresNamingStrategy.class.getName());
      props.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());

      return builder.dataSource(dataSource)
          .packages("it.gov.pagopa.pu.worker")
          .properties(props)
          .build();
  }

  @Bean
  public PlatformTransactionManager myPayTransactionManager(
    EntityManagerFactory myPayEntityManagerFactory) {
        return new JpaTransactionManager(myPayEntityManagerFactory);
  }
}
