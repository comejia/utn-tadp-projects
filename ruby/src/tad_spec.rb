require_relative 'aserciones'
require_relative 'syntax_sugar'

class TADSpec

  def self.testear(*params)
    Object.include Assertions

    suites = get_suites *params

    results = []
    suites.each do |suite|
      suite.include SyntaxSugar
      results.append(run_tests suite)
    end

    print_test_results results

  end

  def self.get_suites(*params)
    case params.length
    when 0
      ObjectSpace.each_object(Class).select { |cls| is_suite?(cls) }
    else
      [params.first]
    end
  end

  def self.run_tests(suite)
    results = []
    tests = self.get_suite_tests suite
    suite_obj = suite.new

    tests.each do |test|
      results << { suite_name: suite, test_name: test, status: suite_obj.send(test.to_sym) }
    end
    results
  end

  def self.get_suite_tests(suite)
    methods = suite.instance_methods false
    methods.filter { |method| is_test?(method) }
  end

  def self.is_suite?(suite)
    suite.instance_methods(false).any? { |method| is_test?(method) }
  end

  def self.is_test?(method)
    method.to_s.start_with?('testear_que')
  end

  def self.print_test_results(results)
    puts results
  end

end
