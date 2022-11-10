//package calabozos
//
////abstract case class Etapa() {
////  val puertaInicial: Puerta
////  val habitacion: Habitacion
////  val puertasDeHabitacion: Set[Puerta]
////
////  def algo() = ???
////}
//
//package object recorrido {
//
//
//  def enfrentarEtapa(grupo: Grupo, etapa: Etapa): Option[Grupo] = {
//    if(grupo.puedeAbrir(etapa.puertaInicial)) {
//      if(etapa.habitacion.aplicarEfecto(grupo).muerto()) {
//        throw new Exception("Fin del juego")
//      }
//      grupo.agregarPuertasAVisitar(etapa.puertasDeHabitacion)
//      val puertaSiguiente = grupo.elegirPuertaSiguiente()
//      enfrentarEtapa(grupo, siguienteEtapa(puertaSiguiente))
//    }
//    None
//  }
//
//  def siguienteEtapa(puerta: Puerta): Etapa = {
//    etapas.filter(e => e.puertaInicial == puerta).head
//  }
//}
