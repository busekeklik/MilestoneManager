import React from 'react';
import { Outlet } from 'react-router-dom';
import Sidebar from '../Sidebar/Sidebar';
import './Layout.css';

const Layout = ({ isSidebarOpen, toggleSidebar, toggleNotifications, showNotifications, user, setUser }) => {
    const userInitials = user ? user.name.split(" ").map((n) => n[0]).join("") : "BK"; // Get user initials

    return (
        <div className={`layout ${isSidebarOpen ? 'sidebar-open' : ''}`}>
            <Sidebar isOpen={isSidebarOpen} toggleSidebar={toggleSidebar} user={user} setUser={setUser} />
            <div className="main-content">
                <header className="header">
                    <div className="left-section">
                        <div className="user-info">
                            <div className="user-icon">{userInitials}</div>
                            <span>{user ? user.name : "Buse Keklik"}</span>
                        </div>
                        <button className="menu-btn" onClick={toggleSidebar} aria-label="Toggle sidebar">☰</button>
                    </div>
                </header>
                <div className="notification-bar">
                    <button className="notifications" onClick={toggleNotifications} aria-label="Toggle notifications">
                        <div className="notification-icon">
                            <span className="notification-text">Bildirimler</span>
                        </div>
                        <span className="dropdown-icon">▼</span>
                    </button>
                    {showNotifications && (
                        <div className="notifications-popup" role="alert">Bildirim yok</div>
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
