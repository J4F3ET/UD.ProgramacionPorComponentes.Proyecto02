# Documentación
## Tecnologías
- Kotlin
- Firebase/ Realtime Database
- Jeckpack Compose
## Descripción

Teniendo en cuenta el proyecto01 se usara un sistema de salas para redireccionar a los jugadores al juego, entonces buscamos hacer un gameState, una Sala, 4 jugadores con nombres y colores correspondiente

Evento de click a `piece` cambiara la posición de la celda en el `gameState.board`, descuerdo a que dado uso, ya que al lanzar los dados son 2 (`CurrentThrow`) y cada dado es un evento, si la ficha llego a una ficha contraria se lleva a la cárcel cosas deberá cambiar el estado de la pieza que se fue a al cárcel

Para el tablero se usa en la lógica un list de 100 partes, y se usaran posiciones absolutas para los colores y para que asi se conozca la posición exacta de las fichas de los jugadores.

## Tablero
El tablero es un `LazyVerticalGrid` de composable. Tiene 9 elementos repartidos en 3 columnas, para repartir el tablero en 9 sectores como si fuere una matriz. Cada sector esta posee nomenclatura como `1x1`,`1x2`,`1x3`,`2x1`,`2x2`, etc. Cada sector es un elemento único con posiciones absolutas del tablero.
### Tipos de sectores
1. #### GridCellJail
    Es el sector de la cárcel, donde se encuentran las fichas de los jugadores cuando son capturadas por otras fichas. Es un `Box` con un `LazyVerticalGrid` que contiene en 2 columnas las fichas (de estado `JAIL`) del jugador con determinado color, esto esa construido en componentes `GridCellPiecesJail`, que posee la Imagen de la ficha. Esto permite renderizar los sectores `1x1`, `1X3`, `3x1` y `3x3` del tablero.
2. #### GridHorizontalCellsBoard y GridVerticalCellsBoard
    Son los sectores del tablero donde se encuentran las fichas de los jugadores cuando se encuentran en el tablero.
    - GridHorizontalCellsBoard: Agrupa en filas las celdas del tablero en un `LazyHorizontalGrid` de acuerdo a la lógica de renderizado se invierte para crear los sectores `2x1` y `2x3`
    - GridVerticalCellsBoard: Agrupa en columnas las celdas del tablero en un `LazyVerticalGrid` de acuerdo a la lógica de renderizado se invierte para crear los sectores `1x2` y `3x2`
    
    Estas fichas que se encuentran en el tablero del juego son aquellas que poseen un estado `SAFE` y `DANGER`.
3. #### GridCellZoneWin
    Es el sector de la zona de meta, donde se encuentran las fichas de los jugadores cuando llegan a la meta, pero al mismo tiempo es el sector de unas celdas del tablero.
    - CellWinner: La celda segura de la zona de meta, donde se encuentran las fichas de los jugadores cuando llegan a la meta, pero al mismo tiempo es el sector de unas celdas del tablero.
    - CellZoneWinner: Son las celdas que hacen de giro, entre zonas del tablero por eso posee dos tipos de celdas, las celdas de giro `CellBoardHorizontalMov` y `CellBoardVerticalMov`

    Con esta este sector se puede renderizar el sector `2x2` del tablero.

## Lógica

Para los jugadores, se hizo un formulario dinámico que permite generar el objeto `Player`, y agregarlo a la base de datos, para que asi en un listado de Salas el jugador pueda escoger si en crear una sala, buscar o unirse a una.

Todo esto se hizo usando firebase realtime database, para poder tener un estado del juego en tiempo real, y que los jugadores puedan ver los cambios en el tablero y salas.

Para que se comunique la misma información entre módulos del proyecto se opto por seguir un patrón Singlenton que guardase la información del juego y demás objetos en mismas direcciones de memoria a las cuales acceder en el momento de necesitarlo.

La mayoría de lógica del proyecto se encuentra en los objetos `services` en la clase `UtilGame` y en los mismo composable del tablero.

##  Clases del juego

1. ### Room
```kotlin
data class Room(
    val key: String,
    var players: MutableList<Player>,
    var gameStateKey: String
){
    fun toMap(): HashMap<String, Any> {
        val result = HashMap<String, Any>()
        result["key"] = key
        result["players"] = players.map{ it.toMap() }
        result["gameStateKey"] = gameStateKey
        return result
    }
}
```
Room es la clase que se usara para crear las salas, esta clase tiene un key que es el id de la sala, una lista de jugadores y un gameState que es el estado del juego, esta clase se usara para crear la sala en firebase y para obtener la sala de firebase

2. ### Player
```kotlin
data class Player(
    var uuid: String,
    var name: String,
    val color: ColorP,
    val confirms: Pair<Boolean,Boolean> = Pair(false,false),
    val pieces: MutableList<Piece> = List(4) { Piece(color,0,State.JAIL) }.toMutableList()
){
    fun toMap():HashMap<String,Any>{
        val result = HashMap<String,Any>()
        result["uuid"] = uuid
        result["name"] = name
        result["color"] = color.toString()
        result["confirms"] = confirms
        result["pieces"] = pieces.map { it.toMap()}
        return result
    }
}
```
Player es la clase que se usara para crear los jugadores, esta clase tiene un uiid que es el id del jugador, un nombre, un color y una lista de fichas, esta clase se usara para crear los jugadores en firebase y para obtener los jugadores de firebase. Las piezas son una lista y son una identidad por separado ya que cada jugador tiene 4 fichas y cada ficha tiene un color diferente y se usara para saber el estado de la ficha, si se encuentra fuera de juego o como ficha winner

3. ### GameState
```kotlin
data class GameState(
    var key: String,
    var board: MutableList<BoardCell>,
    var currentThrow:CurrentThrow,
    var countPair:Int = 0,
    var winner: String = "",
){
    fun toMap(): HashMap<String,Any>{
        val result = HashMap<String,Any>()
        result["key"] = this.key
        result["board"] = this.board.map { it.toMap() }
        result["currentThrow"] = this.currentThrow.toMap()
        result["countPair"] = this.countPair
        result["winner"] = winner
        return result
    }

}
```
GameState es la clase que se usara para crear el estado del juego, esta clase tiene un tablero, un jugador actual y un ganador, esta clase se usara para crear el estado del juego en firebase y para obtener el estado del juego de firebase. El tablero es una lista de celdas, el objeto CurrentThrow es son los datos actuales, el countPair es la cantidad de veces que se ha sacado par y el winner es el ganador del juego
 #### CurrentThrow
```kotlin
data class CurrentThrow(
    var player: Player,
    var checkThrow: Boolean = false,
    var checkMovDice: Pair<Boolean,Boolean> = Pair(false,false),
    var dataToDices: Pair<Int,Int> = Pair(0,0)
){
    fun toMap(): HashMap<String,Any>{
        val result = HashMap<String,Any>()
        result["player"] = this.player
        result["checkThrow"] = this.checkThrow
        result["checkMovDice"] = this.checkMovDice
        result["dataToDices"] = this.dataToDices
        return result
    }
}
```
CurrentThrow es la clase que se usara para crear el estado del jugador actual, esta clase tiene un jugador, un checkThrow que es para saber si el jugador ya lanzo los dados, un checkMovDice que es para saber si el jugador ya movio la ficha y un dataToDices que es para saber los datos de los dados

4. ### BoardCell
```kotlin
data class BoardCell(
    val position: Int,
    val pieces: MutableList<Piece> = mutableListOf(),
){
    fun toMap():HashMap<String,Any>{
        val result = HashMap<String,Any>()
        result["position"]= this.position
        result["pieces"] = pieces.map { it.toMap() }
        return result
    }
}
```
BoardCell es la clase que se usara para crear las celdas del tablero, esta clase tiene una posicion y una lista de jugadores, los jugadores hace referencia a fichas que esten hay en esa posicion

La idea es que se quite y se agregen piezas con su color correspondiente, la manera de constatar el la ficha corresponde a ese jugador es con el Color

5. ### Piece
```kotlin
data class Piece(
    val color: ColorP,
    var countStep: Int,
    var state: State
){
    fun toMap(): HashMap<String,Any>{
        val result = HashMap<String,Any>()
        result["color"] = this.color.toString()
        result["countStep"] = this.countStep.toString()
        result["state"]= this.state.toString()
        return result
    }
}
```
La pieza corresponde a las piezas en el tablero, tienen un color y estado, para poder controlar los eventos con respecto al estado.
Las piezas pueden tomar estado como `JAIL` que es cuando la pieza esta en la carcel, `SAFE` que es cuando la pieza esta en el tablero en zona segura y `WINNER` que es cuando la pieza llego a la meta

## Problemas encontrados

### Casos de uso
Al ser un juego los casos de uso o posibles situaciones son significativamente enormes, por ende se presentan problemas en los cuales no se tiene en cuenta la lógica en el código que pueda solucionar el problema

### Realtime Database
El uso de real time database es muy bueno, pero al ser un sistema de base de datos no relacional, se presentan problemas en la lógica de la base de datos, ya que no se puede hacer una consulta de manera sencilla, por ende se debe hacer una lógica en el código para poder obtener la información de la base de datos, de acuerdo a que se usa Singlenton al momento de recibir notificaciones de cambios en algunos nodos de la base de datos, sobrescribe los datos que no debería, por ejemplo el objeto `currentThrow` se sobrescribe con el objeto `player` y se pierde la información del `currentThrow` y demás objetos.