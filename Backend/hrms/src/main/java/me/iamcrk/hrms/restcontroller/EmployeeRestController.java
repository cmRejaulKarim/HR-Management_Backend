package me.iamcrk.hrms.restcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.iamcrk.hrms.dto.EmployeeDTO;
import me.iamcrk.hrms.entity.Employee;
import me.iamcrk.hrms.entity.User;
import me.iamcrk.hrms.repository.IUserRepo;
import me.iamcrk.hrms.service.AuthService;
import me.iamcrk.hrms.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/employee/")
public class EmployeeRestController {

    @Autowired
    private AuthService authService;

    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private EmployeeService employeeService;


    //    @PostMapping("")
//    public ResponseEntity<Map<String, String>> registerEmployee(
//            @RequestPart(value = "user") String userJson,
//            @RequestPart(value = "employee") String employeeJson,
//            @RequestParam(value = "photo") MultipartFile file
//    ) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        User user = objectMapper.readValue(userJson, User.class);
//        Employee employee = objectMapper.readValue(employeeJson, Employee.class);
//
//        try {
//            authService.registerEmployee(user, file, employee);
//            Map<String, String> response = new HashMap<>();
//            response.put("Message", "User Added Successfully ");
//
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (Exception e) {
//
//            Map<String, String> errorResponse = new HashMap<>();
//            errorResponse.put("Message", "User Add Faild " + e);
//            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    @PostMapping("")
    public ResponseEntity<Map<String, String>> registerEmployee(
            @RequestPart(value = "user") String userJson,
            @RequestPart(value = "employee") String employeeJson,
            @RequestParam(value = "photo") MultipartFile file
    ) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(userJson, User.class);
        EmployeeDTO employeeDTO = objectMapper.readValue(employeeJson, EmployeeDTO.class); // use DTO

        try {
            authService.registerEmployeeDTO(user, file, employeeDTO);
            Map<String, String> response = new HashMap<>();
            response.put("Message", "User Added Successfully ");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("Message", "User Add Failed " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("all")
    public ResponseEntity<List<Employee>> getAllEmployees(Authentication authentication) {
        String email = authentication.getName();
        Optional<User> user = userRepo.findByEmail(email);

        List<Employee> jobSeekerList = employeeService.getAll();
        return ResponseEntity.ok(jobSeekerList);
    }

    @GetMapping("profile")
    public ResponseEntity<?> getProfile(Authentication authentication) {
        String email = authentication.getName();
        Optional<User> user = userRepo.findByEmail(email);
        EmployeeDTO employee = employeeService.getProfileByUserIdFromDto(user.get().getId());

        return ResponseEntity.ok(employee);
    }

    @GetMapping("byDept")
    public ResponseEntity<?> getEmployeesByDept(Authentication authentication) {
        String email = authentication.getName();
        Optional<User> user = userRepo.findByEmail(email);
        List<Employee> allEmpByDept = employeeService.getAllByDepartmentId(user.get().getId());

        return ResponseEntity.ok(allEmpByDept);
    }

    @PatchMapping("{id}/salary")
    public ResponseEntity<EmployeeDTO> updateSalary(@PathVariable Long id,
                                                    @RequestBody EmployeeDTO dto, Authentication authentication) {
        String email = authentication.getName();
        Optional<User> user = userRepo.findByEmail(email);

        Employee updated = employeeService.updateSalary(id, dto);
        return ResponseEntity.ok(new EmployeeDTO(updated));
    }

}
