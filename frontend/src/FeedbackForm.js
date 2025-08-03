import React, { useState } from "react";
import "./css/Feedback.css"
function FeedbackForm() {
    const [feedback, setFeedback] = useState("");
    const [status, setStatus] = useState(null);
    const [isError, setIsError] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!feedback.trim()) return;

        try {
            const payload = { feedback: feedback };
            const response = await fetch("/api/feedback", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                credentials: "same-origin",
                body: JSON.stringify(payload),
            });
            const bodyText = await response.text();
            if (!response.ok) {
                setIsError(true);
                setStatus(`Error: ${bodyText || "Failed to submit feedback."}`);
            }else{
                setIsError(false);
                setStatus("Feedback submitted successfully!");
                setFeedback("");
            }
        } catch (error) {
            setStatus(`Error submitting feedback.: ${error.message}`);
        }
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
            {status && (
                <p className={`feedback-status ${isError ? "error" : "success"}`} role="alert">
                    {status}
                </p>
            )}
        </form>
    );
}

export default FeedbackForm;