using Microsoft.EntityFrameworkCore;
using HireHubServer.Models.Entities;

namespace HireHubServer.Data;

public class ApplicationDbContext : DbContext
{
  public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
      : base(options) { }

  public DbSet<Application> Applications => Set<Application>();
  public DbSet<ApplicationStatusLog> ApplicationStatusLogs => Set<ApplicationStatusLog>();
  public DbSet<Referral> ReferralsEntities => Set<Referral>();
  public DbSet<Employee> Employees => Set<Employee>();
  public DbSet<Candidate> Candidates => Set<Candidate>();
  public DbSet<JobPost> JobPosts => Set<JobPost>();
  public DbSet<User> Users => Set<User>();
  public DbSet<Company> Companies => Set<Company>();

  protected override void OnModelCreating(ModelBuilder modelBuilder)
  {
    base.OnModelCreating(modelBuilder);

    // → APPLICATIONS
    modelBuilder.Entity<Application>(entity =>
    {
      entity.ToTable("applications");
      entity.HasKey(e => e.ApplicationId);

      entity.Property(e => e.ApplicationId).HasColumnName("application_id");
      entity.Property(e => e.CandidateId).HasColumnName("candidate_id");
      entity.Property(e => e.JobId).HasColumnName("job_id");
      entity.Property(e => e.Status).HasColumnName("status");
      entity.Property(e => e.CreatedAt).HasColumnName("created_at");

      entity.HasMany(a => a.StatusLogs)
                .WithOne(l => l.Application)
                .HasForeignKey(l => l.ApplicationId);
    });

    // → APPLICATION_STATUS_LOGS
    modelBuilder.Entity<ApplicationStatusLog>(entity =>
    {
      entity.ToTable("application_status_logs");
      entity.HasKey(e => e.LogId);

      entity.Property(e => e.LogId).HasColumnName("log_id");
      entity.Property(e => e.ApplicationId).HasColumnName("application_id");
      entity.Property(e => e.FromStatus).HasColumnName("from_status");
      entity.Property(e => e.ToStatus).HasColumnName("to_status");
      entity.Property(e => e.UpdatedAt).HasColumnName("updated_at");
    });

    // → EMPLOYEES
    modelBuilder.Entity<Employee>(entity =>
    {
      entity.ToTable("employees");
      entity.HasKey(e => e.EmployeeId);

      entity.Property(e => e.EmployeeId).HasColumnName("employee_id");
      entity.Property(e => e.UserId).HasColumnName("user_id");
      entity.Property(e => e.CompanyId).HasColumnName("company_id");

      entity.HasMany(e => e.Referrals)
                .WithOne(r => r.Employee)
                .HasForeignKey(r => r.EmployeeId);
    });

    // → CANDIDATES
    modelBuilder.Entity<Candidate>(entity =>
    {
      entity.ToTable("candidates");
      entity.HasKey(c => c.CandidateId);

      entity.Property(c => c.CandidateId).HasColumnName("candidate_id");
      entity.Property(c => c.UserId).HasColumnName("user_id");
      entity.Property(c => c.Degree).HasColumnName("degree");
      entity.Property(c => c.ExperienceYears).HasColumnName("experience_years");
      entity.Property(c => c.Skills).HasColumnName("skills");
      entity.Property(c => c.ResumeUrl).HasColumnName("resume_url");
      entity.Property(c => c.CurrentCompany).HasColumnName("current_company");
      entity.Property(c => c.CurrentJobTitle).HasColumnName("current_job_title");
      entity.Property(c => c.CurrentSalary).HasColumnName("current_salary");
      entity.Property(c => c.ExpectedSalary).HasColumnName("expected_salary");
      entity.Property(c => c.EmploymentType).HasColumnName("employment_type");
      entity.Property(c => c.CreatedAt).HasColumnName("created_at");
      entity.Property(c => c.UpdatedAt).HasColumnName("updated_at");

      entity.HasOne(c => c.User)
            .WithMany()
            .HasForeignKey(c => c.UserId);

      entity.HasMany(c => c.Referrals)
            .WithOne(r => r.ReferredCandidate)
            .HasForeignKey(r => r.ReferredCandidateId);
    });
    modelBuilder.Entity<Company>(entity =>
    {
      entity.ToTable("companies");
      entity.HasKey(c => c.CompanyId);

      entity.Property(c => c.CompanyId).HasColumnName("company_id");
      entity.Property(c => c.UserId).HasColumnName("user_id");
      entity.Property(c => c.Address).HasColumnName("address");
      entity.Property(c => c.Industry).HasColumnName("industry");
      entity.Property(c => c.CompanySize).HasColumnName("company_size");
      entity.Property(c => c.IsBlocked).HasColumnName("is_blocked");
      entity.Property(c => c.Website).HasColumnName("website");
      entity.Property(c => c.Description).HasColumnName("description");
      entity.Property(c => c.CreatedAt).HasColumnName("created_at");

      entity.HasOne(c => c.User)
            .WithMany()
            .HasForeignKey(c => c.UserId);
    });
    // → JOB POSTS
    modelBuilder.Entity<JobPost>(entity =>
    {
      entity.ToTable("job_posts");
      entity.HasKey(j => j.JobId);

      entity.Property(j => j.JobId).HasColumnName("job_id");
      entity.Property(j => j.CompanyId).HasColumnName("company_id");
      entity.Property(j => j.JobTitle).HasColumnName("job_title");
      entity.Property(j => j.JobPosition).HasColumnName("job_position");
      entity.Property(j => j.JobLocation).HasColumnName("job_location");
      entity.Property(j => j.JobCategory).HasColumnName("job_category");
      entity.Property(j => j.JobDescription).HasColumnName("job_description");
      entity.Property(j => j.RequiredDegrees).HasColumnName("required_degrees");
      entity.Property(j => j.RequiredExperience).HasColumnName("required_experience");
      entity.Property(j => j.MinPackage).HasColumnName("min_package");
      entity.Property(j => j.MaxPackage).HasColumnName("max_package");
      entity.Property(j => j.Status).HasColumnName("status");
      entity.Property(j => j.PostedAt).HasColumnName("posted_at");
    });

    // → REFERRALS
    modelBuilder.Entity<Referral>(entity =>
 {
   entity.ToTable("referrals");
   entity.HasKey(r => r.ReferralId);

   entity.Property(r => r.ReferralId)
        .HasColumnName("referral_id");

   entity.Property(r => r.EmployeeId)
        .HasColumnName("employee_id");

   entity.Property(r => r.ReferredCandidateId)
        .HasColumnName("referred_candidate_id");

   entity.Property(r => r.JobId)
        .HasColumnName("job_id");

   entity.Property(r => r.ReferralDate)
        .HasColumnName("referral_date");

   entity.HasOne(r => r.Employee)
        .WithMany(e => e.Referrals)
        .HasForeignKey(r => r.EmployeeId);

   entity.HasOne(r => r.ReferredCandidate)
        .WithMany(c => c.Referrals)
        .HasForeignKey(r => r.ReferredCandidateId);

   entity.HasOne(r => r.Job)
        .WithMany()
        .HasForeignKey(r => r.JobId);
 });

  }
}
