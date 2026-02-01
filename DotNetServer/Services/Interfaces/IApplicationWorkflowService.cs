using HireHubServer.Models.Entities;
using HireHubServer.Models.DTOs.Response;

namespace HireHubServer.Services.Interfaces;

public interface IApplicationWorkflowService
{
  /// <summary>
  /// Updates the status of an application and creates a status log entry.
  /// </summary>
  Task<ApplicationStatusLog> UpdateApplicationStatusAsync(long applicationId, string newStatus);

  /// <summary>
  /// Retrieves all status logs for a specific application.
  /// </summary>
  Task<List<ApplicationStatusLogResponse>> GetStatusLogsAsync(long applicationId);
}
