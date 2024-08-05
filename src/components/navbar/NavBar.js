import React from "react";
import { Link } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";
import "./NavBar.css";

const Navbar = () => {
  const { user, logout, isAuthenticated } = useAuth(); // Access user and logout from AuthContext

  return (
    <nav className="navbar">
      <ul>
        <li>
          <Link to="/">Home</Link>
        </li>
        <li>
          <Link to="/movies/popular">Popular</Link>
        </li>
        <li>
          <Link to="/movies/top_rated">Top Rated</Link>
        </li>
        <li>
          <Link to="/movies/upcoming">upcoming</Link>
        </li>
        <li>
          <Link to="/reviews">Reviews</Link>
        </li>

        {isAuthenticated() ? (
          <>
            <li>
              <span>Welcome,{user?.username || "User"}</span>
            </li>
            <li>
              <button onClick={logout}>Logout</button>
            </li>
          </>
        ) : (
          <>
            <li>
              <Link to="/register">Register</Link>
            </li>
            <li>
              <Link to="/login">Login</Link>
            </li>
          </>
        )}
      </ul>
    </nav>
  );
};

export default Navbar;
