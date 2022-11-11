import calabozos._
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._

class PuertasSpec extends AnyFreeSpec {
  "Puertas Cerrada" - {

    "una Puerta Cerrada se abre si el grupo tiene el item Llave" in {
      val guerrero = Aventurero(Caracteristica(fuerza = 50, 10, 10), Guerrero, Introvertido, Heroico)
      val muerto = Aventurero(Caracteristica(50, 10, nivel = 10), Ladron(60), Introvertido, Heroico, salud = 0)
      val grupo = Grupo(List(guerrero, muerto)).agregarItem(Llave)

      val puertaCerrada = PuertaCerrada(Habitacion(NoPasaNada))
      grupo.puedeAbrir(puertaCerrada) shouldBe true
    }

    "una Puerta Cerrada se abre si en el grupo hay un ladron con habilidad >= 10" in {
      val guerrero = Aventurero(Caracteristica(fuerza = 50, 10, 10), Guerrero, Introvertido, Heroico)
      val ladron = Aventurero(Caracteristica(50, 10, nivel = 10), Ladron(15), Introvertido, Heroico)
      val muerto = Aventurero(Caracteristica(50, 10, nivel = 10), Ladron(60), Introvertido, Heroico, salud = 0)
      val grupo = Grupo(List(guerrero, ladron, muerto))

      val puertaCerrada = PuertaCerrada(Habitacion(NoPasaNada))
      grupo.puedeAbrir(puertaCerrada) shouldBe true
    }

    "una Puerta Cerrada se abre si en el grupo hay un ladron y tiene item Ganzuas" in {
      val guerrero = Aventurero(Caracteristica(50, 10, 10), Guerrero, Introvertido, Heroico)
      val ladron = Aventurero(Caracteristica(50, 10, nivel = 10), Ladron(5), Introvertido, Heroico)
      val muerto = Aventurero(Caracteristica(50, 10, 10), Ladron(60), Introvertido, Heroico, salud = 0)
      val grupo = Grupo(List(guerrero, ladron, muerto)).agregarItem(Ganzuas)

      val puertaCerrada = PuertaCerrada(Habitacion(NoPasaNada))
      grupo.puedeAbrir(puertaCerrada) shouldBe true
    }

    "una Puerta Cerrada no se abre si el grupo no tiene item Llave ni un ladron con Ganzuas" in {
      val guerrero = Aventurero(Caracteristica(50, 10, 10), Guerrero, Introvertido, Heroico)
      val ladron = Aventurero(Caracteristica(50, 10, nivel = 1), Ladron(5), Introvertido, Heroico)
      val grupo = Grupo(List(guerrero, ladron))

      val puertaCerrada = PuertaCerrada(Habitacion(NoPasaNada))
      grupo.puedeAbrir(puertaCerrada) shouldBe false
    }
  }

  "Puertas Escondida" - {

    "una Puerta Escondida se abre si el grupo tiene un mago que conoce el hechizo Vislumbrar" in {
      val guerrero = Aventurero(Caracteristica(50, 10, 10), Guerrero, Introvertido, Heroico)
      val mago = Aventurero(Caracteristica(50, 10, nivel = 10), Mago(Set((Vislumbrar, 3))), Introvertido, Heroico)
      val grupo = Grupo(List(guerrero, mago))

      val puertaEscondida = PuertaEscondida(Habitacion(NoPasaNada))
      grupo.puedeAbrir(puertaEscondida) shouldBe true
    }

    "una Puerta Escondida se abre si en el grupo hay un ladron con habilidad >= 6" in {
      val guerrero = Aventurero(Caracteristica(50, 10, 10), Guerrero, Introvertido, Heroico)
      val ladron = Aventurero(Caracteristica(50, 10, 5), Ladron(6), Introvertido, Heroico)
      val grupo = Grupo(List(guerrero, ladron))

      val puertaEscondida = PuertaEscondida(Habitacion(NoPasaNada))
      grupo.puedeAbrir(puertaEscondida) shouldBe true
    }

    "una Puerta Escondida no se abre si en el grupo no hay un mago que conoce el hechizo Vislumbrar ni un ladron habilidoso" in {
      val guerrero = Aventurero(Caracteristica(50, 10, 10), Guerrero, Introvertido, Heroico)
      val mago = Aventurero(Caracteristica(50, 10, nivel = 1), Mago(Set((Vislumbrar, 3))), Introvertido, Heroico)
      val ladron = Aventurero(Caracteristica(50, 10, 0), Ladron(3), Introvertido, Heroico)
      val grupo = Grupo(List(guerrero, ladron, mago))

      val puertaEscondida = PuertaEscondida(Habitacion(NoPasaNada))
      grupo.puedeAbrir(puertaEscondida) shouldBe false
    }
  }

  "Puertas Encantada" - {

    "una Puerta Encantada se abre si el grupo tiene un mago que conoce el hechizo de la puerta" in {
      val guerrero = Aventurero(Caracteristica(50, 10, 10), Guerrero, Introvertido, Heroico)
      val mago = Aventurero(Caracteristica(50, 10, nivel = 10), Mago(Set((Vislumbrar, 3))), Introvertido, Heroico)
      val grupo = Grupo(List(guerrero, mago))

      val puertaEscondida = PuertaEncantada(Vislumbrar, Habitacion(NoPasaNada))
      grupo.puedeAbrir(puertaEscondida) shouldBe true
    }

    "una Puerta Encantada no se abre si el grupo tiene un mago que no conoce el hechizo de la puerta" in {
      val guerrero = Aventurero(Caracteristica(50, 10, 10), Guerrero, Introvertido, Heroico)
      val mago = Aventurero(Caracteristica(50, 10, nivel = 1), Mago(Set((Vislumbrar, 3))), Introvertido, Heroico)
      val grupo = Grupo(List(guerrero, mago))

      val puertaEncantada = PuertaEncantada(Vislumbrar, Habitacion(NoPasaNada))
      grupo.puedeAbrir(puertaEncantada) shouldBe false
    }
  }

  "Cualquier puerta" - {

    "cualquier puerta se abre por un grupo que tiene un ladron con habilidad >= 20" in {
      val guerrero = Aventurero(Caracteristica(50, 10, 10), Guerrero, Introvertido, Heroico)
      val ladron = Aventurero(Caracteristica(50, 10, 5), Ladron(20), Introvertido, Heroico)
      val grupo = Grupo(List(guerrero, ladron))

      val puertaCerrada = PuertaCerrada(Habitacion(NoPasaNada))
      val puertaEscondida = PuertaEscondida(Habitacion(NoPasaNada))
      val puertaEncantada = PuertaEncantada(Vislumbrar, Habitacion(NoPasaNada))

      grupo.puedeAbrir(puertaCerrada) shouldBe true
      grupo.puedeAbrir(puertaEscondida) shouldBe true
      grupo.puedeAbrir(puertaEncantada) shouldBe true
    }

  }
}
