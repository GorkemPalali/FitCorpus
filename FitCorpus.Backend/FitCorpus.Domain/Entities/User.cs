using FitCorpus.Domain.Enums;

namespace FitCorpus.Domain.Entities;

public class User
{
    public Guid Id { get; set; }
    public string Name { get; set; } = string.Empty;
    public string Surname { get; set; } = string.Empty;
    public string Email { get; set; } = string.Empty;
    public string PasswordHash { get; set; } = string.Empty;
    public UserRole Role { get; set; }
    public int? Height { get; set; }
    public float? Weight { get; set; }
    public string? Gender { get; set; }
    public string? City { get; set; }
    public int? Age { get; set; }
    public string? PhotoUrl { get; set; }
    public DateTime CreatedAt { get; set; }
    public DateTime UpdatedAt { get; set; }
    public List<RefreshToken> RefreshTokens { get; set; } = new();
    public TrainerProfile? TrainerProfile { get; set; }
    public List<WorkoutEntry> WorkoutEntries { get; set; } = new();
    public List<DietEntry> DietEntries { get; set; } = new();
}