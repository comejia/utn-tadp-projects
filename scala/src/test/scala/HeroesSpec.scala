import calabozos.{Aventurero, Caracteristica, Guerrero, Heroico, Introvertido, Ladron, Mago, Vislumbrar}
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._

class HeroesSpec extends AnyFreeSpec {


  "Heroes" - {

    "un aventurero esta muerto cuando su salud es 0" in {
      val aventurero = Aventurero(Caracteristica(10, 10, 10), Guerrero, Introvertido, Heroico)
      aventurero.salud = 0

      aventurero.muerto() shouldBe true
    }

    "un aventurero no esta muerto cuando su salud es mayor a 0" in {
      val aventurero = Aventurero(Caracteristica(10, 10, 10), Guerrero, Introvertido, Heroico)
      aventurero.salud = 20

      aventurero.muerto() shouldBe false
    }

    "un guerrero tiene un incremento del 20% de su fuerza por cada nivel" in {
      val guerrero = Aventurero(Caracteristica(fuerza = 50, 10, 10), Guerrero, Introvertido, Heroico)

      guerrero.fuerza() shouldBe 51.0
    }

    "un ladron tiene un incremento de 3 de su habilidad por cada nivel" in {
      val ladron = Aventurero(Caracteristica(50, 10, nivel = 10), Ladron(60), Introvertido, Heroico)

      ladron.habilidad() shouldBe 90
    }

    "un mago puede utilizar hechizos cuando alcanza el nivel necesario" in {
      val mago = Mago(Set((Vislumbrar, 3)))

      mago.puedeUsarHechizo(Caracteristica(50, 10, nivel = 4), Vislumbrar) shouldBe true
    }

    "un mago no puede utilizar hechizos si no alcanza el nivel necesario" in {
      val mago = Mago(Set((Vislumbrar, 3)))

      mago.puedeUsarHechizo(Caracteristica(50, 10, nivel = 2), Vislumbrar) shouldBe false
    }
  }

}
