using FitCorpus.Application.Interfaces;
using FitCorpus.Domain.Entities;
using FitCorpus.Infrastructure.Data;
using Microsoft.EntityFrameworkCore;

namespace FitCorpus.Application.Services;

public class CodeService : ICodeService
{
    private readonly ApplicationDbContext _dbContext;

    public CodeService(ApplicationDbContext dbContext)
    {
        _dbContext = dbContext;
    }

    public async Task<IEnumerable<OnboardingCode>> GetCodesForTrainer(Guid trainerId)
    {
        return await _dbContext.OnboardingCodes
            .Where(code => code.TrainerId == trainerId)
            .ToListAsync();
    }
}