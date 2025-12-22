# TheDisciplineJournal
### This application is a springboot backend application designed to help users track daily habits, monitor streajs, and reflect on their progress through journal entries. Perfect for personal discipline, fitness, reading, coding and nutrition tracking.

### 🧱 Features

- User Management

    - Sign up, login, and authentication using JWT

    - Track streaks, longest streak, and average discipline score

- Journal Management

    - Create, read, and delete daily journal entries

    - Reflect on habits and track progress

    - Pagination for journal entries

- Habit Tracking

    - Log daily habits (workouts, reading, coding, etc.)

    - Mark habits as complete

    - Streaks and discipline scoring system (earned when all habits are completed)

    - “Strikes” system for missed habits

- Security

    -  JWT-based authentication for protected endpoints

    - Password validation with minimum 8 characters, uppercase, lowercase, number, special character, and no spaces

- Profiles & Configuration

    - Spring Profiles for dev and prod environments

    - Environment variables for secrets like JWT key and DB configuration

- Database

    - H2 in-memory database for rapid local development

## 🛠 Tech Stack

- Backend: Spring Boot 3, Spring Data JPA, Spring Security

- Database: H2 (in-memory for dev; easily switchable to PostgreSQL)

- Authentication: JWT (JSON Web Tokens)

- Build & Dependency Management: Maven

- Testing: JUnit, Mockito (if applicable)

- Containerization: Docker (for cloud deployment)