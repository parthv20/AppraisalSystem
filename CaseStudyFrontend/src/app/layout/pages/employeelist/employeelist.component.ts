import { Component, OnInit, inject, signal } from '@angular/core';
import { PageService } from '../page.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-employeelist',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './employeelist.component.html',
  styleUrl: './employeelist.component.css'
})
export class EmployeelistComponent implements OnInit {
  users= signal<any>(0)
  pageservice =inject(PageService)


  ngOnInit(): void {
      this.pageservice.getallusers().subscribe(data=>{
        this.users.set(data.filter((usr:any) => usr.tenure>=12));
        console.log(this.users())
      })
  }

}
