import "./FeedbackList.css";
import React from "react";

function FeedbackList({ feedbacks }) {
    if (!feedbacks.length) return <p>No feedbacks found.</p>;

    return (
        <div className="feedback-list">
            <table className="styled-table">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Feedback</th>
                    <th>Timestamp</th>
                </tr>
                </thead>
                <tbody>
                {feedbacks.map((item, idx) => (
                    <tr key={idx}>
                        <td>{idx + 1}</td>
                        <td>{item.feedback}</td>
                        <td>{item.timestamp}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}


export default FeedbackList;