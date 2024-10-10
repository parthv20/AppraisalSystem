import {
  Component,
  EventEmitter,
  Input,
  Output,
  inject,
  input,
} from '@angular/core';
import { PageService } from '../page.service';
import { FormsModule } from '@angular/forms';
import { Subscription } from 'rxjs';
import { Task } from '../../../layout';

@Component({
  selector: 'app-edittask',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './edittask.component.html',
  styleUrl: './edittask.component.css',
})
export class EdittaskComponent {
  @Output() close = new EventEmitter<void>();
  pageservice = inject(PageService);
  @Input() task!: Task;
  private subscription: Subscription | undefined;

  submittask() {
    let task = this.task;
    if (task) {
      this.pageservice.updatetask(task).subscribe();
    }
    this.closetask();
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
