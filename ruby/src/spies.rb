class SpyMethod
  def initialize(method)
    @calls = 0
    @argsCalled = []
    @method = method
  end

  def spiedMethod(*args)
    @calls += 1
    @argsCalled << args
    @method.call *args
  end

  def veces(quantity)
    result = @calls.eql?(quantity)
    {
      result: result,
      description: result ? "." : "Esperaba que se llame #{quantity} veces y se llamo #{@calls}"
    }
  end

  def con_argumentos(*args)
    result = @argsCalled.any? do |argsCalled|
      args == argsCalled
    end
    {
      result: result,
      description: result ? "." : "No se llamo con estos #{args}"
    }
  end
end

class Command
  attr_accessor :prev

  def initialize(symbol, *args)
    @symbol = symbol
    @args = args
  end

  def call(elem)
    # TODO: improve spy check
    if elem.respond_to? :getMethod
      if @prev.nil?
        return elem.send(@symbol, *@args)
      end
      return @prev.call(elem).send(@symbol, *@args)
    end
    return false
  end

  private def method_missing(symbol, *args)
    c = Command.new(symbol, *args)
    c.prev = self
    return c
  end
end

module Spies
  refine Object do
    def espiar(obj)
      methods = obj.class.instance_methods(false)
      obj.singleton_class.class_eval do
        @@spyMethods = {}

        def self.setMethod(name, method)
          @@spyMethods[name] = method
        end

        def getMethod(*args)
          @@spyMethods[args[0]]
        end
      end
      methods.each do |method_name|
        m = obj.method(method_name)
        spyMethod = SpyMethod.new m
        obj.singleton_class.setMethod(method_name, spyMethod)
        obj.singleton_class.send(:define_method, method_name, &spyMethod.method(:spiedMethod))
      end
      return obj
    end

    def haber_recibido(symbol)
      Command.new(:getMethod, symbol)
    end
  end
end