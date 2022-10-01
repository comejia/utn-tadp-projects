module SyntaxSugar
  def method_missing(symbol, *args, &block)
    if symbol.to_s.start_with?('ser_')
      mensaje = symbol.to_s.gsub('ser_', '') + '?'
      return proc do |x|
        result = x.send(mensaje.to_sym)
        {
          result: result,
          description: result ? "." : "Espera que #{x} sea #{mensaje}"
        }
      end
    elsif symbol.to_s.start_with?('tener_')
      atributo = '@' + symbol.to_s.gsub('tener_', '')
      return proc do |x|
        unless x.instance_variables.include?(atributo.to_sym)
          {
            result: false,
            description: "#{x} no tiene el atributo #{atributo}"
          }
        else
          value = x.instance_variable_get(atributo.to_sym)
          if args[0].is_a? Proc
            args[0].call(value)
          else
            result = args[0].eql? value
            {
              result: result,
              description: result ? "." : "Espera que #{x} tenga #{atributo}"
            }
          end

        end
      end
    else
      super
    end
  end

  def respond_to_missing?(symbol, priv = false)
    symbol.to_s.start_with?('ser_') || symbol.to_s.start_with?('tener_') || super
  end
end
