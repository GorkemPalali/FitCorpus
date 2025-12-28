namespace FitCorpus.Application.Interfaces;

using FitCorpus.Domain.Entities;

public interface IPackageService
{
    Task<IEnumerable<Package>> GetPackagesForTrainer(Guid trainerId);
}