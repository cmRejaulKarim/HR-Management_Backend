package me.iamcrk.hrms.service;

import me.iamcrk.hrms.entity.Accountant;
import me.iamcrk.hrms.entity.Employee;
import me.iamcrk.hrms.entity.Role;
import me.iamcrk.hrms.entity.User;
import me.iamcrk.hrms.repository.IAccountantRepo;
import me.iamcrk.hrms.repository.IEmployeeRepo;
import me.iamcrk.hrms.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AccountantService {

    @Autowired
    private IAccountantRepo accountantRepo;
    @Autowired
    private IEmployeeRepo employeeRepo;
    @Autowired
    private IUserRepo userRepo;

    public Accountant registerAsAccountant(String email) {
        Employee employee = employeeRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        User user = employee.getUser();

        // Check if user has ever been an Accountant
        if (accountantRepo.findByUserId(user.getId()).isPresent()) {
            throw new RuntimeException("You are already registered (current or previous) as an Accountant!");
        }

        // Create new Accountant
        Accountant accountant = new Accountant();
        accountant.setUser(user);
        accountant.setAssignedDate(LocalDate.now());
        accountant.setActive(true);
        accountantRepo.save(accountant);

        // Update user role
        user.setRole(Role.ACCOUNTANT);
        userRepo.save(user);

        return accountant;
    }


    public List<Accountant> getAllActiveAccountants() {
        return accountantRepo.findByActiveTrue();
    }


    public void endAccountantRole(Long accountantId) {
        Accountant acc = accountantRepo.findById(accountantId)
                .orElseThrow(() -> new RuntimeException("Accountant not found"));

        acc.setActive(false);
        acc.setEndDate(LocalDate.now());

        accountantRepo.save(acc);
    }

}
