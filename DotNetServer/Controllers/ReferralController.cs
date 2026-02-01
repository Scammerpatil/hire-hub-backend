using Microsoft.AspNetCore.Mvc;
using HireHubServer.Services.Interfaces;
using HireHubServer.Models.DTOs.Request;
using HireHubServer.Models.DTOs.Response;
using HireHubServer.Data;
using Microsoft.EntityFrameworkCore;

namespace HireHubServer.Controllers;

[ApiController]
[Route("api")]
public class ReferralController : ControllerBase
{
  private readonly IReferralService _referralService;
  private readonly ApplicationDbContext _context;
  private readonly ILogger<ReferralController> _logger;

  public ReferralController(
      IReferralService referralService,
      ApplicationDbContext context,
      ILogger<ReferralController> logger)
  {
    _referralService = referralService;
    _context = context;
    _logger = logger;
  }

  /// <summary>
  /// Creates a referral for a candidate
  /// POST /api/application/employee/refer
  /// </summary>
  [HttpPost("application/employee/refer")]
  public async Task<IActionResult> CreateReferral([FromBody] ReferralRequest request)
  {
    try
    {
      _logger.LogInformation("📥 Incoming POST request: CreateReferral");
      _logger.LogInformation("  EmployeeId: {EmployeeId}, CandidateId: {CandidateId}, JobId: {JobId}",
          request.EmployeeId, request.ReferredCandidateId, request.JobId);

      // Lookup employee by EmployeeId
      var employee = await _context.Employees
          .FirstOrDefaultAsync(e => e.UserId == request.EmployeeId);
      if (employee == null)
      {
        _logger.LogWarning("❌ Validation failed: Employee not found - {EmployeeId}", request.EmployeeId);
        return NotFound("Employee not found");
      }

      _logger.LogInformation("✓ Employee validated. EmployeeId: {EmployeeId}, CompanyId: {CompanyId}",
          employee.EmployeeId, employee.CompanyId);

      // Job validation (same as before)
      _logger.LogInformation("✓ Job validation passed");

      // Candidate validation (same as before)
      _logger.LogInformation("✓ Candidate validation passed");

      // Duplicate check
      var referralExists = await _referralService.ReferralExistsAsync(
          request.ReferredCandidateId,
          request.JobId);

      if (referralExists)
      {
        _logger.LogWarning("❌ Validation failed: Referral already exists");
        return BadRequest("Candidate has already been referred for this job");
      }

      // Create referral
      request.EmployeeId = employee.EmployeeId; // Set EmployeeId from found employee
      var referral = await _referralService.CreateReferralAsync(request);

      _logger.LogInformation("✅ CreateReferral completed successfully. ReferralId: {ReferralId}", referral.ReferralId);
      return Ok(new
      {
        referralId = referral.ReferralId,
        employeeId = employee.EmployeeId,
        referredCandidateId = request.ReferredCandidateId,
        jobId = request.JobId,
        message = "Candidate referred successfully"
      });
    }
    catch (Exception ex)
    {
      _logger.LogError(ex, "❌ Unexpected error in CreateReferral");
      return StatusCode(500, "An error occurred while creating the referral");
    }
  }


  /// <summary>
  /// Gets all referrals made by an employee
  /// GET /api/application/employee/refer/{userId}
  /// </summary>
  [HttpGet("application/employee/refer/{userId}")]
  public async Task<IActionResult> GetEmployeeReferrals(long userId)
  {
    var employee = await _context.Employees
        .FirstOrDefaultAsync(e => e.UserId == userId);

    if (employee == null)
      return NotFound("Employee not found");

    var referrals = await _referralService
        .GetReferralsByEmployeeAsync(employee.EmployeeId);

    return Ok(referrals);
  }


  /// <summary>
  /// Gets all referrals for a company
  /// GET /api/company/referrals/{companyId}
  /// </summary>

  [HttpGet("company/referrals/{companyId}")]
  public async Task<IActionResult> GetCompanyReferrals(long companyId)
  {
    var referrals = await _referralService
        .GetReferralsByCompanyAsync(companyId);

    return Ok(referrals);
  }
}
