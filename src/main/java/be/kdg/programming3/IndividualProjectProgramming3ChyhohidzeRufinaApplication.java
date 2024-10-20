package be.kdg.programming3;

import be.kdg.programming3.presentation.console.Menu;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class IndividualProjectProgramming3ChyhohidzeRufinaApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context =
				SpringApplication.run(IndividualProjectProgramming3ChyhohidzeRufinaApplication.class, args);
		context.getBean(Menu.class).print();
	}
// //
}
