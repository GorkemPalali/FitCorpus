using FitCorpus.Application.Interfaces;
using FitCorpus.Domain.Entities;
using FitCorpus.Infrastructure.Data;
using Microsoft.EntityFrameworkCore;

namespace FitCorpus.Application.Services;

public class StudentService : IStudentService
{
    private readonly ApplicationDbContext _dbContext;

    public StudentService(ApplicationDbContext dbContext)
    {
        _dbContext = dbContext;
    }

    public async Task<IEnumerable<User>> GetStudentsForTrainer(Guid trainerId)
    {
        return await _dbContext.Users
            .Where(user => user.TrainerProfile != null && user.TrainerProfile.Id == trainerId)
            .ToListAsync();
    }
}