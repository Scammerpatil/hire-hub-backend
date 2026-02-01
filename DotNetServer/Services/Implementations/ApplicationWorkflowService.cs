using Microsoft.EntityFrameworkCore;
using HireHubServer.Data;
using HireHubServer.Models.Entities;
using HireHubServer.Models.DTOs.Response;
using HireHubServer.Services.Interfaces;

namespace HireHubServer.Services.Implementations;

public class ApplicationWorkflowService : IApplicationWorkflowService
{
  private readonly ApplicationDbContext _context;
  private readonly ILogger<ApplicationWorkflowService> _logger;

  public ApplicationWorkflowService(ApplicationDbContext context, ILogger<ApplicationWorkflowService> logger)
  {
    _context = context;
    _logger = logger;
  }

  public async Task<ApplicationStatusLog> UpdateApplicationStatusAsync(long applicationId, string newStatus)
  {
    _logger.LogInformation("=== UpdateApplicationStatusAsync ===");
    _logger.LogInformation("ApplicationId: {ApplicationId}, NewStatus: {NewStatus}", applicationId, newStatus);

    // Fetch application
    var application = await _context.Applications.FirstOrDefaultAsync(a => a.ApplicationId == applicationId);

    if (application == null)
    {
      _logger.LogError("Application not found: {ApplicationId}", applicationId);
      throw new InvalidOperationException("Application not found");
    }

    var oldStatus = application.Status;
    _logger.LogInformation("Current status: {OldStatus}", oldStatus);

    // Update application status
    application.Status = newStatus;
    _context.Applications.Update(application);
    await _context.SaveChangesAsync();
    _logger.LogInformation("Application status updated in database");

    // Create status log
    var statusLog = new ApplicationStatusLog
    {
      ApplicationId = applicationId,
      FromStatus = oldStatus,
      ToStatus = newStatus,
      UpdatedAt = DateTime.UtcNow
    };

    _context.ApplicationStatusLogs.Add(statusLog);
    await _context.SaveChangesAsync();
    _logger.LogInformation("Status log created: LogId={LogId}", statusLog.LogId);

    return statusLog;
  }

  public async Task<List<ApplicationStatusLogResponse>> GetStatusLogsAsync(long applicationId)
  {
    _logger.LogInformation("=== GetStatusLogsAsync ===");
    _logger.LogInformation("ApplicationId: {ApplicationId}", applicationId);

    // Check if application exists
    var applicationExists = await _context.Applications.AnyAsync(a => a.ApplicationId == applicationId);
    if (!applicationExists)
    {
      _logger.LogError("Application not found: {ApplicationId}", applicationId);
      throw new InvalidOperationException("Application not found");
    }

    var logs = await _context.ApplicationStatusLogs
        .Where(l => l.ApplicationId == applicationId)
        .OrderBy(l => l.LogId)
        .ToListAsync();

    _logger.LogInformation("Retrieved {Count} status logs", logs.Count);

    var response = logs.Select(l => new ApplicationStatusLogResponse
    {
      LogId = l.LogId,
      FromStatus = l.FromStatus,
      ToStatus = l.ToStatus,
      UpdatedAt = l.UpdatedAt
    }).ToList();

    return response;
  }
}
