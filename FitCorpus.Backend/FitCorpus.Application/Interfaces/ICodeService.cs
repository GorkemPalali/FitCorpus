namespace FitCorpus.Application.Interfaces;

using FitCorpus.Domain.Entities;

public interface ICodeService
{
    Task<IEnumerable<OnboardingCode>> GetCodesForTrainer(Guid trainerId);
}