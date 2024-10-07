import { Routes } from '@angular/router';
import { LoginComponent } from './Authentication/login.component';
import { LayoutComponent } from './layout/layout.component';
import { EmployeedetailsComponent } from './layout/pages/employeelist/employeedetails/employeedetails.component';
import { AttributesComponent } from './layout/attributes/attributes.component';
import { ProfileComponent } from './layout/profile/profile.component';
import { PagesComponent } from './layout/pages/pages.component';
import { DashboardComponent } from './layout/dashboard/dashboard.component';
import { ResponseComponent } from './layout/response/response.component';
import { SignupComponent } from './Authentication/signup/signup.component';

export const routes: Routes = [

    {
        path:'',
        redirectTo :'login',
        pathMatch: 'full'
    },
   {
    path:'login',
    component: LoginComponent,
   },   {
    path:'signup',
    component: SignupComponent,
   },{
    path:'layout',
    component: LayoutComponent,
    children:[
        {
            path:'',
            redirectTo :'dashboard',
            pathMatch: 'full'
        },
        
        { 
            path: 'dashboard',
            component: DashboardComponent,
        },
        {
            path: 'pages',
            component: PagesComponent
        },
        {
            path:'pages/tasks/:id',
            component: EmployeedetailsComponent
        },
        {
            path:'pages/attributes/:id',
            component: AttributesComponent
        },
        {
            path:'profile',
            component: ProfileComponent,
        },
        {
            path:'response',
            component: ResponseComponent,
        },
        
    ]
    
   }


];
