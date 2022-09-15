require_relative '../src/aserciones'

class Dummy
end

describe 'Assertions' do
  let(:una_clase) {
    Dummy.new
  }

  it 'un objeto deberia tener el metodo "deberia"' do
    expect(una_clase.methods.include? :deberia).to be true
  end
end