import calabozos._
import org.scalatest.freespec.AnyFreeSpec

class AnalizadorSpec extends AnyFreeSpec {
  val guerreroLento: Aventurero = Aventurero(Caracteristica(fuerza = 50, 1, 10), Guerrero, Introvertido, Heroico)
  val ladronAbrePuertaCerrada: Aventurero = Aventurero(Caracteristica(50, 10, nivel = 10), Ladron(10), Introvertido, Heroico)
  val ladronSinHabilidad: Aventurero = Aventurero(Caracteristica(50, 10, nivel = 3), Ladron(2), Introvertido, Heroico)
  val magoConoceVislumbrar: Aventurero = Aventurero(Caracteristica(50, 100, nivel = 10), Mago(Set((Vislumbrar, 3))), Introvertido, Heroico)
  val magoConoceVislumbrarYEsLento: Aventurero = Aventurero(Caracteristica(50, 2, nivel = 10), Mago(Set((Vislumbrar, 3))), Introvertido, Heroico)

  val liderGuerreroHeroico: Aventurero = Aventurero(Caracteristica(50, 1000, nivel = 10), Guerrero, Introvertido, Heroico)

  val perdidoDebil: Aventurero = Aventurero(Caracteristica(40, 1000, nivel = 10), Guerrero, Loquito, Heroico)

  "Mejor Grupo" - {
    "dado una lista de grupos y un calabozo, se obtiene el mejor" in {
      val grupoNoPasaNada = Grupo(List(guerreroLento, ladronSinHabilidad))
      val grupoPasaAlgunosObstaculos = Grupo(List(guerreroLento, ladronAbrePuertaCerrada, magoConoceVislumbrarYEsLento))
      val grupoPasaTodo = Grupo(List(magoConoceVislumbrar, ladronAbrePuertaCerrada, guerreroLento))

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

      val mejorGrupo = analizador.mejorGrupo(List(grupoNoPasaNada, grupoPasaAlgunosObstaculos, grupoPasaTodo), calabozo)

      assert(mejorGrupo.isSuccess)
      assert(mejorGrupo.get.puntaje() == 11)
    }

    "si ningun grupo recorre el calabozo, se lanza excepcion" in {
      val grupoNoPasaNada = Grupo(List(guerreroLento, ladronSinHabilidad))
      val grupoPasaAlgunosObstaculos = Grupo(List(guerreroLento, magoConoceVislumbrarYEsLento))
      val grupoPasaTodo = Grupo(List(magoConoceVislumbrar, ladronAbrePuertaCerrada))

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

      assertThrows[NingunGrupoAtravesoElCalabozo] {
        analizador.mejorGrupo(List(grupoNoPasaNada, grupoPasaAlgunosObstaculos, grupoPasaTodo), calabozo).get
      }
    }
  }

  "Nivel necesario" - {
    "dado un grupo y un calabozo, se obtiene los niveles necesarios para recorrer calabozo" in {
      val magoAprendizDeVislumbrar = Aventurero(Caracteristica(50, 100, nivel = 0), Mago(Set((Vislumbrar, 8))), Introvertido, Heroico)
      val ladronInicial = Aventurero(Caracteristica(50, 10, nivel = 0), Ladron(2), Introvertido, Heroico)
      val grupoAmateur = Grupo(List(guerreroLento, ladronInicial, magoAprendizDeVislumbrar))

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

      val nivelesNecesarios = analizador.nivelesNecesarios(grupoAmateur, calabozo)

      assert(nivelesNecesarios.isSuccess)
      assert(nivelesNecesarios.get == 8)
    }

    "dado un grupo y un calabozo, cuando alcanzan el intento maximo para recorrer el calabozo, se lanza excepcion" in {
      val magoAprendizDeVislumbrar = Aventurero(Caracteristica(50, 100, nivel = 0), Mago(Set((Vislumbrar, 8))), Introvertido, Heroico)
      val ladronInicial = Aventurero(Caracteristica(50, 10, nivel = 0), Ladron(2), Introvertido, Heroico)
      val grupoAmateur = Grupo(List(guerreroLento, ladronInicial, magoAprendizDeVislumbrar))

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

      analizador.nivelMaximo = 2
      val nivelesNecesarios = analizador.nivelesNecesarios(grupoAmateur, calabozo)

      assert(nivelesNecesarios.isFailure)
    }
  }
}
