namespace HireHubServer.Models.DTOs.Request;

public class ReferralRequest
{
  public long EmployeeId { get; set; }
  public long ReferredCandidateId { get; set; }
  public long JobId { get; set; }
}
