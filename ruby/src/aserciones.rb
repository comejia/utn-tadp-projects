module Assertions

  def ser(param)
    if param.is_a? Object
      return param
    end
    false
  end

  def deberia(param)
    puts self.eql? param
  end
end


class Object
  include Assertions
end


