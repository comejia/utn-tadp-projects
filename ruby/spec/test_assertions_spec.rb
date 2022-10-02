require_relative '../src/aserciones'
require_relative '../src/syntax_sugar'
require_relative '../tests/persona'

describe 'Assertions' do

  Object.include Assertions
  include Conditions
  include SyntaxSugar

  let(:un_proc) {
    proc {}
  }
  let(:otro_proc) do
    proc { true }
  end
  let(:adulto) {
    Persona.new(90)
  }
  let(:joven) {
    Persona.new(18)
  }
  let(:persona) {
    Persona.new(30)
  }

  describe 'Ser' do
    it 'un objeto entiende el mensaje "deberia"' do
      expect(un_proc.methods.include? :deberia).to be true
    end

    it 'un objeto deberia ser el mismo objeto' do
      expect((persona.deberia ser persona).fetch(:result)).to be true
    end

    it 'un numero deberia ser el mismo numero' do
      expect((7.deberia ser 7).fetch(:result)).to be true
    end

    it 'un numero es diferente a otro si no es el mismo numero' do
      expect((7.deberia ser 8).fetch(:result)).to be false
    end

    it 'un valor verdadero es siempre verdadero' do
      expect((true.deberia ser true).fetch(:result)).to be true
    end

    it 'un valor falso nunca puede ser verdadero' do
      expect((false.deberia ser true).fetch(:result)).to be false
    end

    it 'una persona adulta es mayor a una persona joven' do
      expect((adulto.edad.deberia ser mayor_a joven.edad).fetch(:result)).to be true
    end

    it 'una persona joven no puede ser mayor a una persona adulta' do
      expect((joven.edad.deberia ser mayor_a adulto.edad).fetch(:result)).to be false
    end

    it 'una persona joven es menor a una persona adulta' do
      expect((joven.edad.deberia ser menor_a adulto.edad).fetch(:result)).to be true
    end

    it 'una persona adulta no puede ser menor a una persona joven' do
      expect((adulto.edad.deberia ser menor_a joven.edad).fetch(:result)).to be false
    end

    it 'la edad de una persona se encuentra en la lista' do
      expect((persona.edad.deberia ser uno_de_estos [15, 30, "lista"]).fetch(:result)).to be true
    end

    it 'la edad de una persona no se encuentra en la lista' do
      expect((persona.edad.deberia ser uno_de_estos [15, 90, "lista"]).fetch(:result)).to be false
    end

    it 'la edad de una persona se encuentra entre los parametros' do
      expect((persona.edad.deberia ser uno_de_estos 15, 30, "lista").fetch(:result)).to be true
    end

    it 'la edad de una persona no se encuentra entre los parametros' do
      expect((persona.edad.deberia ser uno_de_estos 15, 90, "lista").fetch(:result)).to be false
    end

    it 'una persona vieja deberia ser viejo' do
      expect((adulto.deberia ser_viejo).fetch(:result)).to be true
      expect((adulto.viejo?.deberia ser true).fetch(:result)).to be true
    end

    it 'una persona que no entiende el mensaje entonces explotar' do
      expect { (persona.deberia ser_joven) }.to raise_error NoMethodError
    end
  end

  describe 'Tener' do
    it 'una persona joven deberia tener 18' do
      expect((joven.deberia tener_edad 18).fetch(:result)).to be true
    end

    it 'una persona sin nombre no entiende cuando lo llaman' do
      expect((persona.deberia tener_nombre "pepe").fetch(:result)).to be false
    end

    it 'una persona adulta deberia tener edad mayor a 20' do
      expect((adulto.deberia tener_edad mayor_a 20).fetch(:result)).to be true
    end

    it 'una persona joven deberia tener edad menor a 25' do
      expect((joven.deberia tener_edad menor_a 25).fetch(:result)).to be true
    end

    it 'la edad de una persona se encuentra en la lista' do
      expect((persona.deberia tener_edad uno_de_estos [15, 30, "lista"]).fetch(:result)).to be true
    end
  end

  describe 'Entender' do
    it 'una persona adulta deberia entender cuando le dicen viejo' do
      expect((adulto.deberia entender :viejo?).fetch(:result)).to be true
    end

    it 'una persona deberia entender cuando lo llaman como a su padre' do
      expect((persona.deberia entender :class).fetch(:result)).to be true
    end

    it 'una persona sin nombre no entiende cuando lo llaman' do
      expect((persona.deberia entender :nombre).fetch(:result)).to be false
    end

    it 'todo objeto entiende method_missing' do
      expect((persona.deberia entender :method_missing).fetch(:result)).to be true
    end
  end

  describe 'Explotar' do
    it 'la division por 0 deberia explotar' do
      expect((en { 7 / 0 }.deberia explotar_con ZeroDivisionError).fetch(:result)).to be true
    end

    it 'una persona que no entiende un mensaje deberia explotar' do
      expect((en { persona.nombre }.deberia explotar_con NoMethodError).fetch(:result)).to be true
    end

    it 'una persona que no entiende un mensaje deberia explotar con un error generico' do
      expect((en { persona.nombre }.deberia explotar_con StandardError).fetch(:result)).to be true
    end

    it 'una persona que entiende un mensaje no deberia explotar' do
      expect((en { persona.viejo? }.deberia explotar_con NoMethodError).fetch(:result)).to be false
    end

    it 'no se puede evaluar un error con otro tipo de error' do
      expect((en { 7 / 0 }.deberia explotar_con NoMethodError).fetch(:result)).to be false
    end
  end

end