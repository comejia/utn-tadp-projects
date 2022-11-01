package viejo

// Usando herencia
class Aventurero(private val _fuerza: Double, val _velocidad: Int, var nivel: Int, var salud: Int = 100) {

  def fuerza(): Double = _fuerza

  def muerto(): Boolean = this.salud <= 0

}

case class Guerrero(fuerza: Double, velocidad: Int, nivel: Int)
  extends Aventurero(fuerza, velocidad, nivel) {

  override def fuerza(): Double = fuerza + (0.2 * nivel * fuerza)

}

case class Ladron(fuerza: Double, velocidad: Int, nivel: Int, private var _habilidad: Int)
  extends Aventurero(fuerza, velocidad, nivel) {

  def habilidad(): Int = _habilidad + 3 * nivel
}
