using Microsoft.AspNetCore.Mvc;
using HireHubServer.Services.Interfaces;
using HireHubServer.Models.DTOs.Response;

namespace HireHubServer.Controllers;

[ApiController]
[Route("api/application-status")]
public class ApplicationStatusController : ControllerBase
{
  private readonly IApplicationWorkflowService _workflowService;
  private readonly ILogger<ApplicationStatusController> _logger;

  public ApplicationStatusController(
      IApplicationWorkflowService workflowService,
      ILogger<ApplicationStatusController> logger)
  {
    _workflowService = workflowService;
    _logger = logger;
  }

  /// <summary>
  /// Updates the status of an application
  /// PUT /api/application-status/update/{applicationId}?newStatus={status}
  /// </summary>
  [HttpPut("update/{applicationId}")]
  public async Task<IActionResult> UpdateStatus(
      long applicationId,
      [FromQuery] string newStatus)
  {
    try
    {
      _logger.LogInformation("📥 Incoming PUT request: UpdateStatus");
      _logger.LogInformation("  ApplicationId: {ApplicationId}, NewStatus: {NewStatus}", applicationId, newStatus);

      // Validate input
      if (string.IsNullOrWhiteSpace(newStatus))
      {
        _logger.LogWarning("⚠️ Validation failed: newStatus is required");
        return BadRequest("Status is required");
      }

      // Call service
      var log = await _workflowService.UpdateApplicationStatusAsync(applicationId, newStatus);

      // Build response
      var response = new ApplicationStatusLogResponse
      {
        LogId = log.LogId,
        FromStatus = log.FromStatus,
        ToStatus = log.ToStatus,
        UpdatedAt = log.UpdatedAt
      };

      _logger.LogInformation("✅ UpdateStatus completed successfully. LogId: {LogId}", log.LogId);
      return Ok(response);
    }
    catch (InvalidOperationException ex)
    {
      _logger.LogError("❌ Error: {Message}", ex.Message);
      return NotFound(ex.Message);
    }
    catch (Exception ex)
    {
      _logger.LogError(ex, "❌ Unexpected error in UpdateStatus");
      return StatusCode(500, "An error occurred while updating the status");
    }
  }

  /// <summary>
  /// Retrieves all status logs for an application
  /// GET /api/application-status/{applicationId}
  /// </summary>
  [HttpGet("{applicationId}")]
  public async Task<IActionResult> GetStatusLogs(long applicationId)
  {
    try
    {
      _logger.LogInformation("📥 Incoming GET request: GetStatusLogs");
      _logger.LogInformation("  ApplicationId: {ApplicationId}", applicationId);

      // Call service
      var logs = await _workflowService.GetStatusLogsAsync(applicationId);

      _logger.LogInformation("✅ GetStatusLogs completed successfully. Count: {Count}", logs.Count);
      return Ok(logs);
    }
    catch (InvalidOperationException ex)
    {
      _logger.LogError("❌ Error: {Message}", ex.Message);
      return NotFound(ex.Message);
    }
    catch (Exception ex)
    {
      _logger.LogError(ex, "❌ Unexpected error in GetStatusLogs");
      return StatusCode(500, "An error occurred while retrieving status logs");
    }
  }
}
