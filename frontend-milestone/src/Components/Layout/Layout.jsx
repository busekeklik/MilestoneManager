import React, { useEffect, useState } from 'react';
import { Outlet } from 'react-router-dom';
import Sidebar from '../Sidebar/Sidebar';
import './Layout.css';

const Layout = ({ isSidebarOpen, toggleSidebar, toggleNotifications, showNotifications, user, setUser }) => {
    const [alerts, setAlerts] = useState([]);
    const [tasks, setTasks] = useState({});
    const userInitials = user && user.userName ? user.userName.split(" ").map((n) => n[0]).join("") : "BK";

    console.log('Rendering Layout'); // Debugging log
    console.log('User:', user); // Debugging log

    // Fetch alerts from API on component mount
    useEffect(() => {
        const fetchAlerts = async () => {
            try {
                const response = await fetch('http://localhost:3307/alert/api/v1/list'); // Replace with your API base URL
                const data = await response.json();
                setAlerts(data);
                fetchTasksForAlerts(data);
            } catch (error) {
                console.error('Error fetching alerts:', error);
            }
        };

        const fetchTasksForAlerts = async (alerts) => {
            const tasks = {};
            for (const alert of alerts) {
                if (alert.taskID) {  // Only proceed if taskID exists
                    try {
                        const response = await fetch(`http://localhost:3307/task/api/v1/find/${alert.taskID}`);
                        const taskData = await response.json();
                        tasks[alert.alertID] = taskData;

                        // Log which alert is related to which task
                        console.log(`Alert "${alert.message}" is related to Task "${taskData.taskName}"`);
                    } catch (error) {
                        console.error(`Error fetching task ${alert.taskID}:`, error);
                    }
                } else {
                    console.warn(`Alert with ID ${alert.alertID} does not have a valid taskID`);
                }
            }
            setTasks(tasks);
        };

        fetchAlerts();
    }, []);

    return (
        <div className={`layout ${isSidebarOpen ? 'sidebar-open' : ''}`}>
            <Sidebar isOpen={isSidebarOpen} toggleSidebar={toggleSidebar} user={user} setUser={setUser} />
            <div className="main-content">
                <header className="header">
                    <div className="left-section">
                        <div className="user-info">
                            <div className="user-icon">{userInitials}</div>
                            <span>{user && user.userName ? user.userName : "Buse Keklik"}</span>
                        </div>
                        <button className="menu-btn" onClick={toggleSidebar} aria-label="Toggle sidebar">☰</button>
                    </div>
                </header>
                <div className={`notifications ${showNotifications ? 'show-notifications' : ''}`}>
                    <button className="notifications" onClick={toggleNotifications} aria-label="Toggle notifications">
                        <div className="notification-icon">
                            <span className="notification-text">Bildirimler</span>
                        </div>
                        <span className="dropdown-icon">▼</span>
                    </button>
                    {showNotifications && (
                        <div className="notifications-popup" role="alert">
                            {alerts.length === 0 ? (
                                <div>Bildirim yok</div>
                            ) : (
                                alerts.map(alert => (
                                    <div key={alert.alertID} className="notification-item">
                                        <div>{alert.message}</div>
                                        <div className="notification-date">{new Date(alert.alertDate).toLocaleString()}</div>
                                        {tasks[alert.alertID] ? (
                                            <div className="related-task">
                                                <strong>İlişkili olduğu Task:</strong> {tasks[alert.alertID].taskName}
                                            </div>
                                        ) : (
                                            <div className="related-task">
                                                <strong>İlişkili olduğu Task:</strong> Not Available
                                            </div>
                                        )}
                                    </div>
                                ))
                            )}
                        </div>
                    )}
                </div>
                <main className="main-content-inner">
                    <Outlet />
                </main>
            </div>
        </div>
    );
};

export default Layout;
