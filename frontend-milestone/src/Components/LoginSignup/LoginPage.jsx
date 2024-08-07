import React, { useState } from 'react';
import './LoginPage.css';
import etiyaLogo from './etiyaLogo.png';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const LoginPage = ({ setUser }) => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleLogin = async () => {
        const formData = { email, password };
        try {
            const response = await axios.post('http://localhost:3307/api/1.0/authenticate', formData, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            console.log('Server response:', response);
            console.log('Raw response data:', response.data);

            let data;
            try {
                data = typeof response.data === 'string' ? JSON.parse(response.data) : response.data;
            } catch (parseError) {
                console.error('Error parsing JSON:', parseError);
                setError('Failed to parse server response: ' + parseError.message);
                return; // Stop further execution if parsing fails
            }

            if (data && data.user) {
                console.log('Extracted user:', data.user);
                localStorage.setItem('user', JSON.stringify(data.user));
                setUser(data.user);
                navigate('/dashboard');
            } else {
                throw new Error('Bu kullanıcı kayıtlı değil !');
            }
        } catch (error) {
            console.error('Login error:', error);
            setError(error.response ? error.response.data.message : 'Giriş başarısız : serverdan data alınamadı ! :/');
        }
    };

    return (
        <div className="login-form-wrapper">
            <div className="login-form-container">
                <img src={etiyaLogo} alt="Etiya Logo" className="logo" />
                <form className="login-form">
                    <input
                        type="email"
                        placeholder="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                    <input
                        type="password"
                        placeholder="Şifre"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <button type="button" onClick={handleLogin}>Giriş</button>
                    {error && <p className="error">{error}</p>}
                </form>
            </div>
        </div>
    );
};

export default LoginPage;
