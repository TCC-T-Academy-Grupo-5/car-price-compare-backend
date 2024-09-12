# Notifications

## user/{id}/notifications

Get notifications associated with user

##### path Parameters

| idrequired | stringExamples:"550e8400-e29b-41d4-a716-446655440000" -The ID of the user |
|------------|---------------------------------------------------------------------------|

### Responses

##### Response Schema: application/json

| notificationId   | stringThe UUID of the notification                       |
|------------------|----------------------------------------------------------|
| userEmail        | stringThe email of the associated user                   |
| model            | stringThe vehicle model                                  |
| brand            | stringThe vehicle brand                                  |
| year             | integer <int32>The year of the vehicle                   |
| timestamp        | string <date-time>When the notification was created      |
| status           | stringThe status of the notification                     |
| currentMinPrice  | integer <int32>The current minimum price for the vehicle |
| currentFipePrice | integer <int32>The current FIPE price for the vehicle    |

##### Response Schema: application/json

| error   | stringThe error description  |
|---------|------------------------------|
| message | stringAdditional information |

##### Response Schema: application/json

| error   | stringThe error description  |
|---------|------------------------------|
| message | stringAdditional information |

get/user/{id}/notifications

### Response samples

- 200
- 404
- 500

Content type

application/json

Expand allCollapse all

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
  },
  {
    "notificationId": "550e8400-e29b-41d4-a716-446655440001",
    "userEmail": "user2@email.com",
    "model": "Civic",
    "brand": "Honda",
    "year": 2020,
    "currentPrice": 38000,
    "fipePrice": 42000,
    "timestamp": "2024-09-04T15:00:00Z",
    "status": "sent"
  },
  {
    "notificationId": "550e8400-e29b-41d4-a716-446655440002",
    "userEmail": "user3@email.com",
    "model": "Focus",
    "brand": "Ford",
    "year": 2022,
    "currentPrice": 32000,
    "fipePrice": 35000,
    "timestamp": "2024-09-04T15:30:00Z",
    "status": "sent"
  }
]
```

## user/{id}/notifications

Associate a user with a new notification for a vehicle price

##### path Parameters

| idrequired | stringExamples:"550e8400-e29b-41d4-a716-446655440000" -The ID of the user |
|------------|---------------------------------------------------------------------------|

##### Request Body schema: application/json

Vehicle information for new notification

| model | stringThe model of the vehicle         |
|-------|----------------------------------------|
| brand | stringThe brand of the vehicle         |
| year  | integer <int32>The year of the vehicle |

### Responses

##### Response Schema: application/json

| message | string |
|---------|--------|

##### Response Schema: application/json

| error   | stringThe error description  |
|---------|------------------------------|
| message | stringAdditional information |

##### Response Schema: application/json

| error   | stringThe error description  |
|---------|------------------------------|
| message | stringAdditional information |

post/user/{id}/notifications

### Request samples

- Payload

Content type

application/json

```bash
{
  "model": "Corolla",
  "brand": "Toyota",
  "year": 2021
}
```

### Response samples

- 201
- 404
- 500

Content type

application/json

```bash
{
  "message": "Notification created successfully."
}
```

## /user/{id}/notifications/{notificationId}

Delete a notification

##### path Parameters

| idrequired             | anyThe Id of the user         |
|------------------------|-------------------------------|
| notificationIdrequired | anyThe Id of the notification |

### Responses

delete/user/{id}/notifications/{notificationId}

### Response samples

- 200
- 404
- 500

Content type

application/json

```bash
{
  "message": "Notification deleted successfully"
}
```

# user

## /user

##### query Parameters

| name  | stringExamples:name=john -name=j -name=doe -get user by name or lastname    |
|-------|-----------------------------------------------------------------------------|
| email | stringExamples:email=johndoe@email.com -email=@email.com -get user by email |

### Responses

[//]: # (TODO!!!!!!!!!!!)

# user

## /user

##### query Parameters

| name  | stringExamples:name=john -name=j -name=doe -get user by name or lastname |
| ----- | ------------------------------------------------------------ |
| email | stringExamples:email=johndoe@email.com -email=@email.com -get user by email |

### Responses

get/user

### Response samples

- 200
- 401

Content type

application/json

Example

Names starting with "j"names by email domainNames starting with "j"

Expand allCollapse all

```
[
{
"name": "John doe",
"last_name": "Green",
"email": "johndoe@email.com",
"cellphone": "819999999"},
{
"name": "Joana",
"last_name": "Green",
"email": "joanagreen@email.com",
"cellphone": "819999919"},
{
"name": "Joao da",
"last_name": "Silva",
"email": "joaozinho@email.com",
"cellphone": "879995454"}]
```

## /user/{id}

##### path Parameters

| idrequired | stringExamples:http://localhost:8080/api/v1/user/151548433 -user id |
| ---------- | ------------------------------------------------------------ |

### Responses

##### Response Schema: application/json

string

get/user/{id}

### Response samples

- 200
- 401
- 404

Content type

application/json

```
{
"name": "John doe",
"last_name": "Green",
"email": "johndoe@email.com",
"cellphone": "819999999"}
```

## /user/{id}/favorites

##### path Parameters

| idrequired | stringExamples:http://localhost:8080/api/v1/user/91491949 -user id |
| ---------- | ------------------------------------------------------------ |

##### query Parameters

| order | stringExamples:order=asc -Query ordered by asc or des        |
| ----- | ------------------------------------------------------------ |
| limit | stringExamples:limit=20 -Results limited by a specific value |

### Responses

get/user/{id}/favorites

### Response samples

- 200
- 401
- 404

Content type

application/json

Expand allCollapse all

```
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
"type": "car"},
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
"type": "car"},
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
"type": "car"}]
```

## /user/{id}/favorites

##### path Parameters

| idrequired | stringExamples:http://localhost:8080/api/v1/user/91491949 -user id |
| ---------- | ------------------------------------------------------------ |

##### Request Body schema: application/json

add a vehicle to favorites

string

### Responses

post/user/{id}/favorites

### Request samples

- Payload

Content type

application/json

```
{
"vehicle_id": "918181515"}
```

### Response samples

- 201
- 400
- 401

Content type

application/json

```
{
"message": "Vehicle added to favorites"}
```

## /user/{id}/favorites

##### path Parameters

| idrequired | stringExamples:http://localhost:8080/api/v1/user/91491949 -user id |
| ---------- | ------------------------------------------------------------ |

##### query Parameters

| favorite_idrequired | stringExamples:favorite_id=2929399 -remove vehicle from favorites, using the favorite's id |
| ------------------- | ------------------------------------------------------------ |

### Responses