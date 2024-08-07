import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import DashboardPage from './Components/Dashboard/DashboardPage';
import LoginPage from './Components/LoginSignup/LoginPage';
import Layout from './Components/Layout/Layout';
import TaskForm from "./Components/TaskForm/TaskForm";

const App = () => {
    const [isSidebarOpen, setIsSidebarOpen] = useState(false);
    const [showNotifications, setShowNotifications] = useState(false);
    const [user, setUser] = useState(null); // Add user state

    const toggleSidebar = () => {
        setIsSidebarOpen(!isSidebarOpen);
    };

    const toggleNotifications = () => {
        setShowNotifications(!showNotifications);
    };

    return (
        <Router>
            <Routes>
                <Route path="/login" element={<LoginPage setUser={setUser} />} /> {/* Pass setUser */}
                <Route path="/" element={<Navigate to="/login" />} />
                <Route path="/*" element={
                    <Layout
                        isSidebarOpen={isSidebarOpen}
                        toggleSidebar={toggleSidebar}
                        toggleNotifications={toggleNotifications}
                        showNotifications={showNotifications}
                    />
                }>
                    <Route path="dashboard" element={<DashboardPage />} />
                    <Route path="taskform" element={<TaskForm />} />
                    {/* other pages */}
                </Route>
                <Route path="*" element={<Navigate to="/login" />} />
            </Routes>
        </Router>
    );
};

export default App;
