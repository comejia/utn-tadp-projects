import calabozos.{Aventurero, Caracteristica, Grupo, Guerrero, Heroico, Introvertido, Ladron, Mago, Vislumbrar}
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._

class GruposSpec extends AnyFreeSpec {

  "Grupos" - {

    "un grupo esta compuesto de aventureros vivos" in {
      val guerrero = Aventurero(Caracteristica(fuerza = 50, 10, 10), Guerrero, Introvertido, Heroico)
      val ladron = Aventurero(Caracteristica(50, 10, nivel = 10), Ladron(60), Introvertido, Heroico)
      val muerto = Aventurero(Caracteristica(50, 10, nivel = 10), Ladron(60), Introvertido, Heroico, salud = 0)

      val grupo = Grupo(List(guerrero, ladron, muerto))

      grupo.aventurerosVivos() shouldBe List(guerrero, ladron)
    }

    "todo grupo tiene un lider" in {
      val guerrero = Aventurero(Caracteristica(fuerza = 50, 10, 10), Guerrero, Introvertido, Heroico)
      val ladron = Aventurero(Caracteristica(50, 10, nivel = 10), Ladron(60), Introvertido, Heroico)
      val muerto = Aventurero(Caracteristica(50, 10, nivel = 10), Ladron(60), Introvertido, Heroico, salud = 0)

      val grupo = Grupo(List(guerrero, ladron, muerto))

      grupo.lider shouldBe guerrero
    }

    "si el lider del grupo muere, se pasa el liderazgo al siguente en la jerarquia" in {
      val guerrero = Aventurero(Caracteristica(fuerza = 50, 10, 10), Guerrero, Introvertido, Heroico)
      val ladron = Aventurero(Caracteristica(50, 10, nivel = 10), Ladron(60), Introvertido, Heroico)
      val muerto = Aventurero(Caracteristica(50, 10, nivel = 10), Ladron(60), Introvertido, Heroico, salud = 0)

      val grupo = Grupo(List(guerrero, ladron, muerto))
      guerrero.salud = 0

      grupo.lider shouldBe ladron
    }
  }

}