import React, { useState, useEffect } from 'react';
import Select from 'react-select';
import './UserPopup.css';

const UserPopup = ({ isOpen, onClose, userToEdit, onSave, onDelete }) => {
    const [userID, setUserID] = useState(null);
    const [userName, setUserName] = useState('');
    const [password, setPassword] = useState('');
    const [email, setEmail] = useState('');
    const [roles, setRoles] = useState([]);
    const [isActive, setIsActive] = useState(true);

    useEffect(() => {
        if (userToEdit) {
            setUserID(userToEdit.userID);
            setUserName(userToEdit.userName);
            setEmail(userToEdit.email);
            setPassword('');
            setRoles(userToEdit.roles.map(role => ({ value: role, label: role })));
            setIsActive(userToEdit.active);
        } else {
            clearForm();
        }
    }, [userToEdit]);

    const clearForm = () => {
        setUserID(null);
        setUserName('');
        setPassword('');
        setEmail('');
        setRoles([]);
        setIsActive(true);
    };

    const handleSave = () => {
        const user = {
            userID,
            userName,
            password,
            email,
            roles: roles.map(role => role.value),
            active: isActive,
            team_id: 1
        };
        onSave(user);
    };

    const roleOptions = [
        { value: 'TEAM_LEADER', label: 'Team Leader' },
        { value: 'BACKEND', label: 'Backend Developer' },
        { value: 'FRONTEND', label: 'Frontend Developer' },
        { value: 'DEVOPS', label: 'DevOps' },
    ];

    if (!isOpen) return null;

    return (
        <div className="modal-overlay">
            <div className="popup-container">
                <div className="popup-form-header">
                    <h1>{userID ? 'Edit User' : 'Add New User'}</h1>
                </div>
                <form className="popup-form">
                    <div className="form-group">
                        <label>Name</label>
                        <input
                            type="text"
                            value={userName}
                            onChange={(e) => setUserName(e.target.value)}
                        />
                    </div>
                    <div className="form-group">
                        <label>Password</label>
                        <input
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                    </div>
                    <div className="form-group">
                        <label>Email</label>
                        <input
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                        />
                    </div>
                    <div className="form-group">
                        <label>Roles</label>
                        <Select
                            isMulti
                            options={roleOptions}
                            value={roles}
                            onChange={setRoles}
                            styles={{
                                control: (base) => ({
                                    ...base,
                                    borderRadius: '10px',
                                    borderColor: '#ccc',
                                }),
                            }}
                        />
                    </div>
                    <div className="form-group">
                        <label>
                            <input
                                type="checkbox"
                                checked={isActive}
                                onChange={(e) => setIsActive(e.target.checked)}
                            />
                            Active
                        </label>
                    </div>
                    <div className="popup-buttons">
                        <button onClick={handleSave}>Save</button>
                        {userID && <button onClick={() => onDelete(userID)}>Delete</button>}
                        <button onClick={onClose}>Cancel</button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default UserPopup;
