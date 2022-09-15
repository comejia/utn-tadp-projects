require_relative '../src/aserciones'

class Persona
  attr_accessor :edad

  def initialize(edad)
    @edad = edad
  end
end

describe 'Assertions' do
  let(:un_objeto) {
    proc {  }
  }
  let(:adulto) {
    Persona.new(90)
  }
  let(:joven) {
    Persona.new(18)
  }

  it 'un objeto entiende el mensaje "deberia"' do
    expect(un_objeto.methods.include? :deberia).to be true
  end

  it 'un numero deberia ser el mismo numero' do
    expect(7.deberia ser 7).to be true
  end

  it 'un numero es diferente a otro si no es el mismo numero' do
    expect(7.deberia ser 8).to be false
  end

  it 'un valor verdadero es siempre verdadero' do
    expect(true.deberia ser true).to be true
  end

  it 'un valor falso nunca puede ser verdadero' do
    expect(false.deberia ser true).to be false
  end

  it 'una persona adulta es mayor a una persona joven' do
    expect(adulto.edad.deberia ser mayor_a joven.edad).to be true
  end

  it 'una persona joven no puede ser mayor a una persona adulta' do
    expect(joven.edad.deberia ser mayor_a adulto.edad).to be false
  end

  it 'una persona joven es menor a una persona adulta' do
    expect(joven.edad.deberia ser menor_a adulto.edad).to be true
  end
end