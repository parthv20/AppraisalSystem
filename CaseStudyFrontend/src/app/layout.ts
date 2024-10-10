export interface Signuupuser {
  name: string;
  email: string;
  password: string;
  designation: string;
  dateOfJoining: string;
  phoneNumber: string;
}

export interface Attributes {
  [key: string]: number;
}

export interface User {
  attributes: Attributes;
  dateOfJoining: string;
  designation: string;
  email: string;
  id: number;
  name: string;
  noifybyadmin: boolean;
  notifyByemployee: boolean;
  phoneNumber: string;
  tasks: Task[];
  tenure: number;
}

export interface Task {
  adminrating: Number;
  appraisable: boolean;
  description: string;
  endDate: string;
  id: Number;
  name: string;
  startDate: string;
}

export interface Notification {
  id: number;
  employeeId: number;
  employeeName: string;
}
