namespace FitCorpus.Domain.Entities;

public class Package
{
    public Guid Id { get; set; }
    public Guid TrainerId { get; set; }
    public string Title { get; set; } = string.Empty;
    public string? Description { get; set; }
    public int Price { get; set; }
    public int DurationWeeks { get; set; }
    public string Mode { get; set; } = string.Empty; // "online", "f2f"
    public List<string> Services { get; set; } = new();
    public DateTime CreatedAt { get; set; }
    public DateTime UpdatedAt { get; set; }
    public TrainerProfile Trainer { get; set; } = null!;
    public List<Match> Matches { get; set; } = new();
}