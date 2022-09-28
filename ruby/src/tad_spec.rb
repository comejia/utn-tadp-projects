require_relative 'aserciones'
require_relative 'syntax_sugar'
require_relative 'suite'
require_relative 'test'

class TADSpec

  def self.testear(*params)
    @suites = []
    Object.include Assertions

    #suites = get_suites *params

    get_suites *params

    #results = []
    @suites.each do |suite|
      suite.run_tests
    end

    print_test_results @suites

  end

  def self.get_suites(*params)
    case params.length
    when 0
      suites = ObjectSpace.each_object(Class).select { |cls| is_suite?(cls) }
      suites.each do |suite|
        new_suite = Suite.new suite
        new_suite.add_tests(get_tests suite)
        @suites.push(new_suite)
      end
    when 1
      suite = Suite.new params.first
      suite.add_tests(get_tests params.first)
      @suites.push(suite)
    else
      suite = Suite.new params.first
      tests = params.drop(1).map do |test|
        test.to_s.prepend("testear_que_").to_sym
      end
      suite.add_tests(tests)
      @suites.push(suite)
    end
  end

  def self.run_tests(suite)
    #puts suite
    #results = []
    #tests = self.get_tests suite
    suite.name.include SyntaxSugar
    suite_obj = suite.name.new

    suite.tests.each do |test|
      #results << { suite_name: suite, test_name: test, status: suite_obj.send(test.to_sym) }
      test.status = suite_obj.send(test.name)
    end
    #results
  end

  def self.get_tests(suite)
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
    results.each do |result|
      puts result
    end
  end

end
