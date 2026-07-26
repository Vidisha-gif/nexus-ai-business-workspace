import "./Sidebar.css";
import {
  LayoutDashboard,
  Users,
  FolderKanban,
  CheckSquare,
  Bot,
  Settings,
  LogOut,
} from "lucide-react";

import { NavLink, useNavigate } from "react-router-dom";

const Sidebar = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  return (
    <aside className="sidebar">
      <div className="logo">
        <h2>NexusAI</h2>
        <p>EMS Dashboard</p>
      </div>

      <nav className="menu">

        <NavLink
          to="/dashboard"
          className={({ isActive }) => (isActive ? "active" : "")}
        >
          <LayoutDashboard size={20} />
          <span>Dashboard</span>
        </NavLink>

        <NavLink
          to="/employees"
          className={({ isActive }) => (isActive ? "active" : "")}
        >
          <Users size={20} />
          <span>Employees</span>
        </NavLink>

        <NavLink
          to="/projects"
          className={({ isActive }) => (isActive ? "active" : "")}
        >
          <FolderKanban size={20} />
          <span>Projects</span>
        </NavLink>

        <NavLink
          to="/tasks"
          className={({ isActive }) => (isActive ? "active" : "")}
        >
          <CheckSquare size={20} />
          <span>Tasks</span>
        </NavLink>

        <NavLink
          to="/ai"
          className={({ isActive }) => (isActive ? "active" : "")}
        >
          <Bot size={20} />
          <span>AI Insights</span>
        </NavLink>

        <NavLink
          to="/profile"
          className={({ isActive }) => (isActive ? "active" : "")}
        >
          <Settings size={20} />
          <span>Profile</span>
        </NavLink>

      </nav>

      <button className="logout-btn" onClick={handleLogout}>
        <LogOut size={20} />
        Logout
      </button>
    </aside>
  );
};

export default Sidebar;