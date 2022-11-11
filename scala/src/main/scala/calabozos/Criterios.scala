package calabozos

trait CriterioSimpatia extends (Grupo => Boolean)

case object Introvertido extends CriterioSimpatia {
  override def apply(grupo: Grupo): Boolean = grupo.tamanioGrupo() <= 3
}

case object Bigote extends CriterioSimpatia {
  override def apply(grupo: Grupo): Boolean = !grupo.heroes.map(h => h.trabajo).contains(Ladron(_))
}

case class Interesado(preferencia: Item) extends CriterioSimpatia {
  override def apply(grupo: Grupo): Boolean = grupo.tieneItem(preferencia)
}

case object Loquito extends CriterioSimpatia {
  override def apply(grupo: Grupo): Boolean = false
}


// TODO: ver como implementar y donde?
trait CriterioPuerta extends (Grupo => Puerta)

case object Heroico extends CriterioPuerta {
  override def apply(grupo: Grupo): Puerta = grupo.puertasAVisitar.head
}

case object Ordenado extends CriterioPuerta {
  override def apply(grupo: Grupo): Puerta = grupo.puertasAVisitar.last
}

case object Vidente extends CriterioPuerta {
  override def apply(grupo: Grupo): Puerta = {
    grupo.puertasAVisitar.maxBy(puerta => {
      puerta.habitacion.aplicarEfecto(grupo).puntaje()
      //puntaje(grupo, puerta)
    })
  }

  //  private def puntaje(grupo: Grupo, puerta: Puerta): Int = {
  //    val grupoAfectado = puerta.habitacion.aplicarEfecto(grupo)
  //    grupoAfectado.heroesVivos() * 10 - grupo.heroesMuertos() * 5 + grupo.tamanioGrupo() + grupo.nivelMasAlto()
  //  }
}
