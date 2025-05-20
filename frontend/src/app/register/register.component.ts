import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  username: string = '';
  password: string = '';
  role: string = 'USER';
  successMessage: string = '';
  errorMessage: string = '';
  isLoading: boolean = false;

  constructor(private http: HttpClient, private router: Router) {}

  register() {
    this.isLoading = true;
    this.successMessage = '';
    this.errorMessage = '';

    const registerData = {
      username: this.username,
      password: this.password,
      role: this.role
    };

    this.http.post<any>('http://localhost:8080/auth/register', registerData).subscribe({
      next: (response) => {
        console.log('Register response:', response);
        this.isLoading = false;
        if (response.success) {
          this.successMessage = '✅ Registration successful. Redirecting to login...';
          setTimeout(() => {
            this.router.navigate(['/login']);
          }, 2000);
        } else {
          this.errorMessage = '❌ Registration failed. Please try again.';
        }
      },
      error: (err) => {
        console.error('Register error:', err);
        this.isLoading = false;
        this.errorMessage = '❌ Registration failed. Please try again.';
      }
    });
  }

  goToLogin() {
    this.router.navigate(['/login']);
  }
}
