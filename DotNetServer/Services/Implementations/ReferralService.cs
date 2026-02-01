using HireHubServer.Data;
using HireHubServer.Models.DTOs.Request;
using HireHubServer.Models.DTOs.Response;
using HireHubServer.Models.Entities;
using Microsoft.EntityFrameworkCore;
using HireHubServer.Services.Interfaces;

public class ReferralService : IReferralService
{
  private readonly ApplicationDbContext _context;

  public ReferralService(ApplicationDbContext context)
  {
    _context = context;
  }

  public async Task<Referral> CreateReferralAsync(ReferralRequest request)
  {
    var referral = new Referral
    {
      EmployeeId = request.EmployeeId,
      ReferredCandidateId = request.ReferredCandidateId,
      JobId = request.JobId,
      ReferralDate = DateTime.UtcNow
    };

    _context.ReferralsEntities.Add(referral);
    await _context.SaveChangesAsync();

    return referral;
  }

  public async Task<List<ReferralResponse>> GetReferralsByEmployeeAsync(long employeeId)
  {
    var referrals = await _context.ReferralsEntities
        .Where(r => r.EmployeeId == employeeId)
        .Include(r => r.Employee)
        .Include(r => r.ReferredCandidate)
        .Include(r => r.Job)
        .ToListAsync();

    return referrals.Select(MapToDto).ToList();
  }

  public async Task<List<ReferralResponse>> GetReferralsByCompanyAsync(long companyId)
  {
    var referrals = await _context.ReferralsEntities
        .Where(r => r.Employee.CompanyId == companyId)
        .Include(r => r.Employee)
        .Include(r => r.ReferredCandidate)
          .ThenInclude(c => c.User)
        .Include(r => r.Job)
        .ToListAsync();

    return referrals.Select(MapToDto).ToList();
  }

  public async Task<bool> ReferralExistsAsync(long candidateId, long jobId)
  {
    return await _context.ReferralsEntities
        .AnyAsync(r =>
            r.ReferredCandidateId == candidateId &&
            r.JobId == jobId);
  }

  private static ReferralResponse MapToDto(Referral r)
  {
    return new ReferralResponse
    {
      ReferralId = r.ReferralId,
      ReferralDate = r.ReferralDate,

      Employee = new EmployeeDTO
      {
        EmployeeId = r.Employee.EmployeeId,
        UserId = r.Employee.UserId,
        CompanyId = r.Employee.CompanyId
      },

      ReferredCandidate = new CandidateDTO
      {
        CandidateId = r.ReferredCandidate.CandidateId,
        UserId = r.ReferredCandidate.UserId,
        FullName = r.ReferredCandidate.User?.FullName,
        ProfileImage = r.ReferredCandidate.User?.ProfileImage,
        Degree = r.ReferredCandidate.Degree,
        ResumeUrl = r.ReferredCandidate.ResumeUrl,
        CurrentJobTitle = r.ReferredCandidate.CurrentJobTitle,
        CurrentCompany = r.ReferredCandidate.CurrentCompany
      },

      Job = new JobPostDTO
      {
        JobId = r.Job.JobId,
        JobTitle = r.Job.JobTitle,
        CompanyId = r.Job.CompanyId
      }
    };
  }
}
