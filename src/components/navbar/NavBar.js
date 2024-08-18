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
          <Link to="/">HOME</Link>
        </li>
        <li>
          <Link to="/movies/popular">POPULAR</Link>
        </li>
        <li>
          <Link to="/movies/top_rated">TOP RATED</Link>
        </li>
        <li>
          <Link to="/movies/upcoming">UPCOMING</Link>
        </li>
        <li>
          <Link to="/reviews">REVIEWS</Link>
        </li>

        {isAuthenticated() ? (
          <>
            <li>
              <span>Welcome,{user?.username || "User"}</span>
            </li>
            <li>
              <button onClick={logout}>LOGOUT</button>
            </li>
          </>
        ) : (
          <>
            <li>
              <Link to="/register">REGISTER</Link>
            </li>
            <li>
              <Link to="/login">LOGIN</Link>
            </li>
          </>
        )}
      </ul>
    </nav>
  );
};

export default Navbar;
