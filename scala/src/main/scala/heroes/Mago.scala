package heroes

class Mago(fuerza: Double, velocidad: Int, val nivelAprendizaje: Int)
  extends Aventurero(fuerza, velocidad, nivelAprendizaje) {

  var nivelAlcanzado = 0

  def puedeUsarHechizo(): Boolean = nivelAlcanzado >= nivelAprendizaje
}
