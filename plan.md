1. Objective

Migrate Application Workflow and Referral functionality from the existing Spring Boot backend to ASP.NET Core, while:

Using the same database

Making no schema changes

Keeping API responses identical

Avoiding Kafka / RabbitMQ

Avoiding new features or refactors

This is a migration, not a redesign.

2. Services in Scope
   ✅ In Scope

Application Status Workflow

Employee Referral System

❌ Out of Scope

Auth (JWT generation)

Company CRUD

Candidate CRUD

Job CRUD

Database schema changes

Performance optimizations

3. Endpoints to Be Served by ASP.NET Core
   3.1 Application Workflow Service
   PUT /api/application-status/update/{applicationId}
   GET /api/application-status/{applicationId}

3.2 Referral Service
POST /api/application/employee/refer
GET /api/application/employee/refer/{userId}
GET /api/company/referrals/{companyId}

These endpoints must be disabled in Spring after migration.

4. Database Rules (CRITICAL)

Use existing tables

Do NOT modify schema

Do NOT add migrations

Column names must remain snake_case

Entity Framework Core mappings must match Spring entities exactly

5. Required Entities in ASP.NET Core

Agent must create only these entities:

Entity Table
User users
Company companies
Employee employees
Candidate candidates
JobPost job_posts
Application applications
ApplicationStatusLog application_status_logs
Referral referrals 6. Mandatory Entity Relationships

Agent MUST implement all relationships below.

6.1 Candidate
candidates.user_id → users.user_id
candidates ← referrals.referred_candidate_id

6.2 Employee
employees.user_id → users.user_id
employees ← referrals.employee_id

6.3 JobPost
job_posts.company_id → companies.company_id

6.4 Company
companies.user_id → users.user_id

6.5 Referral
referrals.employee_id → employees.employee_id
referrals.referred_candidate_id → candidates.candidate_id
referrals.job_id → job_posts.job_id

7. EF Core Configuration Rules
   7.1 DbContext Must Contain
   DbSet<User>
   DbSet<Company>
   DbSet<Employee>
   DbSet<Candidate>
   DbSet<JobPost>
   DbSet<Application>
   DbSet<ApplicationStatusLog>
   DbSet<Referral>

7.2 Fluent API is Mandatory

Do NOT rely on conventions

Every FK must be explicitly mapped

Every table name must be explicitly set

8. Query Rules (MOST IMPORTANT)
   8.1 Referral Queries MUST Use This Include Chain
   \_context.Referrals
   .Include(r => r.Employee)
   .ThenInclude(e => e.User)

.Include(r => r.ReferredCandidate)
.ThenInclude(c => c.User)

.Include(r => r.Job)
.ThenInclude(j => j.Company)

No shortcuts.
No partial includes.

9. DTO Mapping Rules

NEVER return EF entities directly

Always map to DTOs

DTOs must match Spring JSON structure exactly

Example (Required Fields)
referredCandidate:
candidateId
userId
fullName
profileImage
degree
experienceYears
currentJobTitle
currentCompany

job:
jobId
jobTitle
companyId
companyName

10. Authentication Rules

JWT issued by Spring only

ASP.NET Core must:

Read Authorization header

Validate signature

Extract userId & role

No auth endpoints in .NET

11. Logging (Temporary but Mandatory)

Log:

Incoming request

userId & role from token

Whether navigation properties are loaded

Errors

Logs will be removed later.

12. Validation Checklist (MUST PASS)

For every migrated endpoint:

Spring response vs .NET response must match

No null for fields present in Spring

No FK default values (0)

Same HTTP status codes

Same error messages

13. Explicit Non-Goals

No refactoring

No CQRS

No caching

No background jobs

No async messaging

No schema updates

14. Final Instruction to Agent

Do not assume.
Do not redesign.
Do not simplify.

Mirror Spring behavior exactly.

15. Completion Criteria

Migration is complete when:

Frontend works without changes

Spring endpoints can be safely removed

All referral & workflow data is fully populated

No nulls where Spring returns data
