import {
  Component,
  EventEmitter,
  OnInit,
  Output,
  inject,
  signal,
} from '@angular/core';
import { PageService } from '../pages/page.service';
import { User, Notification } from '../../layout';

@Component({
  selector: 'app-notification',
  standalone: true,
  imports: [],
  templateUrl: './notification.component.html',
  styleUrl: './notification.component.css',
})
export class NotificationComponent implements OnInit {
  adminnts = signal<Notification[]>([]);
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
  admin = localStorage.getItem('admin');
  ngOnInit(): void {
    if (this.admin) {
      this.admin = JSON.parse(this.admin);
    }
    if (this.admin) {
      this.pageservice.getnotifications().subscribe((data) => {
        this.adminnts.set(data);
        console.log(data);
      });
    }
  }
  deletenotification(notification: Notification) {
    this.pageservice.deletenotification(notification).subscribe((data) => {
      this.pageservice
        .getnotifications()
        .subscribe((data) => this.adminnts.set(data));
    });
  }
}
