import './App.css';
import React, { useEffect, useState } from 'react';
import DataChart from './DataChart';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import LoginForm from './LoginForm';
import RegisterForm from './RegisterForm';
import Navbar from './Navbar'

const App = () => {

    const [currentUser, setCurrentUser] = useState(null);

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (token) {
            const user = JSON.parse(atob(token.split('.')[1]));
            setCurrentUser(user);
        }
    }, []);

    const handleLogout = () => {
        localStorage.removeItem('token');
        setCurrentUser(null);
        alert('Wylogowano pomyślnie!');
        window.location.reload();
    };

    return (
        <Router>
            <Navbar currentUser={currentUser} onLogout={handleLogout} />
            <Routes>
                <Route path="/" element={ currentUser ? <DataChart currentUser={currentUser} /> : <div className='mt10 ml5vw'><h1>Zaloguj się aby korzystać z aplikacji ↗</h1></div>} />
                <Route path="/login" element={<LoginForm />} />
                <Route path="/register" element={<RegisterForm />} />
            </Routes>
        </Router>
    );
};

export default App;
