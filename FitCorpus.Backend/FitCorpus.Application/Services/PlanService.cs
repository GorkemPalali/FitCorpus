using FitCorpus.Application.Interfaces;
using FitCorpus.Domain.Entities;
using FitCorpus.Infrastructure.Data;
using Microsoft.EntityFrameworkCore;

namespace FitCorpus.Application.Services;

public class PlanService : IPlanService
{
    private readonly ApplicationDbContext _dbContext;

    public PlanService(ApplicationDbContext dbContext)
    {
        _dbContext = dbContext;
    }

    public async Task<IEnumerable<Plan>> GetPlansForTrainer(Guid trainerId)
    {
        return await _dbContext.Plans
            .Where(plan => plan.TrainerId == trainerId)
            .ToListAsync();
    }
}