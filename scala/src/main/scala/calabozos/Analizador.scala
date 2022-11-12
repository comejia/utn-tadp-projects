package calabozos

import scala.util.{Failure, Success, Try}

object analizador {

  def mejorGrupo(grupos: List[Grupo], calabozo: Calabozo): Try[Grupo] = Try {
    grupos.reduce((g1, g2) => {
      (calabozo.recorrer(g1), calabozo.recorrer(g2)) match {
        case (Success(g_1), Success(g_2)) => if (g_1.puntaje() > g_2.puntaje()) g_1 else g_2
        case (Success(g_1), Failure(_)) => g_1
        case (Failure(_), Success(g_2)) => g_2
        case (Failure(_), Failure(_)) => throw new NingunGrupoAtravesoElCalabozo
      }
    })
  }

  def nivelesNecesarios(grupo: Grupo, calabozo: Calabozo, iteracion: Int = 0): Try[Int] = Try {
    if(calabozo.recorrer(grupo).isFailure) throw GrupoNoPudoRecorrerCalabozo(grupo)
    return Try(iteracion)
  }.recover { case _: GrupoNoPudoRecorrerCalabozo =>
    return nivelesNecesarios(grupo.subirNivel(), calabozo, iteracion + 1)
  }
}
