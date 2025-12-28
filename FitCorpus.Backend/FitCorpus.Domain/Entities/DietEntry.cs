namespace FitCorpus.Domain.Entities;

public class DietEntry
{
    public Guid Id { get; set; }
    public Guid? AthleteId { get; set; }
    public DateOnly Date { get; set; }
    public int Calories { get; set; }
    public string? Meal { get; set; } 
    public string? MealName { get; set; }
    public string? Portion { get; set; }
    public string? Note { get; set; }
    public DateTime CreatedAt { get; set; }
    public DateTime UpdatedAt { get; set; }
    public User? Athlete { get; set; }
}