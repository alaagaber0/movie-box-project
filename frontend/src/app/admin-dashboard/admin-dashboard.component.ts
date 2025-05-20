import { Component } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent {
  title: string = '';
  year: string = '';
  searchResults: any[] = [];
  message: string = '';
  error: string = '';
  selectedMovies: any[] = []; // لتخزين الأفلام المختارة للاضافة أو الحذف الجماعي

  constructor(private http: HttpClient) {}

  // البحث عن الأفلام
  searchMovies() {
    if (!this.title.trim()) {
      this.error = 'Please enter a movie title.';
      return;
    }

    const params = new HttpParams()
      .set('title', this.title)
      .set('year', this.year || '');

    const token = localStorage.getItem('access_token');
    const headers = { Authorization: `Bearer ${token}` };

    this.http.get<any>('http://localhost:8080/admin/api/movies', { params, headers }).subscribe({
      next: (response) => {
        if (response.success) {
          this.searchResults = response.data;
          this.message = `${response.total} result(s) found.`;
          this.error = '';
        } else {
          this.searchResults = [];
          this.message = '';
          this.error = response.message;
        }
      },
      error: (err) => {
        console.error(err);
        this.error = 'Failed to fetch movies.';
        this.searchResults = [];
        this.message = '';
      }
    });
  }

  // إضافة فيلم إلى قاعدة البيانات
  addMovie(movie: any) {
    const token = localStorage.getItem('access_token');
    const headers = { Authorization: `Bearer ${token}` };

    this.http.post<any>('http://localhost:8080/admin/api/add', movie, { headers }).subscribe({
      next: (response) => {
        if (response.success) {
          this.message = 'Movie added successfully!';
          this.error = '';
        } else {
          this.error = response.message;
          this.message = '';
        }
      },
      error: (err) => {
        console.error(err);
        this.error = 'Failed to add movie.';
        this.message = '';
      }
    });
  }

  // حذف فيلم من قاعدة البيانات
  deleteMovie(movieId: string) {
    const token = localStorage.getItem('access_token');
    const headers = { Authorization: `Bearer ${token}` };

    this.http.delete<any>(`http://localhost:8080/admin/api/1/${movieId}`, { headers }).subscribe({
      next: (response) => {
        if (response.success) {
          this.message = 'Movie deleted successfully!';
          this.searchMovies(); // إعادة تحميل نتائج البحث بعد الحذف
          this.error = '';
        } else {
          this.error = response.message;
          this.message = '';
        }
      },
      error: (err) => {
        console.error(err);
        this.error = 'Failed to delete movie.';
        this.message = '';
      }
    });
  }

  // إضافة مجموعة من الأفلام
  addBatchMovies() {
    const token = localStorage.getItem('access_token');
    const headers = { Authorization: `Bearer ${token}` };

    this.http.post<any>('http://localhost:8080/admin/api/add_batch', this.selectedMovies, { headers }).subscribe({
      next: (response) => {
        if (response.success) {
          this.message = 'Movies added successfully!';
          this.error = '';
          this.selectedMovies = []; // إفراغ قائمة الأفلام المختارة
        } else {
          this.error = response.message;
          this.message = '';
        }
      },
      error: (err) => {
        console.error(err);
        this.error = 'Failed to add batch movies.';
        this.message = '';
      }
    });
  }

  // حذف مجموعة من الأفلام
  deleteBatchMovies() {
    const token = localStorage.getItem('access_token');
    const headers = { Authorization: `Bearer ${token}` };

    const movieIds = this.selectedMovies.map(movie => movie.imdbID); // استخراج معرّف الفيلم

    this.http.delete<any>('http://localhost:8080/admin/api/delete_batch', { headers, body: { ids: movieIds } }).subscribe({
      next: (response) => {
        if (response.success) {
          this.message = 'Movies deleted successfully!';
          this.error = '';
          this.selectedMovies = []; // إفراغ قائمة الأفلام المختارة
          this.searchMovies(); // إعادة تحميل نتائج البحث بعد الحذف
        } else {
          this.error = response.message;
          this.message = '';
        }
      },
      error: (err) => {
        console.error(err);
        this.error = 'Failed to delete batch movies.';
        this.message = '';
      }
    });
  }

  // تحديد/إلغاء تحديد الأفلام المضافة
  toggleSelection(movie: any) {
    const index = this.selectedMovies.findIndex(item => item.imdbID === movie.imdbID);
    if (index === -1) {
      this.selectedMovies.push(movie);
    } else {
      this.selectedMovies.splice(index, 1);
    }
  }


}
