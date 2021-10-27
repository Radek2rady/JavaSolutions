package services;

import static java.lang.Integer.parseInt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.EmployeeRepository;

@Service
public class EmployeeService {

  private final EmployeeRepository employeeRepository;

  @Autowired
  public EmployeeService(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  String line = "";

  public String saveEmployeeData() {
    HashMap<String, Integer> dataReport = new HashMap<>();
    try {
      BufferedReader br = new BufferedReader(
          new FileReader("src/main/resources/" + System.getenv("FILE_NAME")));
      br.readLine();
      line = null;
      while ((line = br.readLine()) != null) {
        String[] data = line.split(";");
        Optional<Employee> foundEmployee = employeeRepository.findById(parseInt(data[0]));
        if (foundEmployee.isPresent()) {
          var currentEmployee = foundEmployee.get();
          if (currentEmployee.getEmail().equals(data[1]) &&
              currentEmployee.getFirstName().equals(data[2]) &&
              currentEmployee.getSecondName().equals(data[3])) {
            if (dataReport.containsKey("Duplicate")) {
              int tempNr = dataReport.get("Duplicate");
              dataReport.put("Duplicate", tempNr + 1);
            } else {
              dataReport.put("Duplicate", 1);
            }
          } else if (dataReport.containsKey("Changed")) {
            int tempNr = dataReport.get("Changed");
            dataReport.put("Changed", tempNr + 1);
          } else {
            dataReport.put("Changed", 1);
          }
          currentEmployee.setEmail(data[1]);
          currentEmployee.setFirstName(data[2]);
          currentEmployee.setSecondName(data[3]);
          currentEmployee.setLastUpdate(new Date());
          employeeRepository.save(currentEmployee);
        } else {
          Employee employee = new Employee();
          employee.setId(parseInt(data[0]));
          employee.setEmail(data[1]);
          employee.setFirstName(data[2]);
          employee.setSecondName(data[3]);
          employee.setLastUpdate(new Date());
          employeeRepository.save(employee);
          if (dataReport.containsKey("New")) {
            int tempNr = dataReport.get("New");
            dataReport.put("New", tempNr + 1);
          } else {
            dataReport.put("New", 1);
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
//    try {
//      Path temp = Files.move(Paths.get("src/main/resources/" + System.getenv("FILE_NAME")),
//          Paths.get("src/main/resources/Recorded/" + System.getenv("FILE_NAME")));
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
    File file = new File("src/main/resources/" + System.getenv("FILE_NAME"));
    if (file.renameTo(new File("src/main/resources/Recorded/" + System.getenv("FILE_NAME")))) {
      file.delete();
    } else {
      System.out.println("Failed to move the file");
    }
    return dataReport.toString();
  }
}
