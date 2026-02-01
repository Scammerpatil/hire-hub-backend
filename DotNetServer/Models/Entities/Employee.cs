using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace HireHubServer.Models.Entities;

public class Employee
{
  [Key]
  public long EmployeeId { get; set; }

  public long UserId { get; set; }

  public long CompanyId { get; set; }

  // Navigation properties
  [JsonIgnore]
  public ICollection<Referral> Referrals { get; set; }
      = new List<Referral>();
}
