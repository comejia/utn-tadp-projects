import calabozos._
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._

class HabitacionesSpec extends AnyFreeSpec {

  "Habitaciones" - {

    "un grupo que atraviesa la situacion NoPasaNada sigue igual" in {
      val guerrero = Aventurero(Caracteristica(50, 10, 10), Guerrero, Introvertido, Heroico)
      val ladron = Aventurero(Caracteristica(50, 10, 10), Ladron(60), Introvertido, Heroico)
      val grupo = Grupo(List(guerrero, ladron))

      val habitacion = Habitacion(NoPasaNada)

      habitacion.aplicarEfecto(grupo) shouldBe grupo
    }

    "un grupo que atraviesa la situacion TesoroPerdido agrega a su cofre el tesor" in {
      val guerrero = Aventurero(Caracteristica(50, 10, 10), Guerrero, Introvertido, Heroico)
      val ladron = Aventurero(Caracteristica(50, 10, 10), Ladron(60), Introvertido, Heroico)
      val grupo = Grupo(List(guerrero, ladron)).agregarItem(Ganzuas)

      val habitacion = Habitacion(TesoroPerdido(Llave))

      habitacion.aplicarEfecto(grupo) shouldBe grupo.agregarItem(Llave)
    }

    "cuando un grupo atraviesa la situacion MuchosDardos, todos los heroes pierden 10 puntos de salud" in {
      val guerrero = Aventurero(Caracteristica(50, 10, 10), Guerrero, Introvertido, Heroico)
      val ladron = Aventurero(Caracteristica(50, 10, 10), Ladron(60), Introvertido, Heroico)
      val grupo = Grupo(List(guerrero, ladron))

      val habitacion = Habitacion(MuchosDardos(20))

      habitacion.aplicarEfecto(grupo) shouldBe grupo.perderSalud(20)
    }

    "cuando un grupo atraviesa la situacion TrampaDeLeones, el heroe mas lento muere" in {
      val guerrero = Aventurero(Caracteristica(50, 10, 10), Guerrero, Introvertido, Heroico)
      val lento = Aventurero(Caracteristica(50, 1, 10), Ladron(60), Introvertido, Heroico, salud = 80)
      val grupo = Grupo(List(guerrero, lento))

      val habitacion = Habitacion(TrampaDeLeones)

      val grupoAfectado = habitacion.aplicarEfecto(grupo)
      lento.salud = 0

      grupoAfectado.aventurerosVivos() shouldBe grupo.aventurerosVivos()
    }

    "Encuentro" - {
      val introvertido = Aventurero(Caracteristica(50, 10, 10), Guerrero, Introvertido, Heroico)
      val ladron = Aventurero(Caracteristica(50, 1, 10), Ladron(60), Introvertido, Heroico)
      val mago = Aventurero(Caracteristica(50, 10, 10), Mago(Set((Vislumbrar, 3))), Introvertido, Heroico)

      "un grupo chico con lider introvertido y un heroe perdido introvertido, se agradan" in {
        val grupo = Grupo(List(introvertido, ladron))
        val perdido = Aventurero(Caracteristica(10, 10, 10), Guerrero, Introvertido, Heroico)

        val habitacion = Habitacion(Encuentro(perdido))

        habitacion.aplicarEfecto(grupo).aventurerosVivos() shouldBe List(introvertido, ladron, perdido)
      }

      "un grupo sin ladrones, con lider introvertido y un heroe perdido bigote, se agradan" in {
        val grupo = Grupo(List(introvertido))
        val perdido = Aventurero(Caracteristica(10, 10, 10), Ladron(10), Bigote, Heroico)

        val habitacion = Habitacion(Encuentro(perdido))

        habitacion.aplicarEfecto(grupo).aventurerosVivos() shouldBe List(introvertido, perdido)
      }

      "un grupo con item particular, con lider introvertido y un heroe perdido interesado en el item, se agradan" in {
        val grupo = Grupo(List(introvertido)).agregarItem(Ganzuas)
        val perdido = Aventurero(Caracteristica(10, 10, 10), Ladron(10), Interesado(Ganzuas), Heroico)

        val habitacion = Habitacion(Encuentro(perdido))

        habitacion.aplicarEfecto(grupo).aventurerosVivos() shouldBe List(introvertido, perdido)
      }

      "cuando un grupo es fuerte y un heroe perdido nose agradan, van a muerte y el grupo sube de nivel" in {
        val grupo = Grupo(List(introvertido, ladron, mago))
        val perdido = Aventurero(Caracteristica(fuerza = 50, 10, 10), Ladron(10), Loquito, Heroico)

        val habitacion = Habitacion(Encuentro(perdido))

        habitacion.aplicarEfecto(grupo) shouldBe grupo.subirNivel()
      }

      "cuando un grupo es debil y un heroe perdido nose agradan, van a muerte y el grupo pierde salud" in {
        val grupo = Grupo(List(introvertido, ladron, mago))
        val perdido = Aventurero(Caracteristica(fuerza = 180, 10, 10), Ladron(10), Loquito, Heroico)

        val habitacion = Habitacion(Encuentro(perdido))

        habitacion.aplicarEfecto(grupo) shouldBe grupo.perderSalud(60)
      }
    }
  }
}
