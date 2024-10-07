import { CommonModule } from '@angular/common';
import { Component, OnInit, inject, signal } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { PageService } from '../../layout/pages/page.service';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [ReactiveFormsModule,CommonModule,RouterLink,FormsModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent{
    user= signal<any>({
      name:'',
      email:'',
      password:'',
      designation:'',
      dateOfJoining:'',
      phoneNumber:'',
    })
    pageservice=inject(PageService)

    onSubmit(){
      console.log(this.user())
       this.pageservice.signupuser(this.user()).subscribe({
        next:res=>alert('Success'),
        error:err=>{
          if(err){
            alert('error in submitting, please fill it correctly');
            console.log(err)
          }
        },
        

    })
    }
}
