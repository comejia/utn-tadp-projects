require_relative 'aserciones'
require_relative 'syntax_sugar'
require_relative 'suite'
require_relative 'test'
require_relative 'report'

class TADSpec

  def self.testear(*params)
    @suites = []
    Object.include Assertions

    load_suites *params

    @suites.each do |suite|
      suite.run_tests
    end

    report = Report.new @suites
    report.print
  end

  def self.load_suites(*params)
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

end
