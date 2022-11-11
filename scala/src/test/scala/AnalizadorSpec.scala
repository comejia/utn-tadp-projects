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

    val liderGuerreroHeroico = Aventurero(Caracteristica(50, 1000, nivel = 10), Guerrero, Introvertido, Heroico)

    val perdidoDebil = Aventurero(Caracteristica(40, 1000, nivel = 10), Guerrero, Loquito, Heroico)

    "dado una lista de grupos y un calabozo, se obtiene el mejor" in {
      val grupoNoPasaNada = Grupo(List(guerreroLento, ladronSinHabilidad))
      val grupoPasaAlgunosObstaculos = Grupo(List(guerreroLento, ladronAbrePuertaCerrada, magoConoceVislumbrarYEsLento))
      val grupoPasaTodo = Grupo(List(magoConoceVislumbrar, ladronAbrePuertaCerrada, guerreroLento))

      val puerta: Puerta = PuertaCerrada(
        Habitacion(NoPasaNada,
          Set(
            PuertaCerrada(Habitacion(NoPasaNada)),
            PuertaCerrada(Habitacion(TrampaDeLeones)),
            PuertaEncantada(Vislumbrar,
              Habitacion(TrampaDeLeones, Set(PuertaEscondida(Habitacion(Encuentro(perdidoDebil)))))
            ),
            PuertaEncantada(Vislumbrar, Habitacion(MuchosDardos())),
            PuertaEncantada(Vislumbrar, Habitacion(MuchosDardos()), salida = true)
          )
        )
      )
      val calabozo = Calabozo(puertaPrincipal = puerta)

      val mejorGrupo = analizador.mejorGrupo(List(grupoNoPasaNada, grupoPasaAlgunosObstaculos, grupoPasaTodo), calabozo)

      assert(mejorGrupo.isSuccess)
      assert(mejorGrupo.get.puntaje() == 28)
    }

    "si ningun grupo recorre el calabozo, se lanza excepcion" in {
      val grupoNoPasaNada = Grupo(List(guerreroLento, ladronSinHabilidad))
      val grupoPasaAlgunosObstaculos = Grupo(List(guerreroLento, magoConoceVislumbrarYEsLento))
      val grupoPasaTodo = Grupo(List(magoConoceVislumbrar, ladronAbrePuertaCerrada))

      val puerta: Puerta = PuertaCerrada(
        Habitacion(NoPasaNada,
          Set(
            PuertaCerrada(Habitacion(NoPasaNada)),
            PuertaCerrada(Habitacion(TrampaDeLeones)),
            PuertaEncantada(Vislumbrar,
              Habitacion(TrampaDeLeones, Set(PuertaEscondida(Habitacion(Encuentro(perdidoDebil)))))
            ),
            PuertaEncantada(Vislumbrar, Habitacion(MuchosDardos())),
            PuertaEncantada(Vislumbrar, Habitacion(MuchosDardos()), salida = true)
          )
        )
      )
      val calabozo = Calabozo(puertaPrincipal = puerta)

      assertThrows[NingunGrupoAtravesoElCalabozo] {
        analizador.mejorGrupo(List(grupoNoPasaNada, grupoPasaAlgunosObstaculos, grupoPasaTodo), calabozo).get
      }
    }
  }
}
