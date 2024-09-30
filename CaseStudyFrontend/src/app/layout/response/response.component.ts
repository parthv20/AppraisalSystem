import { Component, inject, signal } from '@angular/core';
import { PageService } from '../pages/page.service';

@Component({
  selector: 'app-response',
  standalone: true,
  imports: [],
  templateUrl: './response.component.html',
  styleUrl: './response.component.css'
})
export class ResponseComponent {
  user = signal<any>({});
  pageservice = inject(PageService);
  attributes:any = ''
  
  ngOnInit(): void {
    this.pageservice.getuser()?.subscribe((res) => {
      this.user.set(res);
      this.attributes = Object.keys(this.user().attributes)
    });
  }
}
