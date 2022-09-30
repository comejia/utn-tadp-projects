module Assertions
  def deberia(param)
    param.call(self)
  end

  def ser(param)
    unless param.is_a? Proc
      return proc { |x|
        result = x.eql? param
        {
          result: result,
          description: result ? "." : "Espera que #{x} sea #{param}"
        }
      }
    end

    param
  end

  def mayor_a(param)
    proc { |x|
      result = x > param
      {
        result: result,
        description: result ? "." : "Espera que #{x} sea mayor a #{param}"
      }
    }
  end

  def menor_a(param)
    proc { |x|
      result = x < param
      {
        result: result,
        description: result ? "." : "Espera que #{x} sea menor a #{param}"
      }
    }
  end

  def uno_de_estos(*params)
    params.flatten!
    proc { |x|
      result = params.any? x
      {
        result: result,
        description: result ? "." : "Espera que #{x} sea uno de #{params}"
      }
    }
  end

  def entender(param)
    proc do |x|
      result = x.respond_to? param, true
      {
        result: result,
        description: result ? "." : "Espera que #{x} entienda #{param}"
      }
    end
  end

  def en(&bloque)
    bloque
  end

  def explotar_con(param)
    proc do |x|
      begin
        x.call
      rescue param
        {
          result: true,
          description: "."
        }
      rescue => e # Other error
        {
          result: false,
          description: "Espera que #{x} explote con #{param} y exploto con #{e.class}"
        }
      else # No error
        {
          result: false,
          description: "Espera que #{x} explote con #{param} y no exploto"
        }
      end
    end
  end
end
