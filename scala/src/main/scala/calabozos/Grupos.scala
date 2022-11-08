package calabozos


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
    heroes.exists(h => h.abre(puerta, cofreComun))
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
    val heroeLento: Aventurero = heroes.minBy(h => h.velocidad())
    //heroeLento = heroeLento.danio(100)
    copy(heroes = heroes.map(h => {
      if(h == heroeLento) heroeLento.danio(100)
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

  def agregarPuertaAbierta(puerta: Puerta) : Grupo = copy(puertasAbiertas = puertasAbiertas.+(puerta))

  def muerto(): Boolean = heroes.forall(h => h.muerto())

  def agregarPuertasAVisitar(puertas: Set[Puerta]): Grupo = {
    copy(puertasAbiertas = puertasAVisitar ++ puertas)
  }

  // TODO: es responsabilidad del grupo?? (sin terminar)
  def elegirPuertaSiguiente(puertasDeHabitacion: Set[Puerta]): Puerta = {
    // TODO: terminar de implementar
    val puertasPosibles = puertasAVisitar.filter(p => puedeAbrir(p))
    //lider().elegirPuerta(puertasAVisitar.filter(p => puedeAbrir(p)))
    lider().criterioPuerta match {
      case Heroico => puertasDeHabitacion.head
      case Ordenado => if (puertasPosibles.nonEmpty) puertasPosibles.head else puertasDeHabitacion.head
      //case Vidente =>
    }
  }

  def heroesVivos(): Int = heroes.count(h => !h.muerto())

  def heroesMuertos(): Int = heroes.count(h => h.muerto())

  def nivelMasAlto(): Int = heroes.maxBy(h => h.nivel()).nivel()
}


sealed trait Item

case object Llave extends Item

case object Ganzuas extends Item

// TODO: ver si existe Tesoro u otra cosa que no sea un Item
//case object Tesoro extends Objeto