package calabozos

import scala.util.Try

case class Grupo(heroes: List[Aventurero],
                 cofreComun: Set[Item] = Set(),
                 puertasAbiertas: Set[Puerta] = Set(),
                 puertasAVisitar: List[Puerta] = List()) {

  def aventurerosVivos(): List[Aventurero] = heroes.filter(h => !h.muerto())

  def lider(): Aventurero = aventurerosVivos().head

  def puedeAbrir(puerta: Puerta): Boolean = aventurerosVivos().exists(h => h.abre(puerta, cofreComun))

  def agregarItem(item: Item): Grupo = copy(cofreComun = cofreComun.+(item))

  def perderSalud(danio: Int): Grupo = copy(heroes = heroes.map(h => h.danio(danio)))

  def eliminarElMasLento(): Grupo = {
    val heroeLento: Aventurero = aventurerosVivos().minBy(h => h.velocidad())
    copy(heroes = heroes.map(h => {
      if (h == heroeLento) heroeLento.danio(100)
      else h.danio(0)
    }))
  }

  def fuerza(): Double = heroes.foldRight(0.0)(_.fuerza() + _)

  def subirNivel(): Grupo = copy(heroes = heroes.map(h => h.subirNivel()))

  def tamanioGrupo(): Int = aventurerosVivos().size

  def tieneItem(item: Item): Boolean = cofreComun.contains(item)

  def agregarPuertaAbierta(puerta: Puerta): Grupo =
    copy(puertasAbiertas = puertasAbiertas.+(puerta), puertasAVisitar = puertasAVisitar.dropWhile(p => p == puerta))

  def muerto(): Boolean = heroes.forall(h => h.muerto())

  def agregarPuertasAVisitar(puertas: List[Puerta]): Try[Grupo] = Try {
    copy(puertasAVisitar = puertas ++ puertasAVisitar)
  }

  def elegirPuertaSiguiente(puertasDeHabitacion: List[Puerta]): Try[Puerta] = Try {
    val puertasPosibles = puertasAVisitar.filter(p => puedeAbrir(p))
    if (puertasPosibles.isEmpty) throw NoHayPuertasParaAbrirException(this)
    lider().elegirPuerta(copy(puertasAVisitar = puertasPosibles))
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

  def puntaje(): Int = heroesVivos() * 10 - heroesMuertos() * 5 + tamanioGrupo() + nivelMasAlto()
}


sealed trait Item

case object Llave extends Item

case object Ganzuas extends Item
