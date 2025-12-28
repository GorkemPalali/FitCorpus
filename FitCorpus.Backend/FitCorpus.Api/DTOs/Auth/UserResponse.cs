namespace FitCorpus.Api.DTOs.Auth;

public class UserResponse
{
    public string Id { get; set; } = string.Empty;
    public string Name { get; set; } = string.Empty;
    public string Surname { get; set; } = string.Empty;
    public string Email { get; set; } = string.Empty;
    public string Role { get; set; } = string.Empty;
    public int? Height { get; set; }
    public float? Weight { get; set; }
    public string? Gender { get; set; }
    public string? City { get; set; }
    public int? Age { get; set; }
    public string? PhotoUrl { get; set; }
}