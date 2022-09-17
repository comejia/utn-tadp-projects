require_relative 'persona'

class SuitePersona

  def testear_que_una_persona_tiene_25
    p = Persona.new(25)
    p.edad.deberia ser 25
  end

  def testear_que_una_persona_mayor_tiene_mas_de_50
    p = Persona.new(80)
    p.edad.deberia ser mayor_a 50
  end

  def testear_que_la_edad_esta_en_una_lista
    p = Persona.new(80)
    p.edad.deberia ser uno_de_estos [10, true, 80, "Holaa"]
  end

  def testear_que_la_edad_esta_en_los_parametros
    p = Persona.new(80)
    p.edad.deberia ser uno_de_estos 10, true, 80, "Holaa"
  end

  def testear_que_una_persona_es_viejo
    p = Persona.new(80)
    p.deberia ser_viejo
  end

  def testear_que_una_persona_joven_no_es_viejo
    p = Persona.new(18)
    p.deberia ser_viejo
  end
end
