namespace FitCorpus.Application.Interfaces;

using FitCorpus.Domain.Entities;

public interface IPlanService
{
    Task<IEnumerable<Plan>> GetPlansForTrainer(Guid trainerId);
}