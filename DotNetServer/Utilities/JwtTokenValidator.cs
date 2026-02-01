using System.Security.Claims;

namespace HireHubServer.Utilities;

public class JwtTokenValidator
{
  public static (long? userId, string? role) ExtractClaimsFromUser(ClaimsPrincipal user)
  {
    var userIdClaim = user.FindFirst(ClaimTypes.NameIdentifier)?.Value
                     ?? user.FindFirst("userId")?.Value
                     ?? user.FindFirst("sub")?.Value;

    var roleClaim = user.FindFirst(ClaimTypes.Role)?.Value
                   ?? user.FindFirst("role")?.Value;

    if (long.TryParse(userIdClaim, out var userId))
    {
      return (userId, roleClaim);
    }

    return (null, roleClaim);
  }
}
