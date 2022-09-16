require_relative 'persona'
#require_relative 'tad_spec'

class UnaSuite
  def testear_que_una_persona_mayor_tiene_mas_de_50
    p = Persona.new(80)
    p.edad.deberia ser mayor_a 50
  end

  def testear_que_una_persona_mayor_es_viejo
    p = Persona.new(80)
    p.deberia ser_viejo
  end
end
