import { Component, OnInit, inject, signal } from '@angular/core';
import { PageService } from '../pages/page.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent implements OnInit {
  user = signal<any>(0)
  pageservice = inject(PageService)

  admin = false
  ngOnInit(): void {
    let l = localStorage.getItem('admin')
    if(l){
      this.admin = JSON.parse(l)
    }
    this.pageservice.getuser().subscribe(data=>{
      this.user.set(data);
    })
  }
}
