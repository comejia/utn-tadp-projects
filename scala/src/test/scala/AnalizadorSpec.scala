import calabozos._
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._

class AnalizadorSpec extends AnyFreeSpec {
  "Analizador" - {
    val guerreroLento = Aventurero(Caracteristica(fuerza = 50, 1, 10), Guerrero, Introvertido, Heroico)
    val ladronAbrePuertaCerrada = Aventurero(Caracteristica(50, 10, nivel = 10), Ladron(10), Introvertido, Heroico)
    val ladronSinHabilidad = Aventurero(Caracteristica(50, 10, nivel = 10), Ladron(2), Introvertido, Heroico)
    val magoConoceVislumbrar = Aventurero(Caracteristica(50, 100, nivel = 10), Mago(Set((Vislumbrar, 3))), Introvertido, Heroico)
    val magoConoceVislumbrarYEsLento = Aventurero(Caracteristica(50, 2, nivel = 10), Mago(Set((Vislumbrar, 3))), Introvertido, Heroico)
    val muerto = Aventurero(Caracteristica(50, 10, nivel = 10), Ladron(60), Introvertido, Heroico, salud = 0)

    "un grupo puede recorrer un calabozo vacio" in {
      val grupo = Grupo(List(guerreroLento, ladronAbrePuertaCerrada, muerto))
      val puerta: Puerta = PuertaCerrada(Habitacion(NoPasaNada), salida = true)
      val calabozo = Calabozo(puertaPrincipal = puerta)

      assert(calabozo.recorrer(grupo).isSuccess)
      calabozo.recorrer(grupo).get shouldBe grupo
    }
  }
}
