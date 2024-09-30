import { Component, OnInit, inject, signal } from '@angular/core';
import { EmployeeTasksComponent } from "./employee-tasks/employee-tasks.component";
import { LayoutService } from '../layout.service';
import { PageService } from './page.service';
import { EmployeelistComponent } from "./employeelist/employeelist.component";
import { CreatetaskComponent } from "./createtask/createtask.component";

@Component({
  selector: 'app-pages',
  standalone: true,
  imports: [EmployeeTasksComponent, EmployeelistComponent, CreatetaskComponent],
  templateUrl: './pages.component.html',
  styleUrl: './pages.component.css'
})
export class PagesComponent implements OnInit {
  pageservice = inject(PageService)
  user = signal<any>(0)
  create = false;
  admin:boolean = false;

  ngOnInit(): void {
    const ad = localStorage.getItem('admin')
    if(ad){
      this.admin = JSON.parse(ad);
    }
    console.log(this.user())
      this.pageservice.getuser().subscribe(data=>{
        this.user.set(data);
        console.log(this.user())
      })
  }
 notify(){
   this.user().notifybyemployee = true
   this.pageservice.notify(this.user()).subscribe()

 }
  closecreate(){
    this.create = false
  }

  createtask(){
    this.create = true
  }
}
