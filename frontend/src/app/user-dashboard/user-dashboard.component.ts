import { Component } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.css']
})
export class UserDashboardComponent {
  movies: any[] = [];
  searchResults: any[] = [];
  title: string = '';
  errorMessage: string = '';
  successMessage: string = '';
  selectedMovie: any = null;
  rating: number = 1;
  showAllMovies: boolean = true; // حالة لإظهار أو إخفاء الأفلام

  constructor(private http: HttpClient, private router: Router) {}

  // Get all movies
  getMovies() {
    const token = localStorage.getItem('access_token');
    const headers = { Authorization: `Bearer ${token}` };

    this.http.get<any>('http://localhost:8080/user/api/movies', { headers }).subscribe({
      next: (response) => {
        if (response.success) {
          this.movies = response.data;
        } else {
          this.errorMessage = response.message;
        }
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Failed to fetch movies.';
      }
    });
  }

  // Get movie details
  viewMovieDetails(id: string) {
    this.http.get<any>(`http://localhost:8080/user/api/${id}`).subscribe({
      next: (response) => {
        if (response.success) {
          this.selectedMovie = response.data;
        } else {
          this.errorMessage = response.message;
        }
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Failed to fetch movie details.';
      }
    });
  }

  // Toggle the display of all movies
  toggleMoviesView() {
    this.showAllMovies = !this.showAllMovies;
  }

  // Search movies
  searchMovies() {
    if (!this.title.trim()) {
      this.errorMessage = 'Please enter a movie title.';
      return;
    }

    const params = new HttpParams().set('title', this.title);
    const token = localStorage.getItem('access_token');
    const headers = { Authorization: `Bearer ${token}` };

    this.http.get<any>('http://localhost:8080/user/api/search', { params, headers }).subscribe({
      next: (response) => {
        if (response.success) {
          this.searchResults = response.data;
          this.errorMessage = '';
        } else {
          this.searchResults = [];
          this.errorMessage = response.message;
        }
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Failed to search for movies.';
      }
    });
  }

  // Submit movie rating
  rateMovie(movieId: string) {
    if (this.rating < 1 || this.rating > 5) {
      this.errorMessage = 'Rating must be between 1 and 5.';
      return;
    }

    const token = localStorage.getItem('access_token');
    const headers = { Authorization: `Bearer ${token}` };
    const body = { movieId, rate: this.rating };

    this.http.post<any>('http://localhost:8080/user/api/add_rate', body, { headers }).subscribe({
      next: (response) => {
        if (response.success) {
          this.successMessage = 'Rating submitted successfully.';
          this.errorMessage = '';
          this.rating = 1; // Reset rating
        } else {
          this.errorMessage = response.message;
        }
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Failed to submit rating.';
      }
    });
  }

  ngOnInit() {
    this.getMovies(); // Load movies on initialization
  }
}
