import React, { useEffect, useState } from 'react';

function UsersTable() {
    const [users, setUsers] = useState([]);

    useEffect(() => {
        fetch('/api/users')
            .then(res => {
                if (!res.ok) throw new Error("Failed to fetch users");
                return res.json();
            })
            .then(setUsers)
            .catch(err => alert(err.message));
    }, []);

    return (
        <div className="container" style={{ textAlign: "center" }}>
            <div className="users-table">
                <h2>Registered Users</h2>
                {!users.length ? (
                    <p>No users found.</p>
                ) : (
                    <table className="styled-table">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th className="wide-col">User ID</th>
                            <th>Email</th>
                            <th>Name</th>
                            <th className="pic-col" >Profile Pic</th>
                            <th>Created At</th>
                        </tr>
                        </thead>
                        <tbody>
                        {users.map((u, idx) => (
                            <tr key={u.id}>
                                <td>{idx + 1}</td>
                                <td>{u.id}</td>
                                <td>{u.email}</td>
                                <td>{u.name}</td>
                                <td>
                                    {u.pictureUrl ? (
                                        <img src={u.pictureUrl} alt="profile" width="30"/>
                                    ) : (
                                        "-"
                                    )}
                                </td>
                                <td>{u.createdAt}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                )}
            </div>
        </div>
    );
}

export default UsersTable;