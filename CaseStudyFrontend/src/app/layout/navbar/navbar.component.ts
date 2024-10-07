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
  imports: [RouterLink,NotificationComponent,CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
})
export class NavbarComponent implements OnInit {
  // username = 'input<string>();'
  user= signal<any>({})
  pageservice =inject(PageService)
  options = signal(false);
  sidebar = false;
  notification = signal<boolean>(false);
  ntfs = signal<boolean>(false);

  ngOnInit(): void {
      this.pageservice.getuser().subscribe(data=> this.user.set(data))
  }

  seenotifications(){
    this.ntfs.set(true);
  }

  closentfs() {
    this.ntfs.set(false);
  }

  logout() {
    localStorage.clear();
  }
}
