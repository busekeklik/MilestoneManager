import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom'; // Import useNavigate
import './PopupModal.css';

const PopupModal = ({ isOpen, onClose }) => {
    const [alert, setAlert] = useState(null); // State to hold the first alert
    const navigate = useNavigate(); // Initialize the useNavigate hook

    useEffect(() => {
        if (isOpen) {
            const fetchAlert = async () => {
                try {
                    const response = await fetch('http://localhost:3307/alert/api/v1/list');
                    const data = await response.json();

                    if (data.length > 0) {
                        const firstAlert = data[0];
                        if (firstAlert.taskID) {
                            const taskResponse = await fetch(`http://localhost:3307/task/api/v1/find/${firstAlert.taskID}`);
                            const taskData = await taskResponse.json();
                            firstAlert.taskName = taskData.taskName;
                        } else {
                            firstAlert.taskName = "No related task";
                        }
                        setAlert(firstAlert);
                    }
                } catch (error) {
                    console.error('Error fetching alert:', error);
                }
            };

            fetchAlert();
        }
    }, [isOpen]);

    const handleGoToTask = () => {
        onClose();  // Close the modal before navigating
        navigate('/projects'); // Navigate to the tasks page
    };

    if (!isOpen || !alert) return null;

    return (
        <div className="popup-backdrop">
            <div className="popup-container">
                <button className="close-button" onClick={onClose}>×</button>
                <h2>{alert.message}</h2>
                <p>{new Date(alert.alertDate).toLocaleString()}</p>
                <p><strong>İlişkili olduğu Task:</strong> {alert.taskName}</p>
                <button onClick={handleGoToTask} className="popup-button">
                    Task'a git
                </button>
            </div>
        </div>
    );
};

export default PopupModal;
