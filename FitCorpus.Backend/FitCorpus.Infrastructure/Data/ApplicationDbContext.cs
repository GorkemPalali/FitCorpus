using FitCorpus.Domain.Entities;
using Microsoft.EntityFrameworkCore;

namespace FitCorpus.Infrastructure.Data;

public class ApplicationDbContext : DbContext
{
    public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options) : base(options)
    {
    }

    public DbSet<User> Users { get; set; }
    public DbSet<RefreshToken> RefreshTokens { get; set; }
    public DbSet<TrainerProfile> TrainerProfiles { get; set; }
    public DbSet<Package> Packages { get; set; }
    public DbSet<Match> Matches { get; set; }
    public DbSet<WorkoutEntry> WorkoutEntries { get; set; }
    public DbSet<DietEntry> DietEntries { get; set; }
    public DbSet<Plan> Plans { get; set; }
    public DbSet<PlanExercise> PlanExercises { get; set; }
    public DbSet<OnboardingCode> OnboardingCodes { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);


        modelBuilder.Entity<User>(entity =>
        {
            entity.HasKey(e => e.Id);
            entity.HasIndex(e => e.Email).IsUnique();
            entity.Property(e => e.Email).IsRequired().HasMaxLength(255);
            entity.Property(e => e.Name).IsRequired().HasMaxLength(100);
            entity.Property(e => e.Surname).IsRequired().HasMaxLength(100);
            entity.Property(e => e.PasswordHash).IsRequired();
            
            entity.HasOne(e => e.TrainerProfile)
                .WithOne(e => e.User)
                .HasForeignKey<TrainerProfile>(e => e.UserId)
                .OnDelete(DeleteBehavior.Cascade);
        });


        modelBuilder.Entity<TrainerProfile>(entity =>
        {
            entity.HasKey(e => e.Id);
            entity.HasIndex(e => e.UserId).IsUnique();
            
            entity.Property(e => e.Achievements)
                .HasConversion(
                    v => string.Join(',', v),
                    v => v.Split(',', StringSplitOptions.RemoveEmptyEntries).ToList());
            
            entity.Property(e => e.Modes)
                .HasConversion(
                    v => string.Join(',', v),
                    v => v.Split(',', StringSplitOptions.RemoveEmptyEntries).ToList());
        });


        modelBuilder.Entity<WorkoutEntry>(entity =>
        {
            entity.HasKey(e => e.Id);
            entity.HasIndex(e => e.ClientRequestId).IsUnique();
            entity.HasIndex(e => new { e.AthleteId, e.Date });
            
            entity.Property(e => e.Reps)
                .HasConversion(
                    v => string.Join(',', v),
                    v => v.Split(',', StringSplitOptions.RemoveEmptyEntries)
                        .Select(int.Parse).ToList());
            
            entity.Property(e => e.Weight)
                .HasConversion(
                    v => string.Join(',', v),
                    v => v.Split(',', StringSplitOptions.RemoveEmptyEntries)
                        .Select(float.Parse).ToList());
        });


        modelBuilder.Entity<PlanExercise>(entity =>
        {
            entity.HasKey(e => e.Id);
            
            entity.Property(e => e.Reps)
                .HasConversion(
                    v => string.Join(',', v),
                    v => v.Split(',', StringSplitOptions.RemoveEmptyEntries)
                        .Select(int.Parse).ToList());
            
            entity.Property(e => e.Weight)
                .HasConversion(
                    v => string.Join(',', v),
                    v => v.Split(',', StringSplitOptions.RemoveEmptyEntries)
                        .Select(float.Parse).ToList());
        });

        
        modelBuilder.Entity<OnboardingCode>(entity =>
        {
            entity.HasKey(e => e.Id);
            entity.HasIndex(e => e.Code).IsUnique();
        });
    }
}