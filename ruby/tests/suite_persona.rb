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

  def testear_que_una_persona_no_entiende_un_mensaje
    p = Persona.new(18)
    p.deberia ser_joven
  end

  def testear_que_una_persona_tenga_25
    p = Persona.new(25)
    p.deberia tener_edad 25
  end

  def testear_que_una_persona_sin_nombre_falla
    p = Persona.new(25)
    p.deberia tener_nombre "pepe"
  end

  def testear_que_una_persona_tenga_edad_mayor_a_25
    p = Persona.new(30)
    p.deberia tener_edad mayor_a 25
  end

  def testear_que_una_persona_tenga_edad_menor_a_25
    p = Persona.new(20)
    p.deberia tener_edad menor_a 25
  end

  def testear_que_una_persona_tenga_edad_en_una_lista
    p = Persona.new(20)
    p.deberia tener_edad uno_de_estos [7, 55, true, "lista", 20]
  end

  def testear_que_una_persona_entiende_un_mensaje_propio
    p = Persona.new(20)
    p.deberia entender :viejo?
  end

  def testear_que_una_persona_no_entiende_un_mensaje_desconocido
    p = Persona.new(20)
    p.deberia entender :nombre
  end

  def testear_que_una_persona_entiende_un_mensaje_heredado
    p = Persona.new(20)
    p.deberia entender :class
  end

  def testear_que_una_persona_entiende_un_mensaje_sobreescrito
    p = Persona.new(20)
    p.deberia entender :method_missing
  end
end
