using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json.Serialization;

namespace HireHubServer.Models.Entities;

[Table("companies")]
public class Company
{
  [Key]
  [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
  [Column("company_id")]
  public long CompanyId { get; set; }

  // 🔑 FK → users.user_id
  [Column("user_id")]
  public long UserId { get; set; }

  // Navigation
  public User User { get; set; } = null!;

  [Column("address")]
  public string? Address { get; set; }

  [Column("industry")]
  public string? Industry { get; set; }

  [Column("company_size")]
  public string? CompanySize { get; set; }

  [Column("is_blocked")]
  public bool? IsBlocked { get; set; }

  [Column("website")]
  public string? Website { get; set; }

  [Column("description", TypeName = "text")]
  public string? Description { get; set; }

  [Column("created_at")]
  public DateTime CreatedAt { get; set; }

  // Optional: prevent circular JSON if ever serialized
  [JsonIgnore]
  public ICollection<JobPost> JobPosts { get; set; } = new List<JobPost>();
}
