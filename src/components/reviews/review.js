import React, { useState, useEffect } from "react";
import { useAuth } from "../../context/AuthContext";
import "./review.css";

const Review = () => {
  const { user, getAuthToken } = useAuth();
  const [movies, setMovies] = useState([]);
  const [selectedMovie, setSelectedMovie] = useState(null);
  const [comment, setComment] = useState("");
  const [rating, setRating] = useState(0);
  const [reviewToEdit, setReviewToEdit] = useState(null);
  const [message, setMessage] = useState("");
  const [reviews, setReviews] = useState([]);
  const [showDropdown, setShowDropdown] = useState(false);

  const TMDB_API_KEY = "4e44d9029b1270a757cddc766a1bcb63&language=en-US";
  // Fetch reviews for the selected movie
  useEffect(() => {
    if (selectedMovie) {
      fetchReviewsByMovieId(selectedMovie.id);
      setShowDropdown(false);
    }
  }, [selectedMovie]);

  const handleMovieSearch = async (query) => {
    if (query.length < 3) {
      setMovies([]);
      setShowDropdown(false);
      return;
    }

    try {
      const response = await fetch(
        `https://api.themoviedb.org/3/search/movie?api_key=${TMDB_API_KEY}&query=${query}`
      );
      if (!response.ok) {
        throw new Error("Failed to fetch movies");
      }
      const data = await response.json();
      setMovies(data.results);
      setShowDropdown(true);
    } catch (error) {
      console.error("Error fetching movies:", error);
    }
  };

  const fetchReviewsByMovieId = async (movieId) => {
    try {
      const response = await fetch(
        `http://localhost:8080/api/reviews/movie/${movieId}`,
        {
          headers: {
            Authorization: `Bearer ${getAuthToken()}`,
          },
        }
      );
      if (!response.ok) {
        throw new Error("Failed to fetch reviews");
      }
      const data = await response.json();
      setReviews(data.data);
    } catch (error) {
      console.error("Error fetching reviews:", error);
    }
  };
  const handleMovieSelect = (movie) => {
    setSelectedMovie(movie);
    setMovies([]);
    setShowDropdown(false);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!user || !getAuthToken()) {
      setMessage("You must be logged in to submit a review.");
      return;
    }

    const review = {
      movieId: selectedMovie ? selectedMovie.id : null,
      comment,
      rating,
      username: user.username,
    };

    try {
      const response = reviewToEdit
        ? await fetch(
            `http://localhost:8080/api/reviews/${reviewToEdit.reviewId}`,
            {
              method: "PUT",
              headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${getAuthToken()}`,
              },
              body: JSON.stringify(review),
            }
          )
        : await fetch("http://localhost:8080/api/reviews", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${getAuthToken()}`,
            },
            body: JSON.stringify(review),
          });

      if (!response.ok) {
        const errorData = await response.text();
        console.error("Submission error:", errorData);
        throw new Error("Review submission failed: " + errorData);
      }

      const result = await response.json();
      setMessage(result.message);
      setComment("");
      setRating(0);
      setReviewToEdit(null);

      // Refresh the reviews list
      if (selectedMovie && selectedMovie.id) {
        fetchReviewsByMovieId(selectedMovie.id); // Refresh reviews
      }
    } catch (error) {
      console.error("Error submitting review:", error);
      setMessage("Failed to submit review. Please try again.");
    }
  };

  const handleEdit = (review) => {
    setReviewToEdit(review);
    setComment(review.comment);
    setRating(review.rating);
  };

  const handleDelete = async (reviewId) => {
    if (!window.confirm("Are you sure you want to delete this review?")) {
      return;
    }

    try {
      const response = await fetch(
        `http://localhost:8080/api/reviews/${reviewId}`,
        {
          method: "DELETE",
          headers: {
            Authorization: `Bearer ${getAuthToken()}`,
          },
        }
      );

      if (!response.ok) {
        const errorData = await response.text();
        console.error("Deletion error:", errorData);
        throw new Error("Review deletion failed: " + errorData);
      }

      const result = await response.json();
      setMessage(result.message);

      // Refresh the reviews list
      if (selectedMovie && selectedMovie.id) {
        fetchReviewsByMovieId(selectedMovie.id); // Refresh reviews
      }
    } catch (error) {
      console.error("Error deleting review:", error);
      setMessage("Failed to delete review. Please try again.");
    }
  };

  return (
    <div className="review-container">
      <h2>Submit a Review</h2>
      <form onSubmit={handleSubmit}>
        <div className="movie-search">
          <input
            type="text"
            placeholder="Search for a movie"
            onChange={(e) => handleMovieSearch(e.target.value)}
          />
          {showDropdown && (
            <ul className="dropdown-list">
              {movies.map((movie) => (
                <li key={movie.id} onClick={() => setSelectedMovie(movie)}>
                  {movie.title}
                </li>
              ))}
            </ul>
          )}
        </div>

        {selectedMovie && (
          <div>
            <h3>Selected Movie: {selectedMovie.title}</h3>
            <p>{selectedMovie.overview}</p>
            {selectedMovie.poster_path && (
              <img
                src={`https://image.tmdb.org/t/p/w200${selectedMovie.poster_path}`}
                alt={`${selectedMovie.title} poster`}
              />
            )}
            <textarea
              placeholder="Your comment"
              value={comment}
              onChange={(e) => setComment(e.target.value)}
              required
            />
            <input
              type="number"
              min="1"
              max="5"
              placeholder="Rating (1-5)"
              value={rating}
              onChange={(e) => setRating(parseInt(e.target.value))}
              required
            />
            <button type="submit">
              {reviewToEdit ? "Update Review" : "Submit Review"}
            </button>
          </div>
        )}
        {message && <p>{message}</p>}
      </form>

      {selectedMovie && (
        <div>
          <h3>Reviews for {selectedMovie.title}</h3>
          <ul>
            {reviews.map((review) => (
              <li key={review.reviewId}>
                <strong>{review.username}</strong>: {review.comment} (Rating:
                {review.rating})
                {user && user.username === review.username && (
                  <div>
                    <button onClick={() => handleEdit(review)}>Edit</button>
                    <button onClick={() => handleDelete(review.reviewId)}>
                      Delete
                    </button>
                  </div>
                )}
              </li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
};

export default Review;
