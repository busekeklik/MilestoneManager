// src/Components/RequireAuth.js
import React from 'react';
import { Navigate, useLocation } from 'react-router-dom';

const RequireAuth = ({ user, children }) => {
    const location = useLocation(); // Use useLocation hook to get location object

    if (!user) {
        // Redirect to login, preserving the attempted destination
        return <Navigate to="/login" replace state={{ from: location }} />;
    }

    return children;
};

export default RequireAuth; // Ensure this is correctly exported
