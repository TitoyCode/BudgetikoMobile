# BudgetikoMobile

BudgetikoMobile is an Android application built with Kotlin and Jetpack Compose for authentication, profile management, and personal finance dashboard workflows. The app integrates with a backend API for account registration, login, profile retrieval, profile updates, and password changes.

## Features

- Register a new user account
- Login with API-backed authentication
- Persist session token locally
- View dashboard and finance summary UI
- View profile information from the backend
- Update profile details
- Change account password

## Tech Stack

- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose
- Retrofit
- Gson
- OkHttp Logging Interceptor

## Project Structure

- `app/src/main/java/com/budgetiko/budgetikomobile/data/network`: API client, repository, token storage, request and response models
- `app/src/main/java/com/budgetiko/budgetikomobile/ui/navigation`: app navigation setup
- `app/src/main/java/com/budgetiko/budgetikomobile/ui/screens`: authentication, dashboard, main, and profile-related screens

## Requirements

- Android Studio Hedgehog or newer
- JDK 8 or newer
- Android SDK configured locally
- Running backend API compatible with the endpoints below

## Running the App

1. Open the project in Android Studio.
2. Make sure the Android SDK path is configured in `local.properties`.
3. Start your backend server.
4. Run the app on an emulator or Android device.

For local emulator development, the app is configured to call:

`http://10.0.2.2:8080/`

This is defined in the API client and maps the Android emulator back to the host machine.

## Authentication

- Login and register are public endpoints.
- After a successful login, the app stores the access token in shared preferences.
- Authenticated requests automatically attach the header below when a token is present:

`Authorization: Bearer <token>`

## API Documentation

### Base URL

`http://10.0.2.2:8080/`

### Common Headers

#### Public requests

```http
Accept: application/json
Content-Type: application/json
```

#### Authenticated requests

```http
Accept: application/json
Content-Type: application/json
Authorization: Bearer <token>
```

### Response Envelope

Most endpoints are expected to return a wrapper with this structure:

```json
{
  "success": true,
  "data": {},
  "error": null,
  "timestamp": "2026-03-28T12:00:00Z"
}
```

If a request fails, the backend may return:

```json
{
  "success": false,
  "data": null,
  "error": {
    "message": "Request failed"
  },
  "timestamp": "2026-03-28T12:00:00Z"
}
```

### Endpoints

#### 1. Register

- Method: `POST`
- Endpoint: `api/v1/auth/register`
- Authentication: Not required

Request body:

```json
{
  "email": "user@example.com",
  "password": "secret123",
  "confirmPassword": "secret123",
  "fullName": "Sample User"
}
```

Successful response data example:

```json
{
  "access_token": "jwt-access-token",
  "refresh_token": "jwt-refresh-token",
  "token": "jwt-access-token",
  "message": "Account created successfully"
}
```

#### 2. Login

- Method: `POST`
- Endpoint: `api/v1/auth/login`
- Authentication: Not required

Request body:

```json
{
  "email": "user@example.com",
  "password": "secret123"
}
```

Successful response data example:

```json
{
  "access_token": "jwt-access-token",
  "refresh_token": "jwt-refresh-token",
  "token": "jwt-access-token",
  "message": "Login successful"
}
```

#### 3. Get Profile

- Method: `GET`
- Endpoint: `api/v1/profile`
- Authentication: Required

Successful response data example:

```json
{
  "user": {
    "id": "1",
    "fullName": "Sample User",
    "email": "user@example.com",
    "username": "sampleuser",
    "avatarUrl": null
  },
  "message": "Profile fetched successfully"
}
```

#### 4. Update Profile

- Method: `PUT`
- Endpoint: `api/v1/profile`
- Authentication: Required

Request body:

```json
{
  "fullName": "Updated User",
  "email": "updated@example.com",
  "username": "updateduser",
  "address": "Cebu City"
}
```

Note: the current mobile repository sends `fullName` and `email`, while the request model also supports optional `username` and `address` fields.

Successful response data example:

```json
{
  "user": {
    "id": "1",
    "fullName": "Updated User",
    "email": "updated@example.com",
    "username": "updateduser",
    "avatarUrl": null
  },
  "message": "Profile updated successfully"
}
```

#### 5. Change Password

- Method: `PUT`
- Endpoint: `api/v1/profile/password`
- Authentication: Required

Request body:

```json
{
  "currentPassword": "old-secret",
  "confirmPassword": "new-secret",
  "newPassword": "new-secret"
}
```

Successful response data example:

```json
{
  "message": "Password changed successfully"
}
```

## Error Handling

The repository maps common HTTP and connectivity failures to user-facing messages.

- `400`: invalid request or validation failure
- `401`: unauthorized or expired session
- `500`: server error
- network failure: no internet connection message
- unexpected exceptions: fallback error message from the thrown exception

## App Screens

### Register

- Purpose: create a new account with full name, email, password, and password confirmation
- API used: `POST api/v1/auth/register`
- <img width="1919" height="1079" alt="image" src="https://github.com/user-attachments/assets/5b2aefe6-3861-45ac-8db3-71d7c1c5f7d6" />


### Login

- Purpose: authenticate an existing user and store the returned token locally
- API used: `POST api/v1/auth/login`
- Screenshot: <img width="1919" height="1079" alt="image" src="https://github.com/user-attachments/assets/4b443939-ccb0-4856-9524-a636f9ab7b86" />


### Dashboard

- Purpose: display a finance overview, summary cards, and recent transactions UI
- API used: profile session state is checked before entering the main flow
- Screenshot: add image here

### Profile

- Purpose: fetch and display the authenticated user's profile information
- API used: `GET api/v1/profile`
- Screenshot: <img width="1917" height="1079" alt="image" src="https://github.com/user-attachments/assets/d9cb9f0d-fcd5-47b3-b2ce-735ed3ad645d" />


### Update Profile

- Purpose: edit and submit updated profile details
- API used: `PUT api/v1/profile`
- Screenshot: <img width="1919" height="1079" alt="image" src="https://github.com/user-attachments/assets/fa3548e3-f1d6-4a1d-b8b1-1613eea09557" />


### Change Password

- Purpose: update the authenticated user's password
- API used: `PUT api/v1/profile/password`
- Screenshot: <img width="1913" height="1079" alt="image" src="https://github.com/user-attachments/assets/62094ebe-ec56-4164-ac1a-20d1221534ef" />


## Suggested Screenshot File Names

If you want the README to embed screenshots later, place them in `docs/screenshots/` using these names:

- `register.png`
- `login.png`
- `dashboard.png`
- `profile.png`
- `update-profile.png`
- `change-password.png`

Then replace each `Screenshot: add image here` line with standard Markdown image syntax.

## Notes

- The app currently starts on `main` when a saved token exists, otherwise it starts on `login`.
- The backend base URL is currently set for emulator-to-host local development.
- OkHttp body logging is enabled in the API client, which is useful for development but should be reviewed before production release.
