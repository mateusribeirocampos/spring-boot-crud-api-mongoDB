# Spring Boot CRUD API with MongoDB

A comprehensive RESTful API built with Spring Boot and MongoDB, featuring advanced search capabilities, CRUD operations, and robust date handling utilities.

## 🚀 Features

- **Complete CRUD Operations** for Users and Posts
- **Advanced Search Functionality** with multiple criteria
- **MongoDB Integration** with custom queries
- **Flexible Date Validation** supporting multiple formats
- **RESTful API Design** with proper HTTP status codes
- **Data Transfer Objects (DTOs)** for clean API responses
- **Custom Exception Handling** with proper error responses
- **Auto-initialization** with sample data for testing

## 🛠️ Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 21 | Programming Language |
| **Spring Boot** | 3.5.3 | Framework |
| **Spring Data MongoDB** | 3.5.3 | Database Integration |
| **Spring Web** | 3.5.3 | REST API |
| **Maven** | - | Build Tool |
| **MongoDB** | - | NoSQL Database |

## 📋 Prerequisites

- **Java 21** or higher
- **Maven 3.6+**
- **MongoDB** running on `localhost:27017`
- IDE (IntelliJ IDEA, Eclipse, VS Code)

## ⚡ Quick Start

### 1. Clone the Repository
```bash
git clone <repository-url>
cd spring-boot-crud-api-mongodb
```

### 2. Configure MongoDB
Ensure MongoDB is running on `localhost:27017`. The application will connect to database `mongo_social`.

### 3. Run the Application
```bash
# Using Maven
mvn spring-boot:run

# Or using Maven Wrapper
./mvnw spring-boot:run
```

### 4. Access the API
The application starts on `http://localhost:8080`

## 📚 API Documentation

### 👥 User Endpoints

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| `GET` | `/users` | Get all users | - |
| `GET` | `/users/{id}` | Get user by ID | - |
| `POST` | `/users` | Create new user | `UserCreateDTO` |
| `PUT` | `/users/{id}` | Update user | `UserCreateDTO` |
| `DELETE` | `/users/{id}` | Delete user | - |
| `GET` | `/users/{id}/posts` | Get user's posts | - |
| `GET` | `/users/namesearch?text={name}` | Search users by name | - |
| `GET` | `/users/emailsearch?text={email}` | Search users by email | - |
| `GET` | `/users/nameoremailsearch?text={term}` | Search by name or email | - |

### 📝 Post Endpoints

| Method | Endpoint | Description | Parameters |
|--------|----------|-------------|------------|
| `GET` | `/posts/{id}` | Get post by ID | - |
| `GET` | `/posts/titlesearch?text={title}` | Search posts by title | `text`: search term |
| `GET` | `/posts/complexsearch` | Advanced search | `keyword`, `startDate`, `endDate`, `authorname` |
| `GET` | `/posts/criteriasearch` | Flexible date search | `keyword`, `mindate`, `maxdate` |

### 📊 Request/Response Examples

#### Create User
```bash
POST /users
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "password": "123456"
}
```

#### Complex Post Search
```bash
GET /posts/complexsearch?keyword=viagem&startDate=20/03/2018&endDate=25/03/2018&authorname=Maria
```

#### Criteria Search with Date Range
```bash
GET /posts/criteriasearch?keyword=feliz&mindate=01/01/2018&maxdate=31/12/2018
```

## 🏗️ Project Structure

```
src/main/java/com/campos/sbmongoDb/
├── SpringBootCrudApiMongodbApplication.java  # Main class
├── config/
│   └── Instantiation.java                    # Data initialization
├── domain/
│   ├── User.java                            # User entity
│   └── Post.java                            # Post entity
├── dto/
│   ├── UserDTO.java                         # User data transfer object
│   ├── UserCreateDTO.java                   # User creation DTO
│   ├── AuthorDTO.java                       # Author data transfer
│   └── CommentDTO.java                      # Comment data transfer
├── repository/
│   ├── UserRepository.java                  # User database operations
│   └── PostRepository.java                  # Post database operations
├── resources/
│   ├── UserResource.java                    # User REST controller
│   ├── PostResource.java                    # Post REST controller
│   ├── exception/                           # Exception handling
│   └── util/
│       └── URL.java                         # URL utility functions
├── services/
│   ├── UserService.java                     # User business logic
│   ├── PostService.java                     # Post business logic
│   └── exception/
│       └── ObjectNotFoundException.java     # Custom exception
└── util/
    └── ModernDateValidator.java             # Date validation utility
```

## 🔍 Key Features Explained

### Advanced MongoDB Queries
The application implements sophisticated MongoDB queries using `@Query` annotations:

**Complex Search** (`PostRepository:19-28`):
```java
@Query("{ $and: [" 
    + "{ $or: [" 
    + "{ 'title': { $regex: ?0, $options: 'i'} },"
    + "{ 'body': { $regex: ?0, $options: 'i'} }" 
    + "] }, " 
    + "{ 'date': { $gte: ?1, $lte: ?2 } },"
    + "{ 'author.name': { $regex: ?3, $options: 'i'} },"
    + "{ 'comments': { $exists: true, $not: { $size: 0 } } }" 
    + "] }")
```

### Flexible Date Validation
The `ModernDateValidator` supports multiple date formats:
- `dd/MM/yyyy`
- `dd/MM/yyyy HH:mm:ss`
- `dd-MM-yyyy`
- `dd-MM-yyyy HH:mm:ss`
- `yyyy-MM-dd`

### Data Relationships
- **Users** have a lazy-loaded list of **Posts** (`@DBRef(lazy = true)`)
- **Posts** contain embedded **Comments** and **Author** information
- Proper MongoDB document relationships with referential integrity

### Exception Handling
Global exception handling with `ResourceExceptionHandler` providing consistent error responses across all endpoints.

## 🗄️ Database Schema

### Users Collection
```json
{
  "_id": "ObjectId",
  "name": "String",
  "email": "String",
  "password": "String",
  "posts": ["Post ObjectId references"]
}
```

### Posts Collection
```json
{
  "_id": "ObjectId",
  "date": "Date",
  "title": "String",
  "body": "String",
  "author": {
    "id": "String",
    "name": "String"
  },
  "comments": [
    {
      "text": "String",
      "date": "Date",
      "author": {
        "id": "String",
        "name": "String"
      }
    }
  ]
}
```

## 🧪 Testing

### Run Tests
```bash
mvn test
```

### Sample Data
The application automatically creates sample data on startup:
- 3 test users (Maria, Alex, Bob)
- 2 test posts with comments
- Demonstrates all entity relationships

## 🔧 Configuration

### Application Properties
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/mongo_social
```

### Environment Variables
You can override the MongoDB connection using:
```bash
export SPRING_DATA_MONGODB_URI=mongodb://your-mongodb-server:27017/your-database
```

## 📦 Build & Deployment

### Maven Commands
```bash
# Compile
mvn clean compile

# Run tests
mvn test

# Package
mvn clean package

# Run
mvn spring-boot:run
```

### Docker Support (Suggested Enhancement)
Consider adding Docker support for containerized deployment:

```dockerfile
FROM openjdk:21-jdk-slim
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```

## 🚀 Deployment Suggestions

### Production Considerations
1. **Remove Debug Logs**: Remove `System.out.println` statements in `PostResource`
2. **Security**: Implement proper authentication and authorization
3. **Validation**: Add comprehensive input validation
4. **Monitoring**: Add application monitoring and health checks
5. **Documentation**: Consider adding Swagger/OpenAPI documentation

### Render Deployment
This application is ready for deployment on Render platform:
1. Connect your GitHub repository
2. Set build command: `mvn clean package`
3. Set start command: `java -jar target/*.jar`
4. Configure MongoDB connection string

## 🤝 Contributing

This is a study project. Contributions, issues, and feature requests are welcome!

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📧 Contact

**Mateus Campos**

Project Link: [https://github.com/mateusribeirocampos/spring-boot-crud-api-mongodb](https://github.com/mateusribeirocampos/spring-boot-crud-api-mongodb)

---

⭐ **Star this repository if it helped you learn Spring Boot with MongoDB!**
