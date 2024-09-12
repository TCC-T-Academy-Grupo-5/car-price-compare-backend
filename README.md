
# API Documentation Endpoints

## Tabela de Conteúdos:
- [1. GET /user/{id}/notifications](#1-get-useridnotifications)
- [2. POST /user/{id}/notifications](#2-post-useridnotifications)
- [3. DELETE /user/{id}/notifications/{notificationId}](#3-delete-useridnotificationsnotificationid)
- [4. GET /user](#4-get-user)
- [5. GET /user/{id}](#5-get-userid)
- [6. GET/POST /user/{id}/favorites](#6-getpost-useridfavorites)
- [7. GET /vehicles](#7-get-vehicles)
- [8. GET /vehicles/{vehicleId}](#8-get-vehiclesvehicleid)
- [9. POST /auth/register](#9-post-authregister)
- [10. POST /auth/login](#10-post-authlogin)

## 1. **GET** /user/{id}/notifications

Retrieve notifications associated with a specific user.

### Path Parameters:
| Parameter | Type | Description |
|-----------|------|-------------|
| `id`      | string | The UUID of the user. |

### Response Schema (application/json):
| Field              | Type    | Description                                                |
|--------------------|---------|------------------------------------------------------------|
| `notificationId`   | string  | The UUID of the notification.                              |
| `userEmail`        | string  | The email of the associated user.                          |
| `model`            | string  | The vehicle model.                                         |
| `brand`            | string  | The vehicle brand.                                         |
| `year`             | integer | The year of the vehicle.                                   |
| `timestamp`        | string  | When the notification was created.                         |
| `status`           | string  | The status of the notification.                            |
| `currentMinPrice`  | integer | The current minimum price for the vehicle.                 |
| `currentFipePrice` | integer | The current FIPE price for the vehicle.                    |

### Response Codes:
- **200** - OK
- **404** - Not Found
- **500** - Internal Server Error

### Response Example:
```json
[
  {
    "notificationId": "550e8400-e29b-41d4-a716-446655440000",
    "userEmail": "user1@email.com",
    "model": "Corolla",
    "brand": "Toyota",
    "year": 2021,
    "currentPrice": 45000,
    "fipePrice": 50000,
    "timestamp": "2024-09-04T14:30:00Z",
    "status": "sent"
  }
]
```

---

## 2. **POST** /user/{id}/notifications

Create a new notification associated with a user.

### Path Parameters:
| Parameter | Type | Description |
|-----------|------|-------------|
| `id`      | string | The UUID of the user. |

### Request Body (application/json):
| Field  | Type    | Description            |
|--------|---------|------------------------|
| `model`| string  | The vehicle model.     |
| `brand`| string  | The vehicle brand.     |
| `year` | integer | The year of the vehicle.|

### Response Codes:
- **201** - Created
- **404** - Not Found
- **500** - Internal Server Error

### Request Example:
```json
{
  "model": "Corolla",
  "brand": "Toyota",
  "year": 2021
}
```

### Response Example:
```json
{
  "message": "Notification created successfully."
}
```

---

## 3. **DELETE** /user/{id}/notifications/{notificationId}

Delete a notification for a user.

### Path Parameters:
| Parameter         | Type   | Description                              |
|-------------------|--------|------------------------------------------|
| `id`              | string | The UUID of the user.                    |
| `notificationId`  | string | The UUID of the notification to delete.  |

### Response Codes:
- **200** - OK
- **404** - Not Found
- **500** - Internal Server Error

### Response Example:
```json
{
  "message": "Notification deleted successfully."
}
```

---

## 4. **GET** /user

Retrieve a list of users by name or email.

### Query Parameters:
| Parameter | Type   | Description                            |
|-----------|--------|----------------------------------------|
| `name`    | string | Search users by first or last name.    |
| `email`   | string | Search users by email.                 |

### Response Schema (application/json):
| Field      | Type   | Description         |
|------------|--------|---------------------|
| `name`     | string | The user's first name.|
| `last_name`| string | The user's last name. |
| `email`    | string | The user's email.     |
| `cellphone`| string | The user's cellphone. |

### Response Example:
```json
[
  {
    "name": "John doe",
    "last_name": "Green",
    "email": "johndoe@email.com",
    "cellphone": "819999999"
  }
]
```

---

## 5. **GET** /user/{id}

Retrieve details for a specific user.

### Path Parameters:
| Parameter | Type   | Description          |
|-----------|--------|----------------------|
| `id`      | string | The UUID of the user. |

### Response Example:
```json
{
  "name": "John doe",
  "last_name": "Green",
  "email": "johndoe@email.com",
  "cellphone": "819999999"
}
```

---

## 6. **GET/POST** /user/{id}/favorites

Manage user's favorite vehicles.

### Query Parameters (GET):
| Parameter | Type   | Description                        |
|-----------|--------|------------------------------------|
| `order`   | string | Order results (asc/desc).          |
| `limit`   | string | Limit the number of results.       |

### Response Example (POST):
```json
{
  "message": "Vehicle added to favorites"
}
```

---

## 7. **GET** /vehicles

Retrieve a list of vehicles filtered by various parameters.

### Query Parameters:
| Parameter     | Type     | Description                                        |
|---------------|----------|----------------------------------------------------|
| `brand`       | string   | Filters vehicles by their manufacturer or brand.   |
| `model`       | string   | Filters vehicles by their model name.              |
| `afterYear`   | integer  | Filters vehicles released after the specified year.|
| `beforeYear`  | integer  | Filters vehicles released before the specified year.|
| `minHorsePower`| integer | Filters vehicles with engine output >= the specified horse power.|
| `maxHorsePower`| integer | Filters vehicles with engine output <= the specified horse power.|
| `minPrice`    | number   | Filters vehicles with price >= the specified price.|
| `maxPrice`    | number   | Filters vehicles with price <= the specified price.|

---

## 8. **GET** /vehicles/{vehicleId}

Retrieve details for a specific vehicle by ID.

### Path Parameters:
| Parameter   | Type   | Description              |
|-------------|--------|--------------------------|
| `vehicleId` | string | The UUID of the vehicle.  |

---

## 9. **POST** /auth/register

Register a new user.

### Request Example:
```json
{
  "username": "new_user",
  "password": "password123"
}
```

---

## 10. **POST** /auth/login

Login with user credentials.

### Request Example:
```json
{
  "username": "new_user",
  "password": "password123"
}
```
