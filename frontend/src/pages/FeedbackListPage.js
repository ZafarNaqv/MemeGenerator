// src/pages/FeedbackListPage.js
import React, { useEffect, useState } from "react";
import FeedbackList from "../components/FeedbackList";
import "../App.css"; // or separate CSS if preferred

function FeedbackListPage() {
    const [feedbacks, setFeedbacks] = useState([]);
    const [error, setError] = useState(null);

    const fetchFeedbacks = () => {
         setError(null);
        fetch("/api/feedback")
            .then((res) => {
                if (!res.ok) throw new Error("Failed to fetch feedbacks");
                return res.json();
            })
            .then(setFeedbacks)
            .catch((err) => setError(err.message));
    };

    useEffect(() => {
        fetchFeedbacks();
    }, []);

    return (
        <div className="container">
            <h2>User Feedbacks</h2>
            {error && <p style={{ color: "red" }}>{error}</p>}
            <FeedbackList feedbacks={feedbacks}  onUpdate={fetchFeedbacks} />
        </div>
    );
}

export default FeedbackListPage;