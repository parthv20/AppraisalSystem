import { Component, inject, signal } from '@angular/core';
import { PageService } from '../pages/page.service';
import { Router } from '@angular/router';
import { User } from '../../layout';

@Component({
  selector: 'app-response',
  standalone: true,
  imports: [],
  templateUrl: './response.component.html',
  styleUrl: './response.component.css',
})
export class ResponseComponent {
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
  attributes: string[] = [];
  router = inject(Router);
  loading = signal<boolean>(false);

  ngOnInit(): void {
    this.loading.set(true);
    this.pageservice.getuser()?.subscribe({
      next: (data) => {
        this.user.set(data);
        this.attributes = Object.keys(this.user().attributes);
      },
      error: (err) => {
        this.router.navigateByUrl('/login');
      },
      complete: () => {
        this.loading.set(false);
      },
    });
  }
}
