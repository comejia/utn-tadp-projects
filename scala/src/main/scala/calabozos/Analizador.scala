package calabozos

import scala.util.Try

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
      val a = calabozo.recorrer(g1)
      val b = calabozo.recorrer(g2)
      if (a.get.puntaje() > b.get.puntaje()) g1 else g2
    })

    //    grupos.fold(grupos.head)((g1, g2) => {
    //      val a = calabozo.recorrer(g1)
    //      val b = calabozo.recorrer(g2)
    //      if (a.get.puntaje() > b.get.puntaje()) g1 else g2
    //    })
  }

  def nivelesNecesarios(grupo: Grupo, calabozo: Calabozo): Int = ???
}
