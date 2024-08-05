import React, { useState } from 'react';
import './LoginPage.css';
import etiyaLogo from './etiyaLogo.png';

const LoginPage = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = async () => {
        console.log('Logging in with:', { email, password });

        // API endpoint URL'inizi buraya yazın
        const loginUrl = 'http://localhost:3307/authenticate';

        try {
            const response = await fetch(loginUrl, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ username: email, password: password })
            });

            const data = await response.json();
            if (response.ok) {
                console.log('Login successful:', data);
                // Burada başarılı giriş sonrası işlemler yapabilirsiniz (Örneğin: sayfa yönlendirme)
            } else {
                console.log('Login failed:', data.message);
                // Burada başarısız giriş sonrası işlemler yapabilirsiniz (Örneğin: hata mesajı gösterme)
            }
        } catch (error) {
            console.error('Login error:', error);
            // Burada ağ hataları için işlemler yapabilirsiniz
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
                </form>
            </div>
        </div>
    );
};

export default LoginPage;
