using FitCorpus.Application.DTOs.Auth;

namespace FitCorpus.Application.Interfaces;

public interface IAuthService
{
    Task<TokenResponse> RegisterAsync(RegisterRequest request);
    Task<TokenResponse> LoginAsync(LoginRequest request);
    Task<TokenResponse> RefreshTokenAsync(RefreshTokenRequest request);
    Task<UserResponse> GetCurrentUserAsync(Guid userId);
    Task LogoutAsync(Guid userId, string refreshToken);
}