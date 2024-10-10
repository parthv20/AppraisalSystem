import { Component, OnInit, inject, signal } from '@angular/core';
import { PageService } from '../page.service';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-employeelist',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './employeelist.component.html',
  styleUrl: './employeelist.component.css',
})
export class EmployeelistComponent implements OnInit {
  users = signal<any>(0);
  pageservice = inject(PageService);
  router = inject(Router);
  loading = signal<boolean>(false);

  ngOnInit(): void {
    this.loading.set(true);
    const token = localStorage.getItem('token');
    if (!token) {
      this.router.navigateByUrl('/login');
    }
    const adm = localStorage.getItem('admin');
    if (adm) {
      if (!JSON.parse(adm)) {
        this.router.navigateByUrl('/dashboard');
      }
    }
    this.pageservice.getallusers().subscribe({
      next: (data) => {
        this.users.set(data.filter((usr: any) => usr.tenure >= 12));
        console.log(this.users());
      },
      complete: () => this.loading.set(false),
    });
  }
}
