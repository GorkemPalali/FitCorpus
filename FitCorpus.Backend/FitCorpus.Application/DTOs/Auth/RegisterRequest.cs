using System.ComponentModel.DataAnnotations;

namespace FitCorpus.Application.DTOs.Auth;

public class RegisterRequest
{
    [Required]
    public string Name { get; set; } = string.Empty;
    
    [Required]
    public string Surname { get; set; } = string.Empty;
    
    [Required]
    [EmailAddress]
    public string Email { get; set; } = string.Empty;
    
    [Required]
    [MinLength(6)]
    public string Password { get; set; } = string.Empty;
    
    [Required]
    public string Role { get; set; } = string.Empty;
    
    public string? City { get; set; }
    
    public string? Gender { get; set; }
}