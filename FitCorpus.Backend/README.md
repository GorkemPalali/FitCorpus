# FitCorpus Backend API

.NET 9.0 ile geliştirilmiş RESTful API backend'i.

## Özellikler
- Entity Framework Core
- JWT Authentication
- SQLite Database
- Swagger UI
- CORS Support
- Clean Architecture

## Kurulum ve Çalıştırma

### Gereksinimler

- .NET 9.0 SDK
- (Opsiyonel) SQL Server (Production için)

### SQLite ile Çalıştırma (Önerilen - Development)

```bash
# Projeyi klonlayın
cd FitCorpus.Backend

# Bağımlılıkları yükleyin
dotnet restore

# Backend'i çalıştırın
dotnet run --project FitCorpus.Api/FitCorpus.Api.csproj
```

Backend `http://localhost:8080` adresinde çalışacak.

Swagger UI: `http://localhost:8080/swagger`

### SQL Server ile Çalıştırma (Production)

1. `appsettings.json` dosyasını düzenleyin:

```json
{
  "ConnectionStrings": {
    "DefaultConnection": "Server=YOUR_SERVER;Database=FitCorpusDb;User Id=YOUR_USER;Password=YOUR_PASSWORD;TrustServerCertificate=True;"
  }
}
```

2. `Program.cs` dosyasında SQLite yerine SQL Server kullanın:

```csharp
builder.Services.AddDbContext<ApplicationDbContext>(options =>
    options.UseSqlServer(connectionString));
```

3. Migration oluşturun ve uygulayın:

```bash
dotnet ef migrations add InitialCreate --project FitCorpus.Infrastructure --startup-project FitCorpus.Api
dotnet ef database update --project FitCorpus.Infrastructure --startup-project FitCorpus.Api
```

## API Endpoints

### Authentication

- `POST /v1/auth/register` - Kullanıcı kaydı
- `POST /v1/auth/login` - Giriş
- `POST /v1/auth/refresh` - Token yenileme
- `POST /v1/auth/logout` - Çıkış
- `GET /v1/auth/me` - Mevcut kullanıcı bilgisi

## Yapılandırma

### Port Ayarları

Port ayarları `Properties/launchSettings.json` dosyasında yapılandırılmıştır. Varsayılan port: **8080**

## Database

SQLite kullanıldığında, database dosyası `FitCorpus.Api/FitCorpus.db` konumunda otomatik oluşturulur.

### Database bağlantı hatası

- SQLite kullanıyorsanız: `FitCorpus.db` dosyasının yazma izinlerini kontrol edin
- SQL Server kullanıyorsanız: Connection string'i ve SQL Server servisinin çalıştığını kontrol edin
