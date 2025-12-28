namespace FitCorpus.Domain.Entities;

public class Match
{
    public Guid Id { get; set; }
    public Guid AthleteId { get; set; }
    public Guid TrainerId { get; set; }
    public Guid PackageId { get; set; }
    public string Status { get; set; } = "active";
    public DateTime CreatedAt { get; set; }
    public DateTime? EndedAt { get; set; }
    public User Athlete { get; set; } = null!;
    public User Trainer { get; set; } = null!;
    public Package Package { get; set; } = null!;
}