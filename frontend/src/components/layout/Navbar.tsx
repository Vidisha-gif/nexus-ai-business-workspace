import "./Navbar.css";
import { Bell, Search, UserCircle } from "lucide-react";

const Navbar = () => {
  return (
    <header className="navbar">
      <div className="navbar-left">
        <h2>Dashboard</h2>
      </div>

      <div className="navbar-right">
        <div className="search-box">
          <Search size={18} />
          <input
            type="text"
            placeholder="Search..."
          />
        </div>

        <Bell className="icon" size={22} />

        <div className="profile">
          <UserCircle size={35} />
          <div>
            <h4>Admin</h4>
            <p>Welcome Back</p>
          </div>
        </div>
      </div>
    </header>
  );
};

export default Navbar;