require_relative 'persona'
require_relative '../src/spies'

class SuitePersonaWithSpies
  using Spies
  def testear_que_se_use_la_edad
      pato = Persona.new(23)
      pato = espiar(pato)
      pato.viejo?

      pato.deberia haber_recibido(:edad).veces(1)
      pato.deberia haber_recibido(:viejo?).con_argumentos()
    
  end
end