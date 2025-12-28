using FitCorpus.Application.Interfaces;
using FitCorpus.Domain.Entities;
using FitCorpus.Infrastructure.Data;
using Microsoft.EntityFrameworkCore;

namespace FitCorpus.Application.Services;

public class WorkoutService : IWorkoutService
{
    private readonly ApplicationDbContext _dbContext;

    public WorkoutService(ApplicationDbContext dbContext)
    {
        _dbContext = dbContext;
    }

    public async Task<IEnumerable<WorkoutEntry>> GetWorkoutsForAthlete(Guid athleteId)
    {
        return await _dbContext.WorkoutEntries
            .Where(we => we.AthleteId == athleteId)
            .ToListAsync();
    }

    public async Task AddWorkout(Guid athleteId, WorkoutEntry workoutEntry)
    {
        workoutEntry.AthleteId = athleteId;
        workoutEntry.CreatedAt = DateTime.UtcNow;
        workoutEntry.UpdatedAt = DateTime.UtcNow;

        await _dbContext.WorkoutEntries.AddAsync(workoutEntry);
        await _dbContext.SaveChangesAsync();
    }
}