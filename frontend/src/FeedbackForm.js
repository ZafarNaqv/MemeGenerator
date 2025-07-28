// src/FeedbackForm.js
import React, { useState } from "react";

function FeedbackForm() {
    const [feedback, setFeedback] = useState("");
    const [status, setStatus] = useState(null);

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!feedback.trim()) return;

        try {
            const payload = { feedback: feedback };
            const response = await fetch("/api/feedback", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload),
            });

            if (!response.ok) {
                throw new Error("Failed to submit feedback");
            }

            setStatus("Feedback submitted successfully!");
            setFeedback(""); // clear textarea
        } catch (error) {
            setStatus("Error submitting feedback.");
        }

        // Optional: clear status after some seconds
        setTimeout(() => setStatus(null), 4000);
    };

    return (
        <form onSubmit={handleSubmit} className="feedback-form">
            <label htmlFor="feedback">Feedback:</label>
            <textarea
                id="feedback"
                rows="4"
                maxLength={1000}
                value={feedback}
                onChange={(e) => setFeedback(e.target.value)}
                placeholder="Enter your feedback here..."
            />
            <button type="submit">Submit Feedback</button>
            {status && <p className="feedback-status">{status}</p>}
        </form>
    );
}

export default FeedbackForm;