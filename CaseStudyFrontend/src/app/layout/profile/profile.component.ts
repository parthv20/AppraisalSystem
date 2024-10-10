import { Component, inject, signal } from '@angular/core';
import { PageService } from '../pages/page.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css',
})
export class ProfileComponent {
  user = signal<any>({});
  pageservice = inject(PageService);
  router = inject(Router);
  ngOnInit(): void {
    this.pageservice.getuser()?.subscribe({
      next: (data) => {
        this.user.set(data);
      },
      error: (err) => {
        this.router.navigateByUrl('/login');
      },
    });
  }
}
