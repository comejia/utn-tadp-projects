require_relative 'persona'
require_relative '../src/mocks'

module A
  def a
    :a
  end
end

class B
  include A
end

class SuitePersonaWithMocks
  using Mocks

  def testear_que_personas_viejas_trae_solo_a_los_viejos
    nico = Persona.new(51)
    axel = Persona.new(51)
    lean = Persona.new(22)

    # Mockeo el mensaje para no consumir el servicio y simplificar el test
    PersonaHome.mockear(:todas_las_personas) do
      [nico, axel, lean]
    end

    home = PersonaHome.new
    viejos = home.personas_viejas

    viejos.deberia ser [nico, axel]
  end

  def testear_que_puedo_mockear_metodo_de_un_modulo
    A.mockear(:a) do
      :b
    end

    b = B.new
    b.a.deberia ser :b
  end
end

