import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import styles from './formstyles.module.css';

const RegisterPage = () => {
    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.post('http://localhost:8080/auth/signup', { name: login, password: password });
            navigate('/login');
            alert("Zarejestrowano pomyślnie!");
        } catch (error) {
            console.error('Registration failed:', error.response ? error.response.data : error.message);
            alert('Błąd podczas rejestracji');
        }
    };

    return (
        <div className={styles.marginTop + ' ' + styles.cardForm}>
            <h2>Rejestracja</h2>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    placeholder="Nazwa użytkownika"
                    value={login}
                    onChange={(e) => setLogin(e.target.value)}
                    required
                />
                <input
                    type="password"
                    placeholder="Hasło"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                <button type="submit">Zarejestruj się</button>
            </form>
        </div>
    );
};

export default RegisterPage;
