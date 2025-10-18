package me.iamcrk.hrms.restcontroller;

import me.iamcrk.hrms.entity.Accountant;
import me.iamcrk.hrms.service.AccountantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accountant")
public class AccountantRestController {
    @Autowired
    private AccountantService accountantService;

    @PostMapping("/register")
    public ResponseEntity<Accountant> register(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(accountantService.registerAsAccountant(email));
    }
    @GetMapping("/active")
    public ResponseEntity<List<Accountant>> getAllActiveAccountants() {
        return ResponseEntity.ok(accountantService.getAllActiveAccountants());
    }


    @PutMapping("/end/{id}")
    public ResponseEntity<String> endRole(@PathVariable Long id) {
        accountantService.endAccountantRole(id);
        return ResponseEntity.ok("Accountant role ended successfully.");
    }

}
