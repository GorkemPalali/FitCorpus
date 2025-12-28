namespace FitCorpus.Application.DTOs.Trainer;

public class TrainerResponseDto
{
    public string Id { get; set; } = string.Empty;
    public string Name { get; set; } = string.Empty;
    public string Surname { get; set; } = string.Empty;
    public string? PhotoUrl { get; set; }
    public double RatingAvg { get; set; }
    public int PriceFrom { get; set; }
    public List<string> Modes { get; set; } = new();
    public string? Bio { get; set; }
    public List<string>? Achievements { get; set; }
    public string? Gender { get; set; }
}