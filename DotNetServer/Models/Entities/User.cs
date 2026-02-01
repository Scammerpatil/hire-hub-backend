using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace HireHubServer.Models.Entities;

[Table("users")]
public class User
{
  [Key]
  [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
  [Column("user_id")]
  public long UserId { get; set; }

  [Column("full_name")]
  public string FullName { get; set; } = null!;

  [Required]
  [Column("email")]
  public string Email { get; set; } = null!;

  [Column("phone")]
  public string? Phone { get; set; }

  [Column("profile_image")]
  public string? ProfileImage { get; set; }

  [Column("password")]
  public string Password { get; set; } = null!;

  [Column("role")]
  public string Role { get; set; } = null!;

  [Column("created_at")]
  public DateTime CreatedAt { get; set; }
}
