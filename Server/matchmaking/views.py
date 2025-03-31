from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from django.shortcuts import get_object_or_404
from .models import Game, Player

max_players = 2  # Maximum players per match

@csrf_exempt
def create_game(request):
    """Creates a new game and stores it in the database with a player."""
    # Get the player name from the request
    player_name = request.GET.get('player_name')

    if not player_name:
        return JsonResponse({
            'message': 'Player name is required to create a game.'
        }, status=400)

    # Create a new game entry and add the player
    new_game = Game.objects.create()
    player = Player.objects.create(name=player_name)
    new_game.players.add(player)  # Assuming 'players' is a list or array field
    new_game.save()  # Save the game with the player added

    return JsonResponse({
        'message': f'Game created successfully!',
        'game_code': new_game.game_code  # Send back the game code for the client to use
    })


@csrf_exempt
def join_game(request):
    """Joins an existing game if it's still open."""
    game_code = request.GET.get('game_code')
    player_name = request.GET.get('player_name')

    if not game_code or not player_name:
        return JsonResponse({'message': 'Missing game_code or player_name!'}, status=400)

    try:
        game_code = int(game_code)
    except ValueError:
        return JsonResponse({'message': 'game_code must be an integer!'}, status=400)

    # Retrieve game from the database
    game = get_object_or_404(Game, game_code=game_code)
    
    # Check if game is open
    if game.status == "running":
        return JsonResponse({'message': f'Couldn\'t join match {game_code} because it\'s already started!',}, status=403)

    
    # Check if game is full
    if game.players.count() >= max_players:
        return JsonResponse({'message': f'Game {game_code} is full!'}, status=409)

    # Check if game already has player with the same name
    if game.players.filter(name=player_name).exists():
        return JsonResponse({'message': f'Player {player_name} is already in the game!'}, status=422)


    # Add player and update status
    player = Player.objects.create(name=player_name)
    game.players.add(player)
    game.save()  # Save changes to DB

    return JsonResponse({
        'message': f'Joined match {game_code} successfully!'
    })