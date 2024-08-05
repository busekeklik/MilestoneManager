import React from 'react';
import { Outlet } from 'react-router-dom';
import Sidebar from '../Sidebar/Sidebar';
import './Layout.css';

const Layout = ({ isSidebarOpen, toggleSidebar, toggleNotifications, showNotifications }) => {
    return (
        <div className={`layout ${isSidebarOpen ? 'sidebar-open' : ''}`}>
            <Sidebar isOpen={isSidebarOpen} toggleSidebar={toggleSidebar} />
            <div className="main-content">
                <header className="header">
                    <div className="left-section">
                        <div className="user-info">
                            <div className="user-icon">BK</div>
                            <span>Buse Keklik</span>
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
