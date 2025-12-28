namespace FitCorpus.Domain.Entities;

public class OnboardingCode
{
    public Guid Id { get; set; }
    public string Code { get; set; } = string.Empty;
    public Guid TrainerId { get; set; }
    public string Status { get; set; } = "issued";
    public DateTime? ExpiresAt { get; set; }
    public bool PaidByTrainer { get; set; }
    public DateTime CreatedAt { get; set; }
    public DateTime? RedeemedAt { get; set; }
    public Guid? RedeemedByAthleteId { get; set; }
    public TrainerProfile Trainer { get; set; } = null!;
    public User? RedeemedByAthlete { get; set; }
}