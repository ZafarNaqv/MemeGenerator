import { useEffect, useState } from "react";
import { Navigate } from "react-router-dom";



export default function ProtectedAdminRoute({ children }) {
    const [user, setUser] = useState(null);
    const [checked, setChecked] = useState(false);

    /**
     * @typedef {Object} UserData
     * @property {string} email
     * @property {string} name
     * @property {boolean} isAdmin
     */

    /** @type {UserData|null} */
    let userData = null;
    useEffect(() => {
        fetch("/api/user")
            .then(res => res.ok ? res.json() : null)
            .then(data => {
                userData = data;
                if (userData && userData.isAdmin === true) {
                    setUser(userData);
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