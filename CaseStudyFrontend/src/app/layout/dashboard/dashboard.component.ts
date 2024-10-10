import { Component, inject, signal } from '@angular/core';
import { PageService } from '../pages/page.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {
  user = signal<any>({});
  pageservice = inject(PageService);
  router = inject(Router)
  ngOnInit(): void {
    this.pageservice.getuser()?.subscribe({
      next:(res) => {
        this.user.set(res);
      },
      error:(err)=>{
        this.router.navigateByUrl('/login')
      }
  });
  }
}
