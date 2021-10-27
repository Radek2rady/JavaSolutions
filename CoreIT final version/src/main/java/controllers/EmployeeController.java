package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import services.EmployeeService;

@RestController
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;

  @RequestMapping(path = "/saveDataInDB")
  public String setDataInDB() {
    return employeeService.saveEmployeeData();
  }
}
