package calabozos

case class Aventurero(caracteristica: Caracteristica,
                      salud: Int = 100,
                      trabajo: Trabajo,
                      criterioSimpatia: CriterioSimpatia,
                      criterioPuerta: CriterioPuerta) {
  def fuerza(): Double = trabajo.fuerza(caracteristica)

  def velocidad(): Int = caracteristica.velocidad

  def nivel(): Int = caracteristica.nivel

  def muerto(): Boolean = salud <= 0

  def abre(puerta: Puerta, cofre: Set[Item]): Boolean = trabajo.abrir(puerta, cofre, caracteristica)

  def danio(danio: Int): Aventurero = copy(salud = salud - danio)

  def leAgrada(grupo: Grupo): Boolean = criterioSimpatia(grupo)

  def subirNivel(): Aventurero = copy(caracteristica = caracteristica.copy(nivel = caracteristica.nivel + 1))

  def elegirPuerta(puertas: Set[Puerta]): Puerta = ???
}

case class Caracteristica(fuerza: Int, velocidad: Int, nivel: Int)

trait Trabajo {
  def fuerza(base: Caracteristica): Double = base.fuerza

  def habilidad(base: Caracteristica): Int = 0

  def abrir(puerta: Puerta, cofre: Set[Item], base: Caracteristica): Boolean = {
    puerta match {
      case PuertaCerrada if cofre.contains(Llave) => true
      case _ => abrirParticular(puerta, cofre, base)
    }
  }

  def abrirParticular(puerta: Puerta, cofre: Set[Item], base: Caracteristica): Boolean = false
}

case object Guerrero extends Trabajo {
  override def fuerza(base: Caracteristica): Double = base.fuerza + 0.2 * (base.fuerza / base.nivel)
}

case class Ladron(habilidadMano: Int) extends Trabajo {
  override def habilidad(base: Caracteristica): Int = habilidadMano + 3 * base.nivel

  override def abrirParticular(puerta: Puerta, cofre: Set[Item], base: Caracteristica): Boolean = {
    puerta match {
      // TODO: Las ganzuas son para ladrones o cualquier heroe?
      case PuertaCerrada if habilidadMano >= 10 || cofre.contains(Ganzuas) => true
      case PuertaEscondida if habilidadMano >= 6 => true
      case _ => habilidadMano >= 20
    }
  }
}

case class Mago(hechizos: Set[Aprendizaje]) extends Trabajo {
  def puedeUsarHechizo(base: Caracteristica, hechizo: Hechizo): Boolean = {
    hechizos.exists(h => h.hechizo == hechizo && base.nivel >= h.nivelAprendizaje)
  }

  override def abrirParticular(puerta: Puerta, cofre: Set[Item], base: Caracteristica): Boolean = {
    puerta match {
      case PuertaEscondida if puedeUsarHechizo(base, Vislumbrar) => true
      // TODO: solo conoce el hechizo o tambien lo tiene que poder usar (osea alcanzar nivel)?
      case PuertaEncantada(h) if puedeUsarHechizo(base, h) => true
      case _ => false
    }
  }
}

case class Aprendizaje(hechizo: Hechizo, nivelAprendizaje: Int)

trait Hechizo

case object Vislumbrar extends Hechizo
