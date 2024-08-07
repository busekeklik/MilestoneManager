import React, { useState } from 'react';
import './LoginPage.css';
import etiyaLogo from './etiyaLogo.png';
import axios from 'axios';
import { useNavigate } from "react-router-dom";

const LoginPage = ({ setUser }) => {
    const [formData, setFormData] = useState({
        email: '',
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

            // Extract user data from the response
            const user = response.data && response.data.user ? response.data.user : null;
            console.log('Extracted user:', user);

            if (response.status === 200 && user) {
                localStorage.setItem('user', JSON.stringify(user));
                setUser(user);
                console.log('Navigating to dashboard with user:', user);
                navigate('/dashboard');
            } else {
                console.error('No user data returned in response:', response.data);
                setError('Login failed: No user data returned');
            }
        } catch (error) {
            console.error('Login error:', error);
            setError(error.response ? error.response.data.message : error.message);
        }
    };

    return (
        <div className="login-form-wrapper">
            <div className="login-form-container">
                <img src={etiyaLogo} alt="Etiya Logo" className="logo" />
                <form onSubmit={handleLogin}>
                    <div className="input-group">
                        <label htmlFor="email">Email</label>
                        <input type="email" id="email" name="email" required onChange={handleInputChange} value={formData.email} />
                    </div>
                    <div className="input-group">
                        <label htmlFor="password">Password</label>
                        <input type="password" id="password" name="password" required onChange={handleInputChange} value={formData.password} />
                    </div>
                    <button type="submit">Login</button>
                    {error && <p className="error">{error}</p>}
                </form>
            </div>
        </div>
    );
};

export default LoginPage;
