package calabozos

import scala.util.{Failure, Success, Try}

object analizador {

  def mejorGrupo(grupos: List[Grupo], calabozo: Calabozo): Try[Grupo] = Try {
    //    grupos.reduce { (g1, g2) => {
    //      val tryG1 = calabozo.recorrer(g1)
    //      val tryG2 = calabozo.recorrer(g2)
    //      for {
    //        p1 <- tryG1 if tryG1.isSuccess
    //        p2 <- tryG2 if tryG2.isSuccess
    //      } yield if (p1.puntaje() > p2.puntaje()) g1 else g2
    //    }
    //    }

    grupos.reduce((g1, g2) => {
      (Try(calabozo.recorrer(g1)), Try(calabozo.recorrer(g2))) match {
        case (Success(g_1), Success(g_2)) => if (g_1.get.puntaje() > g_2.get.puntaje()) g_1.get else g_2.get
        case (Success(g_1), Failure(_)) => g_1.get
        case (Failure(_), Success(g_2)) => g_2.get
        case (Failure(_), Failure(_)) => throw new NingunGrupoAtravesoElCalabozo
      }
    })
  }

  def nivelesNecesarios(grupo: Grupo, calabozo: Calabozo, iteracion: Int = 0): Try[Int] = Try {
    calabozo.recorrer(grupo)
    return Try(iteracion)
  }.recover { case _ =>
    return nivelesNecesarios(grupo.subirNivel(), calabozo, iteracion + 1)
  }
}
