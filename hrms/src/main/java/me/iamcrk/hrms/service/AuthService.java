package me.iamcrk.hrms.service;

import jakarta.mail.MessagingException;
import me.iamcrk.hrms.dto.AuthenticationResponse;
import me.iamcrk.hrms.dto.EmployeeDTO;
import me.iamcrk.hrms.entity.*;
import me.iamcrk.hrms.jwt.JwtService;
import me.iamcrk.hrms.repository.IDepartmentRepo;
import me.iamcrk.hrms.repository.IDesignationRepo;
import me.iamcrk.hrms.repository.ITokenRepo;
import me.iamcrk.hrms.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserRepo userRepo;
    @Autowired
    private ITokenRepo tokenRepository;

    @Autowired
    private EmailService emailService;
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JwtService jwtService;


    @Autowired
    private IDepartmentRepo departmentRepo;

    @Autowired
    private IDesignationRepo designationRepo;

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Value("src/main/resources/static/images")
    private String uploadDir;

    public void saveOrUpdate(User user, MultipartFile imageFile) {
        if (imageFile != null && !imageFile.isEmpty()) {

            String fileName = saveImage(imageFile, user);
            user.setPhoto(fileName);
        }

        user.setRole(Role.ADMIN);
        userRepo.save(user);
        sendActivationEmail(user);
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public User findById(Long id) {
        return userRepo.findById(id).get();
    }

    public void delete(User user) {
        userRepo.delete(user);
    }

    private void sendActivationEmail(User user) {

        String subject = "Welcome to Our Service – Confirm Your Registration";

        String activationLink = "http://localhost:8085/api/auth/active/" + user.getId();

        String mailText = "<!DOCTYPE html>"
                + "<html>"
                + "<body style='margin:0; padding:0; font-family:Arial, sans-serif; background-color:#f4f4f4;'>"
                + "  <div style='max-width:600px; margin:40px auto; background:#ffffff; border-radius:12px; overflow:hidden; box-shadow:0 4px 20px rgba(0,0,0,0.1);'>"
                + "    <div style='background-color:#4CAF50; color:#ffffff; padding:20px 30px; text-align:center;'>"
                + "      <h2 style='margin:0; font-size:24px;'>Welcome to Our Platform</h2>"
                + "    </div>"
                + "    <div style='padding:30px;'>"
                + "      <p style='font-size:16px; color:#333333;'>Dear <strong>"
                + user.getName()
                + "</strong>,</p>"
                + "      <p style='font-size:15px; color:#555555; line-height:1.8;'>"
                + "        Thank you for registering with us. We're thrilled to have you on board!"
                + "      </p>"
                + "      <p style='font-size:15px; color:#555555; line-height:1.8;'>"
                + "        Please confirm your email address to activate your account and get started."
                + "      <p><a href=\"" + activationLink + "\">Activate Account</a></p>"
                + "      </p>"
                + "      <p style='font-size:15px; color:#555555; line-height:1.8;'>"
                + "        If you have any questions or need assistance, our support team is here to help."

                + "      </p>"
                + "      <p style='font-size:15px; color:#555555; margin-top:30px;'>"
                + "        Best regards,<br><span style='font-weight:bold;'>The Support Team</span>"
                + "      </p>"
                + "    </div>"
                + "    <div style='background-color:#f0f0f0; padding:15px; text-align:center; font-size:13px; color:#999999;'>"
                + "      &copy; " + java.time.Year.now()
                + " crk. All rights reserved."
                + "    </div>"
                + "  </div>"
                + "</body>"
                + "</html>";


        try {
            emailService.sendSimpleEmail(user.getEmail(), subject, mailText);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send activation email", e);
        }

    }

    //for user Folder
    public String saveImage(MultipartFile file, User user) {

        Path uploadPath = Paths.get(uploadDir + "/users");
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectory(uploadPath);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        String userName = user.getName();
        String fileName = userName.trim().replaceAll("\\s+", "_");

        String savedUserFileName = fileName + "_" + UUID.randomUUID();


        try {
            Path filePath = uploadPath.resolve(savedUserFileName);
            Files.copy(file.getInputStream(), filePath);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return savedUserFileName;
    }

//    //for user Folder
//    public String saveImageForEmployee(MultipartFile file, Employee employee) {
//
//        Path uploadPath = Paths.get(uploadDir + "/employee");
//        if (!Files.exists(uploadPath)) {
//            try {
//                Files.createDirectory(uploadPath);
//
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        String employeeName = employee.getName();
//        String fileName = employeeName.trim().replaceAll("\\s+", "_");
//
//        String savedFileName = fileName + "_" + UUID.randomUUID();
//
//        try {
//            Path filePath = uploadPath.resolve(savedFileName);
//            Files.copy(file.getInputStream(), filePath);
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return savedFileName;
//    }
    //for user Folder
    public String saveImageForEmployeeDTO(MultipartFile file, EmployeeDTO employee) {

        Path uploadPath = Paths.get(uploadDir + "/employee");
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectory(uploadPath);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        String employeeName = employee.getName();
        String fileName = employeeName.trim().replaceAll("\\s+", "_");

        String savedFileName = fileName + "_" + UUID.randomUUID();

        try {
            Path filePath = uploadPath.resolve(savedFileName);
            Files.copy(file.getInputStream(), filePath);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return savedFileName;
    }

//    public void registerEmployee(User user, MultipartFile imageFile, Employee employeeData) {
//        if (imageFile != null && !imageFile.isEmpty()) {
//            String fileName = saveImage(imageFile, user);
//            String employeePhoto = saveImageForEmployee(imageFile, employeeData);
//            employeeData.setPhoto(employeePhoto);
//            user.setPhoto(fileName);
//        }
//
//
//        Department department = departmentRepo.findById(employeeData.getDepartment().getId())
//                .orElseThrow(() -> new RuntimeException("Invalid Department ID: " + employeeData.getDepartment().getId()));
//
//        Designation designation = designationRepo.findById(employeeData.getDesignation().getId())
//                .orElseThrow(() -> new RuntimeException("Invalid Designation ID: " + employeeData.getDesignation().getId()));
//
//
////        System.out.println("Employee payload deptId = " + employeeData.getDepartment().getId());
////        System.out.println("Employee payload desigId = " + employeeData.getDesignation().getId());
//
//
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setRole(Role.EMPLOYEE);
//        user.setActive(false);
//
//        User savedUser = userRepo.save(user);
//
//
//        employeeData.setDepartment(department);
//        employeeData.setDesignation(designation);
//
//        employeeData.setUser(savedUser);
//        employeeData.setJoiningDate(Date.valueOf(LocalDate.now()));
//        employeeService.save(employeeData);
//
//        String jwt = jwtService.generateToken(savedUser);
//        saveUserToken(jwt, savedUser);
//
//        sendActivationEmail(savedUser);
//    }

    public void registerEmployeeDTO(User user, MultipartFile imageFile, EmployeeDTO dto) {
        // Handle photo
        String employeePhoto = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = saveImage(imageFile, user);
            employeePhoto = saveImageForEmployeeDTO(imageFile, dto);
            dto.setPhoto(employeePhoto);
            user.setPhoto(fileName);

        }

        // Fetch Department & Designation using IDs from DTO
        Department department = departmentRepo.findById(dto.getDepartment())
                .orElseThrow(() -> new RuntimeException("Invalid Department ID: " + dto.getDepartment()));

        Designation designation = designationRepo.findById(dto.getDesignation())
                .orElseThrow(() -> new RuntimeException("Invalid Designation ID: " + dto.getDesignation()));

        // Setup User
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.EMPLOYEE);
        user.setActive(false);
        User savedUser = userRepo.save(user);

        // Setup Employee
        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setGender(dto.getGender());
        employee.setAddress(dto.getAddress());
        employee.setDateOfBirth(dto.getDateOfBirth());
        employee.setDepartment(department);
        employee.setDesignation(designation);
        employee.setUser(savedUser);
        employee.setJoiningDate(Date.valueOf(LocalDate.now()));
        employee.setPhoto(employeePhoto);
        employeeService.save(employee);

        // Generate JWT & send activation
        String jwt = jwtService.generateToken(savedUser);
        saveUserToken(jwt, savedUser);

        sendActivationEmail(savedUser);
    }

    /// ///////////////
    private void saveUserToken(String Jwt, User user) {
        Token token = new Token();
        token.setToken(Jwt);
        token.setLogout(false);
        token.setUser(user);

        tokenRepository.save(token);

    }

    private void removeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllTokenByUser(user.getId());

        if (validTokens.isEmpty()) {
            return;
        }
        validTokens.forEach(t -> {
            t.setLogout(true);
        });
        tokenRepository.saveAll(validTokens);
    }

    // It is Login Method
    public AuthenticationResponse authenticate(User request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()));
        User user = userRepo.findByEmail(request.getEmail()).orElseThrow();

        if (!user.isActive()) {
            throw new RuntimeException("Account is not activated. Please check your email for activation link.");
        }

        String jwt = jwtService.generateToken(user);

        removeAllTokenByUser(user);

        saveUserToken(jwt, user);

        return new AuthenticationResponse(jwt, "User Login Successful");
    }

    public String activeUser(Long id) {

        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not Found with this ID " + id));

        if (user != null) {
            user.setActive(true);
            userRepo.save(user);
//            http://localhost:4200
            return "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>Activation Success</title>\n" +
                    "    <!-- Bootstrap CSS -->\n" +
                    "    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\">\n" +
                    "    <style>\n" +
                    "        /* Button Glow Animation with Color Shift */\n" +
                    "        @keyframes pulseGlow {\n" +
                    "            0% {\n" +
                    "                box-shadow: 0 0 10px rgba(0,255,0,0.5), 0 0 20px rgba(0,255,0,0.3);\n" +
                    "                background-color: #28a745; /* Bootstrap green */\n" +
                    "            }\n" +
                    "            50% {\n" +
                    "                box-shadow: 0 0 25px rgba(50,255,150,0.9), 0 0 50px rgba(50,255,150,0.6);\n" +
                    "                background-color: #2ecc71; /* Lighter green */\n" +
                    "            }\n" +
                    "            100% {\n" +
                    "                box-shadow: 0 0 10px rgba(0,255,0,0.5), 0 0 20px rgba(0,255,0,0.3);\n" +
                    "                background-color: #28a745;\n" +
                    "            }\n" +
                    "        }\n" +
                    "        .glow-btn {\n" +
                    "            animation: pulseGlow 3s infinite ease-in-out; /* slow smooth glow */\n" +
                    "            animation-delay: 1.5s; /* starts after fade-in */\n" +
                    "            border: none;\n" +
                    "        }\n" +
                    "\n" +
                    "        /* Fade-in Slide Animation */\n" +
                    "        @keyframes fadeInUp {\n" +
                    "            0% { opacity: 0; transform: translateY(30px); }\n" +
                    "            100% { opacity: 1; transform: translateY(0); }\n" +
                    "        }\n" +
                    "        .fade-box {\n" +
                    "            animation: fadeInUp 1.5s ease-out;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body style=\"background: linear-gradient(135deg, #0f2027, #203a43, #2c5364); height: 100vh; display: flex; justify-content: center; align-items: center; color: #fff;\">\n" +
                    "\n" +
                    "    <div class=\"text-center p-5 rounded fade-box\" style=\"background: rgba(0, 0, 0, 0.6); border: 1px solid rgba(255, 255, 255, 0.1); box-shadow: 0 8px 25px rgba(0,0,0,0.5);\">\n" +
                    "        <h1 style=\"font-size: 2.5rem; font-weight: bold; color: #4caf50;\">✅ Your Account Activated Successfully!</h1>\n" +
                    "        <p style=\"font-size: 1.2rem; margin-top: 10px; color: #ccc;\">Welcome! You can now access all features.</p>\n" +
                    "        <a href=\"http://localhost:4200/\" class=\"btn btn-success btn-lg mt-3 glow-btn\" style=\"border-radius: 30px; font-weight: bold; padding: 10px 25px; transition: 0.4s;\">\n" +
                    "            Go to Home\n" +
                    "        </a>\n" +
                    "    </div>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>\n";
        } else {
            return "Invalid Activation Token!";
        }
    }

}
