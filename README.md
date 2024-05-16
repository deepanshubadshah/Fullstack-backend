# Fullstack-Backend
This Spring Boot application provides API services for all the operations required for the Mailing application.

For in-memory storage, we are using H2.

## Getting started (steps)
This repo contains a Dockerfile to run the application  

### To get the frontend running locally:
 * Clone this repo
 * Open a terminal in the project directory and run the following command to build the image using: `docker build -t fullstack-backend .`
 * After the image is built, start the container with: `docker run -p 8080:8080 fullstack-backend`
 * Awesome! you app is up and running at `http://localhost:8080`
 * Now start the frontend server (React application): https://github.com/deepanshubadshah/Fullstack-frontend
 
