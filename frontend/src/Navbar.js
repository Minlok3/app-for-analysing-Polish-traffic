import React from 'react';
import { Link } from 'react-router-dom';
import styles from "./navstyles.css";

const Navbar = ({ currentUser, onLogout }) => {
    return (
        <div className={styles.divNav}>
            <nav>
                <div></div>
                {currentUser ? (
                    <div className={styles.navRight}>
                        <span>Witaj, {currentUser.sub}!</span>
                        <button onClick={onLogout}>Wyloguj</button>
                    </div>
                ) : (
                    <div className={styles.navRight}>
                        <Link to="/login"><button>Zaloguj się</button></Link>
                        <Link to="/register"><button>Zarejestruj się</button></Link>
                    </div>
                )}
            </nav>
        </div>
        
    );
};

export default Navbar;