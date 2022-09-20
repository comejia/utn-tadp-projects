module SyntaxSugar
  def method_missing(symbol, *args, &block)
    puts "Simbolo no definido: #{symbol}"

    if symbol.to_s.start_with?('ser_')
      mensaje = symbol.to_s.gsub('ser_', '') + '?'

      return proc do |x|
        x.send(mensaje.to_sym)
      end
    elsif symbol.to_s.start_with?('tener_')
      atributo = '@' + symbol.to_s.gsub('tener_', '')
      return proc do |x|
        value = x.instance_variable_get(atributo.to_sym)

        if args[0].is_a? Proc
          result = args[0].call(value)
        else
          result = args[0].eql? value
        end
        result
      end
    end
    nil
  end

  def respond_to_missing?(asd, val)
    puts "RESPOND TO MISSING"
  end
end
