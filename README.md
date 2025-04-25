# MinIO Spring Boot File Storage

This project demonstrates the integration of **MinIO** (an open-source object storage service) with a **Spring Boot** application to handle file uploads and downloads. The application provides APIs for uploading and retrieving files from the MinIO object storage system, allowing easy storage management in a highly scalable, cloud-native environment.

## Key Features:
- **File Upload**: Secure file upload to MinIO storage.
- **File Download**: Retrieve stored files with a simple GET request.
- **MinIO Integration**: Seamless integration with MinIO as the object storage backend.
- **Dockerized**: The entire Spring Boot application and MinIO are dockerized for easy deployment.
- **Docker Compose**: Uses Docker Compose to manage both the Spring Boot app and MinIO services together in a local or production environment.

## Technologies Used:
- **Spring Boot**: For building the RESTful web services.
- **MinIO**: Object storage service for storing files.
- **Docker**: To containerize both Spring Boot and MinIO services.
- **Docker Compose**: To orchestrate multiple containers (Spring Boot app + MinIO).
- **Java 17**: For the backend logic.

## API Documentation:

### 1. **POST /api/minio/upload**
Upload a file to the MinIO object storage.

- **Request**: 
  - Method: `POST`
  - Endpoint: `/api/minio/upload`
  - Content-Type: `multipart/form-data`
  - Form data:
    - **file**: The file to upload.

- **Response**:
  - **200 OK**: "File successfully uploaded: {filename}"
  - **500 Internal Server Error**: "Upload failed: {error message}"

### 2. **GET /api/minio/download/{filename}**
Download a file from MinIO storage.

- **Request**:
  - Method: `GET`
  - Endpoint: `/api/minio/download/{filename}`

- **Response**:
  - **200 OK**: Returns the file for download.
  - **404 Not Found**: "File not found."

## How to Run:

1. Clone the repository:
   git clone https://github.com/yourusername/repository-name.git
Dockerized Version:

The application and MinIO are configured to run using Docker Compose, so you don't need to install or configure MinIO manually.

To start both services, use the following command:

docker-compose up --build
The Spring Boot application will be available at http://localhost:8080.

The MinIO UI will be available at http://localhost:9001 (default credentials: minioadmin / minioadmin).

Without Docker: If you prefer not to use Docker, you can manually run the Spring Boot application by building the project with Maven:

Build the Spring Boot app:

mvn clean install
Run the application:

mvn spring-boot:run
Access the Spring Boot application at http://localhost:8080.

Set up MinIO locally and configure it with the same endpoint, access key, and secret key as in the application.

Installation and Setup:
Dockerized Version: Easy setup and deployment with Docker Compose. No additional setup required for MinIO or Spring Boot.

MinIO Access: MinIO is accessible on http://localhost:9000 (default).

Spring Boot: Runs on port 8080 by default.

License:
This project is licensed under the MIT License - see the LICENSE file for details.

Acknowledgments:
MinIO - Open-source object storage service.

Spring Boot - Framework for building Java-based applications.

Docker - Platform for developing, shipping, and running applications.

