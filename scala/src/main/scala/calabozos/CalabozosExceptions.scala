package calabozos

case class NoSePuedeAbrirPuertaException(grupo: Grupo) extends RuntimeException

case class GrupoMuertoException(grupo: Grupo) extends RuntimeException

case class NoHayPuertasParaAbrirException(grupo: Grupo) extends RuntimeException

case class NingunGrupoAtravesoElCalabozo() extends RuntimeException