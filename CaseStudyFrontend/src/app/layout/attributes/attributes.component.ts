import { Component, OnInit, inject, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { PageService } from '../pages/page.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-attributes',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterLink],
  templateUrl: './attributes.component.html',
  styleUrl: './attributes.component.css',
})
export class AttributesComponent implements OnInit {
  user = signal<any>(0);
  pageservice = inject(PageService);
  route = inject(ActivatedRoute);
  id = 1;
  attributes: any[] = [];
  router = inject(Router);

  submitratings() {
    this.pageservice
      .updateattribute(this.user()[0], this.user()[0].attributes)
      .subscribe();
    this.user()[0].noifybyadmin = true;
    this.pageservice.notifybyadmin(this.user()[0]).subscribe();
  }
  ngOnInit(): void {
    const token = localStorage.getItem('token');
    if (!token) {
      this.router.navigateByUrl('/login');
    }
    const adm = localStorage.getItem('admin');
    if (adm) {
      if (!JSON.parse(adm)) {
        this.router.navigateByUrl('/dashboard');
      }
    }
    this.route.paramMap.subscribe((params) => {
      this.id = Number(params.get('id'));
      this.pageservice.getallusers().subscribe((data) => {
        this.user.set(data.filter((d: any) => d.id === this.id));
        this.attributes = Object.keys(this.user()[0].attributes);
      });
    });
  }
}
