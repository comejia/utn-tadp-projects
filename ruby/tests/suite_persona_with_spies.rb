require_relative 'persona'
require_relative '../src/spies'

class SuitePersonaWithSpies
  using Spies

  def testear_que_se_use_viejo_se_llama_a_edad
    pato = Persona.new(23)
    pato = espiar(pato)
    pato.viejo?

    pato.deberia haber_recibido(:edad)
  end

  def testear_que_se_use_edad_deberia_haber_recibido_viejo_falla
    pato = Persona.new(23)
    pato = espiar(pato)
    pato.edad

    pato.deberia haber_recibido(:viejo?)
  end

  def testear_que_se_use_la_edad
    pato = Persona.new(23)
    pato = espiar(pato)
    pato.viejo?

    pato.deberia haber_recibido(:edad).veces(1)
    pato.deberia haber_recibido(:viejo?).con_argumentos
  end

  def testear_que_se_usa_metodo
    pato = Persona.new(23)
    pato = espiar(pato)
    pato.viejo?

    pato.deberia haber_recibido(:viejo?).veces(1)
  end

  def testear_que_se_usa_metodo_a_traves_de_otro
    pato = Persona.new(23)
    pato = espiar(pato)
    pato.viejo?

    pato.deberia haber_recibido(:edad).veces(1)
  end

  def testear_que_un_metodo_se_llama_varias_veces
    pato = Persona.new(23)
    pato = espiar(pato)
    pato.viejo?
    pato.viejo?
    pato.viejo?

    pato.deberia haber_recibido(:edad).veces(3)
  end

  def testear_que_un_metodo_que_se_llama_una_ves_no_puede_ser_llamado_varias_veces
    pato = Persona.new(23)
    pato = espiar(pato)
    pato.viejo?

    pato.deberia haber_recibido(:edad).veces(3)
  end

  def testear_que_se_llama_un_metodo_sin_argumentos
    pato = Persona.new(23)
    pato = espiar(pato)
    pato.viejo?

    pato.deberia haber_recibido(:viejo?).con_argumentos
  end

  def testear_que_un_metodo_sin_argumentos_falla_si_se_evaluan_los_argumentos
    pato = Persona.new(23)
    pato = espiar(pato)
    pato.viejo?

    pato.deberia haber_recibido(:viejo?).con_argumentos(12, "hola")
  end

  def testear_que_un_objeto_no_espiado_falla
    persona = Persona.new(23)
    persona.viejo?

    persona.deberia haber_recibido(:edad).veces(1)
  end
end