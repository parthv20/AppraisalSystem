import { Component, EventEmitter, OnInit, Output, inject, signal } from '@angular/core';
import { PageService } from '../pages/page.service';

@Component({
  selector: 'app-notification',
  standalone: true,
  imports: [],
  templateUrl: './notification.component.html',
  styleUrl: './notification.component.css'
})
export class NotificationComponent implements OnInit {
  adminnts = signal<any[]>([])
  user = signal<any>(0)
  pageservice = inject(PageService)
  admin = localStorage.getItem('admin')
  ngOnInit(): void {
    if(this.admin){
      this.admin = JSON.parse(this.admin)
    }
    if(this.admin){
    this.pageservice.getnotifications().subscribe(data=>this.adminnts.set(data))
    }
  }
  deletenotification(notification:any){
    this.pageservice.deletenotification(notification).subscribe(
      data=>{
        this.pageservice.getnotifications().subscribe(data=>this.adminnts.set(data))
      }
    )
    
  }
}
