import React, { useState } from 'react';
import './LoginPage.css';
import etiyaLogo from './etiyaLogo.png';

const LoginPage = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = () => {


        console.log('Logging in with:', { email, password });
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
                </form>
            </div>
        </div>
    );
};

export default LoginPage;
