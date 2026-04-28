import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import styles from './formstyles.module.css';

const LoginPage = () => {
    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/auth/login', { name: login, password: password });
            localStorage.setItem('token', response.data.token);
            console.log(response);
            navigate('/');
            alert("Zalogowano pomyślnie!");
            window.location.reload();
        } catch (error) {
            alert('Błąd podczas logowania - błędny login lub hasło');
        }
    };

    return (
        <div className={styles.marginTop + ' ' + styles.cardForm}>
            <h2>Logowanie</h2>
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
                <button type="submit">Zaloguj się</button>
            </form>
        </div>
    );
};

export default LoginPage;
