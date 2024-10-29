# 🚙 Precificar API 🛻

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)

**Version:** v0.0.1  
**Description:** REST API for managing and retrieving information about vehicles, FIPE prices, online deals, and user preferences. The API supports user authentication, notifications, and favorites management.

### Base Server - Swagger Documentation
```http request
GET http://localhost:8080
```
- you will be redirected to `http://localhost:8080/swagger-ui/swagger-ui/index.html`
---

### **Brands**

<details>
<summary>Endpoints for managing vehicle brands.</summary>

### 1. List Brands
- **GET** `/brand` - List brands based on the specified filter
    - **Query Parameters**:
        - `pageNumber` (optional, default: `1`): The page number for pagination.
        - `pageSize` (optional, default: `10`): The number of brands to return per page.
        - `name` (optional): Filter brands by name.
        - `vehicleType` (optional): Filter brands by vehicle type (use the corresponding integer).
        - `brandType` (optional, default: `ALL`): Specify the type of brands to filter (`ALL`, `POPULAR`, or `NOT_POPULAR`).
- **POST** `/brand` - Add a new brand
- **GET** `/brand/{brandId}` - Get brand by ID
- **GET** `/brand/popular` - List popular brands

</details>

### **Vehicles**

<details>
<summary>Endpoints for managing vehicles.</summary>

- **GET** `/vehicle` - List all vehicles
- **POST** `/vehicle` - Add a new vehicle
- **GET** `/vehicle/model` - List all models
- **POST** `/vehicle/model` - Add a new model
- **GET** `/vehicle/brand` - List all vehicle brands
- **POST** `/vehicle/brand` - Add a new vehicle brand
- **GET** `/vehicle/{id}` - Get vehicle by ID
- **GET** `/vehicle/{id}/deals` - Get store prices for a vehicle

</details>

### **Authentication**

<details>
<summary>Endpoints for user authentication and profile management.</summary>

- **POST** `/auth/token` - Validate JWT token
- **POST** `/auth/register` - Register a new user
- **POST** `/auth/login` - Authenticate user and generate token
- **GET** `/auth/profile` - Get user profile from token

</details>

### **Ratings**

<details>
<summary>Endpoints for managing vehicle ratings.</summary>

- **PUT** `/rating/{id}` - Edit rating
- **DELETE** `/rating/{id}` - Delete rating
- **GET** `/rating` - List all ratings
- **POST** `/rating` - Add a rating

</details>

### **Notifications**

<details>
<summary>Endpoints for managing user notifications.</summary>

- **GET** `/user/notifications/{notificationId}` - Get notification by ID
- **PUT** `/user/notifications/{notificationId}` - Update notification
- **DELETE** `/user/notifications/{notificationId}` - Delete notification
- **GET** `/user/notifications` - List all notifications
- **POST** `/user/notifications` - Add a notification
- **GET** `/user/notifications/existsPendingByVehicleId/{vehicleId}` - Check for pending notifications by vehicle ID

</details>

### **Favorites**

<details>
<summary>Endpoints for managing user favorites.</summary>

- **GET** `/user/favorites/{favoriteId}` - Get favorite by ID
- **PUT** `/user/favorites/{favoriteId}` - Update favorite
- **DELETE** `/user/favorites/{favoriteId}` - Delete favorite
- **GET** `/user/favorites` - List all favorites
- **POST** `/user/favorites` - Add a favorite
- **GET** `/user/favorites/vehicle/{id}` - Get favorite by vehicle ID

</details>

### **Price**

<details>
<summary>Endpoints for managing vehicle prices.</summary>

- **POST** `/price/store` - Add store price
- **GET** `/price/vehicle/{vehicleId}` - Get vehicle price
- **GET** `/price/store/{id}` - Get store price

</details>

### **Statistics**

<details>
<summary>Endpoints for vehicle statistics.</summary>

- **GET** `/statistic/year` - Get year statistics
- **GET** `/statistic/vehicle` - Get vehicle statistics
- **GET** `/statistic/model` - Get model statistics
- **GET** `/statistic/brand` - Get brand statistics

</details>

### **User Management**

<details>
<summary>User management endpoints.</summary>

- **PUT** `/user/{id}` - Update user information
- **DELETE** `/user/{id}` - Delete user

</details>

---

## **Schemas**

Main data transfer objects (DTOs) used in the API:

- **UserDTO**
- **GrantedAuthority**
- **User**
- **NotificationRequestDTO**
- **NotificationResponseDTO**
- **VehicleDTO**
- **FavoriteRequestDTO**
- **FavoriteResponseDTO**
- **RatingDTO**
- **Rating**
- **AddVehicleDTO**
- **FipePrice**
- **VehicleResponseDTO**
- **AddModelDTO**
- **Brand**
- **Model**
- **Vehicle**
- **Year**
- **AddBrandDTO**
- **StorePriceDTO**
- **RegisterDTO**
- **RegisterResponse**
- **AuthenticationDTO**
- **LoginResponse**
- **ModelDTO**
- **BrandDTO**
- **PageFavoriteResponseDTO**
- **PageableObject**
- **SortObject**
- **Favorite**
- **StatisticResponseDto**
- **StatisticVehicleResponse**
- **PageRating**
- **FipePriceDTO**
- **UserInformationDTO**

---

For more details on each endpoint and its parameters, refer to the Swagger documentation:  
`http://localhost:8080/v3/api-docs`



