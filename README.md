# Project Name: Real-Time Ticketing System

Welcome to the Real-Time Ticketing System repository!🚀 This project is designed to simulate and manage a ticketing system with real-time updates. It is divided into three main components:

- **CLI**: Command-Line Interface for managing and monitoring the system.
- **Frontend**: Angular application for user interaction.
- **Backend**: Spring Boot application for business logic and API endpoints.

## Features
- Real-time updates using WebSockets for log monitoring and ticket updates.💯
- REST API for configuration and control of the ticketing system.📲
- CLI support for monitoring and debugging.🐞
- Angular-based frontend for an intuitive user experience.👥

---

## Prerequisites
To get started, ensure you have the following installed on your system:

- **Git**: To clone the repository.
- **Node.js** and **npm**: For running the frontend.
- **Java 17+**: For running the backend.
- **Maven**: For building and running the Spring Boot application.

---

## Getting Started
Follow the steps below to clone and set up the repository:

### 1. Clone the Repository
```bash
$ git clone https://github.com/gainduamarasinghe/Real_Time_Ticketing_System.git
$ cd Real_Time_Ticketing_System
```

---

## CLI Setup and Execution
The CLI allows you to interact with the system via the terminal.

### Steps to Run:
```bash
$ cd Real_Time_Ticketing_System
$ javac Main.java
$ java Main
```
- **Note**: Ensure you have Java installed and configured.

---

## Frontend Setup and Execution
The frontend is built with Angular and provides the user interface for the ticketing system.

### Steps to Run:
1. Navigate to the `frontend` folder:
   ```bash
   $ cd frontend
   ```
2. Install dependencies:
   ```bash
   $ npm install
   ```
3. Start the Angular development server:
   ```bash
   $ ng serve
   ```
4. Open the application in your browser:
   - Navigate to [http://localhost:4200](http://localhost:4200).

---

## Backend Setup and Execution
The backend is a Spring Boot application that handles the business logic and API endpoints.

### Steps to Run:
1. Navigate to the `backend` folder:
   ```bash
   $ cd backend
   ```
2. Build the application:
   ```bash
   $ mvn clean install
   ```
3. Run the Spring Boot application:
   ```bash
   $ mvn spring-boot:run
   ```
4. Verify the backend is running:
   - Open your browser and go to [http://localhost:8080](http://localhost:8080).

---

## Notes
- The backend API will be accessible at `http://localhost:8080/api`.
- Ensure the frontend and backend are running simultaneously for full functionality.
- Test cases are available at `w2053783_Test_Cases_Documentation.pdf` 



