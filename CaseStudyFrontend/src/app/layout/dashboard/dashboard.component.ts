import { Component, inject, signal } from '@angular/core';
import { PageService } from '../pages/page.service';

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
  ngOnInit(): void {
    this.pageservice.getuser()?.subscribe((res) => {
      this.user.set(res);
    });
  }
}
