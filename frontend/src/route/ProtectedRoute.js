import { useEffect, useState } from "react";
import { Navigate } from "react-router-dom";

export default function ProtectedRoute({ children }) {
    const [user, setUser] = useState(null);
    const [checked, setChecked] = useState(false);

    useEffect(() => {
        fetch("/api/user")
            .then(res => (res.ok ? res.json() : null))
            .then(data => setUser(data))
            .catch(() => setUser(null))
            .finally(() => setChecked(true));
    }, []);

    if (!checked) return <div>Loading...</div>;
    if (!user) return <Navigate to="/login" />;
    return children;
}