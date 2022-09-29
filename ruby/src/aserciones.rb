module Assertions
  def deberia(param)
    param.call(self)
  end

  def ser(param)
    unless param.is_a? Proc
      return proc { |x|
        x.eql? param
      }
    end

    param
  end

  def mayor_a(param)
    proc { |x|
      x > param
    }
  end

  def menor_a(param)
    proc { |x|
      x < param
    }
  end

  def uno_de_estos(*params)
    if params.length == 1
      return proc { |x|
        params[0].any? x
      }
    end

    proc { |x|
      params.any? x
    }
  end

  def entender(param)
    proc do |x|
      x.respond_to? param, true
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
        true
      rescue # Other error
        false
      else # No error
        false
      end
    end
  end
end
