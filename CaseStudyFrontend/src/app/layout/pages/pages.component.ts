import { Component, OnInit, inject, signal } from '@angular/core';
import { EmployeeTasksComponent } from "./employee-tasks/employee-tasks.component";

import { PageService } from './page.service';
import { EmployeelistComponent } from "./employeelist/employeelist.component";
import { CreatetaskComponent } from "./createtask/createtask.component";
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-pages',
  standalone: true,
  imports: [EmployeeTasksComponent, EmployeelistComponent, CreatetaskComponent,CommonModule],
  templateUrl: './pages.component.html',
  styleUrl: './pages.component.css'
})
export class PagesComponent implements OnInit {
  pageservice = inject(PageService)
  user = signal<any>(0)
  create = false;
  admin:boolean = false;
  ntf = signal<boolean>(false)
  router = inject(Router)

  ngOnInit(): void {
    const ad = localStorage.getItem('admin')
    if(ad){
      this.admin = JSON.parse(ad);
    }
    this.getuser()

  }

  getuser(){
    this.pageservice.getuser().subscribe(data=>{
      this.user.set(data);
      if(this.user().notifyByemployee){
        this.ntf.set(true)
      }
    })
  }

 notify(){
   this.ntf.set(true)
   this.user().notifyByemployee = true
   this.getuser()
   this.pageservice.notify(this.user()).subscribe()
 }
  closecreate(task:any){
    if(task){
      this.user().tasks = [...this.user().tasks, task]
    }
    this.create = false
  }

  createtask(){
    if(this.user().tenure < 12){
      alert("You can't add projects")
      
    }else{
      this.create = true
    }
   
  }
}
