# 🚙 Car Price Compare API 🛻

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)

<h2><a id="top"></a>API Documentation Endpoints</h1>

## Table of Content:

- [Notifications](#notifications)
- [Favorites](#favorites)
- [Users](#users)
- [Vehicles](#vehicles)
- [Auth](#auth)

## Notifications

```http request
GET /user/notifications
```

Retrieve notifications associated with the current user.

### Query Parameters:

| Parameter    | Type   | Description                                   |
|--------------|--------|-----------------------------------------------|
| `pageNumber` | number | The number of the page, which starts at 0     |
| `pageSize`   | number | The number of elements by page                |
| `status`     | number | Notification status. 0: pending, 1: delivered |

### Response Schema (application/json):

| Field                | Type    | Description                                                          |
|----------------------|---------|----------------------------------------------------------------------|
| `notificationId`     | string  | The UUID of the notification.                                        |
| `notificationType`   | string  | The type of notification (FIPE_PRICE_DROP, STORE_PRICE_BELLOW_FIPE). |
| `notificationStatus` | string  | The status of the notification (e.g. PENDING, DELIVERED).            |
| `currentFipePrice`   | number  | The vehicle FIPE price at the time of the notification creation.     |
| `vehicle`            | string  | Information about the vehicle associated with the notification.      |

### Response Codes:

- **200 OK**: Success

```json
{
  "content": [
    {
      "notificationId": "db1ae449-fefe-47c4-9c53-efea1151ee48",
      "notificationType": "FIPE_PRICE_DROP",
      "notificationStatus": "PENDING",
      "currentFipePrice": 24000.0,
      "vehicle": {
        "vehicleId": "00616163-a29f-44eb-8176-752048421175",
        "fipeCode": "516051-0",
        "year": "1993",
        "model": "NL-12 400 6x2 2p (diesel)",
        "brand": "Volvo",
        "vehicleType": "TRUCK"
      }
    },
    {
      "notificationId": "0abc534b-b38b-4ec7-b38a-23ae5d771c0b",
      "notificationType": "FIPE_PRICE_DROP",
      "notificationStatus": "PENDING",
      "currentFipePrice": 75000.0,
      "vehicle": {
        "vehicleId": "7e295497-7779-4091-a7be-77d7189419c9",
        "fipeCode": "516173-8",
        "year": "2020",
        "model": "VM 270 4x2 2p (diesel) (E5)",
        "brand": "Volvo",
        "vehicleType": "TRUCK"
      }
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "last": true,
  "totalElements": 2,
  "totalPages": 1,
  "size": 10,
  "number": 0,
  "sort": {
    "empty": true,
    "sorted": false,
    "unsorted": true
  },
  "numberOfElements": 2,
  "first": true,
  "empty": false
}

```
- **500 Internal Server Error**: An error occurred on the server

```json
{
  "error": "Internal Server Error",
  "message": "An unexpected error occurred while processing the request."
}
```

---

```http request
GET /user/notifications/{notificationId}
```

Returns a specific notification associated with the current user

### Path parameters:

| Field            | Type   | Description                                                 |
|------------------|--------|-------------------------------------------------------------|
| `notificationId` | string | UUID of the notification.                                   |

### Response Codes:

- **200 OK**: Success
```json
{
    "notificationId": "4ecb82e1-d48e-4a85-9327-a39c8d534cf4",
    "notificationType": "FIPE_PRICE_DROP",
    "notificationStatus": "DELIVERED",
    "currentFipePrice": 75000.0,
    "vehicle": {
        "vehicleId": "3eb97b2f-00e3-445d-bc29-f72ebd276850",
        "fipeCode": "004259-5",
        "year": "2008 Gasolina",
        "model": "Astra Sed.Eleg.2.0 MPFI FlexPower 8V 4p",
        "brand": "Chevrolet",
        "vehicleType": "CAR"
    }
}
```

- **201 Created**: Resource created successfully

```json
{
  "message": "Notification created"
}
```

- **404 Not Found**: Resource not found

```json
{
  "error": "notification id 4ecb82e1-d48e-4a85-9327-a39c8d534cf3 not found"
}
```
- **500 Internal Server Error**: An error occurred on the server

```json
{
  "error": "Internal Server Error",
  "message": "An unexpected error occurred while processing the request."
}
```
---

```http request
POST /user/notifications
```
Create a notification associated with the current user.

### Request body (application/json):

| Field            | Type   | Required | Description                                   |
|------------------|--------|----------|-----------------------------------------------|
| notificationType | number | TRUE     | 0: Fipe price drop, 1: Store price below Fipe |
| currentFipePrice | number | TRUE     | The current fipe price for the vehicle        |
| vehicleId        | string | TRUE     | The Id of the vehicle                         |

### Response Codes:

### Request Example:
```json
{
  "notificationType": 0,
  "currentFipePrice": 30000.0,
  "vehicleId": "1d329d10-3500-4644-9cc9-e4b00bddf016"
}
```
- **201 Created**: Resource created successfully
```json
{
  "notificationId": "86b62e0a-4bba-4fe4-a74b-cfe809bb4a41",
  "notificationType": "FIPE_PRICE_DROP",
  "notificationStatus": "PENDING",
  "currentFipePrice": 30000.0,
  "vehicle": {
    "vehicleId": "1d329d10-3500-4644-9cc9-e4b00bddf016",
    "fipeCode": "008178-7",
    "name": "A6 3.0 TFSI V6 Quattro S tronic 4p",
    "year": "2018 Gasolina",
    "model": "A6",
    "brand": "Audi",
    "vehicleType": "CAR",
    "modelCategory": "LARGE_SEDAN"
  }
}
```
- **404 Not Found**: Resource not found
```json
{
    "error": "Vehicle id 934f716e-88fa-428d-aa29-a0f0ee861eb7 not found"
}
```

- **400 Bad Request**: The request could ot be understood or was missing required parameters
```json
{
  "vehicleId": "vehicleId is required"
}
```

- **500 Internal Server Error**: An error occurred on the server
```json
{
  "error": "Internal Server Error",
  "message": "An unexpected error occurred while processing the request."
}
```
---
```http request
PUT /user/notifications/{notificationId}
```
Update a notification associated with the current user.

### Path parameter

| Field            | Type   | Description                                                 |
|------------------|--------|-------------------------------------------------------------|
| `notificationId` | string | UUID of the notification.                                   |

### Request body (application/json):

| Field            | Type   | Required | Description                                   |
|------------------|--------|----------|-----------------------------------------------|
| notificationType | number | TRUE     | 0: Fipe price drop, 1: Store price below Fipe |
| currentFipePrice | number | TRUE     | The current fipe price for the vehicle        |
| vehicleId        | string | TRUE     | The Id of the vehicle                         |

### Response Codes:

### Request Example:
```json
{
  "notificationType": 0,
  "currentFipePrice": 30000.0,
  "vehicleId": "1d329d10-3500-4644-9cc9-e4b00bddf016"
}
```
- **200 OK**: Resource updated successfully
```json
{
  "notificationId": "86b62e0a-4bba-4fe4-a74b-cfe809bb4a41",
  "notificationType": "FIPE_PRICE_DROP",
  "notificationStatus": "PENDING",
  "currentFipePrice": 30000.0,
  "vehicle": {
    "vehicleId": "1d329d10-3500-4644-9cc9-e4b00bddf016",
    "fipeCode": "008178-7",
    "name": "A6 3.0 TFSI V6 Quattro S tronic 4p",
    "year": "2018 Gasolina",
    "model": "A6",
    "brand": "Audi",
    "vehicleType": "CAR",
    "modelCategory": "LARGE_SEDAN"
  }
}

```
- **404 Not Found**: Resource not found
```json
{
    "error": "Vehicle id 934f716e-88fa-428d-aa29-a0f0ee861eb7 not found"
}
```

- **400 Bad Request**: The request could ot be understood or was missing required parameters
```json
{
  "vehicleId": "vehicleId is required"
}
```

- **500 Internal Server Error**: An error occurred on the server
```json
{
  "error": "Internal Server Error",
  "message": "An unexpected error occurred while processing the request."
}
```

---

```http request
DELETE /user/notifications/{notificationId}
```

Delete a specific notification associated with the current user.

### Path Parameters:

| Parameter        | Type   | Description                   |
|------------------|--------|-------------------------------|
| `notificationId` | string | The UUID of the notification. |

### Response Codes:

- **204 No Content**: The request was successful, but there is no content to send in the response.

- **404 Not Found**: Resource not found

```json
{
  "error": "notification id f0b26fd2-4e23-4c1c-99b9-1455ff53c382 not found"
}
```

- **500 Internal Server Error**: An error occurred on the server

```json
{
  "error": "Internal Server Error",
  "message": "An unexpected error occurred while processing the request."
}
```
---

## Favorites

```http request
GET /user/favorites
```
Returns a list of the current user's favorite vehicles

### Query Parameters:

| Parameter   | Type   | Description                                         |
|-------------|--------|-----------------------------------------------------|
| pageNumber  | number | The number of the page, which starts at 0           |
| pageSize    | number | The number of elements by page                      |
| vehicleType | number | Type of Vehicle. 0: cars, 1: motorcycles, 2: trucks |

### Response Codes:

- **200 OK**: Success

```json
{
    "content": [
        {
            "favoriteId": "f0b26fd2-4e23-4c1c-99b9-1455ff53c383",
            "vehicle": {
                "vehicleId": "934f716e-88fa-428d-aa29-a0f0ee861eb8",
                "fipeCode": "516041-3",
                "year": "1989",
                "model": "NL-10 340 4x2 2p (diesel)",
                "brand": "Volvo",
                "vehicleType": "TRUCK"
            }
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 10,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalElements": 1,
    "totalPages": 1,
    "size": 10,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "first": true,
    "numberOfElements": 1,
    "empty": false
}
```
---

```http request
GET /user/favorites/{favoriteId}
```
Returns a specific vehicle in the current user's favorites according to given ID.

### Response codes

- **200 OK**: Success

```json
{
    "favoriteId": "f0b26fd2-4e23-4c1c-99b9-1455ff53c383",
    "vehicle": {
        "vehicleId": "934f716e-88fa-428d-aa29-a0f0ee861eb8",
        "fipeCode": "516041-3",
        "year": "1989",
        "model": "NL-10 340 4x2 2p (diesel)",
        "brand": "Volvo",
        "vehicleType": "TRUCK"
    }
}
```

- **404 Not Found**: Resource not found

```json
{
    "error": "Could not find favorite with id f0b26fd2-4e23-4c1c-99b9-1455ff53c382"
}
```

---

```http request
POST /user/favorites
```

Add a vehicle to the current user's favorites. If the vehicle is already registered as a favorite for the user, the response will be **409 Conflict**

### Request body (application/json):

| Field       | Type   | Required | Description                                               |
|-------------|--------|----------|-----------------------------------------------------------|
| `vehicleId` | string | TRUE     | The ID of the vehicle to be added to the user's favorites |

### Response Codes:

### Request Example:

```json
{
    "vehicleId": "934f716e-88fa-428d-aa29-a0f0ee861eb8"
}
```

- **201 Created**: Resource created successfully

```json
{
  "favoriteId": "558ac18b-3818-41a8-84d6-68bb4a94cf62",
  "vehicle": {
    "vehicleId": "934f716e-88fa-428d-aa29-a0f0ee861eb8",
    "fipeCode": "516041-3",
    "year": "1989",
    "model": "NL-10 340 4x2 2p (diesel)",
    "brand": "Volvo",
    "vehicleType": "TRUCK"
  }
}
```

- **404 Not Found**: Resource not found

```json
{
    "error": "Vehicle id 934f716e-88fa-428d-aa29-a0f0ee861eb7 not found"
}
```

- **400 Bad Request**: The request could ot be understood or was missing required parameters

```json
{
  "vehicleId": "vehicleId is required"
}
```

- **409 Conflict**: The request conflicts with the current state of the server.

```json
{
  "error": "Operation could not be completed due to a data integrity violation"
}
```

- **500 Internal Server Error**: An error occurred on the server

```json
{
  "error": "Internal Server Error",
  "message": "An unexpected error occurred while processing the request."
}
```
---
```http request
PUT /user/favorites/{favoriteId}
```
### Request body (application/json):

| Field     | Type   | Required | Description                                          |
|-----------|--------|----------|------------------------------------------------------|
| vehicleId | string | TRUE     | The ID of the vehicle to be replace the old favorite |

### Response Codes:

### Request example
```http request
PUT /user/favorites/f0b26fd2-4e23-4c1c-99b9-1455ff53c383
```
```json
{
    "vehicleId": "d0c8b862-16f4-4540-bee1-9324232622df"
}
```
- **200 OK**: Success

```json
{
    "favoriteId": "f0b26fd2-4e23-4c1c-99b9-1455ff53c383",
    "vehicle": {
        "vehicleId": "d0c8b862-16f4-4540-bee1-9324232622df",
        "fipeCode": "024011-7",
        "year": "1996 Gasolina",
        "model": "405 SRi 1.8",
        "brand": "Peugeot",
        "vehicleType": "CAR"
    }
}
```
- **404 Not Found**: resource not found

```json
{
  "error": "favorite id f0b26fd2-4e23-4c1c-99b9-1455ff53c382 not found"
}
```

- **400 Bad Request**: The request could ot be understood or was missing required parameters

```json
{
  "vehicleId": "vehicleId is required"
}
```

- **409 Conflict**: The request conflicts with the current state of the server.

```json
{
  "error": "Operation could not be completed due to a data integrity violation"
}
```

- **500 Internal Server Error**: An error occurred on the server

```json
{
  "error": "Internal Server Error",
  "message": "An unexpected error occurred while processing the request."
}
```
---
```http request
DELETE /user/favorites/{favoriteId}
```
Remove a vehicle from the user's favorites

### Response codes:

- **204 No Content**: Request was processed correctly, but there is no content to send

- **404 Not Found**: resource not found

```json
{
  "error": "favorite id f0b26fd2-4e23-4c1c-99b9-1455ff53c382 not found"
}
```
- **500 Internal Server Error**: An error occurred on the server

```json
{
  "error": "Internal Server Error",
  "message": "An unexpected error occurred while processing the request."
}
```
---

## Users

```http request
GET /user 
```

Retrieve user information based on query parameters.

### Query Parameters:

| Parameter | Type   | Description                                                                  |
|-----------|--------|------------------------------------------------------------------------------|
| `name`    | string | Get user by name or last name. Examples: `name=john`, `name=j`, `name=doe`   |
| `email`   | string | Get user by email. Examples: `email=johndoe@email.com`, `email=oe@email.com` |

### Response Schema (application/json):

| Field       | Type   | Description              |
|-------------|--------|--------------------------|
| `name`      | string | First name of the user.  |
| `last_name` | string | Last name of the user.   |
| `email`     | string | Email of the user.       |
| `cellphone` | string | User's cellphone number. |

### Response Codes:

- **200 OK**: Success

```json
[
  {
    "name": "John",
    "last_name": "Doe",
    "email": "johndoe@email.com",
    "cellphone": "819999999"
  },
  {
    "name": "Joana",
    "last_name": "Green",
    "email": "joanagreen@email.com",
    "cellphone": "819999919"
  },
  {
    "name": "Joao",
    "last_name": "Silva",
    "email": "joaozinho@email.com",
    "cellphone": "879995454"
  }
]
```

- **401 Unauthorized**: Authentication failed or user does not have permissions for the requested operation.

```json
{
  "error": "user don't have privileges",
  "message": "only admins can access this feature"
}
```

- **404 Not Found**: Resource not found

```json
{
  "error": "Not Found",
  "message": "User not found."
}
```

- **500 Internal Server Error**: An error occurred on the server

```json
{
  "error": "Internal Server Error",
  "message": "An unexpected error occurred while processing the request."
}
```

```http request
GET /user/{id}/favorites
```

Retrieve a user's list of favorite vehicles.

### Path Parameters:

| Parameter | Type   | Description                                |
|-----------|--------|--------------------------------------------|
| `id`      | string | The UUID of the user. Example: `91491949`. |

### Query Parameters:

| Parameter | Type   | Description                                              |
|-----------|--------|----------------------------------------------------------|
| `order`   | string | Query ordered by `asc` or `desc`. Example: `order=asc`.  |
| `limit`   | string | Limits results by a specific value. Example: `limit=20`. |

### Responses:

- **200 OK**: Success

```json
[
  {
    "id": "123e4567-e89b-12d3-a456-426614174000",
    "model": "Corolla",
    "brand": "Toyota",
    "maxSpeed": 220,
    "fuel": "Gasoline",
    "tankCapacity": 55,
    "color": "Red",
    "year": 2022,
    "currentFipePrice": 90000,
    "type": "car"
  },
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "model": "Civic",
    "brand": "Honda",
    "maxSpeed": 180,
    "fuel": "Gasoline",
    "tankCapacity": 50,
    "color": "Blue",
    "year": 2021,
    "currentFipePrice": 120000,
    "type": "car"
  },
  {
    "id": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
    "model": "Altima",
    "brand": "Nissan",
    "maxSpeed": 200,
    "fuel": "Gasoline",
    "tankCapacity": 60,
    "color": "Black",
    "year": 2023,
    "currentFipePrice": 250000,
    "type": "car"
  }
]
```

]

- **401 Unauthorized**: Authentication failed or user does not have permissions for the requested operation.

```json
{
  "error": "user don't have privileges",
  "message": "only admins can access this feature"
}
```

- **404 Not Found**: Resource not found

```json
{
  "error": "Not Found",
  "message": "User or favorites not found."
}
```

- **500 Internal Server Error**: An error occurred on the server

```json
{
  "error": "Internal Server Error",
  "message": "An unexpected error occurred while processing the request."
}
```

---

## Vehicles

```http request
GET /vehicle 
```

Retrieve a list of vehicles based on various filters.

### Query Parameters:

| Parameter         | Type    | Description                                                                         |
|-------------------|---------|-------------------------------------------------------------------------------------|
| `brand`           | string  | Filters vehicles by their manufacturer or brand.                                    |
| `model`           | string  | Filters vehicles by their model name.                                               |
| `after_year`      | integer | Filter vehicles released after the specified year.                                  |
| `before_year`     | integer | Filters vehicles released before the specified year.                                |
| `min_horse_power` | integer | Filters vehicles with engine output greater than or equal to specified horse power. |
| `max_horse_power` | integer | Filters vehicles with engine output lower than or equal to specified horse power.   |
| `min_speed`       | integer | Filters vehicles with maximum speed greater than or equal to specified speed.       |
| `max_speed`       | integer | Filters vehicles with maximum speed lower than or equal to specified speed.         |
| `min_price`       | double  | Filters vehicles with price greater than or equal to the specified price.           |
| `max_price`       | double  | Filters vehicles with price lower than or equal to the specified price.             |

### Response Codes:

- **200 OK**: Success

```json
[
  {
    "id": "123e4567-e89b-12d3-a456-426614174000",
    "model": "Corolla",
    "brand": "Toyota",
    "max_speed": 220,
    "fuel": "Gasoline",
    "tank_capacity": 55,
    "color": "Red",
    "year": 2022,
    "fipe_price": 90000,
    "type": "car"
  },
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "model": "Civic",
    "brand": "Honda",
    "max_speed": 180,
    "fuel": "Gasoline",
    "tank_capacity": 50,
    "color": "Blue",
    "year": 2021,
    "fipe_price": 120000,
    "type": "car"
  }
]
```

- **404 Not Found**: Resource not found

```json
{
  "error": "Not Found",
  "message": "Vehicle not found."
}

```

- **500 Internal Server Error**: An error occurred on the server

```json
{
  "error": "Internal Server Error",
  "message": "An unexpected error occurred while processing the request."
}
```

```http request
GET /vehicles/{vehicle_id}</span>
```

Retrieve detailed information about a specific vehicle.

### Path Parameters:

| Parameter    | Type   | Description              |
|--------------|--------|--------------------------|
| `vehicle_id` | string | The UUID of the vehicle. |

### Response Codes:

- **200 OK**: Success

```json
{
  "id": "123e4567-e89b-12d3-a456-426614174000",
  "model": "Corolla",
  "brand": "Toyota",
  "max_speed": 220,
  "fuel": "Gasoline",
  "tank_capacity": 55,
  "color": "Red",
  "year": 2022,
  "fipe_price": 90000,
  "type": "car"
}
```

- **404 Not Found**: Resource not found

```json
{
  "error": "Not Found",
  "message": "Vehicle not found."
}

```

- **500 Internal Server Error**: An error occurred on the server

```json
{
  "error": "Internal Server Error",
  "message": "An unexpected error occurred while processing the request."
}
```

```http request
POST /vehicle
```

Create a new vehicle.

### Request Body:

| Field           | Type    | Description                                                 |
|-----------------|---------|-------------------------------------------------------------|
| `model`         | string  | The model of the vehicle.                                   |
| `brand`         | string  | The manufacturer or brand of the vehicle.                   |
| `max_speed`     | integer | The maximum speed of the vehicle in km/h.                   |
| `fuel`          | string  | The type of fuel the vehicle uses (e.g., Gasoline, Diesel). |
| `tank_capacity` | integer | The fuel tank capacity of the vehicle in liters.            |
| `color`         | string  | The color of the vehicle.                                   |
| `year`          | integer | The year the vehicle was manufactured.                      |
| `fipe_price`    | number  | The current FIPE price of the vehicle.                      |
| `type`          | string  | The type of the vehicle (e.g., car, truck, motorcycle).     |

### Response Codes:

- **201 Created**: Resource created successfully

```json
{
  "id": "123e4567-e89b-12d3-a456-426614174000",
  "model": "Corolla",
  "brand": "Toyota",
  "max_speed": 220,
  "fuel": "Gasoline",
  "tank_capacity": 55,
  "color": "Red",
  "year": 2022,
  "current_fipe_price": 90000,
  "type": "car"
}
```

- **500 Internal Server Error**: An error occurred on the server

```json
{
  "error": "Internal Server Error",
  "message": "An unexpected error occurred while processing the request."
}
```

---

## Auth

```http request
POST /auth/register
```

#### Request Body (application/json):

| Field       | Type   | Required | Description                  |
|-------------|--------|----------|------------------------------|
| `name`      | string | TRUE     | The first name of the user   |
| `last_name` | string | FALSE    | The last name of the user    |
| `email`     | string | TRUE     | The email of the user        |
| `password`  | string | TRUE     | The password for the account |
| `cellphone` | string | TRUE     | The cellphone of the user    |

### Response Codes:

- **201 Created**: Resource created successfully

```json
{
  "name": "John Doe Green",
  "email": "johndoe@email.com"
}
```

- **409 Conflict**

```json
{
  "error": "Conflict",
  "message": "The email address 'johndoe@email.com' already exists."
}
```

[Go to Top](#top)



