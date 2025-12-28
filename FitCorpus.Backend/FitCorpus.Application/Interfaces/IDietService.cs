namespace FitCorpus.Application.Interfaces;

using FitCorpus.Domain.Entities;

public interface IDietService
{
    Task<IEnumerable<DietEntry>> GetDietEntriesForAthlete(Guid athleteId);
    Task AddDietEntry(Guid athleteId, DietEntry dietEntry);
}