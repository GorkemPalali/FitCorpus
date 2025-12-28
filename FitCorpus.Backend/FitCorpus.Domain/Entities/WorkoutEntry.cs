namespace FitCorpus.Domain.Entities;

public class WorkoutEntry
{
    public Guid Id { get; set; }
    public string ClientRequestId { get; set; } = string.Empty;
    public Guid? AthleteId { get; set; }
    public Guid? PlanId { get; set; }
    public DateOnly Date { get; set; }
    public string Movement { get; set; } = string.Empty;
    public int Sets { get; set; }
    public List<int> Reps { get; set; } = new();
    public List<float> Weight { get; set; } = new();
    public int? Rpe { get; set; }
    public string? AssignedBy { get; set; }
    public int? RestSeconds { get; set; }
    public DateTime CreatedAt { get; set; }
    public DateTime UpdatedAt { get; set; }
    public User? Athlete { get; set; }
    public Plan? Plan { get; set; }

    public WorkoutEntry()
    {
        Reps = new List<int>();
        Weight = new List<float>();
    }

    public override bool Equals(object? obj)
    {
        if (obj is not WorkoutEntry other) return false;
        return Id == other.Id &&
               ClientRequestId == other.ClientRequestId &&
               AthleteId == other.AthleteId &&
               PlanId == other.PlanId &&
               Date == other.Date &&
               Movement == other.Movement &&
               Sets == other.Sets &&
               Reps.SequenceEqual(other.Reps) &&
               Weight.SequenceEqual(other.Weight) &&
               Rpe == other.Rpe &&
               AssignedBy == other.AssignedBy &&
               RestSeconds == other.RestSeconds &&
               CreatedAt == other.CreatedAt &&
               UpdatedAt == other.UpdatedAt;
    }

    public override int GetHashCode()
    {
        return HashCode.Combine(
                   Id, ClientRequestId, AthleteId, PlanId, Date, Movement, Sets) ^
               HashCode.Combine(Reps, Weight, Rpe, AssignedBy, RestSeconds, CreatedAt, UpdatedAt);
    }
}