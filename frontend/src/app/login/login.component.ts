import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private http: HttpClient, private router: Router) {}

  login() {
    const loginData = {
      username: this.username,
      password: this.password
    };

    this.http.post<any>('http://localhost:8080/auth/login', loginData).subscribe({
      next: (response) => {
        console.log('Login response:', response);
        if (response.success && response.access_token) {
          localStorage.setItem('access_token', response.access_token);

          const tokenPayload = JSON.parse(atob(response.access_token.split('.')[1]));
          console.log('Decoded token payload:', tokenPayload);
          const role = tokenPayload.role;

          console.log('User role:', role);

          if (role === 'ADMIN') {
            this.router.navigate(['/admin']);
          } else if (role === 'USER') {
            this.router.navigate(['/user']);
          } else {
            this.errorMessage = 'Unknown role';
          }
        }
      },
      error: (err) => {
        console.error('Login error:', err);
        this.errorMessage = 'Invalid username or password';
      }
    });
  }

  goToRegister() {
    this.router.navigate(['/register']);
  }
}
