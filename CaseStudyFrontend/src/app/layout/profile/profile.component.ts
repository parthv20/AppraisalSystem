import { Component, inject, signal } from '@angular/core';
import { PageService } from '../pages/page.service';
import { Router } from '@angular/router';
import { User } from '../../layout';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css',
})
export class ProfileComponent {
  user = signal<User>({
    attributes: {},
    dateOfJoining: '',
    designation: '',
    email: '',
    id: 0,
    name: '',
    noifybyadmin: false,
    notifyByemployee: false,
    phoneNumber: '',
    tasks: [],
    tenure: 0,
  });
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
