import React, { useState } from 'react';
import './Sidebar.css';

const Sidebar = ({ isOpen, toggleSidebar }) => {
    const [projelerDropdownOpen, setProjelerDropdownOpen] = useState(false);
    const [ekiplerDropdownOpen, setEkiplerDropdownOpen] = useState(false);

    const toggleDropdown = (dropdown) => {
        if (dropdown === 'projeler-dropdown') {
            setProjelerDropdownOpen(!projelerDropdownOpen);
        } else if (dropdown === 'ekipler-dropdown') {
            setEkiplerDropdownOpen(!ekiplerDropdownOpen);
        }
    };

    return (
        <div className={`sidebar ${isOpen ? 'open' : ''}`} id="sidebar">
            <div className="sidebar-header">
                <div className="user-info">
                    <div className="user-icon">BK</div>
                    <span>Buse Keklik</span>
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
                            <li>Proje A</li>
                            <li>Proje B</li>
                        </ul>
                    )}
                </li>
                <li className="nav-item">
                    <button className="nav-link" onClick={() => toggleDropdown('ekipler-dropdown')}>
                        Ekipler {ekiplerDropdownOpen ? '▲' : '▼'}
                    </button>
                    {ekiplerDropdownOpen && (
                        <ul className="dropdown" id="ekipler-dropdown">
                            <li>Ekip A</li>
                            <li>Ekip B</li>
                        </ul>
                    )}
                </li>
                <li className="nav-item logout">
                    <a className="nav-link" href="#">Çıkış</a>
                </li>
            </ul>
        </div>
    );
};

export default Sidebar;
