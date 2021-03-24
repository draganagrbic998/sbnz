package com.example.demo.config;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAsync
@EnableTransactionManagement
public class AppConfig {

	@Bean
	public KieContainer kieContainer() {
		//KieServices kieService = KieServices.Factory.get();
		//KieContainer kieContainer = kieService.newKieContainer(kieService.newReleaseId(Constants.KNOWLEDGE_GROUP, Constants.KNOWLEDGE_ATRIFACT, Constants.KNOWLEDGE_VERSION));
		//KieScanner kieScanner = kieService.newKieScanner(kieContainer);
		//kieScanner.start(1000);
		//return kieContainer;
		return KieServices.Factory.get().getKieClasspathContainer();
	}
	
}
