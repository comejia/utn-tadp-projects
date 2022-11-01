package viejo

import heroes.Aventurero

class Guerrero(fuerza: Double, velocidad: Int, nivel: Int)
  extends Aventurero(fuerza, velocidad, nivel) {

  override def fuerza(): Double = fuerza + (0.2 * nivel * fuerza)

}
