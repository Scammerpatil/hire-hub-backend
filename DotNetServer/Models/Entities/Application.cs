using System.ComponentModel.DataAnnotations;

namespace HireHubServer.Models.Entities;

public class Application
{
  [Key]
  public long ApplicationId { get; set; }

  public long? CandidateId { get; set; }
  public long? JobId { get; set; }

  public string? Status { get; set; }
  public DateTime? CreatedAt { get; set; }

  public ICollection<ApplicationStatusLog> StatusLogs { get; set; }
      = new List<ApplicationStatusLog>();
}
