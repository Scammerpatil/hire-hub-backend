namespace HireHubServer.Models.DTOs.Response;

public class ApplicationStatusLogResponse
{
  public long LogId { get; set; }
  public string? FromStatus { get; set; }
  public string? ToStatus { get; set; }
  public DateTime UpdatedAt { get; set; }
}
