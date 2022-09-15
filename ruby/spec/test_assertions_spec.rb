require_relative '../src/aserciones'

class Dummy
end

describe 'Assertions' do
  let(:una_clase) {
    Dummy.new
  }

  it 'un objeto entiende el mensaje "deberia"' do
    expect(una_clase.methods.include? :deberia).to be true
  end

  it 'un numero deberia ser el mismo numero' do
    expect(7.deberia ser 7).to be true
  end

  it 'un numero es diferente a otro si no es el mismo numero' do
    expect(7.deberia ser 8).to be false
  end

  it 'un valor verdadero deberia ser siempre verdadero' do
    expect(true.deberia ser true).to be true
  end

  it 'un valor falso nunca puede ser verdadero' do
    expect(false.deberia ser true).to be false
  end
end