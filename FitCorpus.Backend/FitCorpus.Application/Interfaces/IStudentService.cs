namespace FitCorpus.Application.Interfaces;

using FitCorpus.Domain.Entities;

public interface IStudentService
{
    Task<IEnumerable<User>> GetStudentsForTrainer(Guid trainerId);
}