using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using System.Security.Claims;
using FitCorpus.Application.Interfaces;

namespace FitCorpus.Api.Controllers;

[ApiController]
[Route("v1/trainer")]
[Authorize(Policy = "TrainerOnly")]
public class TrainerController : ControllerBase
{
    private readonly IStudentService _studentService;
    private readonly IPackageService _packageService;
    private readonly IPlanService _planService;
    private readonly ICodeService _codeService;
    private readonly ILogger<TrainerController> _logger;

    public TrainerController(
        IStudentService studentService,
        IPackageService packageService,
        IPlanService planService,
        ICodeService codeService,
        ILogger<TrainerController> logger)
    {
        _studentService = studentService;
        _packageService = packageService;
        _planService = planService;
        _codeService = codeService;
        _logger = logger;
    }

    [HttpGet("students")]
    public async Task<IActionResult> GetStudents()
    {
        var trainerId = Guid.Parse(User.FindFirstValue(ClaimTypes.NameIdentifier) ?? throw new UnauthorizedAccessException());
        _logger.LogInformation($"Trainer ID: {trainerId}");
        _logger.LogInformation($"User Claims: {string.Join(", ", User.Claims.Select(c => $"{c.Type}: {c.Value}"))}");

        var students = await _studentService.GetStudentsForTrainer(trainerId);
        return Ok(students);
    }

    [HttpGet("packages")]
    public async Task<IActionResult> GetPackages()
    {
        var trainerId = Guid.Parse(User.FindFirstValue(ClaimTypes.NameIdentifier) ?? throw new UnauthorizedAccessException());
        var packages = await _packageService.GetPackagesForTrainer(trainerId);
        return Ok(packages);
    }

    [HttpGet("plans")]
    public async Task<IActionResult> GetPlans()
    {
        var trainerId = Guid.Parse(User.FindFirstValue(ClaimTypes.NameIdentifier) ?? throw new UnauthorizedAccessException());
        var plans = await _planService.GetPlansForTrainer(trainerId);
        return Ok(plans);
    }

    [HttpGet("codes")]
    public async Task<IActionResult> GetCodes()
    {
        var trainerId = Guid.Parse(User.FindFirstValue(ClaimTypes.NameIdentifier) ?? throw new UnauthorizedAccessException());
        var codes = await _codeService.GetCodesForTrainer(trainerId);
        return Ok(codes);
    }

}