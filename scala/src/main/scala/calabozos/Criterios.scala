package calabozos


trait CriterioSimpatia extends (Grupo => Boolean)

case object Introvertido extends CriterioSimpatia {
  override def apply(grupo: Grupo): Boolean = grupo.tamanioGrupo() <= 3
}

case object Bigote extends CriterioSimpatia {
  override def apply(grupo: Grupo): Boolean = {
    grupo.heroes.exists(h => h match {
      case Aventurero(_, _, Ladron(_), _, _) => true
      case _ => false
    })
  }
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
  override def apply(grupo: Grupo): Puerta = ???
}

case object Vidente extends CriterioPuerta {
  override def apply(grupo: Grupo): Puerta = {
    grupo.puertasAVisitar.maxBy(puerta => puntaje(grupo, puerta))
  }

  // TODO: como aplicar puerta sino sabemos la habitacion siguiente?
  private def puntaje(grupo: Grupo, puerta: Puerta): Int = {
    grupo.heroesVivos() * 10 - grupo.heroesMuertos() * 5 + grupo.tamanioGrupo() + grupo.nivelMasAlto()
  }
}
