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
        setUser({ username: data.username, email: data.email });
        setToken(data.token);
        localStorage.setItem("token", data.token);
      } else {
        const errorData = await response.text();
        console.error("Login error:", errorData);
        throw new Error("Login failed: " + errorData);
      }
    } catch (error) {
      console.error("Login error:", error);
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
        await login(username, password);
      } else {
        const errorData = await response.text();
        console.error("Registration error:", errorData);
        throw new Error("Registration failed: " + errorData);
      }
    } catch (error) {
      console.error("Registration error:", error);
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
