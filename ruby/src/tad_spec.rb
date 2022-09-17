require_relative 'aserciones'

class TADSpec

  def self.test(param)
    Object.include Assertions

    param.include SyntaxSugar

    results = self.run_tests param

    print_test_results results

  end

  def self.run_tests(suite)
    results = []
    tests = self.get_suite_tests suite
    suite_obj = suite.new

    tests.each do |t|
      results << { test_name: t, status: suite_obj.send(t.to_sym) }
    end
    results
  end

  def self.get_suite_tests(suite)
    methods = suite.instance_methods false
    methods.filter { |m| m.to_s.start_with?('testear_que') }
  end

  def self.print_test_results(results)
    puts results
  end

end

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
