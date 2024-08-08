import React, { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import './Sidebar.css';

const Sidebar = ({ isOpen, toggleSidebar }) => {
    const [projelerDropdownOpen, setProjelerDropdownOpen] = useState(false);
    const [ekiplerDropdownOpen, setEkiplerDropdownOpen] = useState(false);
    const [username, setUsername] = useState('');
    const [initials, setInitials] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const storedUsername = localStorage.getItem('username');
        if (storedUsername) {
            setUsername(storedUsername);
            setInitials(getInitials(storedUsername));
        } else {
            console.log("Username not found in local storage");
        }
    }, []);

    const handleLogout = () => {
        localStorage.removeItem('token');
        localStorage.removeItem('username');
        localStorage.removeItem('user'); // Ensure all user-related data is removed
        navigate('/login');
    };

    const toggleDropdown = (dropdown) => {
        if (dropdown === 'projeler-dropdown') {
            setProjelerDropdownOpen(!projelerDropdownOpen);
        } else if (dropdown === 'ekipler-dropdown') {
            setEkiplerDropdownOpen(!ekiplerDropdownOpen);
        }
    };

    const getInitials = (name) => {
        const nameParts = name.split(' ');
        if (nameParts.length === 1) {
            return nameParts[0].charAt(0).toUpperCase();
        } else {
            return nameParts.map(part => part.charAt(0).toUpperCase()).join('');
        }
    };

    return (
        <div className={`sidebar ${isOpen ? 'open' : ''}`} id="sidebar">
            <div className="sidebar-header">
                <div className="user-info">
                    <div className="user-icon">{initials}</div>
                    <span>{username}</span>
                </div>
                <button className="close-btn" onClick={toggleSidebar}>×</button>
            </div>
            <ul className="nav-list">
                <li className="nav-item">
                    <button className="nav-link" onClick={() => toggleDropdown('projeler-dropdown')}>
                        Projeler {projelerDropdownOpen ? '▲' : '▼'}
                    </button>
                    {projelerDropdownOpen && (
                        <ul className="dropdown" id="projeler-dropdown">
                            <li>Projeler</li>
                            <li><Link to="/dashboard" className="nav-link" onClick={toggleSidebar}>Ana Sayfa</Link></li>
                        </ul>
                    )}
                </li>
                <li className="nav-item">
                    <button className="nav-link" onClick={() => toggleDropdown('ekipler-dropdown')}>
                        Ekipler {ekiplerDropdownOpen ? '▲' : '▼'}
                    </button>
                    {ekiplerDropdownOpen && (
                        <ul className="dropdown" id="ekipler-dropdown">
                            <li>Ekipler</li>
                            <li><Link to="/teampage" className="nav-link" onClick={toggleSidebar}>Ekibim</Link></li>
                        </ul>
                    )}
                </li>
                <li className="nav-item logout">
                    <button className="nav-link" onClick={handleLogout}>Çıkış</button>
                </li>
            </ul>


        </div>
    );
};

export default Sidebar;

