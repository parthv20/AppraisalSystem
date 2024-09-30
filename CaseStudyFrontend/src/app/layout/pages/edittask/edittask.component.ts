import { Component, EventEmitter, Output, inject, input } from '@angular/core';
import { PageService } from '../page.service';
import { FormsModule } from '@angular/forms';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-edittask',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './edittask.component.html',
  styleUrl: './edittask.component.css'
})
export class EdittaskComponent {
  @Output() close = new EventEmitter<void>();
  pageservice = inject(PageService);
  task = input<any>();
  private subscription: Subscription | undefined;


  submittask() {
    console.log(this.task())
    this.pageservice.updatetask(this.task()).subscribe()
    this.closetask()
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
