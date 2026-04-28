# App for Analysing Polish Traffic

A Full-Stack web application designed for analyzing and visualizing road traffic and border crossing data in Poland. The system consists of a **Spring Boot** backend and a **React** frontend, fully containerized with **Docker**.

## Table of Contents
- [About the Project](#about-the-project)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Screenshots](#screenshots)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Project Structure](#project-structure)
- [API Documentation](#api-documentation)
- [License](#license)
- [Authors](#authors)

## About the Project
This application processes statistical datasets regarding traffic volume in Poland. Data is ingested from CSV files, processed by the server, and presented to the user through interactive charts. Access to the data is secured via JWT authentication.

## Features
- **User Authentication:** Registration (`/auth/signup`) and Login (`/auth/login`) using JWT.
- **Traffic Data Analysis:** Visualization of traffic intensity based on parameters from CSV files.
- **Border Traffic Module:** Dedicated section for analyzing vehicle flow at state borders.
- **Interactive Charts:** The frontend dynamically renders data fetched from the API.
- **Docker Integration:** Ready for deployment using Docker Compose.

## Tech Stack
- **Backend:** Java 17, Spring Boot 3, Spring Security, JWT, Spring Data JPA.
- **Frontend:** React.js, Chart.js (visualization), CSS Modules.
- **Database:** MySQL 8.0 (running as a Docker container).
- **Infrastructure:** Docker, Docker Compose.

## Screenshots
<img width="1920" height="941" alt="s1" src="https://github.com/user-attachments/assets/9d8694c3-5ad3-49a6-b115-32568b672b07" />
<br>
<img width="1920" height="948" alt="s2" src="https://github.com/user-attachments/assets/b733cb6e-8274-4766-aaef-ce28326f4aee" />
<br>
<img width="1920" height="945" alt="s3" src="https://github.com/user-attachments/assets/cab85e93-fc77-43bd-9077-365ac5064bc3" />
<br>
<p align="center">
<img width="504" height="944" alt="s4" src="https://github.com/user-attachments/assets/acebad5c-bff3-4dc5-b52e-81fc1caa9c52" />
</p>
<img width="1920" height="937" alt="s5" src="https://github.com/user-attachments/assets/1a4da264-1fc6-484b-a01d-e42035877619" />

## Prerequisites
- **Docker** and **Docker Compose** installed.
- (Optional for developers): JDK 17, Maven, and Node.js.

## Installation & Setup

The easiest way to run the project is using Docker Compose:

1. **Clone the repository:**
    ```bash
   git clone https://github.com/Minlok3/app-for-analysing-Polish-traffic.git
   ```
    
2. **Go to the project folder:**
   ```bash
   cd app-for-analysing-Polish-traffic
   ```

3. **Run the containers:**
   ```bash
   docker-compose up --build
   ```

4. **Access the application:**
   - **Frontend:** [http://localhost:3000](http://localhost:3000)
   - **Backend API:** [http://localhost:8080](http://localhost:8080)

## Project Structure
- `backend` - Business logic, API, CSV parsing (Spring Boot).
- `frontend` - User interface and data visualization (React).
- `docker-compose.yaml` - Container orchestration configuration.

## API Documentation

### Authentication (`/auth`)
- `POST /auth/signup` - Create a new account.
- `POST /auth/login` - Login (returns a JWT token required for other requests).

### Data Access (`/dane`) - *Requires 'Authorization: Bearer <token>' header*
- `GET /dane/drogowe` - Retrieve road traffic statistics.
- `GET /dane/granica` - Retrieve border crossing statistics.
- 
## License
This project is open-sourced software licensed under the [MIT license](https://opensource.org/licenses/MIT).

## Authors
<a href="https://github.com/Minlok3">
  <img src="https://github.com/Minlok3.png" width="64" height="64" alt="Minlok3">
</a>
<a href="https://github.com/Eyelor">
  <img src="https://github.com/Eyelor.png" width="64" height="64" alt="Eyelor">
</a>
<a href="https://github.com/HPL21">
  <img src="https://github.com/HPL21.png" width="64" height="64" alt="HPL21">
</a>
