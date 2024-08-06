import React, { useState } from 'react';
import './LoginPage.css';
import etiyaLogo from './etiyaLogo.png';
import axios from 'axios';
import {useNavigate} from "react-router-dom";

const LoginPage = ({ setUser }) => {
    const [formData, setFormData] = useState({
        username: '',
        password: ''
    });
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value
        });
    };
    const handleLogin = async (event) => {
        event.preventDefault();
        try {
            const response = await axios.post('http://localhost:3307/api/1.0/authenticate', formData);
            console.log('Server response:', response.data);
            if (response.status === 200 && response.data.user) {
                const { token, user } = response.data;
                console.log('User data:', user);
                localStorage.setItem('token', token);
                localStorage.setItem('user', JSON.stringify(user));
                console.log("aaaaaaaaaa");

                setUser(user);
                navigate('/dashboard');
            } else {
                setError('Login failed: No user data returned');
            }
        } catch (error) {
            setError(error.response ? error.response.data.message : error.message);
        }
    };

    return (
        <div className="login-form-wrapper">
            <div className="login-form-container">
                <img src={etiyaLogo} alt="Etiya Logo" className="logo" />
                <form onSubmit={handleLogin}>
                    <div className="input-group">
                        <label htmlFor="username">Username</label>
                        <input type="text" id="username" name="username" required onChange={handleInputChange} value={formData.username} />
                    </div>
                    <div className="input-group">
                        <label htmlFor="password">Password</label>
                        <input type="password" id="password" name="password" required onChange={handleInputChange} value={formData.password} />
                    </div>
                    <button type="submit">Login</button>
                </form>
            </div>
        </div>
    );
};

export default LoginPage;