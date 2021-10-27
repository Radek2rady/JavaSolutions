package coreit.coreit;

import controllers.EmployeeController;
import java.util.Map.Entry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import services.EmployeeService;

@SpringBootApplication
@EnableJpaRepositories("repositories")
@EntityScan("models")
@ComponentScan(basePackageClasses = EmployeeController.class)
@ComponentScan(basePackageClasses = EmployeeService.class)
public class CoreitApplication {

  public static void main(String[] args) {
    SpringApplication.run(CoreitApplication.class, args);
  }

}
