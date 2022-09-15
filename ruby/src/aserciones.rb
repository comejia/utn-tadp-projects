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
end


class Object
  include Assertions
end


