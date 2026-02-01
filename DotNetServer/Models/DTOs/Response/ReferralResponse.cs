namespace HireHubServer.Models.DTOs.Response;

public class ReferralResponse
{
  public long ReferralId { get; set; }
  public EmployeeDTO? Employee { get; set; }
  public CandidateDTO? ReferredCandidate { get; set; }
  public JobPostDTO? Job { get; set; }
  public DateTime ReferralDate { get; set; }
}

public class EmployeeDTO
{
  public long EmployeeId { get; set; }
  public long UserId { get; set; }
  public long CompanyId { get; set; }
}

public class CandidateDTO
{
  public long CandidateId { get; set; }
  public long UserId { get; set; }
  public string? FullName { get; set; }
  public string? ProfileImage { get; set; }
  public string? Degree { get; set; }
  public int ExperienceYears { get; set; }
  public string? ResumeUrl { get; set; }
  public string? CurrentJobTitle { get; set; }
  public string? CurrentCompany { get; set; }
}

public class JobPostDTO
{
  public long JobId { get; set; }
  public string? JobTitle { get; set; }
  public string? JobType { get; set; }
  public string? RequiredDegrees { get; set; }
  public int RequiredExperience { get; set; }
  public decimal MinPackage { get; set; }
  public decimal MaxPackage { get; set; }
  public string? Status { get; set; }
  public long CompanyId { get; set; }
  public string? CompanyName { get; set; }
}
