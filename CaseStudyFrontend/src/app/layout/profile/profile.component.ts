import { Component, inject, signal } from '@angular/core';
import { PageService } from '../pages/page.service';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {
  user = signal<any>({});
  pageservice = inject(PageService);
  ngOnInit(): void {
    this.pageservice.getuser()?.subscribe((res) => {
      this.user.set(res);
    });
  }
}
