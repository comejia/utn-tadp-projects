class Persona
  attr_accessor :edad

  def initialize(edad)
    @edad = edad
  end

  def viejo?
    self.edad > 50
  end
end
