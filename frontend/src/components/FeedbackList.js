import "./FeedbackList.css";
import React, {useState} from "react";

function FeedbackList({ feedbacks, onUpdate }){
    const [deleteId, setDeleteId] = useState('');
    const handleDeleteAll = async () => {
        const res = await fetch('/api/feedback', {
            method: 'DELETE',
        });
        if (res.ok) {
            alert('All feedbacks deleted');
            onUpdate();
        } else {
            alert('Failed to delete all');
        }
    };

    const handleDeleteById = async () => {
        if (!deleteId.trim()) return alert('Enter feedback ID');
        const res = await fetch(`/api/feedback/${deleteId}`, {
            method: 'DELETE',
        });
        if (res.ok) {
            alert(`Feedback with Id ${deleteId} deleted`);
            setDeleteId('');
            onUpdate(); // refresh
        } else {
            alert('Delete failed: ' + (await res.text()));
        }
    };


    return (
        <div className="feedback-list">
            {!feedbacks.length ? (
                <p>No feedbacks found.</p>
            ) : (
                <table className="styled-table">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>ID</th>
                        <th>Feedback</th>
                        <th>Timestamp</th>
                    </tr>
                    </thead>
                    <tbody>
                    {feedbacks.map((item, idx) => (
                        <tr key={idx}>
                            <td>{idx + 1}</td>
                            <td>{item.feedbackId  || 'N/A'}</td>
                            <td>{item.feedback}</td>
                            <td>{item.timestamp}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}

            {/* Buttons Section - Below Table */}
            <div style={{ marginTop: '1rem' }}>
                <button onClick={handleDeleteAll}>Delete All Feedbacks</button>
                <input
                    type="text"
                    placeholder="Enter Feedback ID"
                    value={deleteId}
                    onChange={(e) => setDeleteId(e.target.value)}
                    style={{ marginLeft: '1rem', marginRight: '0.5rem' }}
                />
                <button onClick={handleDeleteById}>Delete by ID</button>
            </div>
        </div>
    );
}


export default FeedbackList;