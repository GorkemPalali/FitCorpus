using FitCorpus.Application.Interfaces;
using FitCorpus.Domain.Entities;
using FitCorpus.Domain.Enums;
using FitCorpus.Infrastructure.Data;
using Microsoft.EntityFrameworkCore;

namespace FitCorpus.Application.Services;

public class DiscoverService : IDiscoverService
{
    private readonly ApplicationDbContext _dbContext;

    public DiscoverService(ApplicationDbContext dbContext)
    {
        _dbContext = dbContext;
    }

    public async Task<IEnumerable<User>> GetTrainers()
    {
        return await _dbContext.Users
            .Where(user => user.Role == UserRole.Trainer)
            .ToListAsync();
    }
}