package com.countries;

import com.countries.capital.utils.UserInputPromptUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
		})
public class CountryServiceCapitalRetrieverApplication {

	public static void main(String[] args) {
		SpringApplication.run(CountryServiceCapitalRetrieverApplication.class, args);
		new UserInputPromptUtil().acceptUserInputAndAssertResponse();
	}
}