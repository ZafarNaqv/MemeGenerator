import "../css/FeedbackList.css";
import "../App.css"
import React, {useState} from "react";

function FeedbackList({ feedbacks, onUpdate }){
    const [copiedId, setCopiedId] = useState(null);
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
                        <th>Feedback Id</th>
                        <th>User Id</th>
                        <th>Feedback</th>
                        <th>Created At</th>
                    </tr>
                    </thead>
                    <tbody>
                    {feedbacks.map((item, idx) => (
                        <tr key={idx}>
                            <td>{idx + 1}</td>
                            <td className="id-cell">
                                <div className="tooltip-wrapper">
                                    <span className="short-id">{item.id.slice(0, 8)}</span>
                                    <div
                                        className="tooltip-text"
                                        onMouseEnter={(e) => e.stopPropagation()}
                                        onClick={(e) => e.stopPropagation()}
                                    >
                                        <span>{item.id}</span>
                                        <button
                                            className="copy-btn"
                                            onClick={async () => {
                                                try {
                                                    await navigator.clipboard.writeText(item.id);
                                                    setCopiedId(item.id);
                                                    setTimeout(() => setCopiedId(null), 1500);
                                                } catch (err) {
                                                    console.error("Clipboard write failed:", err);
                                                    alert("Copy failed");
                                                }
                                            }}
                                            title="Copy to clipboard"
                                        >
                                            {copiedId === item.id ? "Copied!" : "📋"}
                                        </button>
                                    </div>
                                </div>
                            </td>

                            <td>{item.userId}</td>
                            <td>{item.feedback}</td>
                            <td>{item.createdAt}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}

            {/* Buttons Section - Below Table */}
            <div style={{marginTop: '1rem'}}>
                <button onClick={handleDeleteAll}>Delete All Feedbacks</button>
                <input
                    type="text"
                    placeholder="Enter Feedback ID"
                    value={deleteId}
                    onChange={(e) => setDeleteId(e.target.value)}
                    style={{marginLeft: '1rem', marginRight: '0.5rem'}}
                />
                <button onClick={handleDeleteById}>Delete by ID</button>
            </div>
        </div>
    );
}


export default FeedbackList;