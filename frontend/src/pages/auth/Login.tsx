import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { loginUser } from "../../services/authService";
import "./Login.css";

const Login = () => {
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    setLoading(true);
    setError("");

    try {
      const response = await loginUser({
        email,
        password,
      });

      console.log("Response:", response);

      localStorage.setItem("token", response.token);

      console.log("Saved Token:", localStorage.getItem("token"));

      console.log("Before navigate");

      navigate("/dashboard");

      console.log("After navigate");


    } catch (err: any) {
      console.error(err);

      setError(
        err?.response?.data?.message ||
        err?.message ||
        "Invalid email or password."
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-container">
      <div className="login-left">
        <h1>NexusAI</h1>

        <h2>Employee Management System</h2>

        <p>
          Manage Employees, Projects, Tasks and Company Operations from one
          place.
        </p>
      </div>

      <div className="login-right">
        <div className="login-card">
          <h2>Welcome Back 👋</h2>

          <p>Login to continue</p>

          <form onSubmit={handleSubmit}>
            <div className="input-group">
              <label>Email</label>

              <input
                type="email"
                placeholder="Enter your email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            </div>

            <div className="input-group">
              <label>Password</label>

              <input
                type="password"
                placeholder="Enter your password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </div>

            {error && (
              <p className="error-message">{error}</p>
            )}

            <button type="submit" disabled={loading}>
              {loading ? "Logging in..." : "Login"}
            </button>
          </form>

          <div className="bottom-text">
            Don't have an account?
            <span
              onClick={() => navigate("/register")}
              style={{
                cursor: "pointer",
                color: "#8b5cf6",
                fontWeight: "600",
                marginLeft: "5px",
              }}
            >
              Register
            </span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;