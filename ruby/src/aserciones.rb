module Assertions
  def deberia(param)
    param.call(self)
  end

  def ser(param)
    if param.is_a? Object
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
end


class Object
  include Assertions
end


