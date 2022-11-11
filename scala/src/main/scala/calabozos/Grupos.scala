package calabozos

import scala.util.Try


case class Grupo(heroes: List[Aventurero],
                 cofreComun: Set[Item] = Set(),
                 puertasAbiertas: Set[Puerta] = Set(),
                 puertasAVisitar: Set[Puerta] = Set()) {

  //var cofreComun: Set[Item] = Set()
  //var puertasAbiertas: Set[Puerta] = Set()
  //var puertasAVisitar: Set[Puerta] = Set()

  def aventurerosVivos(): List[Aventurero] = heroes.filter(h => !h.muerto())

  def lider(): Aventurero = aventurerosVivos().head

  def puedeAbrir(puerta: Puerta): Boolean = {
    //val estadoApertura = heroes.exists(h => h.abre(puerta, cofreComun))
    //    if (estadoApertura) {
    //      puertasAbiertas = puertasAbiertas.+(puerta)
    //    }
    //estadoApertura
    aventurerosVivos().exists(h => h.abre(puerta, cofreComun))
  }

  def agregarItem(item: Item): Grupo = copy(cofreComun = cofreComun.+(item))

  def perderSalud(danio: Int): Grupo = {
    //val grupoDaniado = copy()
    //grupoDaniado.heroes.map(h => h.danio(danio))
    //grupoDaniado
    copy(heroes = heroes.map(h => h.danio(danio)))
  }

  // TODO: no estoy seguro de esto
  def eliminarElMasLento(): Grupo = {
    val heroeLento: Aventurero = aventurerosVivos().minBy(h => h.velocidad())
    //heroeLento = heroeLento.danio(100)
    copy(heroes = heroes.map(h => {
      if (h == heroeLento) heroeLento.danio(100)
      else h.danio(0)
    }))
  }

  def fuerza(): Double = heroes.foldRight(0.0)(_.fuerza() + _)

  def subirNivel(): Grupo = {
    //heroes.foreach(h => h.subirNivel())
    copy(heroes = heroes.map(h => h.subirNivel()))
  }

  def tamanioGrupo(): Int = heroes.size

  def tieneItem(item: Item): Boolean = cofreComun.contains(item)

  def agregarPuertaAbierta(puerta: Puerta): Grupo = {
    copy(puertasAbiertas = puertasAbiertas.+(puerta), puertasAVisitar = puertasAVisitar.-(puerta))
  }

  def muerto(): Boolean = heroes.forall(h => h.muerto())

  def agregarPuertasAVisitar(puertas: Set[Puerta]): Try[Grupo] = Try {
    copy(puertasAVisitar = puertas ++ puertasAVisitar)
  }

  // TODO: es responsabilidad del grupo?? (sin terminar)
  def elegirPuertaSiguiente(puertasDeHabitacion: Set[Puerta]): Try[Puerta] = Try {
    val puertasPosibles = puertasAVisitar.filter(p => puedeAbrir(p))
    if (puertasPosibles.isEmpty) throw NoHayPuertasParaAbrirException(this)
    lider().elegirPuerta(copy(puertasAVisitar = puertasPosibles))
    //lider().criterioPuerta match {
    //case Heroico => puertasDeHabitacion.head
    //case Ordenado => if (puertasPosibles.nonEmpty) puertasPosibles.head else puertasDeHabitacion.head
    //case Ordenado => puertasAVisitar.last
    //case Vidente =>
    //}
  }

  def heroesVivos(): Int = heroes.count(h => !h.muerto())

  def heroesMuertos(): Int = heroes.count(h => h.muerto())

  def nivelMasAlto(): Int = heroes.maxBy(h => h.nivel()).nivel()

  def abrir(puerta: Puerta): Try[Grupo] = Try {
    if (!puedeAbrir(puerta)) throw NoSePuedeAbrirPuertaException(this)
    else agregarPuertaAbierta(puerta)
  }

  def enfrentarSituacion(habitacion: Habitacion): Try[Grupo] = Try {
    val grupoAfectado = habitacion.aplicarEfecto(this)
    if (grupoAfectado.muerto()) throw GrupoMuertoException(this)
    else grupoAfectado
  }
}


sealed trait Item

case object Llave extends Item

case object Ganzuas extends Item

// TODO: ver si existe Tesoro u otra cosa que no sea un Item
//case object Tesoro extends Objeto