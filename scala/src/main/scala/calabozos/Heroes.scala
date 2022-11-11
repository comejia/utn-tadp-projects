package calabozos

case class Aventurero(caracteristica: Caracteristica,
                      trabajo: Trabajo,
                      criterioSimpatia: CriterioSimpatia,
                      criterioPuerta: CriterioPuerta,
                      var salud: Int = 100) {
  def fuerza(): Double = trabajo.fuerza(caracteristica)

  def velocidad(): Int = caracteristica.velocidad

  def nivel(): Int = caracteristica.nivel

  def habilidad(): Int = trabajo.habilidad(caracteristica)

  def muerto(): Boolean = salud <= 0

  def abre(puerta: Puerta, cofre: Set[Item]): Boolean = trabajo.abrir(puerta, cofre, caracteristica)

  def danio(danio: Int): Aventurero = copy(salud = salud - danio)

  def leAgrada(grupo: Grupo): Boolean = criterioSimpatia(grupo)

  def subirNivel(): Aventurero = copy(caracteristica = caracteristica.copy(nivel = caracteristica.nivel + 1))

  def elegirPuerta(grupo: Grupo): Puerta = criterioPuerta(grupo)
}

case class Caracteristica(fuerza: Int, velocidad: Int, nivel: Int)

trait Trabajo {
  def fuerza(base: Caracteristica): Double = base.fuerza

  def habilidad(base: Caracteristica): Int = 0

  def abrir(puerta: Puerta, cofre: Set[Item], base: Caracteristica): Boolean = {
    puerta match {
      case PuertaCerrada(_) if cofre.contains(Llave) => true
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
      case PuertaCerrada(_) if habilidadMano >= 10 || cofre.contains(Ganzuas) => true
      case PuertaEscondida(_) if habilidadMano >= 6 => true
      case _ => habilidadMano >= 20
    }
  }
}

case class Mago(hechizos: Set[(Hechizo, Int)]) extends Trabajo {
  def puedeUsarHechizo(base: Caracteristica, hechizo: Hechizo): Boolean = {
    hechizos.exists(h => h._1 == hechizo && base.nivel >= h._2)
  }

  override def abrirParticular(puerta: Puerta, cofre: Set[Item], base: Caracteristica): Boolean = {
    puerta match {
      case PuertaEscondida(_) if puedeUsarHechizo(base, Vislumbrar) => true
      // TODO: solo conoce el hechizo o tambien lo tiene que poder usar (osea alcanzar nivel)?
      case PuertaEncantada(h, _) if puedeUsarHechizo(base, h) => true
      case _ => false
    }
  }
}

case class Aprendizaje(hechizo: Hechizo, nivelAprendizaje: Int)

trait Hechizo

case object Vislumbrar extends Hechizo
