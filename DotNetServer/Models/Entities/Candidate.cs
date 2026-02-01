using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json.Serialization;

namespace HireHubServer.Models.Entities;

[Table("candidates")]
public class Candidate
{
  [Key]
  [Column("candidate_id")]
  public long CandidateId { get; set; }

  // 🔑 FK → users.user_id
  [Column("user_id")]
  public long UserId { get; set; }

  // Navigation
  public User User { get; set; } = null!;

  [Column("degree")]
  public string? Degree { get; set; }

  [Column("experience_years")]
  public int? ExperienceYears { get; set; }

  [Column("skills")]
  public string? Skills { get; set; }

  [Column("resume_url")]
  public string? ResumeUrl { get; set; }

  [Column("education")]
  public string? Education { get; set; }

  [Column("experience")]
  public string? Experience { get; set; }

  [Column("certifications")]
  public string? Certifications { get; set; }

  [Column("projects")]
  public string? Projects { get; set; }

  [Column("current_job_title")]
  public string? CurrentJobTitle { get; set; }

  [Column("current_company")]
  public string? CurrentCompany { get; set; }

  [Column("current_salary")]
  public int? CurrentSalary { get; set; }

  [Column("expected_salary")]
  public int? ExpectedSalary { get; set; }

  [Column("notice_period")]
  public string? NoticePeriod { get; set; }

  [Column("resume_headline")]
  public string? ResumeHeadline { get; set; }

  [Column("summary")]
  public string? Summary { get; set; }

  [Column("linkedin_url")]
  public string? LinkedinUrl { get; set; }

  [Column("github_url")]
  public string? GithubUrl { get; set; }

  [Column("portfolio_url")]
  public string? PortfolioUrl { get; set; }

  [Column("preferred_locations")]
  public string? PreferredLocations { get; set; }

  [Column("employment_type")]
  public string? EmploymentType { get; set; }

  [Column("work_type")]
  public string? WorkType { get; set; }

  [Column("created_at")]
  public DateTime CreatedAt { get; set; }

  [Column("updated_at")]
  public DateTime? UpdatedAt { get; set; }

  // Navigation
  [JsonIgnore]
  public ICollection<Referral> Referrals { get; set; } = new List<Referral>();
}
