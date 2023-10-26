package com.example.udprogramacionporcomponentes02proyecto.util

enum class RulesGame (val description: String){
    RULE1("Cada jugador tiene 4 fichas que deben llevar a su área segura y ganar el juego."),
    RULE2("Comienzas el juego en la base y debes sacar un 5 para mover una ficha a la casilla de inicio."),
    RULE3("Debes mover tus fichas en sentido horario y avanzar el número de casillas equivalente al valor del dado."),
    RULE4("Si caes en una casilla ocupada por una ficha de otro jugador, la envías a su base."),
    RULE5("Si sacas un 6, puedes sacar una ficha de la base o mover una ficha 6 casillas."),
    RULE6("Ganas al llevar tus 4 fichas a tu área segura y completar una vuelta alrededor del tablero."),
    RULE7("Puedes comer a otras fichas si las alcanzas con el número exacto en tu dado."),
    RULE8("Si sacas tres 6 seguidos, pierdes tu turno y vuelves a tu base.");
    override fun toString(): String {
        return description
    }
}