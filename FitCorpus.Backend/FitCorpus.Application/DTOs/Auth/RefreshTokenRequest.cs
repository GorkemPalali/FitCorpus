using System.ComponentModel.DataAnnotations;

namespace FitCorpus.Application.DTOs.Auth;

public class RefreshTokenRequest
{
    [Required]
    public string RefreshToken { get; set; } = string.Empty;
}