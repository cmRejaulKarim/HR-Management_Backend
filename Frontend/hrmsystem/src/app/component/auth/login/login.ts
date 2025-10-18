import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../../service/auth-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {


  loginForm!: FormGroup;
  errorMessage: string | null = null;
  successMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.loginForm.invalid) {
      return;
    }

    const { email, password } = this.loginForm.value;

    this.authService.login(email, password).subscribe({
      next: (response) => {
        this.successMessage = 'Login successful!';
        this.errorMessage = null;
        console.log('Login successful:', response);
        console.log('User message:', response.message);
        localStorage.setItem('authToken', response.token);

        const role = this.authService.getUserRole();
        console.log('User role:', role);

        if (role === 'EMPLOYEE') {
          this.router.navigate(['/empprofile']);
        }
        else if (role === 'DEPARTMENT_HEAD') {
          this.router.navigate(['/deptheadprofile']);
        }
        else if (role === 'ACCOUNTANT') {
          this.router.navigate(['/accountantprofile']);
        }
        else if (role === 'ADMIN') {
          this.router.navigate(['/adminprofile']);
        }
        else {
          this.errorMessage = 'Unknown user role.';
        }
      },
      error: (err) => {
        this.errorMessage = 'Login failed. Please check your credentials.';
        this.successMessage = null;
      }
    });
  }

  // onSubmit(): void {
  //   if (this.loginForm.invalid) {
  //     this.errorMessage = 'Please fill in all required fields correctly.';
  //     return;
  //   }

  //   const userDetails = this.loginForm.value;

  //   this.authService.login(userDetails).subscribe({
  //     next: (res) => {
  //       console.log('User logged in successfully:', res);

  //       this.authService.storeToken(res.token);

  //       const role = this.authService.getUserRole();
  //       console.log('User role:', role);

  //       if (role === 'user') {
  //         this.router.navigate(['/userprofile']);
  //       }
  //       else if (role === 'supAdmin') {
  //         this.router.navigate(['/adminprofile']);
  //       }
  //       else if (role === 'admin') {
  //         this.router.navigate(['/adminprofile']);
  //       }
  //       else if (role === 'accounts') {
  //         this.router.navigate(['/adminprofile']);
  //       }
  //       else if (role === 'deptHead') {
  //         this.router.navigate(['/adminprofile']);
  //       }
  //       else {
  //         this.errorMessage = 'Unknown user role.';
  //       }

  //       this.loginForm.reset();
  //     },
  //     error: (err) => {
  //       console.error('Error logging in:', err);
  //       this.errorMessage = 'Invalid email or password.';
  //     }
  //   });
  // }

}
