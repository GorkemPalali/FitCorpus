using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using System.Security.Claims;
using FitCorpus.Application.Interfaces;
using FitCorpus.Domain.Entities;

namespace FitCorpus.Api.Controllers;

[ApiController]
[Route("v1/athlete")]
[Authorize(Policy = "AthleteOnly")]
public class AthleteController : ControllerBase
{
    private readonly IWorkoutService _workoutService;
    private readonly IDietService _dietService;
    private readonly IProgramService _programService;
    private readonly IDiscoverService _discoverService;

    public AthleteController(
        IWorkoutService workoutService,
        IDietService dietService,
        IProgramService programService,
        IDiscoverService discoverService)
    {
        _workoutService = workoutService;
        _dietService = dietService;
        _programService = programService;
        _discoverService = discoverService;
    }

    [HttpGet("workouts")]
    public async Task<IActionResult> GetWorkouts()
    {
        var athleteId = Guid.Parse(User.FindFirstValue(ClaimTypes.NameIdentifier) ?? throw new UnauthorizedAccessException());
        var workouts = await _workoutService.GetWorkoutsForAthlete(athleteId);
        return Ok(workouts);
    }

    [HttpPost("workouts")]
    public async Task<IActionResult> AddWorkout([FromBody] WorkoutEntry workoutRequest)
    {
        var athleteId = Guid.Parse(User.FindFirstValue(ClaimTypes.NameIdentifier) ?? throw new UnauthorizedAccessException());
        await _workoutService.AddWorkout(athleteId, workoutRequest);
        return Ok(new { message = "Workout added successfully." });
    }

    [HttpGet("diet")]
    public async Task<IActionResult> GetDietEntries()
    {
        var athleteId = Guid.Parse(User.FindFirstValue(ClaimTypes.NameIdentifier) ?? throw new UnauthorizedAccessException());
        var dietEntries = await _dietService.GetDietEntriesForAthlete(athleteId);
        return Ok(dietEntries);
    }

    [HttpPost("diet")]
    public async Task<IActionResult> AddDietEntry([FromBody] DietEntry dietRequest)
    {
        var athleteId = Guid.Parse(User.FindFirstValue(ClaimTypes.NameIdentifier) ?? throw new UnauthorizedAccessException());
        await _dietService.AddDietEntry(athleteId, dietRequest);
        return Ok(new { message = "Diet entry added successfully." });
    }

    [HttpGet("programs")]
    public async Task<IActionResult> GetPrograms()
    {
        var athleteId = Guid.Parse(User.FindFirstValue(ClaimTypes.NameIdentifier) ?? throw new UnauthorizedAccessException());
        var programs = await _programService.GetProgramsForAthlete(athleteId);
        return Ok(programs);
    }

    [HttpGet("discover")]
    public async Task<IActionResult> DiscoverTrainers()
    {
        var trainers = await _discoverService.GetTrainers();
        return Ok(trainers);
    }
}