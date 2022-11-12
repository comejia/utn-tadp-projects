package calabozos

import scala.util.{Success, Try}


case class Calabozo(puertaPrincipal: Puerta) {

//  def recorrer(grupo: Grupo, entrada: Puerta = puertaPrincipal): Try[Grupo] = {
//    if (entrada._salida) return Success(grupo)
//
//    val grupoRecorriendo: Try[Grupo] = grupo.abrir(entrada)
//      .flatMap(_.enfrentarSituacion(entrada.habitacion))
//      .flatMap(_.agregarPuertasAVisitar(entrada.habitacion.salidas))
//
//    val siguiente = grupoRecorriendo.flatMap(_.elegirPuertaSiguiente(entrada.habitacion.salidas))
//
//    recorrer(grupoRecorriendo.get, siguiente.get)
//  }

  def recorrer(grupo: Grupo, entrada: Puerta = puertaPrincipal): Try[Grupo] = {
    if (entrada._salida) return Success(grupo)

    for {
      grupoAbrio <- grupo.abrir(entrada)
      grupoEnfrentoSituacion <- grupoAbrio.enfrentarSituacion(entrada.habitacion)
      grupoAgregoPuertas <- grupoEnfrentoSituacion.agregarPuertasAVisitar(entrada.habitacion.salidas)
      puerta <- grupoAgregoPuertas.elegirPuertaSiguiente(entrada.habitacion.salidas)
      grupoRecorrio <- recorrer(grupoAgregoPuertas, puerta)
    } yield grupoRecorrio
  }

}

abstract class Puerta(val habitacion: Habitacion, var _salida: Boolean = false)

case class PuertaCerrada(habitacionSiguiente: Habitacion, salida: Boolean = false) extends Puerta(habitacion = habitacionSiguiente, _salida = salida)

case class PuertaEscondida(habitacionSiguiente: Habitacion, salida: Boolean = false) extends Puerta(habitacion = habitacionSiguiente, _salida = salida)

case class PuertaEncantada(hechizo: Hechizo, habitacionSiguiente: Habitacion, salida: Boolean = false) extends Puerta(habitacion = habitacionSiguiente, _salida = salida)


case class Habitacion(situacion: Situacion, salidas: List[Puerta] = List()) {
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
    if (heroePerdido.leAgrada(grupo) && lider.leAgrada(posibleGrupo)) {
      return posibleGrupo
    }
    peleaAMuerte(grupo, heroePerdido)
  }

  private def peleaAMuerte(grupo: Grupo, oponente: Aventurero): Grupo = {
    if (grupo.fuerza() > oponente.fuerza()) {
      grupo.subirNivel()
    } else {
      grupo.perderSalud((oponente.fuerza() / grupo.tamanioGrupo()).toInt)
    }
  }
}

