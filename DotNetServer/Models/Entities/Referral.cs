using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace HireHubServer.Models.Entities;

public class Referral
{
  [Key]
  public long ReferralId { get; set; }

  public long EmployeeId { get; set; }
  public long ReferredCandidateId { get; set; }
  public long JobId { get; set; }

  public DateTime ReferralDate { get; set; }

  // Navigation properties
  [ForeignKey(nameof(EmployeeId))]
  public Employee Employee { get; set; } = null!;

  [ForeignKey(nameof(ReferredCandidateId))]
  public Candidate ReferredCandidate { get; set; } = null!;

  [ForeignKey(nameof(JobId))]
  public JobPost Job { get; set; } = null!;
}
