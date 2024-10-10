import { Component, OnInit, inject, signal } from '@angular/core';
import { EmployeeTasksComponent } from './employee-tasks/employee-tasks.component';

import { PageService } from './page.service';
import { EmployeelistComponent } from './employeelist/employeelist.component';
import { CreatetaskComponent } from './createtask/createtask.component';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Task, User } from '../../layout';

@Component({
  selector: 'app-pages',
  standalone: true,
  imports: [
    EmployeeTasksComponent,
    EmployeelistComponent,
    CreatetaskComponent,
    CommonModule,
  ],
  templateUrl: './pages.component.html',
  styleUrl: './pages.component.css',
})
export class PagesComponent implements OnInit {
  pageservice = inject(PageService);
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
  create = false;
  admin: boolean = false;
  ntf = signal<boolean>(false);
  router = inject(Router);
  loading = signal<boolean>(false);

  ngOnInit(): void {
    const token = localStorage.getItem('token');
    if (!token) {
      this.router.navigateByUrl('/login');
    }
    const ad = localStorage.getItem('admin');
    if (ad) {
      this.admin = JSON.parse(ad);
    }
    this.getuser();
  }

  getuser() {
    this.loading.set(true);
    this.pageservice.getuser()?.subscribe({
      next: (data) => {
        this.user.set(data);
        if (this.user().notifyByemployee) {
          this.ntf.set(true);
        }
      },
      error: (err) => {
        this.router.navigateByUrl('/login');
      },
      complete: () => {
        this.loading.set(false);
      },
    });
  }

  notify() {
    this.ntf.set(true);
    this.user().notifyByemployee = true;
    this.getuser();
    this.pageservice.notify(this.user()).subscribe();
  }
  closecreate(task: Task) {
    if (task) {
      this.user().tasks = [...this.user().tasks, task];
    }
    this.create = false;
  }

  createtask() {
    this.create = true;
  }
}
