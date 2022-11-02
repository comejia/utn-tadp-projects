package heroes

trait Puerta

case object PuertaCerrada extends Puerta

case object PuertaEscondida extends Puerta

case class PuertaEncantada(hechizo: Hechizo) extends Puerta


case class Habitacion(situacion: Situacion) {

  def aplicarEfecto(grupo: Grupo): Grupo = situacion(grupo)
}

trait Situacion extends (Grupo => Grupo)

case object NoPasaNada extends Situacion {
  override def apply(grupo: Grupo): Grupo = grupo
}

case class TesoroPerdido(item: Item) extends Situacion {
  override def apply(grupo: Grupo): Grupo = {
    grupo.agregarItem(item)
    grupo
  }
}

case object MuchosDardos extends Situacion {
  override def apply(grupo: Grupo): Grupo = {
    grupo.perderSalud(10)
    grupo
  }
}

case object TrampaDeLeones extends Situacion {
  override def apply(grupo: Grupo): Grupo = {
    grupo.eliminarElMasLento()
    grupo
  }
}

case class Encuentro(heroePerdido: Aventurero) extends Situacion {
  override def apply(grupo: Grupo): Grupo = {
    val lider: Aventurero = grupo.lider()
    // TODO: ver como generar un nuevo grupo con el viejo y el heroe perdido
    val grupoNuevo: Grupo = Grupo(grupo.heroes + heroePerdido)
    if(heroePerdido.aplicarCriterio(grupo) && lider.aplicarCriterio(grupoNuevo)) {
      return grupoNuevo
    }
    peleaAMuerte(grupo, heroePerdido)
  }

  // TODO: terminar
  private def peleaAMuerte(grupo: Grupo, aventurero: Aventurero) = {

  }
}

