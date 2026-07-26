import api from "./api";

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
}

export const loginUser = async (data: LoginRequest) => {
  const response = await api.post("/auth/login", data);
  return response.data;
};

export const registerUser = async (data: RegisterRequest) => {
  const response = await api.post("/auth/register", data);
  return response.data;
};

export const logout = () => {
  localStorage.removeItem("token");
};

export const isAuthenticated = () => {
  return !!localStorage.getItem("token");
};

export const getToken = () => {
  return localStorage.getItem("token");
};