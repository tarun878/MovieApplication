import "./App.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./pages/home/Home";
import MovieList from "./components/movieList/movieList";
import Movie from "./pages/movieDetail/movie";
import Review from "./components/reviews/review";

import ProtectedRoute from "./context/ProctectedRoute";

import Navbar from "./components/navbar/NavBar";
import RegisterPage from "./components/auth/register";
import LoginPage from "./components/auth/login";

const App = () => {
  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/movies/:type" element={<MovieList />} />
        <Route path="/movie/:id" element={<Movie />} />
        <Route path="/reviews" element={<ProtectedRoute element={Review} />} />

        <Route path="/register" element={<RegisterPage />} />
        <Route path="/login" element={<LoginPage />} />

        <Route path="*" element={<h1>404 - Page Not Found</h1>} />
      </Routes>
    </Router>
  );
};

export default App;
