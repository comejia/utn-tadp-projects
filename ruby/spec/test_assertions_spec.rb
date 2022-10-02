require_relative '../src/aserciones'
require_relative '../tests/persona'

describe 'Assertions' do

  Object.include Assertions
  self.include Conditions

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
end