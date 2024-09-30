import { Component, EventEmitter, OnInit, Output, inject, input, signal } from '@angular/core';
import { PageService } from '../page.service';
import { LayoutService } from '../../layout.service';
import { DatePipe } from '@angular/common';
import { CreatetaskComponent } from '../createtask/createtask.component';
import { EdittaskComponent } from '../edittask/edittask.component';

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

  ngOnInit(): void {
      this.pageservice.getuser().subscribe(data=>this.user.set(data))
  }

  onedit() {
    this.edit = true;
  }
  closeedit(){
    this.edit = false;
  }

  ondelete() {
    this.pageservice.deletetask(this.task()).subscribe()
  }

  show = false;
  showtype() {
    this.show = true;
  }
}
