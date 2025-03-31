from django.db import models


class Player(models.Model):
    name = models.CharField(max_length=100)

    def __str__(self):
        return self.name

class Game(models.Model):
    STATUS_CHOICES = [
        ('open', 'Open'),
        ('running', 'Running'),
        ('finished', 'Finished'),
    ]

    game_code = models.BigAutoField(primary_key=True)
    status = models.CharField(max_length=10, choices=STATUS_CHOICES, default='open')
    players = models.ManyToManyField(Player, related_name="games")  # More efficient than JSONField

    def __str__(self):
        return f"Game {self.game_code} - Status: {self.status}"
