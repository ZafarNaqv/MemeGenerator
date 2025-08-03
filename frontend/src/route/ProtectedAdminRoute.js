import { useEffect, useState } from "react";
import { Navigate } from "react-router-dom";

export default function ProtectedAdminRoute({ children }) {
    const [user, setUser] = useState(null);
    const [checked, setChecked] = useState(false);

    useEffect(() => {
        if (process.env.REACT_APP_DEV_MODE === "true") {
            setUser({ name: "Dev Admin", email: "admin@example.com", isAdmin: true });
            setChecked(true);
            return;
        }

        fetch("/api/user", {
            credentials: "include"
        })
            .then(res => (res.ok ? res.json() : null))
            .then(data => {
                if (data && data.isAdmin === true) {
                    setUser(data);
                } else {
                    setUser(null);
                }
            })
            .catch(() => setUser(null))
            .finally(() => setChecked(true));
    }, []);

    if (!checked) return <div>Loading...</div>;
    if (!user) return <Navigate to="/login" />;
    return children;
}