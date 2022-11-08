package calabozos


case class Calabozo(puertaPrincipal: Puerta,
                    habitaciones: Set[Habitacion],
                    puertaFinal: Puerta) {
}

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
  override def apply(grupo: Grupo): Grupo = grupo.agregarItem(item)
}

case class MuchosDardos(danio: Int = 10) extends Situacion {
  override def apply(grupo: Grupo): Grupo = grupo.perderSalud(danio)
}

case object TrampaDeLeones extends Situacion {
  override def apply(grupo: Grupo): Grupo = grupo.eliminarElMasLento()
}

case class Encuentro(heroePerdido: Aventurero) extends Situacion {
  override def apply(grupo: Grupo): Grupo = {
    val lider: Aventurero = grupo.lider()
    val posibleGrupo: Grupo = grupo.copy(heroes = grupo.heroes :+ heroePerdido)
    if(heroePerdido.leAgrada(grupo) && lider.leAgrada(posibleGrupo)) {
      return posibleGrupo
    }
    peleaAMuerte(grupo, heroePerdido)
  }

  private def peleaAMuerte(grupo: Grupo, oponente: Aventurero): Grupo = {
    if(grupo.fuerza() > oponente.fuerza()) {
      grupo.subirNivel()
    } else {
      grupo.perderSalud((oponente.fuerza() / grupo.tamanioGrupo()).toInt)
    }
  }
}

