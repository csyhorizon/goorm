package startProject.sy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import startProject.example.ExampleService;

@SpringBootApplication
public class SyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SyApplication.class, args);

		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		String[] beanNames = context.getBeanDefinitionNames();
		for (String beanName : beanNames) {
			System.out.println(beanName);
		}
	}
}