# Documentación
## Tecnologías
- Kotlin
- Firebase/ Realtime Database
- Jeckpack Compose
## Descripción

Teniendo en cuenta el proyecto01 se usalara un sistema de salas para redireccionar a los jugadores al juego, entonces buscamos hacer un gameState, una Sala, 4 jugadores con nombres y colores correspondiente

Evento de click a `piece` cambiara la posicion de la celda en el `gameState.board`, deacuerdo a que dado uso, ya que al lanzar los dados son 2 y cada dado es un evento, si la ficha llego a una ficha contraria se lleva a la cárcel? o genera bloqueo? cosas por ver, pero debera cambiar el estado de la pieza, en `gameState.currentPlayer.pieces[#].state`

Para el tablero se usa en la logica un list de N partes, y se usaran posiciones absoluctas para los colores, ejemplo si es rojo inicia si o si en X posicion y su posicion final sera Y, para que asi conocer la posicion exacta de las fichas de los jugadores

Pantalla sera dividada en 3 secciones
|CARCEL|TABLERO|CARCEL|
|----|----|----|
|TABLERO|WIINNER|TABLERO|
|CARCEL|TABLERO|CARCEL|

En cualquier seccion hay tablero en todas las secciones hay celdas tablero, pero cada una tiene zonas especiales donde el state de las piezas cambian 

## Ideas

Las piesas `pieces` en la parte grafica deberan ser botones, asi cuando se selecciona la pieza del juegador actual `gameState.currentPlayer` cambiara la celada de la pieza, de igual manera

##  Clases del juego
1. ### Room
```kotlin
    data class Room(
        var key: String = "",
        val players: List<Player?>,
        val gameState: GameState?
    )
```
Room es la clase que se usara para crear las salas, esta clase tiene un key que es el id de la sala, una lista de jugadores y un gameState que es el estado del juego, esta clase se usara para crear la sala en firebase y para obtener la sala de firebase

2. ### Player
```kotlin
    data class Player(
        val uiid: String,
        val name: String,
        val color: Color,
        val pieces: List<Piece> = List(4) { Piece(color) }
    )

```
Player es la clase que se usara para crear los jugadores, esta clase tiene un uiid que es el id del jugador, un nombre, un color y una lista de fichas, esta clase se usara para crear los jugadores en firebase y para obtener los jugadores de firebase. Las piezas son una lista y son una identidad por separado ya que cada jugador tiene 4 fichas y cada ficha tiene un color diferente y se usara para saber el estado de la ficha, si se encuentra fuera de juego o como ficha winner

3. ### GameState
```kotlin
    data class GameState(
        data class GameState(
        var board: List<BoardCell>,
        var currentPlayer: Player,
        var winner: Player?,
)
    )
```
GameState es la clase que se usara para crear el estado del juego, esta clase tiene un tablero, un jugador actual y un ganador, esta clase se usara para crear el estado del juego en firebase y para obtener el estado del juego de firebase. El tablero es una lista de celdas, el jugador actual es el jugador que le toca jugar y el ganador es el jugador que gano el juego

4. ### BoardCell
```kotlin
    data class BoardCell(
        val position: Int,
        val pieces: MutableList<Piece>,
    )
```
BoardCell es la clase que se usara para crear las celdas del tablero, esta clase tiene una posicion y una lista de jugadores, los jugadores hace referencia a fichas que esten hay en esa posicion

La idea es que se quite y se agregen piezas con su color correspondiente, la manera de constatar el la ficha corresponde a ese jugador es con el Color

5. ### Piece
```kotlin
    data class Piece(
        val color: Color,
        var state: State,
    )
    enum class State {JAIL,SAFE,DANGER,WINNER}
```
La pieza corresponde a las piezas en el tablero, tienen un color y estado, para poder controlar los eventos con respecto al estado.
Las piezas pueden tomar estado como `JAIL` que es cuando la pieza esta en la carcel, `SAFE` que es cuando la pieza esta en el tablero en zona segura y `WINNER` que es cuando la pieza llego a la meta