using HireHubServer.Models.Entities;
using HireHubServer.Models.DTOs.Request;
using HireHubServer.Models.DTOs.Response;

namespace HireHubServer.Services.Interfaces;

public interface IReferralService
{
  /// <summary>
  /// Creates a new referral.
  /// </summary>
  Task<Referral> CreateReferralAsync(ReferralRequest request);

  /// <summary>
  /// Retrieves all referrals made by a specific employee.
  /// </summary>
  Task<List<ReferralResponse>> GetReferralsByEmployeeAsync(long employeeId);

  /// <summary>
  /// Retrieves all referrals for a specific company.
  /// </summary>
  Task<List<ReferralResponse>> GetReferralsByCompanyAsync(long companyId);

  /// <summary>
  /// Checks if a referral already exists for a candidate and job.
  /// </summary>
  Task<bool> ReferralExistsAsync(long candidateId, long jobId);
}
