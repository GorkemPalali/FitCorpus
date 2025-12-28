namespace FitCorpus.Domain.Entities;

public class Plan
{
    public Guid Id { get; set; }
    public Guid? TrainerId { get; set; }
    public string Name { get; set; } = string.Empty;
    public string Type { get; set; } = string.Empty; // "workout", "diet"
    public string? Description { get; set; }
    public List<PlanExercise> Exercises { get; set; } = new();
    public DateTime CreatedAt { get; set; }
    public DateTime UpdatedAt { get; set; }
    public TrainerProfile? Trainer { get; set; }
    public List<WorkoutEntry> WorkoutEntries { get; set; } = new();
}

public class PlanExercise
{
    public Guid Id { get; set; }
    public Guid PlanId { get; set; }
    public string ExerciseName { get; set; } = string.Empty;
    public int Sets { get; set; }
    public List<int> Reps { get; set; } = new();
    public List<float> Weight { get; set; } = new();
    public int? RestSeconds { get; set; }
    public Plan Plan { get; set; } = null!;

    public PlanExercise()
    {
        Reps = new List<int>();
        Weight = new List<float>();
    }

    public override bool Equals(object? obj)
    {
        if (obj is not PlanExercise other) return false;
        return Id == other.Id &&
               PlanId == other.PlanId &&
               ExerciseName == other.ExerciseName &&
               Sets == other.Sets &&
               Reps.SequenceEqual(other.Reps) &&
               Weight.SequenceEqual(other.Weight) &&
               RestSeconds == other.RestSeconds;
    }

    public override int GetHashCode()
    {
        return HashCode.Combine(Id, PlanId, ExerciseName, Sets, Reps, Weight, RestSeconds);
    }
}