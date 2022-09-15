module Assertions

  def ser(param)
    if param.is_a? Object
      return param
    end
    false
  end

  def deberia(param)
    self.eql? param
  end
end


class Object
  include Assertions
end


