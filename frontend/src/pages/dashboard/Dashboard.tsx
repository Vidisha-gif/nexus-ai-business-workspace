import DashboardLayout from "../../components/layout/DashboardLayout";
import "./Dashboard.css";

const Dashboard = () => {
  return (
    <DashboardLayout>

      <div className="cards">

        <div className="card">
          <h3>Total Employees</h3>
          <h1>120</h1>
        </div>

        <div className="card">
          <h3>Projects</h3>
          <h1>18</h1>
        </div>

        <div className="card">
          <h3>Tasks</h3>
          <h1>74</h1>
        </div>

        <div className="card">
          <h3>Departments</h3>
          <h1>6</h1>
        </div>

      </div>

    </DashboardLayout>
  );
};

export default Dashboard;