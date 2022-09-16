require_relative 'persona'

class UnaSuite
  def testear_que_una_persona_mayor_tiene_mas_de_50
    p = Persona.new(80)
    p.edad.deberia ser mayor_a 50
  end

  def testear_que_una_persona_mayor_es_viejo
    p = Persona.new(80)
    p.deberia ser_viejo
  end

  def testear_que_verdadero_siempre_es_verdadero
    true.deberia ser true
  end

  def testear_que_un_numero_es_mayor_a_otro
    100.deberia ser mayor_a 20
  end

  def si_no_es_un_test_se_ignora
    false.deberia ser true
  end
end
