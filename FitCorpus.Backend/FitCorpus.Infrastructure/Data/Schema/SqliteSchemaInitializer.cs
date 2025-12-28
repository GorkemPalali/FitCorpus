using System;
using System.Collections.Generic;
using System.Data.Common;
using System.Threading.Tasks;
using FitCorpus.Infrastructure.Data;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;

namespace FitCorpus.Infrastructure.Schema;

public interface IDatabaseSchemaInitializer
{
    Task EnsureSchemaAsync();
}

public class SqliteSchemaInitializer : IDatabaseSchemaInitializer
{
    private readonly ApplicationDbContext _dbContext;
    private readonly ILogger<SqliteSchemaInitializer> _logger;

    public SqliteSchemaInitializer(
        ApplicationDbContext dbContext,
        ILogger<SqliteSchemaInitializer> logger)
    {
        _dbContext = dbContext;
        _logger = logger;
    }

    public async Task EnsureSchemaAsync()
    {
        var connection = _dbContext.Database.GetDbConnection();
        await connection.OpenAsync();
        try
        {
            await EnsureColumnAsync(connection, "Users", "City", "TEXT NULL");
            await EnsureColumnAsync(connection, "Users", "Gender", "TEXT NULL");
        }
        finally
        {
            await connection.CloseAsync();
        }
    }

    private async Task EnsureColumnAsync(
        DbConnection connection,
        string tableName,
        string columnName,
        string columnDefinition)
    {
        if (await ColumnExistsAsync(connection, tableName, columnName))
        {
            return;
        }

        _logger.LogInformation(
            "Adding missing column {Column} to table {Table}",
            columnName,
            tableName);

        await using var command = connection.CreateCommand();
        command.CommandText = $"ALTER TABLE {tableName} ADD COLUMN {columnName} {columnDefinition};";
        await command.ExecuteNonQueryAsync();
    }

    private static async Task<bool> ColumnExistsAsync(
        DbConnection connection,
        string tableName,
        string columnName)
    {
        var existingColumns = new HashSet<string>(StringComparer.OrdinalIgnoreCase);

        await using var command = connection.CreateCommand();
        command.CommandText = $"PRAGMA table_info('{tableName}');";

        await using var reader = await command.ExecuteReaderAsync();
        while (await reader.ReadAsync())
        {
            existingColumns.Add(reader.GetString(1));
        }

        return existingColumns.Contains(columnName);
    }
}