# Kalah Game

## README Contents

1. Functions
2. Repository Contents
3. Test Infrastructure
4. Makefile

## Functions

The prompt looks like this:
<pre>
Player P1
    (1-6) - house number for move
    n - New game
    s - Save game
    l - Load game
    q - Quit
Choice:
</pre>


### New Game

At any point, either player can restart the game. When this happens, it is as if nothing has happened before this point. So it doesn't matter which player restarts it, P1 will have the first move, and no saves (see below) have taken place.

### Load/Save Game

At any point, either player can save the current state of the game. This will record the state of the board and whose turn it is. At some later point, either player can load the saved game. The game will then proceed from that point. The game can be saved multiple times, but the load will only restore from the most recent save. If a player restarts the game, any saved game is lost.


## Test Infrastructure

The test infrastructure is the same as for previous assignments.

## Makefile

The makefile is the same as for previous assignments.

## Project Summary

Designed and implemented a fully functional two-player Kalah board game in Java, emphasising software quality attributes including maintainability, alterability, and testability. The project involved architecting a clean, modular system from scratch, applying industry-standard design patterns and SOLID principles to produce production-quality code.

Employed the **Strategy and Command patterns** to model game operations (move, new game, save, load, quit) as interchangeable first-class objects, enabling new operations to be added without modifying existing code (Open/Closed Principle). Implemented the **Memento pattern** for game state persistence, serialising board and player state to enable save and restore functionality. Applied the **Facade pattern** through a central `GameControl` class to coordinate complex interactions between the game model, view, and operations. Used the **Null Object pattern** to handle invalid input gracefully, eliminating null checks at call sites and improving robustness.

Maintained strict **Single Responsibility** across all classes — display logic was isolated in `BoardPrinter`, game state in `Game` and `KalahBoard`, and player rules in `Player` — keeping each component focused, readable, and independently modifiable. Centralised all configuration values and user-facing string constants in a dedicated `GameSetting` class, eliminating magic values and making the system easy to reconfigure. Used immutable value objects (`HouseChoice`) with correct `equals` and `hashCode` semantics to ensure correctness and predictability.

Achieved high **testability** by abstracting all I/O behind an injected `IO` interface, allowing the entire game to be driven programmatically without user interaction. Authored over 20 integration test scenarios covering edge cases including seed captures, board wrapping, save/load cycles, full games, ties, and quit operations. Configured a **CI/CD pipeline** using GitHub Actions to automatically execute the full test suite on each push to the submission branch, ensuring continuous verification of correctness.
