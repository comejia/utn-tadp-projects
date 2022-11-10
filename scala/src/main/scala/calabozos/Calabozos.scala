package calabozos

import scala.util.{Success, Try}


case class Calabozo(puertaPrincipal: Puerta,
                    var habitaciones: Set[Habitacion],
                    puertaFinal: Puerta) {

  def recorrer(grupo: Grupo, entrada: Puerta = puertaPrincipal): Try[Grupo] = {
    var grupoAfectado: Grupo = null

    if (entrada == puertaFinal) {
      return Success(grupo)
    }

    if (grupo.puedeAbrir(entrada)) {
      grupoAfectado = grupo.agregarPuertaAbierta(entrada)
      val habitacion = habitaciones.head
      habitaciones = habitaciones.drop(0)
      grupoAfectado = habitacion.aplicarEfecto(grupo)

      if (grupoAfectado.muerto()) {
        throw new RuntimeException("El grupo murio")
      }

      grupoAfectado = grupoAfectado.agregarPuertasAVisitar(Set(PuertaCerrada)) // TODO: ver si la habitacion tiene puertas

      val siguientePuerta = grupoAfectado.elegirPuertaSiguiente(Set(PuertaCerrada))
      recorrer(grupoAfectado, siguientePuerta)
    } else throw new RuntimeException("No hay puertas para abrir")

  }
}

abstract class Puerta(habitacion: Habitacion)

case class PuertaCerrada(habitacionSiguiente: Habitacion) extends Puerta(habitacion = habitacionSiguiente)

case class PuertaEscondida(habitacionSiguiente: Habitacion) extends Puerta(habitacion = habitacionSiguiente)

case class PuertaEncantada(hechizo: Hechizo, habitacionSiguiente: Habitacion) extends Puerta(habitacion = habitacionSiguiente)


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

