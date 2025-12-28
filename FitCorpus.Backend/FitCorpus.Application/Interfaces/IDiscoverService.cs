namespace FitCorpus.Application.Interfaces;

using FitCorpus.Domain.Entities;

public interface IDiscoverService
{
    Task<IEnumerable<User>> GetTrainers();
}