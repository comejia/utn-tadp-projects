class Persona
  attr_accessor :edad

  def initialize(edad)
    @edad = edad
  end

  def viejo?
    self.edad > 50
  end
end

class PersonaHome
  def todas_las_personas
    puts 'Este método consume un servicio web que consulta una base de datos'
  end

  def personas_viejas
    self.todas_las_personas.select{|p| p.viejo?}
  end
end
