from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from django.shortcuts import render

# Global list to hold the current open matches (in-memory storage)
current_game_code = 1000  # Corrected assignment operator
games = []

# Create match view
@csrf_exempt
def create_game(request):
    global current_game_code  # To modify the global variable

    new_game = {'game_code': current_game_code, 'status': 'open'}
    current_game_code += 1  # Increment match number
    games.append(new_game)

    return JsonResponse({'message': f'Match {new_game["game_code"]} created successfully!', 'match': new_game})

@csrf_exempt
def join_game(request):
    # Get match_id from query parameters
    print("Testing join_game")
    game_code = request.GET.get('game_code')  # Ensure URL uses `match_id`

    if not game_code:
        return JsonResponse({'message': 'game_code parameter is missing!'}, status=400)

    try:
        game_code = int(game_code)  # Convert to integer
    except ValueError:
        return JsonResponse({'message': 'game_code must be an integer!'}, status=400)

    # Search for the match by match_id
    for game in games:
        if game["game_code"] == game_code:
            if game["status"] == "running":
                return JsonResponse({'message': f'Couldn\'t join match {game_code} because it\'s already running!', 'match': None})
            if game["status"] == "open":
                game["status"] = "running"
                return JsonResponse({'message': f'Joined match {game_code} successfully!', 'match': game})
            return JsonResponse({'message': f'Couldn\'t join match {game_code} because status is {game["status"]}!', 'match': None})

    return JsonResponse({'message': 'Match not found!'}, status=404)