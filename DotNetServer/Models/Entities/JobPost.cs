using System.ComponentModel.DataAnnotations;

namespace HireHubServer.Models.Entities;

public class JobPost
{
  [Key]
  public long JobId { get; set; }

  public long CompanyId { get; set; }

  public string? JobTitle { get; set; }
  public string? JobPosition { get; set; }
  public string? JobLocation { get; set; }
  public string? JobCategory { get; set; }
  public string? JobDescription { get; set; }

  public string? RequiredDegrees { get; set; }
  public int? RequiredExperience { get; set; }

  public double? MinPackage { get; set; }
  public double? MaxPackage { get; set; }
  public string? Status { get; set; }
  public DateTime PostedAt { get; set; }
}