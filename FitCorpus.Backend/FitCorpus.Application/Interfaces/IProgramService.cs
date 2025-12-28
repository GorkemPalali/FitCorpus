namespace FitCorpus.Application.Interfaces;

using FitCorpus.Domain.Entities;

public interface IProgramService
{
    Task<IEnumerable<Plan>> GetProgramsForAthlete(Guid athleteId);
}