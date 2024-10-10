import { Component, EventEmitter, OnInit, Output, destroyPlatform, inject, input, signal } from '@angular/core';
import { PageService } from '../page.service';
import { DatePipe } from '@angular/common';
import { CreatetaskComponent } from '../createtask/createtask.component';
import { EdittaskComponent } from '../edittask/edittask.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-employee-tasks',
  standalone: true,
  imports: [DatePipe,CreatetaskComponent,EdittaskComponent],
  templateUrl: './employee-tasks.component.html',
  styleUrl: './employee-tasks.component.css'
})
export class EmployeeTasksComponent implements OnInit {
  private pageservice = inject(PageService);
  task = input<any>(0);
  edit = false;
  user = signal<any>('')
  deleted = signal<boolean>(false)
  private router = inject(Router)

  ngOnInit(): void {
    this.pageservice.getuser()?.subscribe({
      next:(data) => {
        this.user.set(data);
      },
      error:(err)=>{
        this.router.navigateByUrl('/login')
      }
  });
  }

  onedit() {
    this.edit = true;
  }
  closeedit(){
    this.edit = false;
  }

  ondelete() {
    this.pageservice.deletetask(this.task()).subscribe()
    this.deleted.set(true)
  }

  show = false;
  showtype() {
    this.show = true;
  }
}
