import api from "./api";
import type { EmployeeRequest } from "../types/employee";

export const getEmployees = async (
  page = 0,
  size = 10
) => {
  const response = await api.get(
    `/employees?page=${page}&size=${size}`
  );

  return response.data;
};

export const getEmployeeById = async (id: number) => {
  const response = await api.get(`/employees/${id}`);
  return response.data;
};

export const createEmployee = async (
  employee: EmployeeRequest
) => {
  const response = await api.post(
    "/employees",
    employee
  );

  return response.data;
};

export const updateEmployee = async (
  id: number,
  employee: EmployeeRequest
) => {
  const response = await api.put(
    `/employees/${id}`,
    employee
  );

  return response.data;
};

export const deleteEmployee = async (id: number) => {
  const response = await api.delete(
    `/employees/${id}`
  );

  return response.data;
};

export const searchEmployees = async (
  keyword: string,
  page = 0,
  size = 10
) => {
  const response = await api.get(
    `/employees/search?keyword=${keyword}&page=${page}&size=${size}`
  );

  return response.data;
};