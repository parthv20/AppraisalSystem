import {
  Component,
  HostListener,
  inject,
  input,
  OnInit,
  signal,
  ViewEncapsulation,
} from '@angular/core';
import { RouterLink } from '@angular/router';
import { NotificationComponent } from '../notification/notification.component';
import { CommonModule } from '@angular/common';
import { PageService } from '../pages/page.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, NotificationComponent, CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
})
export class NavbarComponent implements OnInit {
  // username = 'input<string>();'
  user = signal<any>({});
  pageservice = inject(PageService);
  options = signal(false);
  sidebar = false;
  notification = signal<boolean>(false);
  ntfs = signal<boolean>(false);
  len = signal<number>(0);
  admin = signal<boolean>(false);

  ngOnInit(): void {
    let adm = localStorage.getItem('admin');
    if (adm) {
      this.admin.set(JSON.parse(adm));
    }
    this.pageservice
      .getnotifications()
      .subscribe((data) => this.len.set(data.length));
    this.pageservice.getuser().subscribe((data) => {
      this.user.set(data);
      console.log(this.user());
    });
  }

  seenotifications() {
    this.ntfs.set(true);
    this.pageservice
      .getnotifications()
      .subscribe((data) => this.len.set(data.length));
  }

  closentfs() {
    this.ntfs.set(false);
    this.pageservice
      .getnotifications()
      .subscribe((data) => this.len.set(data.length));
  }

  logout() {
    localStorage.clear();
  }
}
