import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import DashboardPage from './Components/Dashboard/DashboardPage';
import LoginPage from './Components/LoginSignup/LoginPage';
import Layout from './Components/Layout/Layout';
import TaskForm from "./Components/TaskForm/TaskForm";
import TeamPage from "./Components/TeamPage/TeamPage";
import PopupModal from './Components/PopupModal/PopupModal';
import TeamsPage from "./Components/TeamsPage/TeamsPage";
import ProjectsPage from "./Components/ProjectsPage/ProjectsPage";
import RequireAuth from './Components/RequireAuth';

const App = () => {
    const [isSidebarOpen, setIsSidebarOpen] = useState(false);
    const [showNotifications, setShowNotifications] = useState(false);
    const [user, setUser] = useState(() => {
        const storedUser = localStorage.getItem('user');
        return storedUser ? JSON.parse(storedUser) : null;
    });

    useEffect(() => {
        const storedUser = JSON.parse(localStorage.getItem('user'));
        if (storedUser) {
            console.log('Retrieved user from localStorage:', storedUser);
            setUser(storedUser);
        }
    }, []);

    const toggleSidebar = () => {
        setIsSidebarOpen(!isSidebarOpen);
    };

    const toggleNotifications = () => {
        setShowNotifications(!showNotifications);
    };


    return (
        <Router>
            <Routes>
                <Route path="/login" element={<LoginPage setUser={setUser} />} />
                <Route path="/" element={<Navigate to="/dashboard" />} />
                <Route path="*" element={<Navigate to="/login" />} />
                <Route path="/" element={
                    <RequireAuth user={user}>
                        <Layout
                            isSidebarOpen={isSidebarOpen}
                            toggleSidebar={toggleSidebar}
                            toggleNotifications={toggleNotifications}
                            showNotifications={showNotifications}
                            user={user}
                            setUser={setUser}
                        />
                        {isLoggedIn && <PopupModal isOpen={showModal} onClose={handleCloseModal} />}
                    </RequireAuth>
                }>
                    <Route path="dashboard" element={<DashboardPage />} />
                    <Route path="taskform" element={<TaskForm />} />
                    <Route path="teampage" element={<TeamPage />} />
                    <Route path="teamspage" element={<TeamsPage />} />
                    <Route path="projects" element={<ProjectsPage />} />
                </Route>

            </Routes>
        </Router>
    );
}

export default App;