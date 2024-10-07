import { Component, EventEmitter, Output, inject, input, signal } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PageService } from '../page.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-createtask',
  standalone: true,
  imports: [ReactiveFormsModule,FormsModule],
  templateUrl: './createtask.component.html',
  styleUrl: './createtask.component.css'
})
export class CreatetaskComponent {
  @Output() close = new EventEmitter<void>();
  pageservice = inject(PageService);
  user = signal<any>('')
  task = signal<any>({
    "name": "",
    "description": "",
    "startDate": "",
    "endDate": "",
    "adminrating": 0,
    "appraisable": false
  })
  private subscription: Subscription | undefined;

  ngOnInit(): void {
    this.pageservice.getuser().subscribe(data=>this.user.set(data))
  }

  submittask() {
    this.pageservice.savetask(this.task(),this.user()).subscribe({
      next:data=>{
        this.close.emit(this.task());
      },
      error:err=>{
        alert('error')
      }
    })
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
  
  closetask() {
    this.close.emit();
  }
}
