import { useEffect, useState } from "react";
import DashboardLayout from "../../components/layout/DashboardLayout";
import { getEmployees } from "../../services/employeeService";
import type { Employee } from "../../types/employee";
import "./Employees.css";

const Employees = () => {
  const [employees, setEmployees] = useState<Employee[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadEmployees();
  }, []);

  const loadEmployees = async () => {
    try {
      const response = await getEmployees();
      setEmployees(response.content);
    } catch (error) {
      console.error("Error loading employees:", error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <DashboardLayout>
      <div className="employees-page">
        <h1>Employee Management</h1>

        {loading ? (
          <h2>Loading...</h2>
        ) : (
          <table className="employee-table">
            <thead>
              <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Department</th>
                <th>Designation</th>
                <th>Salary</th>
              </tr>
            </thead>

            <tbody>
              {employees.map((emp) => (
                <tr key={emp.id}>
                  <td>{emp.firstName} {emp.lastName}</td>
                  <td>{emp.email}</td>
                  <td>{emp.department}</td>
                  <td>{emp.designation}</td>
                  <td>₹{emp.salary.toLocaleString()}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </DashboardLayout>
  );
};

export default Employees;