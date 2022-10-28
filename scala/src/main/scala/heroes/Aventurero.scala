package heroes

class Aventurero(private val _fuerza: Double, val _velocidad: Int, var nivel: Int, var salud: Int = 100) {

  def fuerza(): Double = _fuerza

  def muerto(): Boolean = this.salud <= 0

}