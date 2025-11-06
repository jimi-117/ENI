# Bleak Falls Dungeon

This is simple CLI RPG game that is inspired by skyrim's dungeon named "Bleak Falls Barrow".

# Objective

Explore into this barrow made by 5 rooms and to get Golden Dragon Crow.

# Architecture

## MVP


<pre>
 dungeonquest
    ├── README.mdq
    ├── game                # Game logics
    │   ├── dungeon.go
    │   ├── engine.go
    │   ├── factory.go
    │   └── player.go
    ├── go.mod
    ├── main.go             # Entry point
    └── objects             # Domaine objects to generate (Targets of factory and clone(prototype))
        └── objets.go
</pre>

## Target architecrture
<pre>
<strong>dungeonquest</strong>/
│   go.mod
│   README.md
│
├───cmd                          # <strong>Application layer</strong> 
│       main.go                  # Entry point
│
└───internal                     # <strong>Internal packages</strong>
    ├───domain                   # Business logic core layer
    │       entity.go            # Game objects (e.g. Player, Room, etc.)
    │       repository.go        # Repository Interface
    │
    ├───factory                  # <strong>Factory layer for objects generation</strong>
    │       builder.go           # Builder pattern
    │       object_factory.go    # Factory methodes
    │       prototype.go         # Prototype pattern (clone)
    │       theme_factory.go     # Abstract factory
    │
    ├───presentation             # <strong>Presentaion layer</strong>
    │       cli.go               # UI (CLI)
    └───service                  # Services layer
            dungeon_service.go   # Generation dungeon logic
            game_manager.go      # Game manager (Singleton)
</pre>