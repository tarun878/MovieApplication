import React, { createContext, useContext, useState, useEffect } from "react";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [token, setToken] = useState(localStorage.getItem("token") || "");

  const login = async (username, password) => {
    try {
      const response = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ username, password }),
      });

      if (response.ok) {
        const data = await response.json();
        if (data.successcode === "success") {
          setUser({ username: data.username, email: data.email });
          setToken(data.token);
          localStorage.setItem("token", data.token);
        } else {
          console.error("Login error:", data.successcode || data.successcode);
          throw data.successcode;
        }
      } else {
        const result = await response.json();
        const errorMessage = result.message || "Login failed.";
        console.error("Login error:", errorMessage);
        throw new Error(errorMessage);
      }
    } catch (error) {
      console.error("Login error:", error.message || error);
      throw error;
    }
  };

  const register = async (username, password, email) => {
    try {
      const response = await fetch("http://localhost:8080/api/auth/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ username, password, email }),
      });

      if (response.ok) {
        const result = await response.json();
        if (result.successcode === "success") {
          // await login(username, password);
        } else {
          throw result.successcode;
        }
      } else {
        const result = await response.json();
        const errorMessage = result.message || "Registration failed.";
        console.error("Registration error:", errorMessage);
        throw new Error(errorMessage);
      }
    } catch (error) {
      console.error("Registration error:", error.message || error);
      throw error;
    }
  };

  const logout = () => {
    setUser(null);
    setToken("");
    localStorage.removeItem("token");
  };

  const isAuthenticated = () => !!token;

  const getAuthToken = () => token;

  useEffect(() => {
    const storedToken = localStorage.getItem("token");
    if (storedToken) {
      setToken(storedToken);
      // Optionally fetch user details based on token
    }
  }, []);

  return (
    <AuthContext.Provider
      value={{ user, login, register, logout, isAuthenticated, getAuthToken }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
