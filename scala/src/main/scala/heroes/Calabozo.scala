package heroes

trait Puerta

case object PuertaCerrada extends Puerta

case object PuertaEscondida extends Puerta

case class PuertaEncantada(hechizo: Hechizo) extends Puerta