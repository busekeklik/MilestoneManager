import React from 'react';
import './PopupModal.css';

const PopupModal = ({ isOpen, onClose }) => {
    if (!isOpen) return null;

    return (
        <div className="popup-backdrop">
            <div className="popup-container">
                <button className="close-button" onClick={onClose}>×</button>
                <h2>Task 3 yarın başlıyor!</h2>
                <button onClick={onClose} className="popup-button">
                    Task'a git
                </button>
            </div>
        </div>
    );
};

export default PopupModal;
