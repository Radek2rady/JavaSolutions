package services;

import static java.lang.Integer.parseInt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.EmployeeRepository;

@Service
public class EmployeeService {

  @Autowired
  private EmployeeRepository employeeRepository;

  public void setConnection() {
    String jdbcUrl = "jdbc:mysql://localhost:3306/employees?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
    String username = "root";
    String password = "password";

    String filePath = System.getenv("FILE_NAME");
    int batchSize = 20;

    Connection connection = null;

    try {
      connection = DriverManager.getConnection(jdbcUrl, username, password);
      connection.setAutoCommit(false);

      String sql = "INSERT INTO employees VALUES(?, ?, ?, ?, ?)";

      PreparedStatement statement = connection.prepareStatement(sql);

      BufferedReader lineReader = new BufferedReader(new FileReader(filePath));

      String lineText = "";
      int count = 0;

      lineReader.readLine();
      String line1 = null;
      while ((lineText = lineReader.readLine()) != null || line1 != null) {
        String[] data = lineText.split(";");

        String id = data[0];
        String email = data[1];
        String firstName = data[2];
        String secondName = data[3];
        String lastUpdate = data[4];

//        statement.setInt(1, parseInt(id));
//        statement.setString(2, email);
//        statement.setString(3, firstName);
//        statement.setString(4, secondName);
//        statement.setString(5, lastUpdate);
//        statement.addBatch();
        Employee employee = new Employee();
        employee.setId(parseInt(id));
        employee.setEmail(email);
        employee.setFirstName(firstName);
        employee.setSecondName(secondName);
        employee.setLastUpdate(lastUpdate);
        employeeRepository.save(employee);

        if (count % batchSize == 0) {
          statement.executeBatch();
        }
      }
      lineReader.close();
      statement.executeBatch();
      connection.commit();
      connection.close();
      System.out.println("Data has been inserted successfully.");

    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public static void main(String[] args) {
    EmployeeService employeeService = null;
    employeeService.setConnection();
  }
}
