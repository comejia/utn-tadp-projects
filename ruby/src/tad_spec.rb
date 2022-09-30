require_relative 'aserciones'
require_relative 'syntax_sugar'
require_relative 'suite'
require_relative 'test'
require_relative 'report'

class TADSpec

  def self.testear(suite = nil, *tests)
    @suites = []
    Object.include Assertions

    load_suites suite, tests

    @suites.each do |suite|
      suite.run_tests
    end

    report = Report.new @suites
    report.print
  end

  def self.load_suites(suite, tests)
    if suite.nil?
      suites = ObjectSpace.each_object(Class).select { |cls| is_suite?(cls) }
      suites.each do |suite|
        new_suite = Suite.new suite
        new_suite.add_tests(get_tests suite)
        @suites.push(new_suite)
      end
    else
      suite_instance = Suite.new suite
      if tests.empty?
        tests = get_tests suite
      else
        tests = tests.map do |test|
          test.to_s.prepend("testear_que_").to_sym
        end
      end
      suite_instance.add_tests(tests)
      @suites.push(suite_instance)
    end
  end

  def self.get_tests(suite)
    methods = suite.instance_methods
    methods.filter { |method| is_test?(method) }
  end

  def self.is_suite?(suite)
    suite.instance_methods.any? { |method| is_test?(method) }
  end

  def self.is_test?(method)
    method.to_s.start_with?('testear_que')
  end

end
