class Suite
  attr_accessor :name, :tests

  def initialize(name)
    @name = name
    @tests = []
  end

  def add_tests(tests)
    tests.each do |test|
      add_test(test)
    end
  end

  def add_test(test)
    @tests.push(Test.new(test))
  end

  def run_tests
    name.include SyntaxSugar
    context = name.new
    @tests.each do |test|
      test.execute context
    end
  end
end
