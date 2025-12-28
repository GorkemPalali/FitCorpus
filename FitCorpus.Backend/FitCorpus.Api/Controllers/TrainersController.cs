using FitCorpus.Application.DTOs.Trainer;
using FitCorpus.Infrastructure.Data;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace FitCorpus.Api.Controllers;

[ApiController]
[Route("v1/trainers")]
[Authorize]
public class TrainersController : ControllerBase
{
    private readonly ApplicationDbContext _context;

    public TrainersController(ApplicationDbContext context)
    {
        _context = context;
    }

    [HttpGet]
    public async Task<IActionResult> GetTrainers(
        [FromQuery] string? location = null,
        [FromQuery] int? priceMin = null,
        [FromQuery] int? priceMax = null,
        [FromQuery] string? gender = null,
        [FromQuery] string? mode = null,
        [FromQuery] string? sort = null,
        [FromQuery] int page = 0,
        [FromQuery] int size = 20
    )
    {
        var query = _context.TrainerProfiles
            .Include(tp => tp.User)
            .Include(tp => tp.Packages)
            .AsQueryable();

        
        if (!string.IsNullOrEmpty(location))
        {
            query = query.Where(tp => tp.Location != null && tp.Location.ToLower() == location.ToLower());
        }

        
        if (priceMin.HasValue)
        {
            query = query.Where(tp => tp.PriceFrom >= priceMin.Value);
        }
        if (priceMax.HasValue)
        {
            query = query.Where(tp => tp.PriceFrom <= priceMax.Value);
        }

        
        if (!string.IsNullOrEmpty(gender))
        {
            query = query.Where(tp => tp.User.Gender != null && tp.User.Gender.ToLower() == gender.ToLower());
        }

        
        if (!string.IsNullOrEmpty(mode))
        {
            query = query.Where(tp => tp.Modes.Contains(mode, StringComparer.OrdinalIgnoreCase));
        }

        
        query = sort?.ToLower() switch
        {
            "rating" => query.OrderByDescending(tp => tp.RatingAvg),
            "price_asc" => query.OrderBy(tp => tp.PriceFrom),
            "price_desc" => query.OrderByDescending(tp => tp.PriceFrom),
            _ => query.OrderByDescending(tp => tp.RatingAvg) // Default: highest rating first
        };

        
        var totalCount = await query.CountAsync();
        var trainers = await query
            .Skip(page * size)
            .Take(size)
            .Select(tp => new TrainerResponseDto
            {
                Id = tp.User.Id.ToString(),
                Name = tp.User.Name,
                Surname = tp.User.Surname,
                PhotoUrl = tp.User.PhotoUrl,
                RatingAvg = tp.RatingAvg,
                PriceFrom = tp.PriceFrom,
                Modes = tp.Modes,
                Bio = tp.Bio,
                Achievements = tp.Achievements,
                Gender = tp.User.Gender
            })
            .ToListAsync();

        return Ok(trainers);
    }

    [HttpGet("{trainerId}")]
    public async Task<IActionResult> GetTrainerById(string trainerId)
    {
        if (!Guid.TryParse(trainerId, out var trainerGuid))
        {
            return BadRequest(new { message = "Invalid trainer ID format" });
        }

        var trainerProfile = await _context.TrainerProfiles
            .Include(tp => tp.User)
            .Include(tp => tp.Packages)
            .FirstOrDefaultAsync(tp => tp.UserId == trainerGuid);

        if (trainerProfile == null)
        {
            return NotFound(new { message = "Trainer not found" });
        }

        var response = new TrainerResponseDto
        {
            Id = trainerProfile.User.Id.ToString(),
            Name = trainerProfile.User.Name,
            Surname = trainerProfile.User.Surname,
            PhotoUrl = trainerProfile.User.PhotoUrl,
            RatingAvg = trainerProfile.RatingAvg,
            PriceFrom = trainerProfile.PriceFrom,
            Modes = trainerProfile.Modes,
            Bio = trainerProfile.Bio,
            Achievements = trainerProfile.Achievements,
            Gender = trainerProfile.User.Gender
        };

        return Ok(response);
    }
}