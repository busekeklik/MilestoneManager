import React from 'react';
import { Navigate, useLocation } from 'react-router-dom';

const RequireAuth = ({ user, children }) => {
    const location = useLocation();

    console.log('RequireAuth user:', user); // Debugging log

    if (!user) {
        return <Navigate to="/login" replace state={{ from: location }} />;
    }

    return children;
};

export default RequireAuth;
