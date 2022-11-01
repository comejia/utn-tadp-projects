import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._
import viejo.{Aventurero, Grupo, Guerrero, Ladron, Mago}

class HeroesSpec extends AnyFreeSpec {


  "Heroes" - {

    "un aventurero esta muerto cuando su salud es 0" in {
      val aventurero = new Aventurero(10, 10, 10)
      aventurero.salud = 0

      aventurero.muerto() shouldBe true
    }

    "un aventurero no esta muerto cuando su salud es mayor a 0" in {
      val aventurero = new Aventurero(10, 10, 10)
      aventurero.salud = 20

      aventurero.muerto() shouldBe false
    }

    "un guerrero tiene un incremento del 20% de su fuerza por cada nivel" in {
      val guerrero = new Guerrero(10, 10, 3)

      guerrero.fuerza() shouldBe 16
    }

    "un ladron tiene un incremento de 3 de su habilidad por cada nivel" in {
      val ladron = new Ladron(10, 10, 4, 5)

      ladron.habilidad() shouldBe 17
    }

    "un mago puede utilizar hechizos cuando alcanza el nivel necesario" in {
      val mago = new Mago(10, 10, 4)
      mago.nivel = 5

      mago.puedeUsarHechizo() shouldBe true
    }

    "un mago no puede utilizar hechizos si no alcanza el nivel necesario" in {
      val mago = new Mago(10, 10, 4)
      mago.nivel = 3

      mago.puedeUsarHechizo() shouldBe false
    }
  }

  "Grupos" - {

    "un grupo esta compuesto de aventureros vivos" in {
      val mago = new Mago(10, 10, 4)
      val ladron = new Ladron(10, 10, 4, 5)
      val muerto = new Aventurero(10, 10, 10, 0)

      val grupo = new Grupo(List(mago, ladron, muerto))

      grupo.aventureros shouldBe List(mago, ladron)
    }

    "todo grupo tiene un lider" in {
      val mago = new Mago(10, 10, 4)
      val ladron = new Ladron(10, 10, 4, 5)
      val muerto = new Aventurero(10, 10, 10, 0)

      val grupo = new Grupo(List(mago, ladron, muerto))

      grupo.lider shouldBe mago
    }

    "si el lider del grupo muere, se pasa el liderazgo al siguente en la lista" in {
      val mago = new Mago(10, 10, 4)
      val ladron = new Ladron(10, 10, 4, 5)
      val muerto = new Aventurero(10, 10, 10, 0)

      val grupo = new Grupo(List(mago, ladron, muerto))
      mago.salud = 0

      grupo.lider shouldBe ladron
    }
  }

}
