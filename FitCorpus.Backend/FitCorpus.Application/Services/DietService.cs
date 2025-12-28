using FitCorpus.Application.Interfaces;
using FitCorpus.Domain.Entities;
using FitCorpus.Infrastructure.Data;
using Microsoft.EntityFrameworkCore;

namespace FitCorpus.Application.Services;

public class DietService : IDietService
{
    private readonly ApplicationDbContext _dbContext;

    public DietService(ApplicationDbContext dbContext)
    {
        _dbContext = dbContext;
    }

    public async Task<IEnumerable<DietEntry>> GetDietEntriesForAthlete(Guid athleteId)
    {
        return await _dbContext.DietEntries
            .Where(de => de.AthleteId == athleteId)
            .ToListAsync();
    }

    public async Task AddDietEntry(Guid athleteId, DietEntry dietEntry)
    {
        dietEntry.AthleteId = athleteId;
        dietEntry.CreatedAt = DateTime.UtcNow;
        dietEntry.UpdatedAt = DateTime.UtcNow;

        await _dbContext.DietEntries.AddAsync(dietEntry);
        await _dbContext.SaveChangesAsync();
    }
}