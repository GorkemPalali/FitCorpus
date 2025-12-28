using FitCorpus.Application.Interfaces;
using FitCorpus.Domain.Entities;
using FitCorpus.Infrastructure.Data;
using Microsoft.EntityFrameworkCore;

namespace FitCorpus.Application.Services;

public class PackageService : IPackageService
{
    private readonly ApplicationDbContext _dbContext;

    public PackageService(ApplicationDbContext dbContext)
    {
        _dbContext = dbContext;
    }

    public async Task<IEnumerable<Package>> GetPackagesForTrainer(Guid trainerId)
    {
        return await _dbContext.Packages
            .Where(package => package.TrainerId == trainerId)
            .ToListAsync();
    }
}