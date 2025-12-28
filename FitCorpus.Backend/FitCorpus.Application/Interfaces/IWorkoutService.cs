namespace FitCorpus.Application.Interfaces;

using FitCorpus.Domain.Entities;

public interface IWorkoutService
{
    Task<IEnumerable<WorkoutEntry>> GetWorkoutsForAthlete(Guid athleteId);
    Task AddWorkout(Guid athleteId, WorkoutEntry workoutEntry);
}