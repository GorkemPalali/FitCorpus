namespace FitCorpus.Domain.Entities;

public class TrainerProfile
{
    public Guid Id { get; set; }
    public Guid UserId { get; set; }
    public string? Bio { get; set; }
    public List<string> Achievements { get; set; } = new();
    public double RatingAvg { get; set; }
    public int PriceFrom { get; set; }
    public List<string> Modes { get; set; } = new();
    public DateTime CreatedAt { get; set; }
    public DateTime UpdatedAt { get; set; }
    public User User { get; set; } = null!;
    public List<Package> Packages { get; set; } = new();
    public List<Plan> Plans { get; set; } = new();
    public List<OnboardingCode> Codes { get; set; } = new();
    public string? Location { get; set; }

    public TrainerProfile()
    {
        Achievements = new List<string>();
        Modes = new List<string>();
    }

    public override bool Equals(object? obj)
    {
        if (obj is not TrainerProfile other) return false;
        return Id == other.Id &&
               UserId == other.UserId &&
               Bio == other.Bio &&
               Achievements.SequenceEqual(other.Achievements) &&
               RatingAvg.Equals(other.RatingAvg) &&
               PriceFrom == other.PriceFrom &&
               Modes.SequenceEqual(other.Modes) &&
               CreatedAt == other.CreatedAt &&
               UpdatedAt == other.UpdatedAt;
    }

    public override int GetHashCode()
    {
        return HashCode.Combine(
                   Id, UserId, Bio, Achievements, RatingAvg, PriceFrom, Modes) ^
               HashCode.Combine(CreatedAt, UpdatedAt);
    }
}