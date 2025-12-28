using FitCorpus.Application.Interfaces;
using FitCorpus.Domain.Entities;
using FitCorpus.Infrastructure.Data;
using Microsoft.EntityFrameworkCore;

namespace FitCorpus.Application.Services;

public class ProgramService : IProgramService
{
    private readonly ApplicationDbContext _dbContext;

    public ProgramService(ApplicationDbContext dbContext)
    {
        _dbContext = dbContext;
    }

    public async Task<IEnumerable<Plan>> GetProgramsForAthlete(Guid athleteId)
    {
        return await _dbContext.Plans
            .Where(plan => plan.TrainerId == athleteId)
            .ToListAsync();
    }
}