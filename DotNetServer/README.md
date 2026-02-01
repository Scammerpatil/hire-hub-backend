# HireHub .NET Server - Application Workflow & Referral Services

## Overview

ASP.NET Core 8.0 microservice for handling Application Workflow and Referral management, migrated from Spring Boot.

## Architecture

```
HireHub.ApplicationWorkflowService
├── Controllers/
│   ├── ApplicationStatusController.cs      (PUT/GET application status)
│   └── ReferralController.cs               (POST/GET referral endpoints)
├── Services/
│   ├── Interfaces/
│   │   ├── IApplicationWorkflowService.cs
│   │   └── IReferralService.cs
│   └── Implementations/
│       ├── ApplicationWorkflowService.cs
│       └── ReferralService.cs
├── Models/
│   ├── Entities/
│   │   ├── Application.cs
│   │   ├── ApplicationStatusLog.cs
│   │   ├── Referral.cs
│   │   └── Employee.cs
│   └── DTOs/
│       ├── Request/
│       │   └── ReferralRequest.cs
│       └── Response/
│           ├── ApplicationStatusLogResponse.cs
│           └── ReferralResponse.cs
├── Data/
│   └── ApplicationDbContext.cs             (EF Core DbContext)
├── Utilities/
│   ├── ApiResponse.cs
│   └── JwtTokenValidator.cs
└── Program.cs                               (Configuration & Startup)
```

## Database

- **Database**: `hirehub_db` (SQL Server)
- **Tables**: `applications`, `applicationStatusLogs`, `referrals`, `employees`
- **ORM**: Entity Framework Core 8.0
- **No Schema Changes**: Maps to existing Spring schema exactly

## API Endpoints

### Application Workflow Service

#### 1. Update Application Status

```
PUT /api/application-status/update/{applicationId}?newStatus={status}
```

**Response:**

```json
{
  "logId": 123,
  "fromStatus": "applied",
  "toStatus": "under_review",
  "updatedAt": "2024-01-29T12:30:45.000Z"
}
```

#### 2. Get Status History

```
GET /api/application-status/{applicationId}
```

**Response:**

```json
[
  {
    "logId": 1,
    "fromStatus": "applied",
    "toStatus": "under_review",
    "updatedAt": "2024-01-29T12:30:45.000Z"
  },
  ...
]
```

### Referral Service

#### 3. Create Referral

```
POST /api/application/employee/refer
Content-Type: application/json

{
  "employeeId": 1,
  "referredCandidateId": 5,
  "jobId": 10
}
```

**Response:** `"Candidate referred successfully"`

#### 4. Get Employee Referrals

```
GET /api/application/employee/refer/{userId}
```

**Response:**

```json
[
  {
    "referralId": 1,
    "employeeId": 1,
    "referredCandidateId": 5,
    "jobId": 10,
    "referralDate": "2024-01-29T12:30:45.000Z"
  },
  ...
]
```

#### 5. Get Company Referrals

```
GET /api/company/referrals/{companyId}
```

**Response:**

```json
[
  {
    "referralId": 1
  },
  ...
]
```

## Configuration

### Connection String (appsettings.json)

```json
"ConnectionStrings": {
  "DefaultConnection": "Server=(local);Database=hirehub_db;User Id=sa;Password=YourPassword123!;Encrypt=false;TrustServerCertificate=true;"
}
```

### CORS

Configured to allow `http://localhost:3000` (frontend development server)

### Logging

- **Console**: All logs output to console
- **Debug**: Debug-level logging enabled
- **Namespace**: `HireHubServer.*` at Information level

## Service Details

### ApplicationWorkflowService

- Updates application status in atomic transaction
- Creates comprehensive status log entry
- Returns full status history in order

### ReferralService

- Validates employee/candidate/job existence
- Prevents duplicate referrals
- Supports company-wide referral queries
- Full referral history tracking

## Validation Rules

### Status Update

- Application must exist
- Status cannot be null
- Creates immutable audit trail

### Referral Creation

- Employee must exist
- Employee must belong to job's company
- Candidate & Job must exist
- Prevents duplicate candidate-job referrals

## Error Handling

- 400 Bad Request: Validation failures
- 404 Not Found: Resource not found
- 500 Internal Server Error: Unexpected errors

All errors logged with full context for debugging.

## Logging

Every endpoint logs:

- Incoming request details
- Request parameters
- Validation steps
- Database operations
- Response details
- All errors with stack traces

## Build & Run

### Build

```powershell
dotnet build
```

### Run

```powershell
dotnet run
```

### Watch Mode

```powershell
dotnet watch run
```

## Dependencies

- Microsoft.EntityFrameworkCore 8.0.0
- Microsoft.EntityFrameworkCore.SqlServer 8.0.0
- System.IdentityModel.Tokens.Jwt 7.0.0
- Swashbuckle.AspNetCore 6.0.0

## Notes

- JWT validation middleware ready for future implementation
- No breaking changes to API contracts
- Feature parity with Spring implementation maintained
- Ready for side-by-side testing with Spring endpoints
