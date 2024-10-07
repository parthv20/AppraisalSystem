import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PageService {
  constructor(private http: HttpClient) {}
  acctoken: string = '';
  getaccesstoken() {
    const tokens = localStorage.getItem('token');
    if (tokens) {
      this.acctoken = JSON.parse(tokens);
    }
  }
  getheaders() {
    this.getaccesstoken();
    const headers = new HttpHeaders({
      Authorization: `Bearer ${this.acctoken}`,
    });
    return headers;
  }
   signupuser(user:any){

    const headers = this.getheaders()
  
    return this.http.post(
       'http://localhost:8080/signup',user,{headers}
    )
   }

  getuser() {
    const headers = this.getheaders()
    let id = localStorage.getItem('id')
    if(id){
      id = JSON.parse(id)
    }
    return this.http.get<any>(
      ` http://localhost:8080/employee/${id}/getEmployeeDetails`,{headers}
    )
  }

  getallusers(){
    const headers = this.getheaders()
    return this.http.get<any>(
      ` http://localhost:8080/admin/getEmployee`,{headers}
    )

    
  }

  getnotifications(){
    const headers = this.getheaders()
    return this.http.get<any>(
      ` http://localhost:8080/notification/getNotifications`,{headers}
    )
  }

  savetask(task:any,user:any){
    const headers = this.getheaders();
    return this.http.post(`http://localhost:8080/employee/${user.id}/saveTask`, task, {
      headers,
    });
  }


  updatetask(task: any) {
    const headers = this.getheaders();
    return this.http.put(`http://localhost:8080/employee/${task.id}/updateTask`, task, {
      headers,
    });
  }
notify(user:any){
  const headers = this.getheaders();
  console.log(user)
    return this.http.post(`http://localhost:8080/notification/saveNotification`, {employeeId:user.id,employeeName:user.name}, {
      headers,
    });
}
notifybyadmin(user: any){
  const headers = this.getheaders();
    return this.http.put(`http://localhost:8080/notification/${user.id}/updateNotification`, user, {
      headers,
    });
}

  deletetask(task: any) {
    const headers = this.getheaders();
    return this.http.delete(` http://localhost:8080/employee/${task.id}/deleteTask`, {
      headers,
    });
  }

  updateattribute(employee:any,attributes:any){
    const headers = this.getheaders();
    return this.http.post(` http://localhost:8080/admin/${employee.id}/saveAttribute`,attributes, {
      headers,
    });
  }

  deletenotification(notification:any){
    const headers = this.getheaders();
    return this.http.delete(` http://localhost:8080/notification/${notification.employeeId}/deleteNotification`, {
      headers,
    });
  }
}
