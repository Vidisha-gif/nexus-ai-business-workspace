export interface Employee {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  department: string;
  designation: string;
  salary: number;
}

export interface EmployeeRequest {
  firstName: string;
  lastName: string;
  email: string;
  department: string;
  designation: string;
  salary: number;
}