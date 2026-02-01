using Microsoft.EntityFrameworkCore;
using HireHubServer.Data;
using HireHubServer.Services.Interfaces;
using HireHubServer.Services.Implementations;
using Pomelo.EntityFrameworkCore.MySql.Infrastructure; // ✅ Important

var builder = WebApplication.CreateBuilder(args);

// Configure CORS
builder.Services.AddCors(options =>
{
  options.AddPolicy("AllowLocalhost", policy =>
  {
    policy.WithOrigins("http://localhost:3000")
            .AllowAnyHeader()
            .AllowAnyMethod();
  });
});

// Add controllers and Swagger
builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

// Configure EF Core with Pomelo MySQL
var connectionString = builder.Configuration.GetConnectionString("DefaultConnection");

builder.Services.AddDbContext<ApplicationDbContext>(options =>
{
  options.UseMySql(connectionString, ServerVersion.AutoDetect(connectionString));
  options.LogTo(Console.WriteLine);
});

// Register your services
builder.Services.AddScoped<IApplicationWorkflowService, ApplicationWorkflowService>();
builder.Services.AddScoped<IReferralService, ReferralService>();

// Logging
builder.Services.AddLogging(config =>
{
  config.AddConsole();
  config.AddDebug();
});

var app = builder.Build();

// Middleware
if (app.Environment.IsDevelopment())
{
  app.UseSwagger();
  app.UseSwaggerUI();
}

if (!app.Environment.IsDevelopment())
{
  app.UseHttpsRedirection();
}

app.UseCors("AllowLocalhost");
app.UseAuthorization();
app.MapControllers();

Console.WriteLine("🚀 HireHub .NET Server starting...");
app.Run();
