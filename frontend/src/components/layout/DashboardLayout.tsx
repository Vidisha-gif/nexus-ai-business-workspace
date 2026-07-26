import { ReactNode } from "react";
import Sidebar from "./Sidebar";
import Navbar from "./Navbar";
import "./DashboardLayout.css";

interface Props {
  children: ReactNode;
}

const DashboardLayout = ({ children }: Props) => {
  return (
    <div className="layout">
      <Sidebar />

      <div className="main">
        <Navbar />

        <div className="content">
          {children}
        </div>
      </div>
    </div>
  );
};

export default DashboardLayout;