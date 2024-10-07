import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { LoginService } from './login.service';
import { Subscription } from 'rxjs';


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule,RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  user = signal({
    email: '',
    password: '',
  });
  users = signal<any[]>([]);
  invalid = signal(false);
  private loginservice = inject(LoginService);
  private router = inject(Router);
  private subscription: Subscription | undefined;

  ngOnInit(): void {
    if (localStorage.getItem('token')) {
      this.router.navigateByUrl('/layout');
    }
  }
  onlogin() {
    this.subscription = this.loginservice.onlogin(this.user()).subscribe(
      (data: any) => {
        localStorage.setItem('token', JSON.stringify(data.token));
        localStorage.setItem('id',JSON.stringify(data.id));
        localStorage.setItem('admin',JSON.stringify(data.admin));
        this.router.navigateByUrl('/layout');
      },
      (error) => {
        this.invalid.set(true);
      }
    );
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

}
