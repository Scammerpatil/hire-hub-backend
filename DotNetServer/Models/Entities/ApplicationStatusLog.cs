using System.ComponentModel.DataAnnotations;

namespace HireHubServer.Models.Entities;

public class ApplicationStatusLog
{
  [Key]
  public long LogId { get; set; }

  public long ApplicationId { get; set; }

  public string? FromStatus { get; set; }
  public string? ToStatus { get; set; }

  public DateTime UpdatedAt { get; set; }

  public Application Application { get; set; } = null!;
}
