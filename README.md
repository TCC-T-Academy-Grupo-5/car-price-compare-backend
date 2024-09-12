<h1><a id="top"></a>API Documentation Endpoints</h1>

## Table of Content:

- [Notifications](#notifications)
- [Users](#users)
- [Vehicles](#vehicles)
- [Auth](#auth)

## Notifications

<div style="display:flex; align-items:center;">
    <h4 style="
        display: flex;
        align-items: center;
        padding: 16px 0 0 0;
    ">
        <button style="
            border: none;
            border-radius: 2px;
            color: #fff;
            font-weight: 800;
            padding: 2px 8px;
            margin-right: 12px;
            filter: brightness(110%);
            background-color: #166534;
        ">200</button> Response Example:
    </h4>
</div>
Retrieve notifications associated with a specific user.

### Path Parameters:

| Parameter | Type   | Description           |
|-----------|--------|-----------------------|
| `id`      | string | The UUID of the user. |

### Response Schema (application/json):

| Field               | Type    | Description                                                   |
|---------------------|---------|---------------------------------------------------------------|
| `id`                | string  | The UUID of the notification.                                 |
| `title`             | string  | The title of the notification message.                        |
| `priotity`          | enum    | priorities [HIGH, MEDIUM, LOW].                               |
| `body`              | string  | The text body of the notification message.                    |
| `status`            | string  | The status of the notification (e.g., SENT, PENDING, FAILED). |
| `user_id`           | string  | The ID of the associated user.                                |
| `vehicle_id`        | string  | The ID of the associated vehicle.                             |
| `user_email`        | string  | The email of the associated user.                             |
| `vehicle_brand`     | string  | The vehicle brand.                                            |
| `vehicle_model`     | string  | The vehicle model.                                            |
| `vehicle_year`      | integer | The year of the vehicle.                                      |
| `timestamp`         | string  | When the notification was created.                            |
| `current_min_price` | number  | The current minimum price for the vehicle.                    |
| `fipe_price`        | number  | The current FIPE price for the vehicle.                       |

### Response Codes:

<div style="display:flex; align-items:center;">
    <h4 style="
        display: flex;
        align-items: center;
        padding: 16px 0 0 0;
    ">
        <button style="
            border: none;
            border-radius: 2px;
            color: #fff;
            font-weight: 800;
            padding: 2px 8px;
            margin-right: 12px;
            filter: brightness(110%);
            background-color: #166534;
        ">200</button> Response Example:
    </h4>
</div>

```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "title": "Price Drop Notification",
    "priority": "HIGH",
    "body": "The price of your selected vehicle has dropped.",
    "status": "sent",
    "user_id": "user1",
    "vehicle_id": "vehicle1",
    "user_email": "user1@email.com",
    "vehicle_brand": "Toyota",
    "vehicle_model": "Corolla",
    "vehicle_year": 2021,
    "timestamp": "2024-09-04T14:30:00Z",
    "current_min_price": 45000,
    "fipe_price": 50000
  },
  {
    "id": "550e8400-e29b-41d4-a716-446655440001",
    "title": "Price Drop Notification",
    "priority": "MEDIUM",
    "body": "The price of your selected vehicle has dropped.",
    "status": "sent",
    "user_id": "user2",
    "vehicle_id": "vehicle2",
    "user_email": "user2@email.com",
    "vehicle_brand": "Fiat",
    "vehicle_model": "Toro",
    "vehicle_year": 2024,
    "timestamp": "2024-09-04T15:00:00Z",
    "current_min_price": 98000,
    "fipe_price": 102000
  }
]

```

<h4 class="flex subtitle" style="display:flex; align-items:center; padding:16px 0 0 0;">
    <button class="error" style="border:none; border-radius:2px; color:#fff; font-weight:800; padding:2px 8px; margin-right:12px; filter:brightness(110%); background-color:#991b1b;">
        404
    </button>
    Response Example:
</h4>


```json
{
  "error": "User not found",
  "message": "Check the ID and try again"
}
```

<h4 class="flex subtitle" style="display:flex; align-items:center; padding:16px 0 0 0;">
    <button class="error" style="border:none; border-radius:2px; color:#fff; font-weight:800; padding:2px 8px; margin-right:12px; filter:brightness(110%); background-color:#991b1b;">
        500    </button>
    Response Example:
</h4>


```json
{
  "error": "Internal server error",
  "message": "An error has occurred. Please try again"
}
```

---

<h3 style="background-color: #11171A; color: #fff; border-radius: 4px; padding: 20px;"> 
    <button style="background-color:#854d0e; color: #fff; padding: 2px 8px; border-radius: 2px;">POST</button> <span>/user/{user_id}/notifications</span>
</h3>
Create a new notification associated with a user.

### Request Body (application/json):

| Field        | Type   | Required | Description                                                 |
|--------------|--------|----------|-------------------------------------------------------------|
| `title`      | string | FALSE    | The title of the notification message.                      |
| `priority`   | string | TRUE     | The priority of the notification (e.g., HIGH, MEDIUM, LOW). |
| `body`       | string | TRUE     | The text body of the notification message.                  |
| `vehicle_id` | string | TRUE     | Vehicle ID to get data from the vehicle.                    |

### Response Codes:

### Request Example:

```json
{
  "title": "Price Drop Alert",
  "priority": "HIGH",
  "body": "The price of the Toyota Corolla has dropped significantly.",
  "vehicle_id": "123456789"
}

```

<div style="display:flex; align-items:center;">
    <h4 style="
        display: flex;
        align-items: center;
        padding: 16px 0 0 0;
    ">
        <button style="
            border: none;
            border-radius: 2px;
            color: #fff;
            font-weight: 800;
            padding: 2px 8px;
            margin-right: 12px;
            filter: brightness(110%);
            background-color: #166534;
        ">201</button> Response Example:
    </h4>
</div>

```json
{
  "message": "Notification created"
}
```

<h4 class="flex subtitle" style="display:flex; align-items:center; padding:16px 0 0 0;">
    <button class="error" style="border:none; border-radius:2px; color:#fff; font-weight:800; padding:2px 8px; margin-right:12px; filter:brightness(110%); background-color:#991b1b;">
        404
    </button>
    Response Example:
</h4>


```json
{
  "error": "Not Found",
  "message": "User ID not be found."
}
```

<h4 class="flex subtitle" style="display:flex; align-items:center; padding:16px 0 0 0;">
    <button class="error" style="border:none; border-radius:2px; color:#fff; font-weight:800; padding:2px 8px; margin-right:12px; filter:brightness(110%); background-color:#991b1b;">400</button> Response Example:</h4>

```json
{
  "error": "Bad Request",
  "message": "The request body is missing required fields or contains invalid data."
}
```

<h4 class="flex subtitle" style="display:flex; align-items:center; padding:16px 0 0 0;">
    <button class="error" style="border:none; border-radius:2px; color:#fff; font-weight:800; padding:2px 8px; margin-right:12px; filter:brightness(110%); background-color:#991b1b;">
        500    </button>
    Response Example:
</h4>


```json
{
  "error": "Internal Server Error",
  "message": "An unexpected error occurred while processing the request."
}
```

---

<h3 style="background-color: #11171A; color: #fff; border-radius: 4px; padding: 20px;"> 
    <button style="background-color:#991b1b; color: #fff; padding: 2px 8px; border-radius: 2px;">delete</button>
    <span>/user/{id}/notifications/{notification_id}</span>
</h3>
Delete a specific notification associated with a user.

### Path Parameters:

| Parameter         | Type   | Description                   |
|-------------------|--------|-------------------------------|
| `id`              | string | The UUID of the user.         |
| `notification_id` | string | The UUID of the notification. |

### Response Codes:

<h4 class="flex subtitle" style="display:flex; align-items:center; padding:16px 0 0 0;">
    <button class="error" style="border:none; border-radius:2px; color:#fff; font-weight:800; padding:2px 8px; margin-right:12px; filter:brightness(110%); background-color:#166534;">204</button> Response Example:</h4>

```json
{
  "message": "Notification deleted"
}
```

<h4 class="flex subtitle" style="display:flex; align-items:center; padding:16px 0 0 0;">
    <button class="error" style="border:none; border-radius:2px; color:#fff; font-weight:800; padding:2px 8px; margin-right:12px; filter:brightness(110%); background-color:#991b1b;">
        404
    </button>
    Response Example:
</h4>


```json
{
  "error": "Not Found",
  "message": "User or notification ID not found."
}
```

<h4 class="flex subtitle" style="display:flex; align-items:center; padding:16px 0 0 0;">
    <button class="error" style="border:none; border-radius:2px; color:#fff; font-weight:800; padding:2px 8px; margin-right:12px; filter:brightness(110%); background-color:#991b1b;">
        500    </button>
    Response Example:
</h4>


```json
{
  "error": "Internal Server Error",
  "message": "An unexpected error occurred while processing the request."
}
```

---

## Users

<h3 style="background-color: #11171A; color: #fff; border-radius: 4px; padding: 20px;"> 
    <button style="background-color:#166534; color: #fff; padding: 2px 8px; border-radius: 2px;">GET</button> <span>/user</span>
</h3>

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

<div style="display:flex; align-items:center;">
    <h4 style="
        display: flex;
        align-items: center;
        padding: 16px 0 0 0;
    ">
        <button style="
            border: none;
            border-radius: 2px;
            color: #fff;
            font-weight: 800;
            padding: 2px 8px;
            margin-right: 12px;
            filter: brightness(110%);
            background-color: #166534;
        ">200</button> Response Example:
    </h4>
</div>

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

<h4 class="flex subtitle" style="display:flex; align-items:center; padding:16px 0 0 0;">
    <button class="error" style="border:none; border-radius:2px; color:#fff; font-weight:800; padding:2px 8px; margin-right:12px; filter:brightness(110%); background-color:#991b1b;">
        401
    </button>
    Response Example:
</h4>


```json
{
  "error": "user don't have privileges",
  "message": "only admins can access this feature"
}
```

<h4 class="flex subtitle" style="display:flex; align-items:center; padding:16px 0 0 0;">
    <button class="error" style="border:none; border-radius:2px; color:#fff; font-weight:800; padding:2px 8px; margin-right:12px; filter:brightness(110%); background-color:#991b1b;">
        404
    </button>
    Response Example:
</h4>


```json
{
  "error": "Not Found",
  "message": "User not found."
}
```

<h4 class="flex subtitle" style="display:flex; align-items:center; padding:16px 0 0 0;">
    <button class="error" style="border:none; border-radius:2px; color:#fff; font-weight:800; padding:2px 8px; margin-right:12px; filter:brightness(110%); background-color:#991b1b;">
        500    </button>
    Response Example:
</h4>


```json
{
  "error": "Internal Server Error",
  "message": "An unexpected error occurred while processing the request."
}
```

<h3 style="background-color: #11171A; color: #fff; border-radius: 4px; padding: 20px;"> 
    <button style="background-color:#166534; color: #fff; padding: 2px 8px; border-radius: 2px;">GET</button> 
    <span>/user/{id}/favorites</span>
</h3>

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

<div style="display:flex; align-items:center;">
    <h4 style="
        display: flex;
        align-items: center;
        padding: 16px 0 0 0;
    ">
        <button style="
            border: none;
            border-radius: 2px;
            color: #fff;
            font-weight: 800;
            padding: 2px 8px;
            margin-right: 12px;
            filter: brightness(110%);
            background-color: #166534;
        ">200</button> Response Example:
    </h4>
</div>

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
<h4 class="flex subtitle" style="display:flex; align-items:center; padding:16px 0 0 0;">
    <button class="error" style="border:none; border-radius:2px; color:#fff; font-weight:800; padding:2px 8px; margin-right:12px; filter:brightness(110%); background-color:#991b1b;">
        401
    </button>
    Response Example:
</h4>


```json
{
  "error": "user don't have privileges",
  "message": "only admins can access this feature"
}
```

<h4 class="flex subtitle" style="display:flex; align-items:center; padding:16px 0 0 0;">
    <button class="error" style="border:none; border-radius:2px; color:#fff; font-weight:800; padding:2px 8px; margin-right:12px; filter:brightness(110%); background-color:#991b1b;">
        404
    </button>
    Response Example:
</h4>


```json
{
  "error": "Not Found",
  "message": "User or favorites not found."
}
```

<h4 class="flex subtitle" style="display:flex; align-items:center; padding:16px 0 0 0;">
    <button class="error" style="border:none; border-radius:2px; color:#fff; font-weight:800; padding:2px 8px; margin-right:12px; filter:brightness(110%); background-color:#991b1b;">
        500    </button>
    Response Example:
</h4>


```json
{
  "error": "Internal Server Error",
  "message": "An unexpected error occurred while processing the request."
}
```

---

## Vehicles

<h3 style="background-color: #11171A; color: #fff; border-radius: 4px; padding: 20px;"> 
    <button style="background-color:#166534; color: #fff; padding: 2px 8px; border-radius: 2px;">GET</button> 
    <span>/vehicle</span>
</h3>

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

<div style="display:flex; align-items:center;">
    <h4 style="
        display: flex;
        align-items: center;
        padding: 16px 0 0 0;
    ">
        <button style="
            border: none;
            border-radius: 2px;
            color: #fff;
            font-weight: 800;
            padding: 2px 8px;
            margin-right: 12px;
            filter: brightness(110%);
            background-color: #166534;
        ">200</button> Response Example:
    </h4>
</div>

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

<h4 class="flex subtitle" style="display:flex; align-items:center; padding:16px 0 0 0;">
    <button class="error" style="border:none; border-radius:2px; color:#fff; font-weight:800; padding:2px 8px; margin-right:12px; filter:brightness(110%); background-color:#991b1b;">
        404    
    </button>
    Response Example:
</h4>


```json
{
  "error": "Not Found",
  "message": "Vehicle not found."
}

```

<h4 class="flex subtitle" style="display:flex; align-items:center; padding:16px 0 0 0;">
    <button class="error" style="border:none; border-radius:2px; color:#fff; font-weight:800; padding:2px 8px; margin-right:12px; filter:brightness(110%); background-color:#991b1b;">
        500    
    </button>
    Response Example:
</h4>


```json
{
  "error": "Internal Server Error",
  "message": "An unexpected error occurred while processing the request."
}
```

<h3 style="background-color: #11171A; color: #fff; border-radius: 4px; padding: 20px;"> 
    <button style="background-color:#166534; color: #fff; padding: 2px 8px; border-radius: 2px;">GET</button>
    <span>/vehicles/{vehicle_id}</span>
</h3>

Retrieve detailed information about a specific vehicle.

### Path Parameters:

| Parameter    | Type   | Description              |
|--------------|--------|--------------------------|
| `vehicle_id` | string | The UUID of the vehicle. |

### Response Codes:

<h4 class="flex subtitle" style="display:flex; align-items:center; padding:16px 0 0 0;">
    <button style="border:none; border-radius:2px; color:#fff; font-weight:800; padding:2px 8px; margin-right:12px; filter:brightness(110%); background-color:#166534;">
        200
    </button>
    Response Example:
</h4>


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

<h4 class="flex subtitle" style="display:flex; align-items:center; padding:16px 0 0 0;">
    <button class="error" style="border:none; border-radius:2px; color:#fff; font-weight:800; padding:2px 8px; margin-right:12px; filter:brightness(110%); background-color:#991b1b;">
        404
    </button>
    Response Example:
</h4>


```json
{
  "error": "Not Found",
  "message": "Vehicle not found."
}

```

<h4 class="flex subtitle" style="display:flex; align-items:center; padding:16px 0 0 0;">
    <button class="error" style="border:none; border-radius:2px; color:#fff; font-weight:800; padding:2px 8px; margin-right:12px; filter:brightness(110%); background-color:#991b1b;">
        500    </button>
    Response Example:
</h4>


```json
{
  "error": "Internal Server Error",
  "message": "An unexpected error occurred while processing the request."
}
```

<h3 style="background-color: #11171AFF; color: #fff; border-radius: 4px; padding: 20px; width: max-content; height: 30px; display: flex; align-items: center;">
    <button style="border:none; border-radius:2px; color:#fff; font-weight:800; padding:2px 8px; margin-right:12px; filter:brightness(110%); background-color:#854d0e;">
        POST
    </button>
    <span>/vehicle</span>
</h3>

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


<div style="display:flex; align-items:center;">
    <h4 style="
        display: flex;
        align-items: center;
        padding: 16px 0 0 0;
    ">
        <button style="
            border: none;
            border-radius: 2px;
            color: #fff;
            font-weight: 800;
            padding: 2px 8px;
            margin-right: 12px;
            filter: brightness(110%);
            background-color: #166534;
        ">201</button> Response Example:
    </h4>
</div>

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

<h4 class="flex subtitle" style="display:flex; align-items:center; padding:16px 0 0 0;">
    <button class="error" style="border:none; border-radius:2px; color:#fff; font-weight:800; padding:2px 8px; margin-right:12px; filter:brightness(110%); background-color:#991b1b;">
        500    </button>
    Response Example:
</h4>


```json
{
  "error": "Internal Server Error",
  "message": "An unexpected error occurred while processing the request."
}
```

---

## Auth

<h3 style="background-color: #11171A; color: #fff; border-radius: 4px; padding: 20px;"> 
    <button style="background-color:#854d0e; color: #fff; padding: 2px 8px; border-radius: 2px;">POST</button> <span>/auth/register</span>
</h3>

#### Request Body (application/json):

| Field       | Type   | Required | Description                  |
|-------------|--------|----------|------------------------------|
| `name`      | string | TRUE     | The first name of the user   |
| `last_name` | string | FALSE    | The last name of the user    |
| `email`     | string | TRUE     | The email of the user        |
| `password`  | string | TRUE     | The password for the account |
| `cellphone` | string | TRUE     | The cellphone of the user    |

### Response Codes:

<h4 class="flex subtitle" style="display:flex; align-items:center; padding:16px 0 0 0;">
    <button class="error" style="border:none; border-radius:2px; color:#fff; font-weight:800; padding:2px 8px; margin-right:12px; filter:brightness(110%); background-color:#166534;">201</button> Success Response:</h4>

```json
{
  "name": "John Doe Green",
  "email": "johndoe@email.com"
}
```

<h4 class="flex subtitle" style="display:flex; align-items:center; padding:16px 0 0 0;">
    <button class="error" style="border:none; border-radius:2px; color:#fff; font-weight:800; padding:2px 8px; margin-right:12px; filter:brightness(110%); background-color:#991b1b;">409</button> Conflict Response:</h4>

```json
{
  "error": "Conflict",
  "message": "The email address 'johndoe@email.com' already exists."
}
```
[Go to Top](#top)
