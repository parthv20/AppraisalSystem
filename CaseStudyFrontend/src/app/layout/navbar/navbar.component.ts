import {
  Component,
  HostListener,
  input,
  OnInit,
  signal,
  ViewEncapsulation,
} from '@angular/core';
import { RouterLink } from '@angular/router';
import { NotificationComponent } from '../notification/notification.component';


@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink,NotificationComponent],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
})
export class NavbarComponent {
  // username = 'input<string>();'
  options = signal(false);
  sidebar = false;
  notification = signal<boolean>(false);
  ntfs = signal<boolean>(false);

  seenotifications(){
    this.ntfs.set(true);
  }

  opennotifications() {
    // this.notification.set(true);
  }
  closentfs() {
    this.ntfs.set(false);
  }

  changeoptions() {
    // this.options.set(!this.options());
  }

  opensidebar() {
    // this.sidebar = true;
  }
  closesidebar() {
    // this.sidebar = false;
  }

  logout() {
    localStorage.clear();
  }
}
