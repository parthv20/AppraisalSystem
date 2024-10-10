import { Component, OnInit, inject, signal } from '@angular/core';
import { PageService } from '../../page.service';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-employeedetails',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterLink],
  templateUrl: './employeedetails.component.html',
  styleUrl: './employeedetails.component.css',
})
export class EmployeedetailsComponent implements OnInit {
  user = signal<any>(0);
  pageservice = inject(PageService);
  route = inject(ActivatedRoute);
  id = 1;
  router = inject(Router);

  submitratings() {
    for (let task of this.user()[0].tasks) {
      this.pageservice.updatetask(task).subscribe();
    }
  }

  ngOnInit(): void {
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
    this.route.paramMap.subscribe((params) => {
      this.id = Number(params.get('id'));
      this.pageservice.getallusers().subscribe((data) => {
        this.user.set(data.filter((d: any) => d.id === this.id));
      });
    });
  }
}
