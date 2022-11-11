import calabozos._
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._

class CalabozosSpec extends AnyFreeSpec {

  "Calabozos" - {
    val guerreroLento = Aventurero(Caracteristica(fuerza = 50, 1, 10), Guerrero, Introvertido, Heroico)
    val ladronAbrePuertaCerrada = Aventurero(Caracteristica(50, 10, nivel = 1), Ladron(10), Introvertido, Heroico)
    val ladronSinHabilidad = Aventurero(Caracteristica(50, 10, nivel = 2), Ladron(2), Introvertido, Heroico)
    val magoConoceVislumbrar = Aventurero(Caracteristica(50, 100, nivel = 10), Mago(Set((Vislumbrar, 3))), Introvertido, Heroico)
    val magoConoceVislumbrarYEsLento = Aventurero(Caracteristica(50, 2, nivel = 10), Mago(Set((Vislumbrar, 3))), Introvertido, Heroico)
    val muerto = Aventurero(Caracteristica(50, 10, nivel = 10), Ladron(60), Introvertido, Heroico, salud = 0)

    val liderGuerreroHeroico = Aventurero(Caracteristica(50, 1000, nivel = 10), Guerrero, Introvertido, Heroico)

    val perdidoDebil = Aventurero(Caracteristica(40, 1000, nivel = 10), Guerrero, Loquito, Heroico)

    "un grupo puede recorrer un calabozo vacio" in {
      val grupo = Grupo(List(guerreroLento, ladronAbrePuertaCerrada, muerto))
      val puerta: Puerta = PuertaCerrada(Habitacion(NoPasaNada), salida = true)
      val calabozo = Calabozo(puertaPrincipal = puerta)

      assert(calabozo.recorrer(grupo).isSuccess)
      calabozo.recorrer(grupo).get shouldBe grupo
    }

    "si un grupo no puede abrir la puerta principal, lanza excepcion" in {
      val grupo = Grupo(List(guerreroLento, ladronSinHabilidad))
      val puerta: Puerta = PuertaCerrada(
        Habitacion(NoPasaNada,
          List(
            PuertaCerrada(Habitacion(NoPasaNada), salida = true)
          )
        )
      )
      val calabozo = Calabozo(puertaPrincipal = puerta)

      // TODO: como validar por isFailure??
      assertThrows[NoSePuedeAbrirPuertaException] {
        calabozo.recorrer(grupo)
      }
    }

    "si un grupo muere, lanza excepcion" in {
      val grupo = Grupo(List(magoConoceVislumbrar, ladronAbrePuertaCerrada))
      val puerta: Puerta = PuertaCerrada(
        Habitacion(NoPasaNada,
          List(
            PuertaCerrada(Habitacion(NoPasaNada)),
            PuertaCerrada(Habitacion(TrampaDeLeones)),
            PuertaEncantada(Vislumbrar, Habitacion(TrampaDeLeones)),
            PuertaEncantada(Vislumbrar, Habitacion(MuchosDardos()), salida = true)
          )
        )
      )
      val calabozo = Calabozo(puertaPrincipal = puerta)

      assertThrows[GrupoMuertoException] {
        calabozo.recorrer(grupo)
      }
    }

    "si un grupo se queda sin puertas posibles para abrir, lanza excepcion" in {
      val grupo = Grupo(List(magoConoceVislumbrarYEsLento, ladronAbrePuertaCerrada, guerreroLento))
      val puerta: Puerta = PuertaCerrada(
        Habitacion(NoPasaNada,
          List(
            PuertaCerrada(Habitacion(TrampaDeLeones)),
            PuertaEncantada(Vislumbrar, Habitacion(TrampaDeLeones)),
            PuertaEncantada(Vislumbrar, Habitacion(MuchosDardos()), salida = true)
          )
        )
      )
      val calabozo = Calabozo(puertaPrincipal = puerta)

      assertThrows[NoHayPuertasParaAbrirException] {
        calabozo.recorrer(grupo)
      }
    }

    "un grupo puede recorrer un calabozo facil" in {
      val grupo = Grupo(List(magoConoceVislumbrar, ladronAbrePuertaCerrada, guerreroLento))
      val puerta: Puerta = PuertaCerrada(
        Habitacion(NoPasaNada,
          List(
            PuertaCerrada(Habitacion(NoPasaNada)),
            PuertaCerrada(Habitacion(TrampaDeLeones)),
            PuertaEncantada(Vislumbrar, Habitacion(TrampaDeLeones)),
            PuertaEncantada(Vislumbrar, Habitacion(MuchosDardos()), salida = true)
          )
        )
      )
      val calabozo = Calabozo(puertaPrincipal = puerta)

      assert(calabozo.recorrer(grupo).isSuccess)
    }

    "un grupo puede recorrer un calabozo dificil" in {
      val grupo = Grupo(List(magoConoceVislumbrar, ladronAbrePuertaCerrada, guerreroLento))
      val puerta: Puerta = PuertaCerrada(
        Habitacion(NoPasaNada,
          List(
            PuertaCerrada(Habitacion(NoPasaNada)),
            PuertaCerrada(Habitacion(TrampaDeLeones)),
            PuertaEncantada(Vislumbrar,
              Habitacion(TrampaDeLeones, List(PuertaEscondida(Habitacion(Encuentro(perdidoDebil)))))
            ),
            PuertaEncantada(Vislumbrar, Habitacion(MuchosDardos())),
            PuertaEncantada(Vislumbrar, Habitacion(MuchosDardos()), salida = true)
          )
        )
      )
      val calabozo = Calabozo(puertaPrincipal = puerta)

      assert(calabozo.recorrer(grupo).isSuccess)
    }

    "un grupo puede recorrer un calabozo si encuentra la salida rapidamente" in {
      val grupo = Grupo(List(liderGuerreroHeroico, magoConoceVislumbrar, ladronAbrePuertaCerrada, guerreroLento))
      val puerta: Puerta = PuertaCerrada(
        Habitacion(NoPasaNada,
          List(
            PuertaCerrada(Habitacion(NoPasaNada)),
            PuertaCerrada(Habitacion(TrampaDeLeones), salida = true),
            PuertaEncantada(Vislumbrar, Habitacion(TrampaDeLeones)),
            PuertaEncantada(Vislumbrar, Habitacion(MuchosDardos()), salida = true)
          )
        )
      )
      val calabozo = Calabozo(puertaPrincipal = puerta)

      assert(calabozo.recorrer(grupo).isSuccess)
    }
  }
}