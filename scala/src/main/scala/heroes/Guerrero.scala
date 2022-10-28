package heroes

class Guerrero(fuerza: Double, velocidad: Int, nivel: Int)
  extends Aventurero(fuerza, velocidad, nivel) {

  override def fuerza(): Double = fuerza + 1.2 * nivel

}
