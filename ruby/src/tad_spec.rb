require_relative 'una_suite'
require_relative 'aserciones'

class TADSpec

  def self.test(param)
    Object.include Aserciones

    param.include Mixi

    a = param.new

    #a.send(:testear_que_una_persona_mayor_a_50_es_vieja)
    a.testear_que_una_persona_mayor_es_viejo
  end

end

module Mixi

  def method_missing(symbol, *args, &block)
    puts "MISSING"
    if symbol.to_s.start_with?('ser_')
      mensaje = symbol.to_s.gsub('ser_', '') + '?'

      return proc do |x|
        x.send(mensaje.to_sym)
      end
    end
    nil
  end

  def respond_to_missing?(asd, val)
    puts "RESPOND TO MISSING"
  end

end
