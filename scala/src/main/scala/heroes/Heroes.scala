package heroes

case class Aventurero(caracteristica: Caracteristica, trabajo: Trabajo) {
  def fuerza(): Double = trabajo.fuerza(caracteristica)

  def muerto(): Boolean = caracteristica.salud <= 0

  def abrir(puerta: Puerta, cofre: Set[Item]): Boolean = {
    trabajo.abrir(puerta, cofre, caracteristica)
  }
}

case class Caracteristica(fuerza: Int, velocidad: Int, nivel: Int, salud: Int = 100)

abstract class Trabajo {
  def fuerza(caracteristica: Caracteristica): Double

  def habilidad(caracteristica: Caracteristica): Int

  def abrir(puerta: Puerta, cofre: Set[Item], caracteristica: Caracteristica): Boolean = {
    puerta match {
      case PuertaCerrada if cofre.contains(Llave) => true
      case _ => abrirParticular(puerta, cofre, caracteristica)
    }
  }

  def abrirParticular(puerta: Puerta, cofre: Set[Item], caracteristica: Caracteristica): Boolean
}

case object Guerrero extends Trabajo {
  def fuerza(caracteristica: Caracteristica): Double = {
    caracteristica.fuerza + 0.2 * caracteristica.nivel * caracteristica.fuerza
  }

  def habilidad(caracteristica: Caracteristica): Int = 0

  def abrirParticular(puerta: Puerta, cofre: Set[Item], caracteristica: Caracteristica): Boolean = false
}

case class Ladron(habilidad: Int) extends Trabajo {
  def fuerza(caracteristica: Caracteristica): Double = caracteristica.fuerza

  def habilidad(caracteristica: Caracteristica): Int = habilidad + 3 * caracteristica.nivel

  def abrirParticular(puerta: Puerta, cofre: Set[Item], caracteristica: Caracteristica): Boolean = {
    puerta match {
      // TODO: Las ganzuas son para ladrones o cualquier heroe?
      case PuertaCerrada if habilidad >= 10 || cofre.contains(Ganzuas) => true
      case PuertaEscondida if habilidad >= 6 => true
      case _ => habilidad >= 20
    }
  }
}

// TODO: ver si pasar el hechizo a tupla
case class Mago(hechizos: Set[(Hechizo, Int)]) extends Trabajo {
  def fuerza(caracteristica: Caracteristica): Double = caracteristica.fuerza

  def habilidad(caracteristica: Caracteristica): Int = 0

  def puedeUsarHechizo(caracteristica: Caracteristica, hechizo: Hechizo): Boolean = {
    hechizos.exists(t => t._1 == hechizo && caracteristica.nivel >= t._2)
  }

  def abrirParticular(puerta: Puerta, cofre: Set[Item], caracteristica: Caracteristica): Boolean = {
    puerta match {
      case PuertaEscondida if puedeUsarHechizo(caracteristica, Vislumbrar) => true
      // TODO: solo conoce el hechizo o tambien lo tiene que poder usar (osea alcanzar nivel)?
      case PuertaEncantada(h) if puedeUsarHechizo(caracteristica, h) => true
      case _ => false
    }
  }
}

trait Hechizo

case object Vislumbrar extends Hechizo


case class Grupo(heroes: List[Aventurero]) {
  var cofreComun: Set[Item] = Set()

  def aventureros(): List[Aventurero] = heroes.filter(h => !h.muerto())

  def lider(): Aventurero = aventureros().head

  // TODO: Devolver monada?
  def abrirPuerta(puerta: Puerta): Boolean = {
    heroes.exists(h => h.abrir(puerta, cofreComun))
  }
}


trait Objeto

case class Item() extends Objeto

case object Llave extends Item

case object Ganzuas extends Item

// TODO: ver si existe Tesoro u otra cosa que no sea un Item
case object Tesoro extends Objeto