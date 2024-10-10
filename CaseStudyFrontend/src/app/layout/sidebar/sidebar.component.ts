import { Component, OnInit, inject, signal } from '@angular/core';
import { PageService } from '../pages/page.service';
import { RouterLink } from '@angular/router';
import { User } from '../../layout';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css',
})
export class SidebarComponent implements OnInit {
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
  sidebar = signal<boolean>(false);
  clicked = signal<boolean>(false);

  admin = false;
  ngOnInit(): void {
    let l = localStorage.getItem('admin');
    if (l) {
      this.admin = JSON.parse(l);
    }
    this.pageservice.getuser().subscribe((data) => {
      this.user.set(data);
    });
    setInterval(() => {
      if (window.innerWidth > 1500) {
        this.sidebar.set(true);
        this.clicked.set(false);
      } else if (!this.clicked()) {
        this.sidebar.set(false);
      }
    }, 100);
  }
  toggleside() {
    this.sidebar.set(!this.sidebar());
    this.clicked.set(true);
  }

  clicking() {
    this.clicked.set(false);
    this.sidebar.set(false);
  }
}
